*-----------------------------------------------------------
* Title      :Ex L3B
* Written by : Jaume Adrover Fernández
* Date       :22/03/2021
* Description: Calculate the sum of N numbers using DBRA
*-----------------------------------------------------------
    ORG    $1000
    N: EQU 5
    SUM: DS.W 1
START:                                ; halt simulator
    MOVE.W #N,D0
    MOVE.W #N,SUM
    SUB.W #1,D0
    
LOOP:
    ADD.W D0,SUM
    DBRA D0,LOOP
    
    SIMHALT
* Put variables and constants here

    END    START        ; last line of source

*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
