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
            INCLUDE "GRID.X68"              ; GRID MANAGEMENT
            INCLUDE "GOL.X68"               ; GAME OF LIFE APPLICATION

; --- INITIALIZE --------------------------------------------------------------

START       JSR     SYSINIT                 ; INITIALIZE SYSTEM CODE
            JSR     GOLINIT                 ; INITIALIZE GAME OF LIFE

; --- UPDATE ------------------------------------------------------------------

.LOOP       TRAP    #MOUTRAP                ; READ MOUSE
            JSR     GOLUPD                  ; UPDATE APPLICATION

; --- PLOT --------------------------------------------------------------------

            JSR     GOLPLOT                 ; PLOT APPLICATION
            TRAP    #SCRTRAP                ; SHOW GRAPHICS
            BRA     .LOOP
            SIMHALT

; --- VARIABLES ---------------------------------------------------------------

            INCLUDE "SYSVAR.X68"            ; SYSTEM VARIABLES
            INCLUDE "VAR.X68"               ; USER VARIABLES

            END    START