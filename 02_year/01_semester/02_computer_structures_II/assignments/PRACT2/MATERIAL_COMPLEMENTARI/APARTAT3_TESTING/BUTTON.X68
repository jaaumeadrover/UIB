; =============================================================================
; TITLE    : BUTTON MANAGEMENT
; AUTHOR   : ANTONI BURGUERA
; HISTORY  : 05-JULY-2021 - CREATION
; COMMENTS : * A BUTTON IS DEFINED BY A STATIC DATA BLOCK (SDB), WHICH DOES NOT
;              CHANGE OVER TIME, AND A VARIABLE DATA BLOCK (VDB), THAT CAN
;              CHANGE OVER TIME. CHANGES IN THE VDB ARE PRODUCED WHEN CALLING
;              SUBROUTINE BTNUPD.
;
;            * THE BUTTON SDB (CAN BE IN ROM) HAS THE FOLLOWING FIELDS. THE
;              NUMBER DENOTES THE OFFSET WITH RESPECT TO THE SDB START:
;
;                +0  - WORD - YTOP COORDINATE (PIXELS)
;                +2  - WORD - XLEFT COORDINATE (PIXELS)
;                +4  - WORD - HEIGHT (PIXELS)
;                +6  - WORD - WIDTH (PIXELS)
;                +8  - LONG - CALLBACK POINTER
;                +12 - BYTE STRING - ZERO TERMINATED STRING. TEXT TO SHOW.
;
;            * THE BUTTON VDB (MUST BE IN RAM) IS:
;
;                +0 - LONG - POINTER TO THE STATIC DATABLOCK
;                +4 - BYTE - STATUS WITH FORMAT XXXXXCPI WHERE:
;                            - I (BIT 0) - 1=MOUSE INSIDE, 0=MOUSE OUTSIDE
;                            - P (BIT 1) - 1=MOUSE PRESSED INSIDE
;                                          0=MOUSE NOT PRESSED INSIDE
;                            - C (BIT 2) - 1=MOUSE CLICKED INSIDE
;                                          0=MOUSE NOT CLICKED INSIDE
;                            NOTE THAT IF I=0 THEN P=C=0, IF I=1 AND P=0
;                            THEN C=0.
; =============================================================================

; -----------------------------------------------------------------------------
BTNINIT
; INITIALIZES THE BUTTON BY:
;  * COPYING THE SDB POINTER INTO THE VDB.
;  * CLEARING THE STATUS BYTE.
; INPUT    : A0 - POINTER TO THE VARIABLE DATABLOCK
;            A1 - POINTER TO THE STATIC DATABLOCK
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------

; TODO : PLACE YOUR CODE HERE
            MOVE.L  A1, (A0) ;FICAM EL SDB A VDB
            CLR.B   4(A0)    ;CLEAR STATUS BYTE
            RTS
            
; -----------------------------------------------------------------------------
BTNUPD
; UPDATES THE BUTTON VDB AND EXECUTES THE CALLBACK IF REQUIRED
; INPUT    : A0 - POINTER TO THE VARIABLE DATABLOCK
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------

; TODO : PLACE YOUR CODE HERE
            MOVEM.L D0-D2/A1, -(A7)
            MOVE.L  (A0), A1                ;OBTENIM EL SDB DEL VDB
            MOVE.W  2(A1), D0               ;OBTENIM XLEFT DE SDB
            MOVE.W  6(A1), D1               ;OBTENIM EL WIDTH DE SDB
            CMP.W   (MOUX), D0              ;COMPROVAM EL COMENÇAMENT DEL BOTO X
            BGT     .NO                     ;NO DINS BOTO
            ADD.W   D0, D1                  ;AMPLADA DEL BOTO
            CMP.W   (MOUX), D1              ;COMPROVAM EL FINAL DEL BOTO X
            BLT     .NO                     ;NO DINS BOTO
            MOVE.W  (A1), D0                ;UPPERY
            MOVE.W  4(A1), D1               ;HEIGHT
            CMP.W   (MOUY), D0              ;COMPROVAM EL COMENÇAMENT DEL BOTO Y
            BGT     .NO                     ;NO DINS BOTO
            ADD.W  D0, D1                   ;ALTURA BOTO
            CMP.W   (MOUY), D1              ;COMPROVAM EL FINAL DEL BOTO Y
            BLT     .NO                     ;NO DINS BOTO
            MOVE.B  4(A0), D0               ;OBTENIM L'STATUS BYTE
            
            ;COMPROVAM EL VALOR DEL BIT I I EL MODIFICAM
            ORI.B   #$01, D0                ;POSAP I A 1  
         
            ;COMPROVAM EL VALOR DEL BIT P I EL MODIFICAM
            MOVE.B  (MOUVAL), D1            ;OBTENIM EL VALOR ACTUAL DEL MOUSE
            BTST.L  #0, D1                  ;MIRAM SI S'HA PITJAT EL BOTO ESQUERRA
            BEQ     .CLRP                   ;NO PTJAT
            ORI.W   #$02, D0                ;POSAM P A 1
            BRA     .CHECKC    
.CLRP       ANDI.B  #$0D, D0                ;CLEAR DE P
.CHECKC

            ;COMPROVAM EL VALOR DEL BIT C I EL MODIFICAM
            MOVE.B  (MOUEDGE), D1
            BTST.L  #0, D1                  ;CLICAT?
            BEQ     .CLEARC
            ORI.W   #$04, D0                ;POSAM C A 1
            MOVE.L  8(A1), D2               ;OBTENIM EL CALLBACK
            MOVE.L  D2, A1
            JSR     (A1)                    ;JSR CAP A LA DIRECCIO DEL CALLBACK
            BRA     .SETC
.CLEARC     ANDI.B  #$0B, D0                ;CLEAR DE C    
.SETC
            BRA     .END                  
.NO         MOVE.B  4(A0), D0               ;OBTENIM L'STATUS BYTE
            ANDI.B  #$00, D0                ;CLEAR DE C P I 
.END        MOVE.B  D0, 4(A0)
            MOVEM.L (A7)+, D0-D2/A1
            RTS

; -----------------------------------------------------------------------------
BTNPLOT
; PLOTS THE BUTTON. A RECTANGLE IS DRAWN WITH THICKNESS BTNPENWD. THE CONTOUR
; COLOR IS BTNPENCL. THE FILL COLOR IS BLACK IF THE MOUSE IS NOT INSIDE,
; BTNSELCL IF THE MOUSE IS INSIDE BUT THE MOUSE BUTTON IS NOT PRESSED AND
; BTNPRSCL IF THE MOUSE BUTTON IS PRESSED INSIDE.
; INPUT    : A0 - POINTER TO THE VARIABLE DATABLOCK
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------

; TODO : PLACE YOUR CODE HERE
            MOVEM.L D0-D5/A1, -(A7)
            
            MOVE.W #93,D0
            MOVE.B #BTNPENWD,D1
            TRAP #15            ;ESTABLIB LA GRUIXADA DEL CONTORN A BTNPENDWD

            MOVE.W #80,D0
            MOVE.L #BTNPENCL,D1
            TRAP #15            ;ESTABLIB EL COLOR DE DIBUIX A BTNPENCL
            
            ADDQ.W #1,D0        ;SUMAM 80+1=81
            MOVE.B 4(A0),D5
            BTST.L #1,D5        ;MIRAR BIT P
            BEQ .NOTPRESSED
            ;CAS PITJAT
            BTST.L #0,D5
            BEQ .NOTINSIDE
            ;PITJAT, DEDINS
            MOVE.L #BTNPRSCL,D1
            JMP .SYSTEM_CALL
.NOTPRESSED
            BTST.L #0,D5
            BEQ .NOTINSIDE
            MOVE.L #BTNSELCL,D1
            JMP .SYSTEM_CALL
            

.NOTINSIDE
            MOVE.L #CLRBLACK,D1
            
.SYSTEM_CALL
            TRAP #15
            
.DRAW_RECT  
            MOVE.B #87,D0
            MOVE.L (A0),A1      ;SDB
            MOVE.W 2(A1),D1     ;XLEFT TO D1
            MOVE.W (A1),D2      ;YTOP  TO D2
            MOVE.W 6(A1),D3     ;WIDTH TO D3
            ADD.W  D1,D3        ;XLEFT+WIDTH TO D3
            MOVE.W 4(A1),D4     ;YTOP  TO D4
            ADD.W  D2,D4        ;YTOP-HEIGHT TO D4
            TRAP #15            
            
            ADDA.L #12,A1              
            JSR     UTLSTRLN                
            
            SUB.W   D1, D3                  ;RESTABLIM EL VALOR HEIGTH      
            SUB.W   D2, D4                  ;RESTABLIM EL VALOR WIDTH
            
            ;COORDENADAX=LEFTX+(WIDTH-(STRLEN*CHRWDTH)/2
            MULU.W  #CHRWIDTH, D0           ;LONGITUD PARAULA*PIXELES D'UNA LLETRA
            SUB.W   D0, D3                   
            DIVS    #2, D3                  
            ADD.W   D3, D1                  
            
            ;COORDENADAY=UPPERY+(HEIGHT-(STRLEN*CHRHEIGHT)/2
            MOVE.W  #CHRHEIGH, D0
            SUB.W   D0, D4                  ;D4(YHEIGHT)-D0(STRLEN*CHRHEIGHT)
            DIVS    #2, D4
            ADD.W   D4, D2                  
            
            MOVE.B  #95, D0                 
            TRAP    #15               
            MOVEM.L (A7)+, D0-D5/A1           
            RTS 
*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
