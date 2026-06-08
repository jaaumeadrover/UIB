; =============================================================================
; TITLE    : FIRST LAB ASSIGNMENT
; AUTHOR   : ANTONI BURGUERA
; COMMENTS : THIS FILE CANNOT BE MODIFIED
; HISTORY  : 24/SEPTEMBER/2020 - CREATION
; =============================================================================

            ORG     $1000

            ; --- CONSTANTS, MACROS AND CODE INCLUDES -------------------------

            INCLUDE "CONST.X68"             ; CONST. MUST BE BEFORE THEIR USE
            INCLUDE "MAP.X68"               ; MEMORY MAPPED HARDWARE CODE
            INCLUDE "AUXILIAR.X68"          ; AUXILIARY/MISCELLANY CODE

            ; --- MAIN PROGRAM ------------------------------------------------

            ; --- INITIALIZATION

START       MOVE.B  #MAPGETAD|MAPSHWHW,D0   ; REQUEST HARD WIN AND ADDRESSES
            LEA     MAPADDR,A0              ; PREPARE STORAGE PARAMETER
            JSR     MAPINIT                 ; SHOW WIN AND GET ADDRESSES
            MOVE.L  (MAPBUTAD), A0          ; GET BUTTONS MAPPING ADDRESS
            MOVE.L  (MAPSEGAD), A1          ; GET DISPLAYS MAPPING ADDRESS

            ; --- INFINITE LOOP

.LOOP       JSR     MAPPRBIT                ; UPDATE DISP. ACCORDING TO BUTTONS
            BRA     .LOOP                   ; REPEAT UNTIL THE END OF TIMES

            ; --- END OF SIMULATION -------------------------------------------

            SIMHALT                         ; THIS LINE WILL NEVER BE REACHED

            ; --- VARIABLE DECLARATIONS ---------------------------------------

            INCLUDE "VARS.X68"

            END     START
*~Font name~Courier New~
*~Font size~10~
*~Tab type~0~
*~Tab size~4~
