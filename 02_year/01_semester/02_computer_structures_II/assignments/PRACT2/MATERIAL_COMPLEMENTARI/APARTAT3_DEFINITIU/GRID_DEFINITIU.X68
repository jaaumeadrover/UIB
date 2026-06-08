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
            MOVEM.L D0-D3/A0, -(A7)
            CLR.L   D1                      ; WILL BE USED FOR DIVIDE OP
            CLR.L   D2
            MOVE.B  #MOUMSKLF+MOUMSKRT, D1
            MOVE.B  (MOUVAL), D3
            AND.B   D1, D3
            TST.B   D3
            BEQ     .END
            CMP     #GRDVEMRG, (MOUY)
            BLT     .END
            MOVE.W  #GRDVEMRG, D0
            MULU    #GRDWIDTH, D0
            CMP     (MOUY), D0
            BLE     .END
            MOVE.L  (GRDDST), A0            ; A0: POINTER TO THE DATA TO WRITE
            MOVE.W  #GRDWIDTH, D0           ; PUT NR OF COLUMNS IN D0
            MOVE.W  (MOUY), D2              ; GET MOUSE POSITION
            SUB.W   #GRDYOFF, D2            ; INITIAL Y
            DIVS.W  #GRDSZCEL, D2           ; GET Y OFFSET                     
            MULU.W  D2, D0
            MOVE.W  (MOUX), D1              
            SUB.W   #GRDXOFF, D1            ; INITIAL X
            DIVS.W  #GRDSZCEL, D1           ; GET X OFFSET
            ADD.W   D1, D0
            BTST.L  #MOUMSKLF, D3           ; IS RIGHT CLICK?
            BNE     .DELETE
            CMP.W   #GRDWIDTH/2, D1         ; CHECK IF PLR1 OR PLR2
            BGE     .PLR2
            MOVE.B  #1,(A0, D0)
            BRA     .END
.PLR2       MOVE.B  #2, (A0, D0)
            BRA     .END
.DELETE     MOVE.B  #0, (A0, D0)
.END        MOVEM.L (A7)+, D0-D3/A0
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
            MOVEM.L D0-D4/D7/A0-A4, -(A7)
            CLR.L   D4
            CLR.W   D0
            MOVE.W  (GRDNGEN), D0           ; ADD 1 TO GENERATION NR
            ADDQ.B  #1, D0
            MOVE.W  D0, (GRDNGEN)
            ;
            MOVE.L  (GRDSRC), A0
            MOVE.L  (GRDDST), A1
            MOVE.L  A0, (GRDDST)
            MOVE.L  A1, (GRDSRC)
            ;
            ; WE USE A2 EXCLUSIVELY AS CURRENT CELL ITERATOR
            MOVE.L  A1, A2                  ; SRC MATRIX WHERE TO READ
            MOVE.L  A1, A0                  ; A0 WITH AUXILIAR POINTER TO GRDSRC
            ; WE USE A3 EXCLUSIVELY AS CELL EDITOR
            MOVE.L  (GRDDST), A3            ; DST MATRIX WHERE TO WRITE
            CLR.W   D3                      ; X INITIAL
            CLR.L   D0                      ; NP1 COUNTER
            CLR.L   D1                      ; NP2 COUNTER
            ;UP LEFT
            ;FIRST CELL DIFFERENT
            CMP.B   #2, (GRDWIDTH*GRDHEIGH-1, A1) ; LAST CELL OF MATRIX
            BNE     .PLR1ULF                ; CHECK PLR1 UL
            ADDQ.B  #1, D1   
            BRA     .FIRSTUP
.PLR1ULF    ADD.B   (GRDWIDTH*GRDHEIGH-1, A1), D0 ; WHATEVER VALOR, WE ADD TO NP1
            ; CHECK UPPER NEIGHBOUR
.FIRSTUP    MOVE.L  A1, -(A7)
            ADDA.L  #GRDWIDTH*GRDHEIGH-GRDWIDTH, A1
            CMP.B   #2, (A1, D3)
            BNE     .PLR1UF                 ;PLAYER1UPFIRST
            ADDQ.B  #1, D1
            BRA     .RST1
.PLR1UF     ADD.B   (A1, D3), D0
.RST1       MOVE.L  (A7)+, A1
            ; CHECK UPPER RIGHT NEIGHBOUR
            MOVE.L  A1, -(A7)
            ADDA.L  #GRDWIDTH*GRDHEIGH-GRDWIDTH+1, A1
            CMP.B   #2, (A1)
            BNE     .PLR1URF
            ADDQ.B  #1, D1
            BRA     .RST2
.PLR1URF    ADD.B   (A1), D0
.RST2       MOVE.L  (A7)+, A1
            ; CHECK RIGHT NEIGHBOUR
            CMP.B   #2, (1, A1)
            BNE     .PLR1RF
            ADDQ.B  #1, D1
            BRA     .FIRSTBR
.PLR1RF     ADD.B   (1, A1), D0
.FIRSTBR    ; CHECK BOTTOM RIGHT NEIGHBOUR
            CMP.B   #2, (GRDWIDTH+1,A1)   
            BNE     .PLR1BRF
            ADDQ.B  #1, D1
            BRA     .FIRSTB
.PLR1BRF    ADD.B   (GRDWIDTH+1,A1), D0
            ; CHECK BOTTOM NEIGHBOUR
.FIRSTB     CMP.B   #2, (GRDWIDTH, A1)
            BNE     .PLR1BF
            ADDQ.B  #1, D1
            BRA     .FIRSTBL
.PLR1BF     ADD.B   (GRDWIDTH,A1), D0
.FIRSTBL    ; CHECK BOTTOM LEFT NEIGHBOUR
            CMP.B   #2, (GRDWIDTH*2-1,A1)
            BNE     .PLR1BLF
            ADDQ.B  #1, D1
            BRA     .FIRSTL
.PLR1BLF    ADD.B   (GRDWIDTH*2-1,A1), D0
.FIRSTL     CMP.B   #2, (GRDWIDTH-1,A1)
            BNE     .PLR1LF
            ADDQ.B  #1, D1
            BRA     .ENDFRST
.PLR1LF     ADD.B   (GRDWIDTH-1,A1), D0
            ;FIRST ROW DIFFERENT
.ENDFRST    BSR     .RULES

            CLR.B   D0
            CLR.B   D1                      ; RESET NEIGHBOURS
            
.LOOPFR     ADDQ.W  #1, D3                  ; THIS WILL CONTROL LAST CELL
            ; WE LOOP THE FIRST ROW UNTIL END (MINUS FIRST CELL AND LAST CELL) 
            CMP.B   #GRDWIDTH-1, D3         
            BGE     .ENDFC
            ; FIRST ROW HAS IS UPPER LEFT NEIGHBOURS 
            ; AT THE BOTTOM ROW (GRDWIDTH*GRDHEIGH-GRDWIDTH) AND WE
            ; DISPLACE CURRENT X TO POSITION - 1
            MOVE.L  A1, -(A7)
            ADDA.L  #GRDWIDTH*GRDHEIGH-GRDWIDTH-1, A1
            CMP.B   #2, (A1, D3) 
            BNE     .PR1ULFR
            ADDQ.B  #1, D1
            BRA     .FRU                                
.PR1ULFR    ADD.B   (A1, D3), D0           
.FRU        MOVE.L  (A7)+, A1
            ; FIRST ROW UPPER NEIGHBOUR
            MOVE.L  A1, -(A7)
            ADDA.L  #GRDWIDTH*GRDHEIGH-GRDWIDTH, A1
            CMP.B   #2, (A1, D3)
            BNE     .PR1UFR               
            ADDQ.B  #1, D1
            BRA     .FRUR                   
.PR1UFR     ADD.B   (A1, D3), D0
.FRUR       MOVE.L  (A7)+, A1
            ; FIRST ROW UPPER RIGHT NEIGHBOUR
            MOVE.L  A1, -(A7)
            ADDA.L  #GRDWIDTH*GRDHEIGH-GRDWIDTH+1, A1
            CMP.B   #2, (A1, D3)
            BNE     .PR1URFR
            ADDQ.B  #1, D1
            BRA     .FRR
.PR1URFR    ADD.B   (A1, D3), D0
.FRR        MOVE.L  (A7)+, A1
            ; FIRST ROW RIGHT NEIGHBOUR
            CMP.B   #2, (1, A1, D3)
            BNE     .PR1RFR
            ADDQ.B  #1, D1
            BRA     .FRBR
.PR1RFR     ADD.B   (1, A1, D3), D0
.FRBR       CMP.B   #2, (GRDWIDTH+1, A1, D3)
            BNE     .PR1BRFR
            ADDQ.B  #1, D1
            BRA     .FRB
.PR1BRFR    ADD.B   (GRDWIDTH+1, A1, D3), D0
.FRB        CMP.B   #2, (GRDWIDTH, A1, D3)
            BNE     .PR1BFR
            ADDQ.B  #1, D1
            BRA     .FRBL
.PR1BFR     ADD.B   (GRDWIDTH, A1, D3), D0
.FRBL       CMP.B   #2, (GRDWIDTH-1, A1, D3)
            BNE     .PR1BLFR
            ADDQ.B  #1, D1
            BRA     .FRL
.PR1BLFR    ADD.B   (GRDWIDTH-1, A1, D3), D0
.FRL        CMP.B   #2, (-1, A1, D3)
            BNE     .PR1LFR
            ADDQ.B  #1, D1
            BRA     .RULESFR
.PR1LFR     ADD.B   (-1, A1, D3), D0
.RULESFR    BSR     .RULES
            CLR.B   D0
            CLR.B   D1
            BRA     .LOOPFR
.ENDFC      ; NEXT CELL (LAST OF FIRST ROW)  
                
            ; LAST CELL OF FIRST ROW
            ;UP LEFT
            CMP.B   #2, (GRDWIDTH*GRDHEIGH-2, A1) ; FIRST CELL, LAST ROW
            BNE     .PLR1ULL                ; CHECK PLR1 UL
            ADDQ.B  #1, D1   
            BRA     .LASTUP
.PLR1ULL    ADD.B   (GRDWIDTH*GRDHEIGH-2, A1), D0 ; WHATEVER VALOR, WE ADD TO NP1
            ; CHECK UPPER NEIGHBOUR
.LASTUP     MOVE.L  A1, -(A7)
            ADDA.L  #GRDWIDTH*GRDHEIGH-1, A1
            CMP.B   #2, (A1)
            BNE     .PLR1U                
            ADDQ.B  #1, D1
            BRA     .LASTUR
.PLR1U      ADD.B   (A1), D0
.LASTUR     MOVE.L  (A7)+, A1
            ; CHECK UPPER RIGHT NEIGHBOUR
            MOVE.L  A1, -(A7)
            ADDA.L  #GRDWIDTH*GRDHEIGH-GRDWIDTH, A1
            CMP.B   #2, (A1)
            BNE     .PLR1UR
            ADDQ.B  #1, D1
            BRA     .LASTR
.PLR1UR     ADD.B   (A1), D0
.LASTR      MOVE.L  (A7)+, A1
            ; CHECK RIGHT NEIGHBOUR
            CMP.B   #2, (A1)                ; A1 POINTS TO FIRST ROW POS
            BNE     .PLR1R
            ADDQ.B  #1, D1
            BRA     .LASTBR
.PLR1R      ADD.B   (A1), D0
.LASTBR     ; CHECK BOTTOM RIGHT NEIGHBOUR
            CMP.B   #2, (1, A1, D3)   
            BNE     .PLR1BR
            ADDQ.B  #1, D1
            BRA     .LASTB
.PLR1BR     ADD.B   (1, A1, D3), D0
            ; CHECK BOTTOM NEIGHBOUR
.LASTB      CMP.B   #2, (GRDWIDTH, A1, D3)
            BNE     .PLR1B
            ADDQ.B  #1, D1
            BRA     .LASTBL
.PLR1B      ADD.B   (GRDWIDTH, A1, D3), D0
.LASTBL     ; CHECK BOTTOM LEFT NEIGHBOUR
            CMP.B   #2, (GRDWIDTH-1, A1, D3)
            BNE     .PLR1BL
            ADDQ.B  #1, D1
            BRA     .LASTL
.PLR1BL     ADD.B   (GRDWIDTH-1, A1, D3), D0
.LASTL      CMP.B   #2, (-1, A1, D3)
            BNE     .PLR1L
            ADDQ.B  #1, D1
            BRA     .ENDLAST
.PLR1L      ADD.B   (-1,A1, D3), D0
.ENDLAST    BSR     .RULES
            
            ; SECOND TO LAST-1 ROWS *******************************************
            CLR.B   D0
            CLR.B   D1
            ADDA.L  #GRDHEIGH*GRDWIDTH-GRDWIDTH, A0 ; ADDRESS TO LAST ROW
            ;FIRST CELL OF ROW
.NXTROW     CLR.B   D3                      ; RESET X
            ADDA.L  #GRDWIDTH, A1           ; NEXT ROW
            CMP.L   A0, A1
            BEQ     .LASTROW
            ;FIRST CELL OF ROW
            ; CHECK UPPER LEFT NEIGHBOUR
            CMP.B   #2, (-1, A1)
            BNE     .P1UNFC                 ; PLAYER 1 UPPER NEIGHBOUR FIRST CELL
            ADDQ.B  #1, D1
            BRA     .UFC
.P1UNFC     ADD.B   (-1, A1), D0
.UFC        ; CHECK UPPER NEIGHBOUR FIRST CELL
            CMP.B   #2, (-GRDWIDTH, A1)
            BNE     .P1UFC
            ADDQ.B  #1, D1
            BRA     .URFC
.P1UFC      ADD.B   (-GRDWIDTH, A1), D0
            ; CHECK UPPER RIGHT NEIGHBOUR FIRST CELL
.URFC       CMP.B   #2, (-GRDWIDTH+1, A1)
            BNE     .P1URFC
            ADDQ.B  #1, D1
            BRA     .RFC
.P1URFC     ADD.B   (-GRDWIDTH+1, A1), D0
            ; CHECK RIGHT FIRST CELL
.RFC        CMP.B   #2, (1, A1)
            BNE     .P1RFC
            ADDQ.B  #1, D1
            BRA     .BRFC
.P1RFC      ADD.B   (1, A1), D0
            ; CHECK BOTTOM RIGHT FIRST CELL
.BRFC       CMP.B   #2, (GRDWIDTH+1, A1)
            BNE     .P1BRFC
            ADDQ.B  #1, D1
            BRA     .BFC
.P1BRFC     ADD.B   (GRDWIDTH+1, A1), D0
            ; CHECK BOTTOM FIRST CELL
.BFC        CMP.B   #2, (GRDWIDTH, A1)
            BNE     .P1BFC
            ADDQ.B  #1, D1
            BRA     .BLFC
.P1BFC      ADD.B   (GRDWIDTH, A1), D0
            ; CHECK BOTTOM LEFT FIRST CELL
.BLFC       CMP.B   #2, (GRDWIDTH*2-1, A1)
            BNE     .P1BLFC
            ADDQ.B  #1, D1
            BRA     .LFC
.P1BLFC     ADD.B   (GRDWIDTH*2-1, A1), D0
            ; CHECK LEFT FIRST CELL
.LFC        CMP.B   #2, (GRDWIDTH-1, A1)
            BNE     .P1LFC
            ADDQ.B  #1, D1
            BRA     .RULESFC                ; GO TO RULES
.P1LFC      ADD.B   (GRDWIDTH-1, A1), D0
.RULESFC    BSR     .RULES
            
            CLR.B   D0
            CLR.B   D1
            
            ;LOOP REST OF CELLS (GRDWIDTH-2)
.LOOPROW    ADDQ.B  #1, D3                  
            CMP.B   #GRDWIDTH-1, D3
            BGE     .LASTRC                 ; GO TO LAST CELL OF ROW
            
            ; CHECK UPPER LEFT NEIGHBOUR
            CMP.B   #2, (-GRDWIDTH-1, A1, D3)
            BNE     .P1UN                   
            ADDQ.B  #1, D1
            BRA     .U
.P1UN       ADD.B   (-GRDWIDTH-1, A1, D3), D0
.U          ; CHECK UPPER NEIGHBOUR
            CMP.B   #2, (-GRDWIDTH, A1, D3)
            BNE     .P1U
            ADDQ.B  #1, D1
            BRA     .UR
.P1U        ADD.B   (-GRDWIDTH, A1, D3), D0
            ; CHECK UPPER RIGHT NEIGHBOUR
.UR         CMP.B   #2, (-GRDWIDTH+1, A1, D3)
            BNE     .P1UR
            ADDQ.B  #1, D1
            BRA     .R
.P1UR       ADD.B   (-GRDWIDTH+1, A1, D3), D0
            ; CHECK RIGHT 
.R          CMP.B   #2, (1, A1, D3)
            BNE     .P1R
            ADDQ.B  #1, D1
            BRA     .BR
.P1R        ADD.B   (1, A1, D3), D0
            ; CHECK BOTTOM RIGHT 
.BR         CMP.B   #2, (GRDWIDTH+1, A1, D3)
            BNE     .P1BR
            ADDQ.B  #1, D1
            BRA     .B
.P1BR       ADD.B   (GRDWIDTH+1, A1, D3), D0
            ; CHECK BOTTOM
.B          CMP.B   #2, (GRDWIDTH, A1, D3)
            BNE     .P1B
            ADDQ.B  #1, D1
            BRA     .BL
.P1B        ADD.B   (GRDWIDTH, A1, D3), D0
            ; CHECK BOTTOM LEFT
.BL         CMP.B   #2, (GRDWIDTH-1, A1, D3)
            BNE     .P1BL
            ADDQ.B  #1, D1
            BRA     .L
.P1BL       ADD.B   (GRDWIDTH-1, A1, D3), D0
            ; CHECK LEFT
.L          CMP.B   #2, (-1, A1, D3)
            BNE     .P1L
            ADDQ.B  #1, D1
            BRA     .RULESRC                ; GO TO RULES FOR ROW CELL
.P1L        ADD.B   (-1, A1, D3), D0
.RULESRC    BSR     .RULES
            CLR.B   D0
            CLR.B   D1
            BRA     .LOOPROW
                                  

            ; LAST CELL OF ROW
.LASTRC     ; CHECK UPPER LEFT NEIGHBOUR
            CMP.B   #2, (-GRDWIDTH-1, A1, D3)
            BNE     .P1ULRLC                   
            ADDQ.B  #1, D1
            BRA     .URLC
.P1ULRLC    ADD.B   (-GRDWIDTH-1, A1, D3), D0
.URLC       ; CHECK UPPER NEIGHBOUR
            CMP.B   #2, (-GRDWIDTH, A1, D3)
            BNE     .P1URLC
            ADDQ.B  #1, D1
            BRA     .URRLC
.P1URLC     ADD.B   (-GRDWIDTH, A1, D3), D0
            ; CHECK UPPER RIGHT NEIGHBOUR
.URRLC      CMP.B   #2, (-GRDWIDTH*2+1, A1, D3)
            BNE     .P1URRLC
            ADDQ.B  #1, D1
            BRA     .RRLC
.P1URRLC    ADD.B   (-GRDWIDTH*2+1, A1, D3), D0
            ; CHECK RIGHT 
.RRLC       CMP.B   #2, (A1)                ; FIRST CELL OF ROW
            BNE     .P1RRLC
            ADDQ.B  #1, D1
            BRA     .BRRLC
.P1RRLC     ADD.B   (A1), D0
            ; CHECK BOTTOM RIGHT 
.BRRLC      CMP.B   #2, (1, A1, D3)
            BNE     .P1BRRLC
            ADDQ.B  #1, D1
            BRA     .BRLC
.P1BRRLC    ADD.B   (1, A1, D3), D0
            ; CHECK BOTTOM
.BRLC       CMP.B   #2, (GRDWIDTH, A1, D3)
            BNE     .P1BRLC
            ADDQ.B  #1, D1
            BRA     .BLRLC
.P1BRLC     ADD.B   (GRDWIDTH, A1, D3), D0
            ; CHECK BOTTOM LEFT
.BLRLC      CMP.B   #2, (GRDWIDTH-1, A1, D3)
            BNE     .P1BLRLC
            ADDQ.B  #1, D1
            BRA     .LRLC
.P1BLRLC    ADD.B   (GRDWIDTH-1, A1, D3), D0
            ; CHECK LEFT
.LRLC       CMP.B   #2, (-1, A1, D3)
            BNE     .P1LRLC
            ADDQ.B  #1, D1
            BRA     .RULESRLC                ; GO TO RULES FOR ROW LAST CELL
.P1LRLC     ADD.B   (-1, A1, D3), D0
.RULESRLC   BSR     .RULES
            CLR.B   D0
            CLR.B   D1
            BRA     .NXTROW
            
            ; LAST ROW
.LASTROW    ; FIRST CELL OF LAST ROW
            ; CHECK UPPER LEFT NEIGHBOUR
            CMP.B   #2, (-1, A1, D3)
            BNE     .P1ULFLR                ; PLAYER 1 UL FIRST LAST ROW               
            ADDQ.B  #1, D1
            BRA     .UFLR
.P1ULFLR    ADD.B   (-1, A1, D3), D0
.UFLR       ; CHECK UPPER NEIGHBOUR
            CMP.B   #2, (-GRDWIDTH, A1)
            BNE     .P1UFLR
            ADDQ.B  #1, D1
            BRA     .URFLR
.P1UFLR     ADD.B   (-GRDWIDTH, A1), D0
            ; CHECK UPPER RIGHT NEIGHBOUR
.URFLR      CMP.B   #2, (-GRDWIDTH+1, A1)
            BNE     .P1URFLR
            ADDQ.B  #1, D1
            BRA     .RFLR
.P1URFLR    ADD.B   (-GRDWIDTH+1, A1), D0
            ; CHECK RIGHT 
.RFLR       CMP.B   #2, (1, A1)                ; FIRST CELL OF ROW
            BNE     .P1RFLR
            ADDQ.B  #1, D1
            BRA     .BRFLR
.P1RFLR     ADD.B   (1, A1), D0
            ; CHECK BOTTOM RIGHT 
.BRFLR      MOVE.L  A1, -(A7)
            MOVE.L  (GRDSRC), A1
            CMP.B   #2, (1, A1)             ; SECOND CELL OF MATRIX
            BNE     .P1BRFLR
            ADDQ.B  #1, D1
            BRA     .BFLR
.P1BRFLR    ADD.B   (1, A1), D0         
.BFLR       ; CHECK BOTTOM
            CMP.B   #2, (A1)                ; FIRST CELL OF MATRIX
            BNE     .P1BFLR
            ADDQ.B  #1, D1
            BRA     .BLFLR
.P1BFLR     ADD.B   (A1), D0
.BLFLR      ; CHECK BOTTOM LEFT
            CMP.B   #2, (GRDWIDTH-1, A1)    ; LAST CELL OF FIRST ROW
            BNE     .P1BLFLR
            ADDQ.B  #1, D1
            BRA     .LFLR
.P1BLFLR    ADD.B   (GRDWIDTH-1, A1), D0
.LFLR       MOVE.L  (A7)+, A1
            ; CHECK LEFT
            CMP.B   #2, (GRDWIDTH-1, A1)
            BNE     .P1LFLR
            ADDQ.B  #1, D1
            BRA     .RULESFLR                ; GO TO RULES FOR ROW LAST CELL
.P1LFLR     ADD.B   (GRDWIDTH-1, A1), D0
.RULESFLR   BSR     .RULES
            CLR.B   D0
            CLR.B   D1
            CLR.B   D3                      ; RESET X
            ; CELLS UNTIL LAST CELL OF LAST ROW
.LOOPCLR    ADDQ.B  #1, D3
            CMP.B   #GRDWIDTH-1, D3
            BGE     .LCM                    ; LAST CELL OF MATRIX
            ; CHECK UPPER LEFT NEIGHBOUR
            CMP.B   #2, (-GRDWIDTH-1, A1, D3)
            BNE     .P1ULCLR                ; CELLS OF LAST ROW (CLR)                             
            ADDQ.B  #1, D1
            BRA     .UCLR
.P1ULCLR    ADD.B   (-GRDWIDTH-1, A1, D3), D0
.UCLR       ; CHECK UPPER NEIGHBOUR
            CMP.B   #2, (-GRDWIDTH, A1, D3)
            BNE     .P1UCLR
            ADDQ.B  #1, D1
            BRA     .URCLR
.P1UCLR     ADD.B   (-GRDWIDTH, A1, D3), D0
            ; CHECK UPPER RIGHT NEIGHBOUR
.URCLR      CMP.B   #2, (-GRDWIDTH+1, A1, D3)
            BNE     .P1URCLR
            ADDQ.B  #1, D1
            BRA     .RCLR
.P1URCLR    ADD.B   (-GRDWIDTH+1, A1, D3), D0
            ; CHECK RIGHT 
.RCLR       CMP.B   #2, (1, A1, D3)                
            BNE     .P1RCLR
            ADDQ.B  #1, D1
            BRA     .BRCLR
.P1RCLR     ADD.B   (1, A1, D3), D0
            ; CHECK BOTTOM RIGHT 
.BRCLR      MOVE.L  A1, -(A7)
            MOVE.L  (GRDSRC), A1
            CMP.B   #2, (1, A1, D3)         ; COINCIDENT CELL FIRST ROW
            BNE     .P1BRCLR
            ADDQ.B  #1, D1
            BRA     .BCLR
.P1BRCLR    ADD.B   (1, A1, D3), D0         
.BCLR       ; CHECK BOTTOM
            CMP.B   #2, (A1, D3)            ; COINCIDENT CELL FIRST ROW
            BNE     .P1BCLR
            ADDQ.B  #1, D1
            BRA     .BLCLR
.P1BCLR     ADD.B   (A1, D3), D0
.BLCLR      ; CHECK BOTTOM LEFT
            CMP.B   #2, (-1, A1, D3)        ; LAST CELL OF FIRST ROW
            BNE     .P1BLCLR
            ADDQ.B  #1, D1
            BRA     .LCLR
.P1BLCLR    ADD.B   (-1, A1, D3), D0
.LCLR       MOVE.L  (A7)+, A1
            ; CHECK LEFT
            CMP.B   #2, (-1, A1, D3)
            BNE     .P1LCLR
            ADDQ.B  #1, D1
            BRA     .RULESCLR                ; GO TO RULES FOR ROW LAST CELL
.P1LCLR     ADD.B   (-1, A1, D3), D0
.RULESCLR   BSR     .RULES
            CLR.B   D0
            CLR.B   D1
            BRA     .LOOPCLR     
            ; LAST CELL OF LAST ROW
.LCM        ; CHECK UPPER LEFT NEIGHBOUR
            CMP.B   #2, (-GRDWIDTH-1, A1, D3)
            BNE     .P1ULLCM                ; PLAYER 1 UL FIRST LAST ROW               
            ADDQ.B  #1, D1
            BRA     .ULCM
.P1ULLCM    ADD.B   (-GRDWIDTH-1, A1, D3), D0
.ULCM       ; CHECK UPPER NEIGHBOUR
            CMP.B   #2, (-GRDWIDTH, A1, D3)
            BNE     .P1ULCM
            ADDQ.B  #1, D1
            BRA     .URLCM
.P1ULCM     ADD.B   (-GRDWIDTH, A1, D3), D0
            ; CHECK UPPER RIGHT NEIGHBOUR
.URLCM      CMP.B   #2, (-GRDWIDTH, A1)            
            BNE     .P1URLCM
            ADDQ.B  #1, D1
            BRA     .RLCM
.P1URLCM    ADD.B   (-GRDWIDTH, A1), D0     ; CELL UP FROM FIRST OF LAST ROW
            ; CHECK RIGHT 
.RLCM       CMP.B   #2, (A1)                ; POINTER TO FIRST CELL IN ROW 
            BNE     .P1RLCM
            ADDQ.B  #1, D1
            BRA     .BRLCM
.P1RLCM     ADD.B   (A1), D0
            ; CHECK BOTTOM RIGHT 
.BRLCM      MOVE.L  A1, -(A7)
            MOVE.L  (GRDSRC), A1
            CMP.B   #2, (A1)                ; FIRST CELL OF MATRIX
            BNE     .P1BRLCM
            ADDQ.B  #1, D1
            BRA     .BLCM
.P1BRLCM    ADD.B   (A1), D0         
.BLCM       ; CHECK BOTTOM
            CMP.B   #2, (GRDWIDTH-1, A1)  ; FIRST CELL OF MATRIX
            BNE     .P1BLCM
            ADDQ.B  #1, D1
            BRA     .BLLCM
.P1BLCM     ADD.B   (GRDWIDTH-1, A1), D0
.BLLCM      ; CHECK BOTTOM LEFT
            CMP.B   #2, (GRDWIDTH-2, A1) ; LAST CELL OF FIRST ROW
            BNE     .P1BLLCM
            ADDQ.B  #1, D1
            BRA     .LLCM
.P1BLLCM    ADD.B   (GRDWIDTH-2, A1), D0
.LLCM       MOVE.L  (A7)+, A1
            ; CHECK LEFT
            CMP.B   #2, (-1, A1, D3)
            BNE     .P1LLCM
            ADDQ.B  #1, D1
            BRA     .RULESLCM                ; GO TO RULES FOR ROW LAST CELL
.P1LLCM     ADD.B   (-1, A1, D3), D0
.RULESLCM   BSR     .RULES
            CLR.B   D0
            CLR.B   D1
            ; RETURN SUBRUTINE, WE FINISHED
.ENDUPD     
            MOVEM.L (A7)+, D0-D4/D7/A0-A4   
            RTS       


;**************************** ALL CELLS CHECKED *******************************              
.RULES      ; WE HAVE NP1 IN D0 AND NP2 IN D1
            MOVE.B  (A2)+, D7               ; GET VALOR
            CMP.B   #2, D7                  ; IS PLR2 CELL?
            BNE     .CHECKPL1
            ;PLR2 RULES
            MOVEM.L D0-D2, -(A7)
            MOVE.L  D1, D2                  ; STORE NP2
            SUB.B   D0, D1                  ; NP2-NP1
            CMP.B   #3, D1                  ; IS IT 3?
            BNE     .PLR2IS2
            ; ALREADY A P2 CELL, WE DO NOTHING
            BRA     .P2END
.PLR2IS2    CMP.B   #2, D1                  ; OR IS IT 2
            BNE     .PLR2IS1
            ; ALREADY A P2 CELL, WE DO NOTHING 
            BRA     .P2END  
.PLR2IS1    CMP.B   #1, D1                  ; IF IS 1 
            BNE     .PLR2IS0                
            CMP.B   #2, D2                  ; NP2 >= 2?
            BLT     .PLR2IS0
            ; ALREADY A P2 CELL, WE DO NOTHING     
            BRA     .P2END   
.PLR2IS0    CLR.B   D7 
.P2END      MOVEM.L (A7)+, D0-D2
            BRA     .NEXT                   ; GO TO NEXT CELL
            
            
            
            
.CHECKPL1   CMP.B   #1, D7
            BNE     .EMPTY
            ; PLR1 RULES
            MOVEM.L D0-D2, -(A7)
            MOVE.L  D0, D2
            SUB.B   D1, D0                  ; NP1-NP2
            CMP.B   #3, D0                  ; IS IT 3?
            BNE     .PLR1IS2
            ; ALREADY A P1 CELL, WE DO NOTHING
            BRA     .P1END
.PLR1IS2    CMP.B   #2, D0                  ; OR IS IT 2
            BNE     .PLR1IS1
            ; ALREADY A P1 CELL, WE DO NOTHING 
            BRA     .P1END  
.PLR1IS1    CMP.B   #1, D0                  ; IF IS 1 
            BNE     .PLR1IS0                
            CMP.B   #2, D2                  ; NP1>=2?
            BLT     .PLR1IS0
            ; ALREADY A P1 CELL, WE DO NOTHING     
            BRA     .P1END   
.PLR1IS0    CLR.B   D7 
.P1END      MOVEM.L (A7)+, D0-D2
            BRA     .NEXT
            
.EMPTY      
            MOVE.L  D1, -(A7)
            OR.B    D0, D1                  ; SEE IF ANY VALUE IN BOTH
            TST.B   D1
            BEQ     .NONG                   ; NO NEIGHBOURS
            MOVE.L  (A7)+, D1
            MOVEM.L D1, -(A7)               ; SAVE COUNTERS
            AND.B   D0, D1                  ; IF D1=3, NP1=NP2
            CMP.B   #3, D1
            BNE     .NORAND                 ; CHECK OTHER POSSIBILITIES
            JSR     UTLRAND
            MOVE.B  D1, D7
            MOVEM.L (A7)+, D1
            BRA     .NEXT
            
.NORAND     MOVEM.L (A7)+, D1
            CMP.B   #3, D1                  ; HAS PL2 EXACTLY 3 NEIGHBOURS?
            BNE     .CHECKP1                ; IF NOT CHECK PL1
            CMP.B   #3, D0                  ; IS NP1 != 3?
            BEQ     .CHECKP1
            MOVE.B  #2, D7                  ; NEW PL2 CELL IS BORN
            BRA     .NEXT
            
.CHECKP1    CMP.B   #3, D0                  ; HAS PL1 EXACTLY 3 NEIGHBOURS?
            BNE     .NEXT                   ; NO CELL IS BORN
            CMP.B   #3, D1                  ; IS NP2 != 3?
            BEQ     .NEXT
            MOVE.B  #1, D7                  ; NEW PL1 CELL IS BORN
.NEXT       MOVE.B  D7, (A3)+               ; EDIT CELL, STORE IT, NEXT CELL
            RTS
 
.NONG       MOVE.L  (A7)+, D1
            BRA     .NEXT
      
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
            ; UNTIL MIDDLE ONLY
            CMP.B   #GRDWIDTH/2, D4         ; MIDDLE OF MATRIX
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
            ; FROM MIDDLE TO END ONLY
            CMP.B   #GRDWIDTH/2, D4         ; MIDDLE OF MATRIX
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
            MOVEM.L (A7)+, D0-D4/A0-A3/A6
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
            MOVEM.L D0-D6/A0, -(A7)
            CLR.W   GRDNPLR1
            CLR.W   GRDNPLR2
            CLR.L   D5
            CLR.L   D6
            MOVE.B  #93, D0
            MOVE.B  #0, D1
            TRAP    #15
            MOVE.B  #80, D0
            MOVE.L  #CLRDKGRN,D1
            TRAP    #15
            MOVE.B  #81, D0
            MOVE.L  #CLRBLACK,D1
            TRAP    #15
            MOVE.L  (GRDDST), A0
            MOVE.W  #GRDYOFF, D2            ; YINITIAL
            MOVE.W  #GRDXOFF, D1            ; XINITIAL
            MOVE.W  D1, D3
            MOVE.W  D2, D4
            ADD.W   #GRDSZCEL, D3
            ADD.W   #GRDSZCEL, D4
            MOVE.W  #GRDHEIGH-1, D5         ; D3: NUMBER OF ROWS - 1
.LOOPY      MOVE.W  #GRDWIDTH-1, D6         ; D2: NUMBER OF COLUMNS - 1
.LOOP       MOVE.B  (A0)+, D0               ; GET VALOR TO PLOT
            MOVEM.L D0-D1, -(A7)            ; STORE REGISTERS, WE NEED THEM
            CMP.B   #1, D0                  ; CHECK IF PLR1 CELL
            BEQ     .PNTP1                  
            CMP.B   #2, D0                  ; CHECK IF PLR2 CELL
            BEQ     .PNTP2
            MOVE.L  #CLRBLACK, D1           ; ELSE SET BLACK
.RESTORE    MOVE.B  #81, D0                 ; PREPARE FILL COLOR
            TRAP    #15                     ; SET IT
            MOVEM.L (A7)+, D0-D1            ; RESTORE VALUES
            MOVE.B  #87, D0                 ; PREPARE CELL
            TRAP    #15                     ; PLOT IT
            ADD.W   #GRDSZCEL, D1           ; ADD FOR NEXT CELL IN ROW
            ADD.W   #GRDSZCEL, D3           
            DBRA.W  D6, .LOOP               ; NEXT CELL
            ADD.W   #GRDSZCEL, D2           ; ADD FOR NEXT ROW
            ADD.W   #GRDSZCEL, D4
            MOVE.W  #GRDXOFF, D1            ; RESET X
            MOVE.W  D1, D3                  
            ADD.W   #GRDSZCEL, D3
            DBRA.W  D5,.LOOPY               ; NEXT ROW
            ; PRINT P1 SCORE NUMBER
            MOVE.B  #81, D0
            MOVE.L  #GRDCOLP1, D1
            TRAP    #15
            MOVE.B  #11, D0                 ; SET POSITION
            MOVE.B  #GRDSCP1X, D1         ; SET COL NR
            ROR.W   #8, D1
            MOVE.B  #GRDSCP1Y, D1           ; SET ROW NR
            TRAP    #15
            MOVE.B  #3, D0
            CLR.L   D1                      ; EVITAM NUMEROS ERRONIS
            MOVE.W  GRDNPLR1, D1            ; P1 SCORE
            TRAP    #15
            ; PRINT OR NOT AN "*"
            MOVE.W  GRDNPLR1, D2            ; STORE IN D2 PLR1
            MOVE.W  GRDNPLR2, D3            ; SOTRE IN D3 PLR2
            CMP.W   D3, D2                  ; CHECK PLR1 SCORE
            BLT     .PL2SCR
            MOVE.B  #11, D0
            MOVE.B  #GRDSCP1X, D1           ; SET COL NR
            ROR.W   #8, D1
            MOVE.B  #GRDSCP1Y-1, D1         ; SET ROW NR
            TRAP    #15
            MOVE.B  #6, D0
            MOVE.B  #$2A, D1
            TRAP    #15
            ; PRINT P2 SCORE NUMBER
.PL2SCR     MOVE.B  #81, D0
            MOVE.L  #GRDCOLP2, D1
            TRAP    #15
            MOVE.B  #11, D0                 ; SET POSITION
            MOVE.B  #GRDSCP2X, D1           ; SET COL NR
            ROR.W   #8, D1
            MOVE.B  #GRDSCP2Y, D1           ; SET ROW NR
            TRAP    #15
            MOVE.B  #3, D0
            CLR.L   D1                      ; EVITAM NUMEROS ERRONIS
            MOVE.W  GRDNPLR2, D1            ; P2 SCORE
            TRAP    #15
            ; PRINT "*" OR NOT FOR PLR2, D2 AND D3 STILL VALID
            CMP.W   D2, D3                  ; CHECK PLR2 SCORE
            BLT     .GENSCR
            MOVE.B  #11, D0
            MOVE.B  #GRDSCP2X, D1           ; SET COL NR
            ROR.W   #8, D1
            MOVE.B  #GRDSCP2Y-1, D1         ; SET ROW NR
            TRAP    #15
            MOVE.B  #6, D0
            MOVE.B  #$2A, D1
            TRAP    #15
            ; PRINT GENERATION NUMBER
.GENSCR     MOVE.B  #81, D0
            MOVE.L  #CLRLTGRN, D1
            TRAP    #15
            MOVE.B  #11, D0                 ; SET POSITION
            MOVE.B  #GRDSCNGX, D1           ; SET COL NR
            ROR.W   #8, D1
            MOVE.B  #GRDSCNGY, D1           ; SET ROW NR
            TRAP    #15
            MOVE.B  #3, D0
            CLR.L   D1                      ; EVITAM NUMEROS ERRONIS
            MOVE.W  GRDNGEN, D1             ; P2 SCORE
            TRAP    #15
            ; CHECK WHO IS WINNING
            MOVEM.L (A7)+, D0-D6/A0         ; RESTORE REGISTERS
            RTS                             
.PNTP1      MOVE.L  #GRDCOLP1, D1           ; SET PLR1 COLOR
            ADDQ.W  #1, GRDNPLR1
            BRA     .RESTORE
.PNTP2      MOVE.L  #GRDCOLP2, D1           ; SET PLR2 COLOR
            ADDQ.W  #1, GRDNPLR2
            BRA     .RESTORE

















*~Font name~Courier New~
*~Font size~10~
*~Tab type~0~
*~Tab size~4~
