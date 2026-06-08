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

            ;DO=NOMBRE ITERACIONES MATRIZ
            ;D1=COLUMNA
            ;D2=FILA
            ;D3=MOUVAL
            ;D4=VALUE

            MOVEM.L D0-D4/A0,-(A7)
            CLR.L   D0
            CLR.L   D1
            CLR.L   D2
            MOVE.B  (MOUVAL),D3
            CMP.B   #0,D3        ;MIRAMOS SI HA SIDO PULSADO EL MOUSE
            BEQ     .END         ;SI ES IGUAL A 0, MOUSE NOT PRESSED
; --------------------------CHECK IF MOUSE INSIDE------------------------------
            MOVE.W  (MOUX),D1
            CMP.W   #GRDXOFF,D1  ;MOUSE ESTÁ A LA DERECHA DEL MARGEN IZQ?            
            BLE     .END         ;SI MOUSE A LA IZQ DEL GRID(MARGEN NO INCLUIDO)
            SUB.W   #GRDXOFF,D1  ;OBTENEMOS POSICIÓN RELATIVA RESPECTO MATRIZ
            CMP.W   #GRDPXWID,D1 ;MIRAMOS SI ESTÁ A LA IZQ DEL MARGEN DERECHO
            BGE     .END         ;ESTÁ FUERA DE LA MATRIZ(MARGEN NO INCLUIDO)           
            MOVE.W  (MOUY),D2    ;ALMACENAMOS COORDS Y DEL MOUSE
            CMP.W   #GRDYOFF,D2  ;
            BLE     .END         ;ESTÁ MAS ARRIBA
            SUB.W   #GRDYOFF,D2  ;OBTENEMOS POSICIÓN RELATIVA RESPECTO MATRIZ
            CMP.W   #GRDPXHEI,D2
            BGE     .END         ;ESTÁ MÁS ABAJO, FUERA
;-------------------------OBTENEMOS CASILLA CORRESPONDIENTE--------------------
            DIVS.W  #GRDSZCEL,D1 ;OBTENEMOS COLUMNA(COORDS X)
            DIVS.W  #GRDSZCEL,D2 ;OBTENEMOS FILA(COORDS Y)
;-------------------------MIRAMOS QUE BOTÓN DEL MOUSE ESTÁ PULSADO-------------
            LEA  GRDDST,A0  ;APUNTAMOS A LA MATRIZ                         
            ;NºElemento=fila*64+columna.     
            MOVE.W  D2,D0          
            MULS.W  #GRDWIDTH,D0
            ADD.W   D1,D0
            AND.B   #MOUMSKLF,D3 ;LEFT CLICK?
            BEQ     .RGT_GRID    ;NO --> RIGHT CLICK
            ;AÑADIR/PINTAR
            MOVE.B  D1,D4          ;GUARDAMOS LA FILA
            DIVS.W  #GRDWIDTH/2,D4 ;OBTENEMOS 0/1, DEPENDIENDO DE LA MITAD          COMMA EXPECTED
            ADDQ.B  #1,D4          ;SUMAMOS 1 PARA OBTENER EL VALUE
;------------------------CAMBIAMOS VALOR DE LA MATRIZ--------------------------   
            MOVE.B  D4,(A0,D0)
            JMP     .END
;------------------------ACTUALIZAMOS CONTADORES DE CÉLULAS--------------------

.RGT_GRID    
            CLR.B (A0,D0)
;-----------------------FINAL SUBRUTINA----------------------------------------  
.END
            MOVEM.L (A7)+,D0-D4/A0
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

*DO=INDEX PER A BUCLE RECORREGUT CASELLES
*D1=FILA
*D2=COLUMNA
*D3=FILA'
*D4=COLUMNA'
*D5=DESPLAZAMIENTO TOTAL
*D6=VALOR ALMACENADO EN MATRIZ(COMPROBAR NP1,NP2)
*            
*            MOVEM.L D0-D6/A0-A5,-(A7)
*            ADDQ.W #1,(GRDNGEN)
*            
*;------------------------INTERCANVIAM PUNTERS GRDDST I GRDSRC------------------
*
*            MOVE.L #GRDDST,A1
*            MOVE.L #GRDSRC,(GRDDST)
*            MOVE.L A1,(GRDSRC)
*
*            LEA  GRDSRC,A1     ;APUNTAM A LA MATRIU ORIGEN
*            LEA  GRDDST,A2     ;APUNTAM A LA MATRIU DESTÍ
*            
*;------------------------BUCLE DE RECORREGUT DE LA MATRIU----------------------
*
*
*            CLR.B   D0              ;D0-> INDEX PER A OBTENIR LES CASELLES
*            
*.MAIN_LOOP
*            JMP .CHECK_NEIGHBOURS   ;MIRAM VEINATS(A5,0)->NP1||(A5,1)->NP2       
*.CHECK_CELL
*            CMP.B   #0,(A1,D0)      ;MIRAR SI CELULA ESTA VIVA O MUERTA     
*            
*            ;
*.NEXT_CELL            
*            ADDQ.B  #1,D0
*            CMP.W   #GRDWIDTH*GRDHEIGH,D0  ;si D0=2048-->FINAL
*            BNE     .MAIN_LOOP
*            JMP .MAIN_END
*
*
*
*;------------------------------MIRAR VEINATS------------------------------
*            
*.CHECK_NEIGHBOURS
*            LEA     MOVEMENT,A3    ;MOVIMENT PER FILES
*            LEA     NPLAYER,A5     
*            CLR.B   0(A5)          ;NP1
*            CLR.B   1(A5)          ;NP2
*            
*            MOVE.B  (A1,D0),D6     
*            CMP.B   #0,D6
*            BEQ     .CONFIG
*            SUBQ.B  #1,D6
*            SUBQ.B  #1,(A5,D6)     ;Inicialitzam el contador de veins per a que
*                                   ;descarti el cas de la casella actual
*            
*            
*.CONFIG            
*            MOVE.B D0,D1
*            MOVE.B D0,D2
*            DIVS.W #64,D1           ;D1--->fila  (desplaçar cap dreta)
*            AND.W  #MOD_64,D2       ;D2--->columna(resto division) 
*            
*.LOOP       
*            LEA     MOVEMENT,A4        ;RESET MOVIMENT COLUMNES
*            CLR.B   D3
*            MOVE.B  D1,D3              ;Fila a ser modificada
*            ADD.B   (A3)+,D3           ;DESPLAÇAMENT
*            AND.W   #MOD_32,D3         ;MÒDUL,fila definitva
*            MULS.W  #64,D3             ;Desplaçament de fila 
*.LOOP2
*            CLR.B   D4
*            CLR.B   D5
*            CLR.B   D6
*            MOVE.B  D2,D4               ;D4-> columna a ser modificada
*            ADD.B   (A4)+,D4            ;D4+DESPLAÇAMENT
*            AND.W   #MOD_64,D4          ;MÒDUL,columna definitiva
*            MOVE.B  D4,D5
*            ADD.B   D3,D5               ;desplaçament total--->D5
*            MOVE.B  (A0,D5),D6
*            CMP.B   #0,D6           
*            BEQ     .END2
*            SUBQ.B  #1,D6
*            ADDQ.B  #1,(A5,D6)           ;Actualitzam valor de veinats NP1,NP2                      
*.END2       
*            CMP.B   D2,D4                ;si columna no ha estat modificada surtim
*            BNE     .LOOP2            
*                                       
*.END
*            DIVS.W  #64,D3
*            CMP.B   D1,D3            
*            BEQ     .CHECK_CELL          ;FINAL BUCLE PRINCIPAL
*            
*            
*            JMP     .LOOP
*              
*;------------------------------MIRAR NORMES SUPERVIVÈNCIA---------------------- 
*
*.           
*            
*;------------------------------FINAL SUBRUTINA---------------------------------
*.MAIN_END
*
*            MOVEM.L (A7)+,D0-D6/A0-A5
*            RTS
*
*
*MOVEMENT   DC.B    1,1,0
*NPLAYER    DS.B     2               ;ARRAY PUNTUACIONS(0->NP1,1->NP2)
*
*
*MOD_32      EQU     %0011111        ;ETIQUETA PER A REALITZAR EL MÒDUL 32(NOT 32)->FILES    
*MOD_64      EQU     %01111111       ;ETIQUETA PER A REALITZAR EL MÒDUL 64(NOT 64)->COLUMNES
*MOD_2048    EQU     %011111111111   ;CONTADOR BUCLE PRINCIPAL?

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
            MOVEM.L D0-D7/A0-A6,-(A7)       ; TO BE OPTIMIZED
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
            MOVEM.L (A7)+,D0-D7/A0-A6       ; TO BE OPTIMIZED
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
            MOVEM.L D0-D7/A0-A6,-(A7)       ; TO BE OPTIMIZED
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
   ;---------------------------------------------
            DIVU.W  #2,D2     
   ;---------------------------------------------             
.COPY       MOVE.B  (A1)+,D0                ; PICK ONE LOADED CHARACTER
            SUB.B   #'0',D0                 ; CONVERT FROM ASCII TO NUMBER
            MOVE.B  D0,(A0)+                ; STORE DATA
            ADDQ.L  #1,A1                   ; SKIP THE COMMA
            DBRA.W  D2,.COPY
            DBRA.W  D3,.LOOP
            MOVE.B  #56,D0                  ; CLOSE FILE
            TRAP    #15
.END        ADD.L   #512,A7                 ; RESTORE STACK
            MOVEM.L (A7)+,D0-D7/A0-A6       ; TO BE OPTIMIZED
            RTS
.MSG        DC.B    'SELECT A CSV FILE TO LOAD',0
.FILTER     DC.B    '*.CSV',0
            DS.W    0












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
            MOVEM.L D0-D7/A0-A6,-(A7)       ; TO BE OPTIMIZED
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
   ;---------------------------------------------
            DIVU.W  #2,D2
            ADD.L   D2,A0     
   ;---------------------------------------------             
.COPY       MOVE.B  (A1)+,D0                ; PICK ONE LOADED CHARACTER
            SUB.B   #'0',D0                 ; CONVERT FROM ASCII TO NUMBER
            MOVE.B  D0,(A0)+                ; STORE DATA
            ADDQ.L  #1,A1                   ; SKIP THE COMMA
            DBRA.W  D2,.COPY
            DBRA.W  D3,.LOOP
            MOVE.B  #56,D0                  ; CLOSE FILE
            TRAP    #15
.END        ADD.L   #512,A7                 ; RESTORE STACK
            MOVEM.L (A7)+,D0-D7/A0-A6       ; TO BE OPTIMIZED
            RTS
.MSG        DC.B    'SELECT A CSV FILE TO LOAD',0
.FILTER     DC.B    '*.CSV',0
            DS.W    0
; -----------------------------------------------------------------------------
GRDPLOT
; PLOTS THE GRID
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------

; TODO : PLACE YOUR CODE HERE

*            MOVEM.L D0-D7,-(A7)
*            LEA GRDDST,A0 ;APUNTAM A LA MATRIU DESTÍ
*            MOVE.W  #GRDWIDTH,D0
   ;APARTADO CONTADORES Q NO LE FUNCIONA AL BICHO MASTODONTE DE VIKTOR
;----------------------------GRID----------------------------------------------
            
            
    MOVEM.L D0-D6/A0-A3, -(A7)
;-------------------------------RESET CONTADORES-------------------------------
    LEA     GRDNPLR1,A1
    LEA     GRDNPLR2,A2
    CLR.W   (A1)
    CLR.W   (A2)
;------------------------------------------------------------------------------
    MOVE.L  (GRDDST), A0            ; A0: POINTER TO THE DATA TO WRITE
    
    MOVE.W #GRDWIDTH,D0
    LEA GRDNPLR1,A2
    LEA GRDNPLR2,A3
    CLR.W   (A2)
    CLR.W   (A3)

*    ;CONTADORES
*    MOVE.B #95,D0
*         
*    MOVE.W #GRDSCP1X,D1
*    MOVE.W #GRDSCP1Y,D2
*    MOVE.L #GRDNPLR1,A1
*    TRAP #15
*    
*    
*    MOVE.B #95,D0
*    
*    MOVE.W #GRDSCP2X,D1
*    MOVE.W #GRDSCP2Y,D2
*    MOVE.L #GRDNPLR2,A1
*    TRAP #15
*   
*
    MOVE.B #95,D0
    
    MOVE.W #GRDSCNGX,D1
    MOVE.W #GRDSCNGY,D2
    MOVE.L #GRDNGEN,A1
    TRAP #15
    ;;;;;;;;;;;;;;;;;;
    
    
    ;GRID
    MOVE.W  #GRDHEIGH,D5          ; NUMBER OF ROWS
    SUBQ.W #1,D5
    
    MOVE.W  #GRDWIDTH,D6          ; NUMBER OF COLUMNS
    SUBQ.W #1,D6
    
    MOVE.L #GRDDST, A0
    
    
    ;GREEN PEN
    MOVE.B #80,D0
    MOVE.L #CLRDKGRN,D1
    TRAP #15

  
    ;CHANGE WIDTH OF PEN
    MOVE.B #93,D0
    MOVE.B #2,D1
    TRAP #15
    
    MOVE.W #GRDXOFF, D3
    MOVE.W #GRDYOFF, D2
    
    
.FILA:
    
    MOVE.B (A0)+,D7
   
    ;PLAYER 1
    CMP #1,D7
    BEQ .PLAYER1
    ;PLAYER 2?
    CMP #2,D7
    BEQ .PLAYER2
    ;NO PLAYER
    
    ;PAINT RECTANGLE PLAYER 1
    ;FILL COLOR
    MOVE.B #81,D0
    MOVE.L #CLRBLACK,D1
    TRAP #15
    
    ;RECTANGLE
    MOVE.B #87,D0
    MOVE.W D3,D1
    MOVE.W D2,D4    
    
    ADD.W #GRDSZCEL,D3
    ADD.W #GRDSZCEL,D4
    
    TRAP #15
    
.LOOP_PLAYER:  

    
    DBRA D6, .FILA
    
    MOVE.W #GRDXOFF,D3
    ADD.W #GRDSZCEL,D2
    
    MOVE.W  #GRDWIDTH,D6          ; NUMBER OF COLUMNS
    SUBQ.W #1,D6
    
    DBRA D5, .FILA
    JMP .FINAL

.PLAYER1:


    ;PAINT RECTANGLE PLAYER 1
    ;FILL COLOR
    MOVE.B #81,D0
    MOVE.L #CLRLTBLU,D1
    TRAP #15
    
    ;RECTANGLE
    MOVE.B #87,D0
    MOVE.W D3,D1
    MOVE.W D2,D4
    

    ADD.W #GRDSZCEL,D3
    ADD.W #GRDSZCEL,D4
    
    TRAP #15
    
    ;SI CASILLA LLENA, INCREMENTAMOS CONTADOR
    ADDQ.W  #1,(A2)
    JMP .LOOP_PLAYER


.PLAYER2:


    ;PAINT RECTANGLE PLAYER 2
    ;FILL COLOR
    MOVE.B #81,D0
    MOVE.L #CLRLTRED,D1
    TRAP #15
    
    
    ;RECTANGLE
    MOVE.B #87,D0
    MOVE.W D3,D1
    MOVE.W D2,D4
    

    ADD.W #GRDSZCEL,D3
    ADD.W #GRDSZCEL,D4
    
    TRAP #15
    ADDQ.W  #1,(A3)
    JMP .LOOP_PLAYER
 
          
.FINAL:

    MOVEM.L  (A7)+, D0-D6/A0-A3
    RTS
            
            
*            MOVEM.L (A7)+,D0-D7
*            RTS


































*~Font name~Courier New~
*~Font size~10~
*~Tab type~0~
*~Tab size~4~
