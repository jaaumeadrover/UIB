; =============================================================================
; TITLE    : GAME OF LIFE APPLICATION
; AUTHOR   : ANTONI BURGUERA
; CREATION : 05-JULY-2021
; =============================================================================

            ORG     $1000

; --- CODE INCLUDES -----------------------------------------------------------

            INCLUDE "SYSCONST.X68"          ; SYSTEM CONSTANTS
            INCLUDE "SYSTEM.X68"            ; SYSTEM CODE

; --- INITIALIZE --------------------------------------------------------------

START       JSR     SYSINIT                 ; INITIALIZE SYSTEM CODE

; --- UPDATE ------------------------------------------------------------------

.LOOP       TRAP    #MOUTRAP                ; READ MOUSE

            ; SET COLOR DEPENDING ON THE MOUSE BUTTON PRESSED

            CLR.L   D1                      ; PREPARE BLACK COLOR IN ADVANCE
            MOVE.B  (MOUVAL),D0             ; GET MOUSE BUTTONS STATUS
            BEQ     .SETCOL                 ; NO BUTTON: GO TO SET COLOR
            MOVE.L  #$00FF0000,D1           ; PREPARE BLUE COLOR IN ADVANCE
            BTST.L  #MOUBITLF,D0            ; IS IT LEFT BUTTON?
            BNE     .SETCOL                 ; IF LEFT: GO TO SET COLOR
            MOVE.L  #$0000FF00,D1           ; PREPARE GREEN COLOR IN ADVANCE
            AND.B   #MOUMSKRT,D0            ; IS IT RIGHT BUTTON?
            BNE     .SETCOL                 ; IF RIGHT: GO TO SET COLOR
            MOVE.L  #$000000FF,D1           ; RED. MID BUTTON IS ASSURED HERE
.SETCOL     MOVE.B  #81,D0                  ; SET FILL COLOR TRAP ID
            TRAP    #15                     ; SET FILL COLOR

            ; PLACE THE TEXT CURSOR AT (0,0)

            MOVE.B  #11,D0                  ; TASK ID TO POSITION TEXT
            TRAP    #15                     ; POSITION TEXT AT (0,0)

            ; PRINT THE MOUSE X COORDINATE

            CLR.L   D1                      ; POS=(0,0) AND CLEAR ALL BITS
            MOVE.B  #3,D0                   ; TASK ID TO PRINT A NUMBER
            MOVE.W  (MOUX),D1               ; GET THE MOUSE X COORDINATE
            TRAP    #15                     ; PRINT THE X COORDINATE

            ; PRINT A COMMA

            MOVE.B  #6,D0                   ; TASK ID TO PRINT A CHARACTER
            MOVE.B  #',',D1                 ; CHARACTER TO PRINT
            TRAP    #15                     ; PRINT THE COMMA

            ; PRINT THE MOUSE Y COORDINATE

            MOVE.B  #3,D0                   ; TASK ID TO PRINT A NUMBER
            MOVE.W  (MOUY),D1               ; GET THE MOUSE Y COORDINATE
            TRAP    #15                     ; PRINT THE Y COORDINATE

; --- PLOT --------------------------------------------------------------------


            TRAP    #SCRTRAP                ; SHOW GRAPHICS
            BRA     .LOOP
            SIMHALT

; --- VARIABLES ---------------------------------------------------------------

            INCLUDE "SYSVAR.X68"            ; SYSTEM VARIABLES

            END    START
*~Font name~Courier New~
*~Font size~10~
*~Tab type~0~
*~Tab size~4~
