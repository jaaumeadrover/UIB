*-----------------------------------------------------------
* Title      : L3A
* Written by : Jaume Adrover Fernández
* Date       : 22/03/2021
* Description: Calculate the sum of N first naturan numbers (Bxx)
*-----------------------------------------------------------
    ORG    $1000
    N: EQU 5
    A: DS.W 1
    
START:     
    MOVE.W #0,D0
    CLR D1
LOOP:
    ADD.W D1,D0
    ADD.W #1,D1
    CMP #N,D1
    BLS LOOP

    SIMHALT             ; halt simulator

* Put variables and constants here

    END    START        ; last line of source

*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
