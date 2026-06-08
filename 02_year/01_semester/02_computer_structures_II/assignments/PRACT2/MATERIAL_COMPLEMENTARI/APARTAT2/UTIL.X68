; =============================================================================
; TITLE    : UTILITY SUBROUTINES
; AUTHOR   : ANTONI BURGUERA
; HISTORY  : 05-JULY-2021 - CREATION
; COMMENTS : GENERAL PURPOSE SUBROUTINES.
; =============================================================================

; -----------------------------------------------------------------------------
UTLINIT
; INITIALIZES UTILITIES.
; INPUT    : NONE
; OUTPUT   : NONE
; MODIFIES : NONE
; -----------------------------------------------------------------------------
            CLR.B   (UTLRNDIX)
            RTS

; -----------------------------------------------------------------------------
UTLSTRLN
; COMPUTES THE LENGTH OF A ZERO-TERMINATED STRING
; INPUT    - A1 POINTER TO THE STRING
; OUTPUT   - D0.W STRING LENGTH
; MODIFIES - NONE (ASIDE OF OUTPUT)
; -----------------------------------------------------------------------------
            MOVE.L  A1,-(A7)
            CLR.W   D0
.LOOP       TST.B   (A1)+
            BEQ     .END
            ADDQ.W  #1,D0
            BRA     .LOOP
.END        MOVE.L  (A7)+,A1
            RTS

; -----------------------------------------------------------------------------
UTLRAND
; PROVIDES A PSEUDO-RANDOM COIN FLIP (RETURNS 1 OR 2)
; INPUT    - NONE
; OUTPUT   - D1.L RANDOM VALUE (ONLY BYTE)
; MODIFIES - NONE (ASIDE OF OUTPUT)
; -----------------------------------------------------------------------------
            MOVE.L  A0,-(A7)
            LEA     .RANDVEC,A0
            CLR.L   D1
            MOVE.B  (UTLRNDIX),D1
            MOVE.B  (A0,D1.L),D1
            ADDQ.B  #1,(UTLRNDIX)
            MOVE.L  (A7)+,A0
            RTS

.RANDVEC    DC.B    2, 2, 2, 2, 1, 2, 1, 2
            DC.B    1, 1, 1, 2, 1, 1, 1, 2
            DC.B    2, 1, 2, 1, 1, 2, 2, 1
            DC.B    2, 1, 2, 1, 1, 1, 1, 2
            DC.B    2, 2, 1, 1, 2, 2, 1, 1
            DC.B    2, 1, 2, 2, 2, 1, 1, 1
            DC.B    2, 2, 1, 1, 1, 1, 1, 2
            DC.B    1, 1, 1, 2, 1, 2, 1, 2
            DC.B    2, 2, 2, 2, 1, 2, 1, 2
            DC.B    1, 1, 2, 2, 2, 1, 2, 1
            DC.B    2, 2, 2, 1, 1, 2, 1, 1
            DC.B    1, 2, 1, 1, 1, 2, 1, 2
            DC.B    1, 2, 2, 1, 2, 1, 2, 2
            DC.B    1, 2, 1, 2, 1, 1, 2, 1
            DC.B    1, 2, 1, 1, 1, 1, 1, 1
            DC.B    2, 1, 1, 2, 1, 1, 1, 2
            DC.B    2, 1, 1, 1, 1, 2, 1, 1
            DC.B    1, 1, 2, 1, 1, 2, 1, 2
            DC.B    2, 2, 1, 1, 2, 2, 2, 2
            DC.B    1, 1, 2, 1, 2, 2, 1, 2
            DC.B    1, 2, 1, 2, 2, 2, 2, 1
            DC.B    1, 1, 2, 2, 1, 1, 2, 1
            DC.B    2, 2, 1, 2, 1, 2, 1, 1
            DC.B    2, 1, 2, 1, 1, 2, 1, 2
            DC.B    1, 1, 2, 2, 2, 1, 1, 1
            DC.B    1, 1, 2, 2, 1, 2, 1, 2
            DC.B    1, 2, 1, 1, 1, 2, 1, 1
            DC.B    2, 1, 1, 2, 1, 2, 2, 2
            DC.B    2, 1, 1, 1, 2, 2, 1, 1
            DC.B    2, 2, 2, 1, 2, 2, 1, 2
            DC.B    1, 2, 1, 1, 2, 1, 2, 2
            DC.B    2, 2, 2, 1, 1, 1, 1, 2