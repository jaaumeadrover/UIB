*-----------------------------------------------------------
* Title      :LAB5 SOLUTION
* Written by :JAUME ADROVER
* Date       :
* Description:
*-----------------------------------------------------------
    ORG    $1000
N: EQU 5
V1: DC.W 10,-15,-100,89,-1
V2: DS.W N
START:
    MOVE.W #N,D1
    SUBQ.W #1,D1
    LEA.L V2,A1
    ADDQ.L #N,A1
    MOVEA.L #0,A2
LOOP:
    MOVE.w -(A1),D0

    NOT.W D0
    ADDQ.W #1,D0
    MOVE.W D0,V2(A2)
    ADDQ.W #2,A2
    DBRA D1,LOOP

                  
* Put program code here

    SIMHALT             ; halt simulator

* Put variables and constants here

    END    START        ; last line of source

*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
