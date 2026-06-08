; =============================================================================
; TITLE    : GAME OF LIFE APPLICATION
; AUTHOR   : ANTONI BURGUERA
; CREATION : 05-JULY-2021
; =============================================================================

            ORG     $1000

; --- CODE INCLUDES -----------------------------------------------------------

            INCLUDE "SYSCONST.X68"          ; SYSTEM CONSTANTS
            INCLUDE "SYSTEM.X68"            ; SYSTEM CODE
            INCLUDE "CONST.X68"             ; USER CONSTANTS
            INCLUDE "UTIL.X68"              ; UTILITY CODE
            INCLUDE "BUTTON.X68"            ; BUTTON MANAGEMENT
            INCLUDE "BTNLIST.X68"           ; BUTTON LIST MANAGEMENT

; --- INITIALIZE --------------------------------------------------------------

START       JSR     SYSINIT                 ; INITIALIZE SYSTEM CODE
            JSR     UIINIT                  ; INITIALIZE THE USER INTERFACE

; --- UPDATE ------------------------------------------------------------------

.LOOP       TRAP    #MOUTRAP                ; READ MOUSE
            JSR     BTLUPD                  ; UPDATE THE USER INTERFACE

; --- PLOT --------------------------------------------------------------------

            JSR     BTLPLOT                 ; PLOT THE USER INTERFACE
            TRAP    #SCRTRAP                ; SHOW GRAPHICS
            BRA     .LOOP
            SIMHALT

; --- CALLBACKS ---------------------------------------------------------------

; -----------------------------------------------------------------------------
CALP1WIN
; SUBROUTINE (USED AS CALLBACK) THAT PLAYS THE SOUND "PLAYER 1 WINS"
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------
            MOVEM.L D0/A1,-(A7)
            LEA     .FNAME,A1
            MOVE.B  #70,D0
            TRAP    #15
            MOVEM.L (A7)+,D0/A1
            RTS
.FNAME      DC.B    'P1WINS.WAV',0
            DS.W    0

; -----------------------------------------------------------------------------
CALP2WIN
; SUBROUTINE (USED AS CALLBACK) THAT PLAYS THE SOUND "PLAYER 2 WINS"
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------
            MOVEM.L D0/A1,-(A7)
            LEA     .FNAME,A1
            MOVE.B  #70,D0
            TRAP    #15
            MOVEM.L (A7)+,D0/A1
            RTS
.FNAME      DC.B    'P2WINS.WAV',0
            DS.W    0

; -----------------------------------------------------------------------------
CALTIE
; SUBROUTINE (USED AS CALLBACK) THAT PLAYS A SOUND SIMBOLIZING A TIE (SORT OF)
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------
            MOVEM.L D0/A1,-(A7)
            LEA     .FNAME,A1
            MOVE.B  #70,D0
            TRAP    #15
            MOVEM.L (A7)+,D0/A1
            RTS
.FNAME      DC.B    'TIE.WAV',0
            DS.W    0

; --- SUBROUTINES -------------------------------------------------------------

; -----------------------------------------------------------------------------
UIINIT
; INITS THE USER INTERFACE BY INSERTINT THE THREE BUTTONS INTO THE BUTTON LIST
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------
            MOVEM.L A0/A1,-(A7)
            JSR     BTLINIT
            LEA     .SDBLIST,A0
.LOOP       MOVE.L  (A0)+,A1
            CMP.L   #-1,A1
            BEQ     .END
            JSR     BTLADD
            BRA     .LOOP
.END        MOVEM.L (A7)+,A0/A1
            RTS
.SDBLIST    DC.L    SDBP1W,SDBP2W,SDBTIE,-1

; --- BUTTONS STATIC DATA BLOCKS ----------------------------------------------

SDBP1W      DC.W    (SCRHEIGH-3*BTLVERSZ-2*BTLVEMRG)/2 ; YTOP COORDINATE
            DC.W    (SCRWIDTH-200)/2        ; XLEFT COORDINATE
            DC.W    BTLVERSZ                ; HEIGHT
            DC.W    200                     ; WIDTH
            DC.L    CALP1WIN                ; CALLBACK
            DC.B    'PLAYER 1 WINS!',0      ; BUTTON TEXT

SDBP2W      DC.W    (SCRHEIGH-BTLVERSZ)/2   ; YTOP COORDINATE
            DC.W    (SCRWIDTH-200)/2        ; XLEFT COORDINATE
            DC.W    BTLVERSZ                ; HEIGHT
            DC.W    200                     ; WIDTH
            DC.L    CALP2WIN                ; CALLBACK
            DC.B    'PLAYER 2 WINS!',0      ; BUTTON TEXT

SDBTIE      DC.W    (SCRHEIGH+BTLVERSZ)/2+BTLVEMRG ; YTOP COORDINATE
            DC.W    (SCRWIDTH-200)/2        ; XLEFT COORDINATE
            DC.W    BTLVERSZ                ; HEIGHT
            DC.W    200                     ; WIDTH
            DC.L    CALTIE                  ; CALLBACK
            DC.B    'TIE',0                 ; BUTTON TEXT

; --- VARIABLES ---------------------------------------------------------------

            INCLUDE "SYSVAR.X68"            ; SYSTEM VARIABLES
            INCLUDE "VAR.X68"               ; USER VARIABLES

            END    START