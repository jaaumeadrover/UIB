; =============================================================================
; TITLE    : GAME OF LIFE APPLICATION
; AUTHOR   : ANTONI BURGUERA
; HISTORY  : 05-JULY-2021 - CREATION
; COMMENTS : THIS IS AN APPLICATION. SO, IT IMPLEMENTS INITIALIZATION, UPDATE
;            AND PLOT, AS WELL AS SOME AUXILIARY SUBROUTINES.
; =============================================================================

; -----------------------------------------------------------------------------
GOLINIT
; INITIALIZE GAME OF LIFE APPLICATION AS FOLLOWS:
; * MAKES THE INTERNAL STATE TO BE "PAUSED/STOPPED"
; * INITIALIZES THE USER INTERFACE (BY INITIALIZING THE BUTTONS LIST AND
;   ADDING BUTTONS INTO IT)
; * INITIALIZES THE GRID.
; * INITIALIZES THE UTILITY CODE.
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------
            MOVEM.L D0-D7/A0-A6,-(A7)
            MOVE.B  #GOLSPAUS,(GOLSTATE)
            JSR     BTLINIT
            LEA     .SDBLIST,A0
.LOOP       MOVE.L  (A0)+,A1
            CMP.L   #-1,A1
            BEQ     .END
            JSR     BTLADD
            BRA     .LOOP
.END        JSR     GRDINIT
            JSR     UTLINIT
            MOVEM.L (A7)+,D0-D7/A0-A6
            RTS
.SDBLIST    DC.L    GOLCLRBT,GOLRUNBT,GOLSTPBT,GOLSTEBT,GOLLLFBT,GOLLRTBT
            DC.L    GOLLOABT,GOLSAVBT,-1

; -----------------------------------------------------------------------------
GOLUPD
; UPDATE GAME OF LIFE APPLICATION AS FOLLOWS:
; * UPDATES THE BUTTON LIST
; * EXECUTES CODE DEPENDING ON THE CURRENT APPLICATION STATE.
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------
            MOVEM.L A0-A1/D0-D1,-(A7)
            JSR     BTLUPD
            LEA     .JMPTBL,A0
            CLR.L   D0
            MOVE.B  (GOLSTATE),D0
            LSL.B   #2,D0
            MOVE.L  (A0,D0),A0
            JSR     (A0)
            MOVEM.L (A7)+,A0-A1/D0-D1
            RTS
.JMPTBL     DC.L    GRDMUPD,GOLDORUN

; -----------------------------------------------------------------------------
GOLPLOT
; PLOTS THE APPLICATION BY CALLING BTLPLOT AND GRDPLOT
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------
            JSR     BTLPLOT
            JMP     GRDPLOT

; -----------------------------------------------------------------------------
GOLDORUN
; EXECUTES THE RUN STATE. IT ALSO CHECKS IF THE MAXIMUM NUMBER OF GENERATIONS
; IS REACHED AND, IF SO, ANNOUNCES THE WINNER.
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------
            MOVEM.L D0/A1,-(A7)
            CMP.W   #GOLMAXGN,(GRDNGEN)
            BLT     .DOIT
            LEA     .TIE,A1
            MOVE.W  (GRDNPLR1),D0
            CMP.W   (GRDNPLR2),D0
            BEQ     .PLAYSOUND
            LEA     .P1WINS,A1
            BGT     .PLAYSOUND
            LEA     .P2WINS,A1
.PLAYSOUND  MOVE.B  #70,D0
            TRAP    #15
.NOSOUND    JSR     GOLTOPAU
.DOIT       JSR     GRDRUPD
            MOVEM.L (A7)+,D0/A1
            RTS
.P1WINS     DC.B    'P1WINS.WAV',0
.P2WINS     DC.B    'P2WINS.WAV',0
.TIE        DC.B    'TIE.WAV',0
            DS.W    0

; -----------------------------------------------------------------------------
GOLTORUN
; SWITCHES TO RUN STATE
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------
            CLR.W   (GRDNGEN)               ; CLEAR GENERATIONS COUNT
            MOVE.B  #GOLSEXEC,(GOLSTATE)
            RTS

; -----------------------------------------------------------------------------
GOLTOPAU
; SWITCHES TO PAUSE STATE
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------
            MOVE.B  #GOLSPAUS,(GOLSTATE)
            RTS

; --- STATIC DATA -------------------------------------------------------------

; CLEAR BUTTON SDB

GOLCLRBT    DC.W    BTLVEMRG                ; YTOP
            DC.W    0*(SCRWIDTH/4)+BTLHOMRG ; XLEFT
            DC.W    BTLVERSZ                ; HEIGHT
            DC.W    SCRWIDTH/4-2*BTLHOMRG   ; WIDTH
; TODO: PLACE THE CALLBACK HERE
            DC.L    GOLINIT                 ;CALLBACK
            DC.B    'CLEAR',0               ; TEXT
            DS.W    0

; RUN BUTTON SDB

GOLRUNBT    DC.W    BTLVEMRG                ; YTOP
            DC.W    1*(SCRWIDTH/4)+BTLHOMRG ; XLEFT
            DC.W    BTLVERSZ                ; HEIGHT
            DC.W    SCRWIDTH/4-2*BTLHOMRG   ; WIDTH
; TODO: PLACE THE CALLBACK HERE
            DC.L    GOLTORUN                ;CALLBACK
            DC.B    'RUN',0                 ; TEXT
            DS.W    0

; STOP BUTTON SDB

GOLSTPBT    DC.W    BTLVEMRG                ; YTOP
            DC.W    2*(SCRWIDTH/4)+BTLHOMRG ; XLEFT
            DC.W    BTLVERSZ                ; HEIGHT
            DC.W    SCRWIDTH/4-2*BTLHOMRG   ; WIDTH
; TODO: PLACE THE CALLBACK HERE
            DC.L    GOLTOPAU                ;CALLBACK
            DC.B    'STOP',0                ; TEXT
            DS.W    0

; STEP BUTTON SDB

GOLSTEBT    DC.W    BTLVEMRG                ; YTOP
            DC.W    3*(SCRWIDTH/4)+BTLHOMRG ; XLEFT
            DC.W    BTLVERSZ                ; HEIGHT
            DC.W    SCRWIDTH/4-2*BTLHOMRG   ; WIDTH
; TODO: PLACE THE CALLBACK HERE
            DC.L    GOLDORUN                        ;CALLBACK(ONLY 1 CALL)
            DC.B    'STEP',0                ; TEXT
            DS.W    0

; LOAD LEFT BUTTON SDB

GOLLLFBT    DC.W    2*BTLVEMRG+BTLVERSZ     ; YTOP
            DC.W    0*(SCRWIDTH/4)+BTLHOMRG ; XLEFT
            DC.W    BTLVERSZ                ; HEIGHT
            DC.W    SCRWIDTH/4-2*BTLHOMRG   ; WIDTH
; TODO: PLACE THE CALLBACK HERE
            DC.L    GRDLLEFT                 ;CALLBACK
            DC.B    'LOAD LEFT',0           ; TEXT
            DS.W    0

; LOAD RIGHT BUTTON SDB

GOLLRTBT    DC.W    2*BTLVEMRG+BTLVERSZ     ; YTOP
            DC.W    1*(SCRWIDTH/4)+BTLHOMRG ; XLEFT
            DC.W    BTLVERSZ                ; HEIGHT
            DC.W    SCRWIDTH/4-2*BTLHOMRG   ; WIDTH
; TODO: PLACE THE CALLBACK HERE
            DC.L    GRDLRGT                 ;CALLBACK
            DC.B    'LOAD RIGHT',0          ; TEXT
            DS.W    0

; LOAD ALL BUTTON SDB

GOLLOABT    DC.W    2*BTLVEMRG+BTLVERSZ     ; YTOP
            DC.W    2*(SCRWIDTH/4)+BTLHOMRG ; XLEFT
            DC.W    BTLVERSZ                ; HEIGHT
            DC.W    SCRWIDTH/4-2*BTLHOMRG   ; WIDTH
; TODO: PLACE THE CALLBACK HERE
            DC.L    GRDLOAD                 ;CALLBACK
            DC.B    'LOAD ALL',0            ; TEXT
            DS.W    0

; SAVE BUTTON SDB

GOLSAVBT    DC.W    2*BTLVEMRG+BTLVERSZ     ; YTOP
            DC.W    3*(SCRWIDTH/4)+BTLHOMRG ; XLEFT
            DC.W    BTLVERSZ                ; HEIGHT
            DC.W    SCRWIDTH/4-2*BTLHOMRG   ; WIDTH
; TODO: PLACE THE CALLBACK HERE
            DC.L    GRDSAVE
            DC.B    'SAVE',0          ; TEXT
            DS.W    0
*~Font name~Courier New~
*~Font size~10~
*~Tab type~0~
*~Tab size~4~
