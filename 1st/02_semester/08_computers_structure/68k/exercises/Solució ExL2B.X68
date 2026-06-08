*-----------------------------------------------------------
* Title      : Solució EXL2B
* Written by :Jaume Adrover Fernández
* Date       :15/03/2021
* Description:
*-----------------------------------------------------------
    ORG    $1000
    A: DC.W 40
    B: DC.W 13
    C: DS.W 1
START:                  
     MOVE.W A,D0
     SUB.W  B,D0
     CMP.W  #-1,D0
     BGT ET0
     MOVE.W #0,C
     BRA FIN
ET0:
     MOVE.W #1,C
FIN: SIMHALT
    
    SIMHALT             ; halt simulator

* Put variables and constants here

    END    START        ; last line of source

*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
