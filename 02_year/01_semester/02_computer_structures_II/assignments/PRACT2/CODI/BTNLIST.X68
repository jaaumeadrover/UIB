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
            MOVEM.L D0/A0,-(A7)
            LEA     BTLDATA,A0
            MOVE.W  #BTLMXVDB-1,D0          ; NUM ITERATIONS
.LOOP       MOVE.L  #$FFFFFFFF,(A0)         ; MARK SLOT AS EMPTY
            ADD.L   #BTNSZVDB,A0            ; GO TO NEXT SLOT
            DBRA.W  D0,.LOOP                ; LOOP FOR ALL SLOTS
            MOVEM.L (A7)+,D0/A0
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
            MOVEM.L A0/D0,-(A7)

            ; SEARCH FOR AN EMPTY SLOT

            LEA     BTLDATA,A0
            MOVE.W  #BTLMXVDB-1,D0
.LOOP       CMP.L   #$FFFFFFFF,(A0)
            BEQ     .FOUND
            ADD.L   #BTNSZVDB,A0
            DBRA.W  D0,.LOOP

            ; NO SLOT? DO NOTHING

.END        MOVEM.L (A7)+,A0/D0
            RTS

            ; SLOT FOUND? PLACE THE BUTTON THERE BY CALLING BTNINIT

.FOUND      JSR     BTNINIT
            BRA     .END

; -----------------------------------------------------------------------------
BTLUPD
; CALLS BTNUPD FOR ALL THE BUTTONS IN THE BUTTON LIST.
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------

            MOVEM.L A0/D0,-(A7)

            ; TRAVERSE THE LIST

            LEA     BTLDATA,A0
            MOVE.W  #BTLMXVDB-1,D0
.LOOP       CMP.L   #$FFFFFFFF,(A0)
            BEQ     .DONE

            ; UPDATE EACH BUTTON

            JSR     BTNUPD

            ; GO TO THE NEXT BUTTON

            ADD.L   #BTNSZVDB,A0
            DBRA.W  D0,.LOOP

.DONE       MOVEM.L (A7)+,A0/D0
            RTS


; -----------------------------------------------------------------------------
BTLPLOT
; CALLS BTNUPD FOR ALL THE BUTTONS IN THE BUTTON LIST.
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
