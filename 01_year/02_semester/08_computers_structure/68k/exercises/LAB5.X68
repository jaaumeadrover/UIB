*-----------------------------------------------------------
* Title      : LAB5
* Written by : Jaume Adrover Fernández
* Date       : 12/04/2021
* Description: 
*-----------------------------------------------------------
    ORG    $1000
    
N: EQU 5
V1: DC.W 10,-15,-100,89,-1
V2: DS.W N

START:                  
    LEA.L V1,A0
    ADD.W #10,A0
    MOVE.W #0,A1
    
LOOP:
    MOVE.W -(A0),D0
    MULS.W #-1,D0
    MOVE.W (A0),V2(A1)
    ADD.W #2,A1
    
    
* Put program code here

    SIMHALT             ; halt simulator

* Put variables and constants here

    END    START        ; last line of source

*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
