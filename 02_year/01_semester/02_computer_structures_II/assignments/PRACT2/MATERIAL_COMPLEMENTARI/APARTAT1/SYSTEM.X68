; =============================================================================
; TITLE    : SYSTEM MANAGEMENT (SYSTEM CODE)
; AUTHOR   : ANTONI BURGUERA
; HISTORY  : 05-JULY-2021 - CREATION
; COMMENTS : IT IS DIVIDED IN THREE BLOCKS:
;            - SYS : GENERIC.
;            - SCR : SCREEN MANAGEMENT.
;            - MOU : MOUSE MANAGEMENT
;            THE FORMAT OF MOUVAL AND MOUEDGE IS DESCRIBED IN SYSCONST.
; =============================================================================

; -----------------------------------------------------------------------------
SYSINIT
; INITIALIZES THE SYSTEM AS FOLLOWS:
; * DISABLES INTERRUPTS
; * CALLS SCRINIT
; * CALLS MOUINIT
; * ENABLES SIMULATOR EXCEPTION PROCESSING
; * SWITCHES TO USER
; INPUT    - NONE
; OUTPUT   - NONE
; MODIFIES - NONE
; NOTE     - NOTE THAT INTERRUPTS REMAIN DISABLED AFTER THIS SUBROUTINE
; -----------------------------------------------------------------------------

; TODO : PLACE YOUR CODE HERE

    MOVE.W SR,-(A7)

    AND.W #$DFFF,(A7)

    OR.W #$0700,(A7)

    ;GUARDAM REGISTRES
    MOVEM.L D0-D1, -(A7)

    ;DESACTIVAR INTERRUPCIONS
    ;OR.W #$0700,SR

    ;CRIDAR A SCRINIT I MOUINIT
    JSR SCRINIT
    JSR MOUINIT

    ;ACTIVAR SIMULADOR DE EXEPCIO
    MOVE.B #32,D0
    MOVE.B #5,D1
    TRAP #15


    ;RESTAURAM REGISTRES
    MOVEM.L (A7)+,D0-D1

    ;RTE, RESTAURA SR I PC
    RTE




; =============================================================================
; MOUSE MANAGEMENT (SYSTEM CODE)
; =============================================================================

; -----------------------------------------------------------------------------
MOUINIT
; INITIALIZES MOUSE AS FOLLOWS:
; * STORES CURRENT MOUSE POSITION AND BUTTON STATUS INTO MOUY, MOUX AND MOUVAL
; * CLEARS MOUEDGE
; * INSTALLS MOUREAD INTO TRAP #MOUTRAP
; INPUT    - NONE
; OUTPUT   - NONE
; MODIFIES - ALL REGISTERS CAN BE MODIFIED
; -----------------------------------------------------------------------------

; TODO : PLACE YOUR CODE HERE
            
     MOVEM.L D0-D1, -(A7)

    ;OBTENIM LES COORDS DEL MOUSE
    MOVE.B #61,D0
    CLR.B D1
    TRAP #15

    ;GUARDAM LES VARIABLES MOUY AND MOUVAL
    MOVE.L D1,(MOUY)
    MOVE.B D0,(MOUVAL)


    ;CLEAR MOUEDGE 
    MOVE.B #0,(MOUEDGE)


    ;INSTALL MOUREAD INTO TRAP #MOUTRAP 
    MOVE.L #MOUREAD,($80+MOUTRAP*4)


    ;RESTAURAM I TORNAM
    MOVEM.L (A7)+, D0-D1
    RTS


; -----------------------------------------------------------------------------
MOUREAD
; MOUSE READING TRAP SERVICE ROUTINE. IT DOES THE FOLLOWING:
; * STORES CURRENT MOUSE POSITION INTO MOUY AND MOUX.
; * STORES CURRENT BUTTON STATUS INTO MOUVAL
; * COMPUTES AND STORES MOUEDGE
; INPUT    - NONE
; OUTPUT   - NONE
; MODIFIES - NONE
; -----------------------------------------------------------------------------

; TODO : PLACE YOUR CODE HERE

            MOVEM.L D0-D2,-(A7)
            
            MOVE.B #61,D0
            CLR.W     D1
            TRAP #15 ;STORE CURRENT MOUSE POSITION IN D1.L(Y,X), D0.B(XXXX210)
            
            MOVE.L D1,(MOUY)
            ;VERIFICAMOS SI DEBEMOS CAMBIAR EL MOUEDGE
            NOT.B MOUVAL
            MOVE.B D0,D2
            
            ;MIRAMOS SI HA CAMBIADO DE 0 A 1
            AND.B  MOUVAL,D2
            MOVE.B D2,(MOUEDGE)
            MOVE.B D0,(MOUVAL)
            
            
            
            ;MASCARA BITS(TOTS MENYS ELS 3 DARRERS)
            ;MOVE.B D0,(MOUVAL)
            
            
            MOVEM.L (A7)+,D0-D2
            
            RTE









; =============================================================================
; SCREEN MANAGEMENT (SYSTEM CODE)
; =============================================================================

; -----------------------------------------------------------------------------
SCRINIT
; INITIALIZES THE SCREEN, SCREEN-RELATED INTERRUPT AND VARS AS FOLLOWS:
; * INSTALLS SCRUPD INTO TRAP #SCRTRAP
; * SETS OUTPUT RESOLUTION TO SCRWIDTH*SCRHEIGH
; * SETS OUTPUT TO WINDOWED MODE
; * ENABLES DOUBLE BUFFER AND CLEARS BOTH SCREEN BUFFERS
; INPUT    - NONE
; OUTPUT   - NONE
; MODIFIES - ALL REGISTERS CAN BE MODIFIED
; -----------------------------------------------------------------------------
            MOVE.L  #SCRUPD,($80+SCRTRAP*4) ; INSTALL TRAP
            MOVE.L  #SCRWIDTH<<16|SCRHEIGH,D1 ; SET RESOLUTION
            MOVE.B  #33,D0
            TRAP    #15
            MOVE.L  #1,D1                   ; SET WINDOWED MODE
            TRAP    #15
            MOVE.W  #$FF00,D1               ; CLEAR VISIBLE BUFFER
            MOVE.B  #11,D0
            TRAP    #15
            MOVE.B  #17,D1                  ; ENABLE DOUBLE BUFFER
            MOVE.B  #92,D0
            TRAP    #15
            MOVE.W  #$FF00,D1               ; CLEAR HIDDEN BUFFER
            MOVE.B  #11,D0
            TRAP    #15
            RTS

; -----------------------------------------------------------------------------
SCRUPD
; TRAP SERVICE ROUTINE IN CHARGE OF VISUALIZING CURRENT FRAME AND CLEARING
; BUFFER FOR THE NEXT ONE. PERFORMS TWO TASKS:
; * CLEARS ACTIVE (HIDDEN) GRAPHICS BUFFER
; * SWITCHES ACTIVE AND VISIBLE GRAPHICS BUFFER
; INPUT    - NONE
; OUTPUT   - NONE
; MODIFIES - NONE
; -----------------------------------------------------------------------------
            MOVEM.W D0-D1,-(A7)
            MOVE.B  #94,D0                  ; UPDATE DOUBLE BUFFER
            TRAP    #15
            MOVE.W  #$FF00,D1               ; CLEAR HIDDEN BUFFER
            MOVE.B  #11,D0
            TRAP    #15
            MOVEM.W (A7)+,D0-D1
            RTE





*~Font name~Courier New~
*~Font size~10~
*~Tab type~0~
*~Tab size~4~
