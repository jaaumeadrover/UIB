*-----------------------------------------------------------
* Title      :Ex L2B
* Written by :Jaume Adrover Fernández
* Date       :15/03/2021
* Description: set C.W to 1 if A.W - B.W > -1 (signed), and 0 otherwise
*-----------------------------------------------------------
    ORG    $1000
    A: DC.W 40
    B: DC.W 13
    C: DS.W 1
START:  
        MOVE A,D0
        CMP.W B,D0
        BGE  ET0               ; first instruction of program
        BRA  ET1
ET0: 
        MOVE.W #1,C
        BRA FIN
ET1:
        MOVE.W #0,C
FIN:
        
    SIMHALT             ; halt simulator

* Put variables and constants here

    END    START        ; last line of source

*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
