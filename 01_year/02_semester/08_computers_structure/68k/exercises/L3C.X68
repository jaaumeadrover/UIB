*-----------------------------------------------------------
* Title      : Ex L3C
* Written by : Jaume Adrover Fernández
* Date       : 22/03/2021
* Description: Calculate P=A*B
*-----------------------------------------------------------
    ORG    $1000
    A: DC.W 2
    B: DC.W 3
    P: DS.W 1
START:       
    MOVE.W B,D0
    SUBQ.W #1,D0
    MOVE.W A,D1
    CLR.W  P
LOOP:
    ADD.W D1,P
    DBRA D0,LOOP
               
    SIMHALT             ; halt simulator

* Put variables and constants here

    END    START        ; last line of source

*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
