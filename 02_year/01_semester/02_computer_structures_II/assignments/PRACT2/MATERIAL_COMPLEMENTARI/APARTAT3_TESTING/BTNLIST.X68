; =============================================================================
; TITLE    : BUTTON LIST MANAGEMENT
; AUTHOR   : ANTONI BURGUERA
; HISTORY  : 05-JULY-2021 - CREATION
; COMMENTS : * ALLOWS ADDING, UPDATING AND PLOTTING A LIST OF BUTTONS. ALLOWS
;              REMOVING THEM ALL, BUT NOT INDIVIDUALLY.
;
;            * THE LIST OF BUTTONS IS STORED AS A LIST OF BUTTON'S VDB. THIS
;              LIST IS STORED AT BTLDATA.
;
;            * THE LIST OF VBD IS BUILT ASSUMING A MAXIMUM OF BTLMXVDB BUTTONS
;              AND A SIZE OF BTNSZVDB BYTES FOR EACH VDB.
; =============================================================================

; -----------------------------------------------------------------------------
BTLINIT
; INITIALIZES BUTTON LIST BY SETTING ALL THE POINTERS TO STATIC DATA BLOCKS
; TO $FFFFFFFF.
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------
            MOVEM.L D2/A0,-(A7)             ;GUARDAM REGISTRE CONTADOR I PUNTER
            
            LEA     BTLDATA,A0              ;APUNTAM A LA LLISTA DE BUTONS
            MOVE.W  #BTLMXVDB-1,D2          ;GUARDAM NOMBRE DE ITERACIONS
.LOOP       MOVE.L  #$FFFFFFFF,(A0)         ;ASSIGNAM VALOR BUIT AL BOTÓ
            ADD.L   #BTNSZVDB,A0            ;AVANÇAM A LA SEGÜENT POSICIÓ
            DBRA.W  D2,.LOOP                
            
            MOVEM.L (A7)+,D2/A0             ;RESTAURAM REGISTRES
            RTS

; -----------------------------------------------------------------------------
BTLCLEAR    EQU     BTLINIT
; CLEARS THE BUTTON LIST. SAME AS BTLINIT.
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------

; -----------------------------------------------------------------------------
BTLADD
; ADDS A NEW BUTTON INTO THE BUTTON LIST AND INITS IT.
; INPUT    : A1 - POINTER TO THE BUTTON SDB
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------

            
; ----------------------CERCA DEL PRIMER ELEMENT BUIT--------------------------
            MOVEM.L A0/D2,-(A7)             ;GUARDAM REGISTRES


            LEA     BTLDATA,A0              ;APUNTAM A LA LLISTA DE BUTONS
            MOVE.W  #BTLMXVDB-1,D2          ;ASSIGNAM NOMBRE DE ITERACIONS A D2
.LOOP       CMP.L   #$FFFFFFFF,(A0)         ;MIRAM SI EL BOTÓ ESTÀ 'BUIT'
            BEQ     .TROBAT                 ;SI TROBAT, INIT DEL BOTO
            ADD.L   #BTNSZVDB,A0            ;AVANÇAM POSICIÓ
            DBRA.W  D2,.LOOP

; ----------------------BOTÓ AFEGIT CORRECTAMENT-------------------------------

.FINAL      MOVEM.L (A7)+,A0/D2             ;RESTAURAM REGISTRES
            RTS

; ----------------------INIT DEL BOTÓ A AFEGIR---------------------------------

.TROBAT     JSR     BTNINIT
            BRA     .FINAL
            

; -----------------------------------------------------------------------------
BTLUPD
; CALLS BTNUPD FOR ALL THE BUTTONS IN THE BUTTON LIST.
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------



; ----------------------BUCLE RECORREGUT SOBRE BOTONS--------------------------
            MOVEM.L A0/D2,-(A7)             ;GUARDAM REGISTRES

            LEA     BTLDATA,A0              ;APUNTAM A LA LLISTA DE BOTONS
            MOVE.W  #BTLMXVDB-1,D2          ;ASSIGNAM NOMBRE ITERACIONS A D2
.LOOP       CMP.L   #$FFFFFFFF,(A0)         ;MIRAM SI ESTA BUIT EL BOTÓ
            BEQ     .FINAL                 ;SI=HEM RECORREGUT TOTA LA LLISTA

            
            JSR     BTNUPD                  ;ACTUALITZAM CADA BOTÓ


            ADD.L   #BTNSZVDB,A0            ;AVANÇAM POSICIÓ DINS EL BUCLE
            DBRA.W  D2,.LOOP                
; ----------------------FINAL BUCLE RECORREGUT BOTONS--------------------------
.FINAL     MOVEM.L (A7)+,A0/D2             ;RESTAURAM REGISTRES
            RTS


; -----------------------------------------------------------------------------
BTLPLOT
; CALLS BTNPLOT FOR ALL THE BUTTONS IN THE BUTTON LIST.
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------

; TODO : PLACE YOUR CODE HERE
            MOVEM.L A0/D2,-(A7)             ;GUARDAM REGISTRES

; ----------------------BUCLE RECORREGUT SOBRE BOTONS--------------------------
            LEA     BTLDATA,A0              ;APUNTAM A LA LLISTA
            MOVE.W  #BTLMXVDB-1,D2          ;ASSIGNAM ITERACIONS A D2
.LOOP       CMP.L   #$FFFFFFFF,(A0)         ;MIRAM SI FINAL DE LLISTA
            BEQ     .FINAL

            JSR     BTNPLOT                 ;DIBUIXAM BOTÓ

            ADD.L   #BTNSZVDB,A0            ;AVANÇAM POSICIÓ
            DBRA.W  D2,.LOOP
; ----------------------FINAL BUCLE RECORREGUT BOTONS--------------------------

.FINAL      MOVEM.L (A7)+,A0/D2             ;RESTAURAM REGISTRES
            RTS

*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
