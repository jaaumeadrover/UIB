*-----------------------------------------------------------
* Title      : LAB_4
* Written by : Jaume Adrover Fernández
* Date       : 29/03/2021
* Description: 
*-----------------------------------------------------------
    ORG    $1000
    
    N: EQU 7
    V: DC.B -10,120,8,70,0,-99,-106
    C: DC.B 0
    T1: EQU 8
    T2: EQU 99
    
START:

    LEA.L V,A0
    MOVE.B C,D0
    MOVE.B #N,D1
    SUB.B #1,D1
*INICIO BUCLE TRATAMIENTO
TRATAMIENTO:
    MOVE.B (A0)+,D2
    CMP #0,D2
    BLT ABS
RETURN:
    CMP T1,D2
    BHS ANALIZE
    DBRA D1,TRATAMIENTO
INCREASE:
    ADD.B #1,D0    
    DBRA D1,TRATAMIENTO
    
    BRA FIN    
ABS:
    MULS.W #-1,D2
    BRA RETURN
ANALIZE:
    CMP T2,D2
    BLS INCREASE
    
    
FIN:
                  
    SIMHALT             ; halt simulator

* Put variables and constants here

    END    START        ; last line of source

*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
