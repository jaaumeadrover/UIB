*-----------------------------------------------------------
* Title      :
* Written by :
* Date       :
* Description:
*-----------------------------------------------------------
    ORG    $1000
    
    A:DC.W 40
    B:DC.W 13
    C:DS.W 1
    D:DS.W 1
    
START: 
 MOVE.W A,D0
 
 ADD.W  B,D0

 MOVE.W D0,C
 
 MOVE.W A,D0
 
 SUB.W  B,D0
 
 MOVE.W D0,D
               
* Put program code here

    SIMHALT             ; halt simulator

* Put variables and constants here

    END    START        ; last line of source

*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
