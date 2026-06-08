*-----------------------------------------------------------
* Title      : L2A
* Written by : Jaume Adrover Fernández
* Date       : 13/03/2021
* Description: set C.W to |A.W + B.W|
*-----------------------------------------------------------
    ORG    $1000
    A: DC.W 40
    B: DC.W 13
    C: DS.W 1
    
    
START:  MOVE.W A,D0
        ADD.W B,D0
        MOVE.W D0,C
        CMPI.W #0,D0
        BLT SIGN
        BRA FIN
        
SIGN:   SUB.W D0,D0
        SUB.W C,D0
        MOVE.W D0,C                        ; first instruction of program

FIN:
* Put program code here

    SIMHALT             ; halt simulator

* Put variables and constants here

    END    START        ; last line of source



*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
