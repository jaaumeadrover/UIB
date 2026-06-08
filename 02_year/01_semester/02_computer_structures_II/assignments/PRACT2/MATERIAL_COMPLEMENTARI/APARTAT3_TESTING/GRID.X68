; =============================================================================
; TITLE    : GRID MANAGEMENT
; AUTHOR   : ANTONI BURGUERA
; HISTORY  : 05-JULY-2021 - CREATION
; COMMENTS : * THERE ARE TWO MATRICES (GRDMATR0 AND GRDMATR1) AND TWO POINTERS
;              TO THESE MATRICES (GRDSRC AND GRDDST).
;
;            * THE MATRIX TO BE PLOTTED IS ALWAYS THE ONE POINTED BY GRDDST.
;
;            * THE MATRIX TO BE UPDATED BY THE MOUSE (GRDMUPD) IS ALWAYS THE
;              ONE POINTED BY GRDDST.
;
;            * THE RULES-UPDATE (GRDRUPD) SWITCHES THE POINTERS AND THEN READS
;              THE NEW SOURCE MATRIX (GRDSRC) TO DETERMINE THE NUMBER OF
;              NEIGHBORS. THE RESULTING CELL VALUE IS WRITTEN TO THE DESTINA-
;              TION MATRIX, POINTED BY GRDDST.
; =============================================================================

; -----------------------------------------------------------------------------
GRDINIT
; INITIALIZE THE GRID AS FOLLOWS:
; * POINTS THE SOURCE GRID POINTER TO GRDMATR0.
; * POINTS THE DESTINATION GRID POINTER TO GRDMATR1.
; * RESETS THE COUNTERS (GRDNPLR1, GRDNPLR2, GRDNGEN).
; * FILLS THE TWO MATRICES (GRDMATR0 AND GRDMATR1) WITH ZEROS.
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------
            MOVEM.L A0/D0,-(A7)
            MOVE.L  #GRDMATR0,(GRDSRC)
            MOVE.L  #GRDMATR1,(GRDDST)
            CLR.W   (GRDNPLR1)
            CLR.W   (GRDNPLR2)
            CLR.W   (GRDNGEN)
            LEA     GRDMATR0,A0
            MOVE.W  #GRDWIDTH*GRDHEIGH*2-1,D0
.LOOP       CLR.B   (A0)+
            DBRA.W  D0,.LOOP
            MOVEM.L (A7)+,A0/D0
            RTS

; -----------------------------------------------------------------------------
GRDMUPD
; GRID UPDATE USING MOUSE. THE INTERFACE IS AS FOLLOWS:
; * IF MOUSE IS NOT PRESSED OR IS OUTSIDE THE PLOTTED GRID, DOES NOTHING.
; * IF LEFT MOUSE BUTTON IS PRESSED INSIDE THE GRID:
;   - A PLAYER 1 (VALUE=1) CELL IS CREATED AT THE MOUSE POSITION IF THE MOUSE
;     IS ON THE LEFT HALF OF THE GRID.
;   - A PLAYER 2 (VALUE=2) CELL IS CREATED AT THE MOUSE POSITION IF THE MOUSE
;     IS ON THE RIGHT HALF OF THE GRID.
; * IF RIGHT MOUSE BUTTON IS PRESSED INSIDE THE GRID, THE CELL WHERE THE MOUSE
;   IS IS CLEARED (VALUE=0).
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------

; TODO : PLACE YOUR CODE HERE

*REGISTRES UTILITZATS:
 *D0: Màscara left/right click,coordsX(columnes)
 *D1:
 *D2:
 *D3:
 
            MOVEM.L D0-D3/A0, -(A7)         ;GUARDAM REGISTRES           
            
;-----------------------COMPROVAM SI EL RATOLÍ HA ESTAT PITJAT----------------
            MOVE.B  #MOUMSKLF+MOUMSKRT, D2
            MOVE.B  (MOUVAL), D1
            AND.B   D2, D1                  ;MÀSCARA QUE DETECTA EL RATOLÍ
            CMP.B   #0,D1
            BEQ     .FINAL                  ;SI NO PITJAT->FINAL
            
;-----------------------COMPROVAM SI EL RATOLÍ ESTÀ DINS LA MATRIU-------------
            CMP     #GRDVEMRG, (MOUY)
            BLT     .FINAL                  
            MOVE.W  #GRDVEMRG, D0
            MULU    #GRDWIDTH, D0
            CMP     (MOUY), D0              
            BLE     .FINAL
;-----------------------ACTUALITZAM LA MATRIU----------------------------------
            MOVE.L  (GRDDST), A0            ;APUNTAM A LA MATRIU DESTÍ
*            LEA     GRDDST,A0
            MOVE.W  #GRDWIDTH, D0           ; PUT NR OF COLUMNS IN D0
            MOVE.W  (MOUY), D3              ; GET MOUSE POSITION
            SUB.W   #GRDYOFF, D3            ; INITIAL Y
            DIVS.W  #GRDSZCEL, D3           ; GET Y OFFSET                     
            MULU.W  D3, D0
            MOVE.W  (MOUX), D2              
            SUB.W   #GRDXOFF, D2            ; INITIAL X
            DIVS.W  #GRDSZCEL, D2           ; GET X OFFSET
            ADD.W   D2, D0
            BTST.L  #MOUMSKLF, D1           ; IS RIGHT CLICK?
            BNE     .ESBORRAR
            CMP.W   #GRDWIDTH/2, D2        ; CHECK IF PLR1 OR PLR2
            BGE     .PLR2
            MOVE.B  #1,(A0, D0)
            JMP     .FINAL
            
.PLR2       
            MOVE.B  #2, (A0, D0)
            JMP     .FINAL
            
.ESBORRAR  
            MOVE.B  #0, (A0, D0)
;----------------------------FINAL SUBRUTINA-----------------------------------
.FINAL      
            MOVEM.L (A7)+, D0-D3/A0         ;RESTAURAM REGISTRES
            RTS

; -----------------------------------------------------------------------------
GRDRUPD
; UPDATES THE GRID ACCORDING TO 2-PLAYER GAME OF LIFE RULES AS FOLLOWS:
; * ADDS 1 TO THE NUMBER OF GENERATIONS (GRDNGEN)
; * SWITCHES THE MATRIX POINTERS (GRDDST AND GRDSRC).
; * CHECKS THE NEIGHBORS USING THE NEW SOURCE MATRIX (POINTER BY GRDDRC)
; * THE RESULTS OF APPLYING THE RULES ARE WRITTEN INTO THE NEW DESTINATION
;   MATRIX (POINTED BY GRDDST).
; * THE RULES ARE THE FOLLOWING, WHERE P1 AND P2 ARE 1 AND 2 RESPECTIVELY,
;   DEAD CELLS ARE REPRESENTED BY 0 AND NP1 AND NP2 DENOTE THE NUMBER OF
;   PLAYER 1 AND PLAYER 2 NEIGHBORS RESPECTIVELY:
;   + SURVIVAL RULES
;     - IF CELL IS P1 AND NP1-NP2 IS 2 OR 3 -> NEW VALUE IS P1
;     - IF CELL IS P1 AND NP1-NP2 IS 1 AND NP1>=2 -> NEW VALUE IS P1
;     - OTHERWISE -> NEW VALUE IS 0
;     - IF CELL IS P2 AND NP2-NP1 IS 2 OR 3 -> NEW VALUE IS P2
;     - IF CELL IS P2 AND NP2-NP1 IS 1 AND NP2>=2 -> NEW VALUE IS P2
;     - OTHERWISE -> NEW VALUE IS 0
;   + BIRTH RULES
;     - THE CELL HAS EXACTLY THREE P1 NEIGHBOURS AND THE NUMBER
;       OF P2 NEIGHBOURS IS DIFFERENT FROM THREE. IN THIS CASE A
;       P1 TOKEN IS BORN IN THE CELL.
;     - THE CELL HAS EXACTLY THREE P2 NEIGHBOURS AND THE NUMBER
;       OF P1 NEIGHBOURS IS DIFFERENT FROM THREE. IN THIS CASE A
;       P2 TOKEN IS BORN IN THE CELL.
;     - THE CELL HAS EXACTLY THREE WHITE AND THREE BLACK NEIGHBOURS.
;       IN THIS CASE AN UNBIASED COIN DETERMINES WHETHER A WHITE OR
;       BLACK TOKEN IS BORN IN THE CELL.
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------

; TODO : PLACE YOUR CODE HERE
            MOVEM.L D0-D4/D7/A0-A4, -(A7)   ;GUARDAM REGISTRES
            ADDQ.W  #1,GRDNGEN              ;AUGMENTAM CONTADOR DE GENERACIONS
;---------------------------INTERCANVIAM PUNTERS DE MATRIU---------------------            
            MOVE.L  (GRDSRC), A0
            MOVE.L  (GRDDST), A1
            MOVE.L  A0, (GRDDST)
            MOVE.L  A1, (GRDSRC)
            ;DEBUGGER
            ;A1->LLEGIR
            MOVE.L  (GRDDST), A0
            ;A0->ESCRIURE
;--------------------------APUNTAM A LES MATRIUS ORIGEN I DESTÍ----------------
*            LEA     GRDSRC,A0               ;A0->MATRIU ORIGEN
*            LEA     GRDDST,A1               ;A1->MATRIU DESTÍ

;---------------------------BUCLE DE RECORREGUT--------------------------------

            CLR.W   D0
            
.MAIN_LOOP

            JMP     .CHECK_VEINATS          ;COMPROVAM VEINATS
.RULES
            JMP     .NORMES                 ;COMPROVAM NORMES            
            
.SEGUENT            
            
            ADDQ.W  #1,D0
            CMP.W   #GRDWIDTH*GRDHEIGH,D0
            BNE     .MAIN_LOOP
            ;FINAL BUCLE
            ; RETURN SUBRUTINE, WE FINISHED
            
            
            
.ENDUPD     
            MOVEM.L (A7)+, D0-D4/D7/A0-A4   
            RTS      
         
         
         
         
;----------------------------COMPROVAM ELS VEINATS DE LA CASELLA---------------


.CHECK_VEINATS
            MOVEM.L D0-D3,-(A7)
            ;OBTENIM FILA I COLUMNES
            MOVE.W  D0,D1
            MOVE.W  D0,D2
            LSR.W   #GRDSHWID,D1            ;D1-->FILES(DIVISIÓ ENTERA)
            AND.W   #GRDMSWID,D2            ;D2-->COLUMNES(MÒDUL AMB AND)
            
            ;RESETEAM CONTADOR VEINATS
            CLR.W   D4                      ;RESET NP1
            CLR.W   D5                      ;RESET NP2
            
            ;APUNTAM AL VECTOR DE CASELLES VEINAT
            LEA     MOVEMENTX,A2            ;A2->col
            LEA     MOVEMENTY,A3            ;A3->row
            
            ;NOMBRE DE ITERACIONS DINS D3
            MOVE.W  #NMOVEMENTS,D3
            SUBQ.W  #1,D3                   ;D3-->CONTADOR
            
   ;----------------------------BUCLE RECORREGUT VEINATS-----------------------            
.LOOP
            MOVEM.L D0-D2,-(A7)             ;GUARDAM POSICIÓ INICIAL(D0,D1,D2)
            ADD.W   (A2)+,D2                ;D2->COLUMNA ACTUALITZADA
            AND.W   #GRDMSWID,D2            ;MÒDUL 64->COLUMNA ACTUALITZADA
            ADD.W   (A3)+,D1                ;D3->FILA ACTUALITZADA
            AND.W   #GRDMSHEI,D1            ;mòdul 32->
            
            LSL.W   #GRDSHWID,D1            ;MULTIPLICAM D1 PER 64--->D1:recorregut de files
            CLR.W   D7
            ADD.W   D1,D7                   
            ADD.W   D2,D7                   ;D0--->RECORREGUT TOTAL
            
            MOVE.B  (A1,D7),D6              ;D6->VALOR DEL VEINAT
            CMP.B   #1,D6              
            BGT     .VEINAT_P2              ;>1--->JUGADOR 2
            CMP.B   #0,D6
            BEQ     .PROXIM                 ;SI ESTA BUIT
            ADDQ.W  #1,D4                   ;AUGMENTAM CONTADOR NP1
.PROXIM            
            CLR.B   D6                      RESETEAM VALOR VEINAT GUARDAT
            CLR.W   D7
            MOVEM.L (A7)+,D0-D2             ;RESTAURAM POSICIÓ INICIAL(D0,D1,D2)
            DBRA    D3,.LOOP

  ;----------------------------------------------------------------------------

            MOVEM.L (A7)+,D0-D3
            JMP     .RULES
            ;SALTA A COMPROVAR REGLES
            
            
.VEINAT_P2
            ADDQ.W  #1,D5                   ;AUGMENTAM CONTADOR NP2
            JMP     .PROXIM
            
            
            
            
            
            
;**************************** ALL CELLS CHECKED *******************************     
         
.NORMES     ; WE HAVE NP1 IN D0 AND NP2 IN D1
            MOVE.B  0(A1,D0), D6             ; GET VALOR
            CMP.B   #2, D6                  ; IS PLR2 CELL?
            BNE     .CHECKPL1

;---------------------------------NORMES JUGADOR 2-----------------------------
            MOVEM.L D4-D5, -(A7)
            MOVE.L  D4, D7                  ; STORE NP2
            SUB.B   D4, D5                  ; NP2-NP1
            CMP.B   #3, D5                  ; IS IT 3?
            BNE     .PLR2IS2
            ; ALREADY A P2 CELL, WE DO NOTHING
            BRA     .P2END
.PLR2IS2    
            CMP.B   #2, D5                  ; OR IS IT 2
            BNE     .PLR2IS1
            ; ALREADY A P2 CELL, WE DO NOTHING 
            BRA     .P2END  
.PLR2IS1    
            CMP.B   #1, D5                  ; IF IS 1 
            BNE     .PLR2IS0                
            CMP.B   #2, D7                  ; NP2 >= 2?
            BLT     .PLR2IS0
            ; ALREADY A P2 CELL, WE DO NOTHING     
            BRA     .P2END   
.PLR2IS0    
            CLR.B   D6 
.P2END      
            MOVEM.L (A7)+, D4-D5
            BRA     .NEXT                   ; GO TO NEXT CELL

;-------------------------------NORMES JUGADOR 1-------------------------------           
.CHECKPL1   
            CMP.B   #1, D6
            BNE     .EMPTY
            ; PLR1 RULES
            MOVEM.L D4-D5/D7, -(A7)
            MOVE.L  D4, D7                  ;GUARDAM NP1
            SUB.B   D5, D4                  ; NP1-NP2
            CMP.B   #3, D4                  ; IS IT 3?
            BNE     .PLR1IS2
            ; ALREADY A P1 CELL, WE DO NOTHING
            BRA     .P1END
.PLR1IS2    CMP.B   #2, D4                  ; OR IS IT 2
            BNE     .PLR1IS1
            ; ALREADY A P1 CELL, WE DO NOTHING 
            BRA     .P1END  
.PLR1IS1    CMP.B   #1, D7                  ; IF IS 1 
            BNE     .PLR1IS0                
            CMP.B   #2, D7                  ; NP1>=2?
            BLT     .PLR1IS0
            ; ALREADY A P1 CELL, WE DO NOTHING     
            BRA     .P1END   
.PLR1IS0    
            CLR.B   D6 
.P1END      
            MOVEM.L (A7)+, D4-D5/D7
            BRA     .NEXT
;---------------------------------NORMES CASELLA BUIDA-------------------------
.EMPTY      
            MOVE.L  D5, -(A7)
            OR.B    D4, D5                  ; SEE IF ANY VALUE IN BOTH
            TST.B   D5
            BEQ     .NONG                   ; NO NEIGHBOURS
            MOVE.L  (A7)+, D5
            
            MOVEM.L D5, -(A7)               ; SAVE COUNTERS
            AND.B   D4, D5                  ; IF D1=3, NP1=NP2
            CMP.B   #3, D5
            BNE     .NORAND                 ; CHECK OTHER POSSIBILITIES
            JSR     UTLRAND
            MOVE.B  D1, D6
            MOVEM.L (A7)+, D5
            
            BRA     .NEXT
.NORAND     
            MOVEM.L (A7)+, D5
            CMP.B   #3, D5                  ; HAS PL2 EXACTLY 3 NEIGHBOURS?
            BNE     .CHECKP1                ; IF NOT CHECK PL1
            CMP.B   #3, D4                  ; IS NP1 != 3?
            BEQ     .CHECKP1
            MOVE.B  #2, D6                  ; NEW PL2 CELL IS BORN
            BRA     .NEXT
.CHECKP1    
            CMP.B   #3, D4                  ; HAS PL1 EXACTLY 3 NEIGHBOURS?
            BNE     .NEXT                   ; NO CELL IS BORN
            CMP.B   #3, D5                  ; IS NP2 != 3?
            BEQ     .NEXT
            MOVE.B  #1, D6                  ; NEW PL1 CELL IS BORN
.NEXT       
            MOVE.B  D6, (A0,D0)             ;ESCRIPTURA MATRIU DESTÍ
            BRA     .SEGUENT
 
.NONG       
            MOVE.B  D6,(A0,D0)
            MOVE.L  (A7)+, D5
            BRA     .SEGUENT









            
            
MOVEMENTX   DC.W    $FFFF,0,1,$FFFF,1,$FFFF,0,1   ;MOVIMENT PER COLUMNES

MOVEMENTY   DC.W    $FFFF,$FFFF,$FFFF,0,0,1,1,1   ;MOVIMENT PER FILES

NMOVEMENTS  EQU    8                      ;NOMBRE DE VEINATS POSSIBLES
      
; -----------------------------------------------------------------------------
GRDSAVE
; SAVES THE GRID INTO A FILE IN CSV FORMAT
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; NOTE     : THE LOCAL BUFFER SIZE MUST BE MAX(512,2*GRDWIDTH+1). PLEASE CHANGE
;            IT ACCORDINGLY IF GRDWIDTH*2+1 IS LARGER THAN 512.
; -----------------------------------------------------------------------------
            MOVEM.L D0-D7/A0-A6,-(A7)       ; TO BE OPTIMIZED
            MOVE.B  #50,D0                  ; CLOSE ALL FILES (RECOMMENDED)
            TRAP    #15
            SUB.L   #512,A7                 ; ROW BUFFER IN LOCAL MEMORY
            MOVE.B  #58,D0                  ; SHOW FILE DIALOG TASK
            MOVE.L  #1,D1                   ; DISPLAY SAVE DIALOG
            LEA     .MSG,A1                 ; STANDARD MESSAGE
            LEA     .FILTER,A2              ; NO FILTER
            MOVE.L  A7,A3                   ; FILE NAME BUFFER
            CLR.W   (A3)                    ; ENSURE THERE IS NO STRING
            TRAP    #15
            TST.B   D1
            BEQ     .END                    ; IF CANCELLED, DO NOTHING
            MOVE.L  A3,A1                   ; PLACE THE FILENAME INTO A1
            MOVE.B  #52,D0                  ; CREATE NEW FILE
            TRAP    #15
            TST.W   D0                      ; CHECK CREATION ERROR
            BNE     .END                    ; DO NOTHING MORE IF ERROR
            MOVE.L  A7,A6                   ; PUT A6 INTO START OF BUFFER
            MOVE.L  A7,A1                   ; PUT A1 INTO START OF BUFFER
            MOVE.L  (GRDDST), A0            ; A0: POINTER TO THE DATA TO WRITE
            MOVE.W  #GRDHEIGH-1,D3          ; D3: NUMBER OF ROWS - 1
.LOOPY      MOVE.W  #GRDWIDTH-1,D2          ; D2: NUMBER OF COLUMNS - 1
.LOOP       MOVE.B  #'0',D0                 ; ADD CHAR '0' AND THE CELL VALUE
            ADD.B   (A0)+,D0
            MOVE.B  D0, (A6)+               ; STORE IT IN THE BUFFER
            MOVE.B  #',',(A6)+              ; NOW PUT THE COMMA
            DBRA.W  D2,.LOOP
            MOVE.B  #$0D,-1(A6)             ; STORE CARRIAGE RETURN (REMOVE ',')
            MOVE.B  #$0A,(A6)               ; STORE LINE FEED
            MOVE.B  #54,D0                  ; WRITE TO FILE TASK NUMBER
            MOVE.L  #GRDWIDTH*2+1,D2        ; NUMBER OF BYTES TO WRITE
            TRAP    #15                     ; WRITE (A1 IS ALREADY THERE)
            MOVE.L  A1,A6                   ; GO BACK TO THE BUFFER START
            DBRA.W  D3,.LOOPY
            MOVE.B  #56,D0                  ; CLOSE FILE
            TRAP    #15
.END        ADD.L   #512,A7                 ; RESTORE STACK
            MOVEM.L (A7)+,D0-D7/A0-A6       ; TO BE OPTIMIZED
            RTS
.MSG        DC.B    'SELECT A CSV FILE TO SAVE',0
.FILTER     DC.B    '*.CSV',0
            DS.W    0

; -----------------------------------------------------------------------------
GRDLOAD
; LOADS THE GRID FROM A CSV FORMATTED FILE
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; NOTE     : THE LOCAL BUFFER SIZE MUST BE MAX(512,2*GRDWIDTH+1). PLEASE CHANGE
;            IT ACCORDINGLY IF GRDWIDTH*2+1 IS LARGER THAN 512.
; -----------------------------------------------------------------------------
            MOVEM.L D0-D4/A0-A3/A6,-(A7)    ; TO BE OPTIMIZED
            MOVE.B  #50,D0                  ; CLOSE ALL FILES (RECOMMENDED)
            TRAP    #15
            SUB.L   #512,A7                 ; ROW BUFFER IN LOCAL MEMORY
            MOVE.B  #58,D0                  ; SHOW FILE DIALOG TASK
            CLR.L   D1                      ; DISPLAY LOAD DIALOG
            LEA     .MSG,A1                 ; STANDARD MESSAGE
            LEA     .FILTER,A2              ; NO FILTER
            MOVE.L  A7,A3                   ; FILE NAME BUFFER
            CLR.W   (A3)                    ; ENSURE THERE IS NO STRING
            TRAP    #15
            TST.B   D1
            BEQ     .END                    ; IF CANCELLED, DO NOTHING
            MOVE.L  A3,A1                   ; PLACE THE FILENAME INTO A1
            MOVE.B  #51,D0                  ; OPEN EXISTING FILE
            TRAP    #15
            TST.W   D0                      ; CHECK ERRORS
            BNE     .END                    ; IF ERRORS, DO NOTHING MORE
            MOVE.L  A7,A6                   ; PUT A6 INTO START OF BUFFER
            MOVE.L  A7,A1                   ; PUT A1 INTO START OF BUFFER
            MOVE.L  (GRDDST), A0            ; POINTER TO STORE PROCESS. DATA
            MOVE.W  #GRDHEIGH-1,D3
.LOOP       MOVE.L  A6,A1
            MOVE.B  #53,D0                  ; READ DATA TASK NUMBER
            MOVE.L  #GRDWIDTH*2+1,D2        ; NUMBER OF BYTES TO READ
            TRAP    #15                     ; READ THE DATA
            MOVE.W  #GRDWIDTH-1,D2
.COPY       MOVE.B  (A1)+,D0                ; PICK ONE LOADED CHARACTER
            SUB.B   #'0',D0                 ; CONVERT FROM ASCII TO NUMBER
            MOVE.B  D0,(A0)+                ; STORE DATA
            ADDQ.L  #1,A1                   ; SKIP THE COMMA
            DBRA.W  D2,.COPY
            DBRA.W  D3,.LOOP
            MOVE.B  #56,D0                  ; CLOSE FILE
            TRAP    #15
.END        ADD.L   #512,A7                 ; RESTORE STACK
            MOVEM.L (A7)+,D0-D4/A0-A3/A6    ; TO BE OPTIMIZED
            RTS
.MSG        DC.B    'SELECT A CSV FILE TO LOAD',0
.FILTER     DC.B    '*.CSV',0
            DS.W    0

; -----------------------------------------------------------------------------
GRDLLEFT
; LOADS THE LEFT PART OF THE GRID FROM A CSV FORMATTED FILE
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; NOTE     : THE LOCAL BUFFER SIZE MUST BE MAX(512,2*GRDWIDTH+1). PLEASE CHANGE
;            IT ACCORDINGLY IF GRDWIDTH*2+1 IS LARGER THAN 512.
; NOTE     : ALL THE ACTIVE CELLS ARE STORED AS PLAYER 1 INDEPENDLY OF THEIR
;            ACTUAL VALUE.
; -----------------------------------------------------------------------------

; TODO : PLACE YOUR CODE HERE
            MOVEM.L D0-D4/A0-A3/A6, -(A7)
            CLR.L   D4                      ; COUNTER
            MOVE.B  #50,D0                  ; CLOSE ALL FILES (RECOMMENDED)
            TRAP    #15
            SUB.L   #512,A7                 ; ROW BUFFER IN LOCAL MEMORY
            MOVE.B  #58,D0                  ; SHOW FILE DIALOG TASK
            CLR.L   D1                      ; DISPLAY LOAD DIALOG
            LEA     .MSG,A1                 ; STANDARD MESSAGE
            LEA     .FILTER,A2              ; NO FILTER
            MOVE.L  A7,A3                   ; FILE NAME BUFFER
            CLR.W   (A3)                    ; ENSURE THERE IS NO STRING
            TRAP    #15
            TST.B   D1
            BEQ     .END                    ; IF CANCELLED, DO NOTHING
            MOVE.L  A3,A1                   ; PLACE THE FILENAME INTO A1
            MOVE.B  #51,D0                  ; OPEN EXISTING FILE
            TRAP    #15
            TST.W   D0                      ; CHECK ERRORS
            BNE     .END                    ; IF ERRORS, DO NOTHING MORE
            MOVE.L  A7,A6                   ; PUT A6 INTO START OF BUFFER
            MOVE.L  A7,A1                   ; PUT A1 INTO START OF BUFFER
            MOVE.L  (GRDDST), A0            ; POINTER TO STORE PROCESS DATA
            MOVE.W  #GRDHEIGH-1,D3
.LOOP       MOVE.L  A6,A1
            MOVE.B  #53,D0                  ; READ DATA TASK NUMBER
            MOVE.L  #GRDWIDTH*2+1,D2        ; NUMBER OF BYTES TO READ
            TRAP    #15                     ; READ THE DATA
            MOVE.W  #GRDWIDTH-1,D2
.COPY       MOVE.B  (A1)+,D0                ; PICK ONE LOADED CHARACTER                  
            ; NOMÉS FINS LA MEITAT
            CMP.B   #GRDWIDTH/2, D4         ; MIRAM SI HA ARRIBAT A LA MEITAT
            BGE     .SKIP1
            ADDQ.B  #1, D4
            SUB.B   #'0',D0                 ; CONVERT FROM ASCII TO NUMBER
            MOVE.B  D0,(A0)+                ; STORE DATA
.SKIP2      ADDQ.L  #1,A1                   ; SKIP THE COMMA
            DBRA.W  D2,.COPY
            DBRA.W  D3,.LOOP
            MOVE.B  #56,D0                  ; CLOSE FILE
            TRAP    #15
.END        ADD.L   #512,A7                 ; RESTORE STACK
            MOVEM.L (A7)+, D0-D4/A0-A3/A6
            RTS
.MSG        DC.B    'SELECT A CSV FILE TO LOAD',0
.FILTER     DC.B    '*.CSV',0
            DS.W    0
.SKIP1      MOVE.B  (A0)+, D0
            ADDQ.B  #1, D4
            CMP.B   #GRDWIDTH, D4
            BNE     .NORESET
            CLR.B   D4
.NORESET    BRA     .SKIP2

; -----------------------------------------------------------------------------
GRDLRGT
; LOADS THE RIGHT PART OF THE GRID FROM A CSV FORMATTED FILE
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; NOTE     : THE LOCAL BUFFER SIZE MUST BE MAX(512,2*GRDWIDTH+1). PLEASE CHANGE
;            IT ACCORDINGLY IF GRDWIDTH*2+1 IS LARGER THAN 512.
; NOTE     : ALL THE ACTIVE CELLS ARE STORED AS PLAYER 2 INDEPENDLY OF THEIR
;            ACTUAL VALUE.
; -----------------------------------------------------------------------------

; TODO : PLACE YOUR CODE HERE
            MOVEM.L D0-D4/A0-A3/A6, -(A7)   ; GUARDAM REGISTRES
            
            CLR.L   D4                      ; COUNTER
            MOVE.B  #50,D0                  ; CLOSE ALL FILES (RECOMMENDED)
            TRAP    #15
            SUB.L   #512,A7                 ; ROW BUFFER IN LOCAL MEMORY
            MOVE.B  #58,D0                  ; SHOW FILE DIALOG TASK
            CLR.L   D1                      ; DISPLAY LOAD DIALOG
            LEA     .MSG,A1                 ; STANDARD MESSAGE
            LEA     .FILTER,A2              ; NO FILTER
            MOVE.L  A7,A3                   ; FILE NAME BUFFER
            CLR.W   (A3)                    ; ENSURE THERE IS NO STRING
            TRAP    #15
            TST.B   D1
            BEQ     .END                    ; IF CANCELLED, DO NOTHING
            MOVE.L  A3,A1                   ; PLACE THE FILENAME INTO A1
            MOVE.B  #51,D0                  ; OPEN EXISTING FILE
            TRAP    #15
            TST.W   D0                      ; CHECK ERRORS
            BNE     .END                    ; IF ERRORS, DO NOTHING MORE
            MOVE.L  A7,A6                   ; PUT A6 INTO START OF BUFFER
            MOVE.L  A7,A1                   ; PUT A1 INTO START OF BUFFER
            MOVE.L  (GRDDST), A0            ; POINTER TO STORE PROCESS DATA
            MOVE.W  #GRDHEIGH-1,D3
.LOOP       MOVE.L  A6,A1
            MOVE.B  #53,D0                  ; READ DATA TASK NUMBER
            MOVE.L  #GRDWIDTH*2+1,D2        ; NUMBER OF BYTES TO READ
            TRAP    #15                     ; READ THE DATA
            MOVE.W  #GRDWIDTH-1,D2
.COPY       MOVE.B  (A1)+,D0                ; PICK ONE LOADED CHARACTER
            ; MITAT FINS FINAL
            CMP.B   #GRDWIDTH/2, D4         ; COMENÇAM PER LA MEITAT DE LA MATRIU
            BLT     .SKIP1
            ADDQ.B  #1, D4
            CMP.B   #GRDWIDTH, D4
            BNE     .NORESET
            CLR.B   D4
.NORESET    SUB.B   #'0',D0                 ; CONVERT FROM ASCII TO NUMBER
            MOVE.B  D0,(A0)+                ; STORE DATA
.SKIP2      ADDQ.L  #1,A1                   ; SKIP THE COMMA
            DBRA.W  D2,.COPY
            DBRA.W  D3,.LOOP
            MOVE.B  #56,D0                  ; CLOSE FILE
            TRAP    #15
.END        ADD.L   #512,A7                 ; RESTORE STACK
            MOVEM.L (A7)+, D0-D4/A0-A3/A6   ; RESTAURAM REGISTRES
            RTS
.MSG        DC.B    'SELECT A CSV FILE TO LOAD',0
.FILTER     DC.B    '*.CSV',0
            DS.W    0
.SKIP1      MOVE.B  (A0)+, D0
            ADDQ.B  #1, D4
            BRA     .SKIP2

; -----------------------------------------------------------------------------
GRDPLOT
; PLOTS THE GRID
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------
; TODO : PLACE YOUR CODE HERE

*REGISTRES UTILITZATS:
*D0
*D1
*D2
*D3
*D4
            ; -----------------------------------------------------------------------------
GRDPLOT
; PLOTS THE GRID
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------
; TODO : PLACE YOUR CODE HERE

            MOVEM.L D0-D6/A0, -(A7)         ;GUARDAM REGISTRES
;----------------------------POSADA A PUNT(RESET)------------------------------
            CLR.W   GRDNPLR1
            CLR.W   GRDNPLR2
;----------------------------POSAR COLOR VERD AL LLAPIS------------------------           
            MOVE.B  #80, D0
            MOVE.L  #CLRDKGRN,D1
            TRAP    #15
;----------------------------POSAR PEN WIDTH-----------------------------------            
            MOVE.B  #93, D0
            MOVE.B  #0, D1
            TRAP    #15
;----------------------------BUCLE DE RECORREGUT-------------------------------            
            MOVE.L  (GRDDST), A0
	MOVE.W  #GRDXOFF, D1           
            MOVE.W  #GRDYOFF, D2            
            MOVE.W  D1, D3
            MOVE.W  D2, D4
            ADD.W   #GRDSZCEL, D3
            ADD.W   #GRDSZCEL, D4
            
            MOVE.W  #GRDHEIGH-1, D5         ;NOMBRE DE FILES-1
.LOOPY      
            MOVE.W  #GRDWIDTH-1, D6         ;NOMBRE DE COLUMNES-1
.LOOP       MOVE.B  (A0)+, D0               ;OBTENIM VALOR DE PINTAR
            MOVEM.L D0-D1, -(A7)           
            CMP.B   #1, D0                  
            BEQ     .PLAYER1                  
            CMP.B   #2, D0                  ;CASELLA JUGADOR 2?
            BEQ     .PLAYER2
            JMP     .NOPINTA
            MOVE.L  #CLRBLACK, D1           ;SINO, NEGRE
.PINTA      
            MOVE.B  #81, D0                 ;COLOR PER EMPLENAR
            TRAP    #15                     
.NOPINTA           
            MOVEM.L (A7)+, D0-D1            
            MOVE.B  #87, D0                 ;FICAM TASCA A EXECUTAR
            TRAP    #15                     ;DIBUIXAM RECTANGLE
            ADD.W   #GRDSZCEL, D1           
            ADD.W   #GRDSZCEL, D3           
            DBRA.W  D6, .LOOP               
            
            ADD.W   #GRDSZCEL, D2           
            ADD.W   #GRDSZCEL, D4
            MOVE.W  #GRDXOFF, D1            
            MOVE.W  D1, D3                  
            ADD.W   #GRDSZCEL, D3
            DBRA.W  D5,.LOOPY               ; SEGUENT CASELLA
;----------------------------IMPRIMIM PUNTUACIÓ JUGADOR 1----------------------
            
            ;POSAM EL COLOR BLAU AL LLAPIS
            MOVE.B  #81, D0
            MOVE.L  #GRDCOLP1, D1
            TRAP    #15
            
            ;UBICAM EL CURSOR PER PINTAR EL CONTADOR DEL JUGADOR 1
            MOVE.B  #11, D0                 
            MOVE.W  #GRDSCP1X<<8|GRDSCP1Y,D1
            TRAP    #15
            
            ;IMPRIMIM EL CONTADOR
            MOVE.B  #3, D0
            CLR.L   D1                      
            MOVE.W  GRDNPLR1, D1            
            TRAP    #15
            
            ;MIRAM SI HEM D'IMPRIMIR L'ASTERISC O NO
            MOVE.W  GRDNPLR1, D2            
            MOVE.W  GRDNPLR2, D3            
            CMP.W   D3, D2   
            BLT     .PLR2SCORE                 ;PLR2>PLR1--->NO IMPRIMIR ASTERISC
            
            ;UBICAM EL CURSOR PER PINTAR L'ASTERISC(1 COLUMNA MENYS)
            MOVE.B  #11, D0
            MOVE.W  #GRDSCP1X<<8|GRDSCP1Y-1,D1
            TRAP    #15
            
            ;IMPRIMIM L'ASTERISC '*'(42 EN ASCII)
            MOVE.B  #6, D0
            MOVE.B  #$2A, D1                ;#$2A=42 EN ASCII
            TRAP    #15
            
;----------------------------IMPRIMIM PUNTUACIÓ JUGADOR 2----------------------
.PLR2SCORE                 
            ;POSAM EL COLOR VERMELL AL LLAPIS
            MOVE.B  #81, D0
            MOVE.L  #GRDCOLP2, D1
            TRAP    #15
            
            ;UBICAM EL CURSOR PER PINTAR EL CONTADOR DEL JUGADOR 2
            MOVE.B  #11, D0                 
            MOVE.W  #GRDSCP2X<<8|GRDSCP2Y,D1
            TRAP    #15
            
            ;IMPRIMIM EL CONTADOR
            MOVE.B  #3, D0
            CLR.L   D1                      
            MOVE.W  GRDNPLR2, D1            
            TRAP    #15
            
            ;MIRAM SI HEM D'IMPRIMIR L'ASTERISC O NO
            CMP.W   D2, D3                  
            BLT     .GENCOUNT                 ;PLR1>PLR2--->NO IMPRIMIR ASTERISC
            
            ;UBICAM EL CURSOR PER PINTAR L'ASTERISC(1 COLUMNA MENYS)
            MOVE.B  #11, D0
            MOVE.W  #GRDSCP2X<<8|GRDSCP2Y-1,D1
            TRAP    #15
            
            ;IMPRIMIM L'ASTERISC '*'(42 EN ASCII)
            MOVE.B  #6, D0
            MOVE.B  #$2A, D1                ;#$2A=42 EN ASCII
            TRAP    #15
            
;----------------------------IMPRIMIM EL CONTADOR DE GENERACIONS---------------           
.GENCOUNT     
            ;POSAM EL COLOR DEL LLAPIS A VERD
            MOVE.B  #81, D0                 ;TASCA DE TRAP
            MOVE.L  #CLRLTGRN, D1
            TRAP    #15
            
            MOVE.B  #11, D0                 ;APUNTAM A LA POSICIÓ
            MOVE.W  #GRDSCNGX<<8|GRDSCNGY,D1 
            TRAP    #15
            
            MOVE.B  #3, D0
            CLR.L   D1                      ; EVITAM NUMEROS ERRONIS
            MOVE.W  GRDNGEN, D1             ; P2 SCORE
            TRAP    #15
            
            ; CHECK WHO IS WINNING
            MOVEM.L (A7)+, D0-D6/A0         ; RESTORE REGISTERS
            RTS      
;--------------------POSAM COLOR AL LLAPIS SEGONS EL JUGADOR-------------------                       
.PLAYER1            
            MOVE.L  #GRDCOLP1, D1           ;COLOR=BLAU
            ADDQ.W  #1, GRDNPLR1            ;AUGMENTAM CONTADOR NP1
            JMP     .PINTA
.PLAYER2      
            MOVE.L  #GRDCOLP2, D1           ;COLOR=VERMELL
            ADDQ.W  #1, GRDNPLR2            ;AUGMENTAM CONTADOR NP2
            JMP     .PINTA



















*~Font name~Courier New~
*~Font size~10~
*~Tab type~0~
*~Tab size~4~
