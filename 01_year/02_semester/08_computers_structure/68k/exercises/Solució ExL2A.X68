*-----------------------------------------------------------
* Title      :Solució ExL2A
* Written by :Jaume Adrover
* Date       :15/03/2021
* Description: set C.W to |A.W + B.W|
*-----------------------------------------------------------
    ORG    $1000
    A: DC.W 40
    B: DC.W 13
    C: DS.W 1
    
START:                 
    MOVE.W A,D0
    ADD.W  B,D0
    CMP.W  #0,D0
    BPL SIGN
* NEG.W D0
* MULS(SIGNED) // MULU(UNSIGNED) MULTIPLICA
* NOT.W D0

    NOT.W D0
    ADD.W #1,D0
SIGN:
    MOVE.W D0,C
    
    
* Put program code here

    SIMHALT             ; halt simulator

* Put variables and constants here

    END    START        ; last line of source

*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
