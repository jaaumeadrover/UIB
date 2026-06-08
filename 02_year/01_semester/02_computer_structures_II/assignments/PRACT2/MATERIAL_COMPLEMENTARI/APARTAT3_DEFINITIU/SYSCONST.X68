; =============================================================================
; TITLE    : SYSTEM CONSTANTS
; AUTHOR   : ANTONI BURGUERA
; HISTORY  : 05-JULY-2021 - CREATION
; COMMENTS : * MOUVAL AND MOUEDGE FORMAT IS AS FOLLOWS:
;              +---+---+---+---+---+---+---+---+
;              | 7 | 6 | 5 | 4 | 3 | 2 | 1 | 0 |
;              +---+---+---+---+---+---+---+---+
;              \-------------------/ |   |   |
;                        |           |   |   +-- LEFT MOUSE BUTTON
;                        |           |   +------ RIGHT MOUSE BUTTON
;                        |           +---------- CENTRAL MOUSE BUTTON
;                        +---------------------- 0
;              A "1" IN ONE OF THESE BITS IN MOUVAL MEANS "BUTTON PRESSED".
;              A "0" IN ONE OF THESE BITS IN MOUVAL MEANS "BUTTON NOT PRESSED".
;              A "1" IN ONE OF THESE BITS IN MOUEDGE MEANS "BUTTON CLICKED".
;              A "0" IN ONE OF THESE BITS IN MOUEDGE MEANS "BUTTON NOT CLICKED".
;              A "CLICK" IS A CHANGE FROM 0 TO 1 IN SUBSEQUENT UPDATES.
;              BOTH MOUVEL AND MOUEDGE ARE UPDATED BY TRAP #MOUTRAP
;
;            * MASKS AND BIT POSITIONS ARE DEFINED TO EASE MOUSE ACCESS. FOR
;              EXAMPLE, ONE CAN DO THE FOLLOWING TO CHECK IF THE LEFT BUTTON
;              IS PRESSED:
;
;              BTST.L #MOUBITLF,(MOUVAL)
;              BNE ...
;
;              ALTERNATIVELY, ONE CAN ALSO DO THE FOLLOWING DEPENDING ON THE
;              CONTEXT:
;
;              MOVE.B  (MOUVAL),D0
;              AND.B   #MOUMSKLF,D0
;              BNE ...
; =============================================================================

; --- SCREEN RELATED CONSTANTS ------------------------------------------------

SCRTRAP     EQU     0                       ; SCREEN REFRESH TRAP NUMBER
SCRWIDTH    EQU     800                     ; SCREEN WIDTH (PIXELS)
SCRHEIGH    EQU     600                     ; SCREEN HEIGHT (PIXELS)

; --- MOUSE RELATED CONSTANTS -------------------------------------------------

MOUTRAP     EQU     1                       ; MOUSE QUERY TRAP NUMBER
MOUMSKLF    EQU     %00000001               ; LEFT MOUSE BUTTON MASK
MOUMSKRT    EQU     %00000010               ; RIGHT MOUSE BUTTON MASK
MOUMSKMD    EQU     %00000100               ; MIDDLE MOUSE BUTTON MASL
MOUBITLF    EQU     0                       ; LEFT MOUSE BUTTON BIT POSITION
MOUBITRT    EQU     1                       ; RIGHT MOUSE BUTTON BIT POSITION
MOUBITMD    EQU     2                       ; MIDDLE MOUSE BUTTON BIT POSITION