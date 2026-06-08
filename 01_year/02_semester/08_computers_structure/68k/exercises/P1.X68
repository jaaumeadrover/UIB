*--------------------------------------------
* Titulo : P1 (2020-2021) - Simple Encryption
* Autores: Alumno 1 y Alumno 2
*--------------------------------------------
    ORG $1000
    DICT_SIZE: EQU 3
    DICT_IN: DC.B  'EA2'
    DICT_OUT: DC.B 'AE1'
    PHRASE:   DC.B 'ASTRUCTURE DA COMPUTEDORAS 2',0
    NCHANGES: DS.W 1
              DS.W 0
*--------------------------------------------
    PHRASE_SIZE: EQU 28
 
START:
    LEA.L DICT_IN,A0
    LEA.L DICT_OUT,A1
    *
    MOVE.W #0,NCHANGES     *CLR
    MOVE.B #DICT_SIZE,D3   *INICIALIZAMOS CONTADOR LECTURAS=3
    
LECT_DICT_IN:
    MOVE.B (A0)+,D0        *LECTURA LETRA DICT_IN
    MOVE.B (A1)+,D1        *LECTURA LETRA DICT_OUT
    MOVE.W #PHRASE_SIZE,D2  *INICIALIZAMOS CONTADOR LETRAS=28
    

    
    
    SUBQ.W #1,D2
    LEA.L PHRASE,A2
LECT_PHRASE:
    MOVE.B (A2),D4
    CMP.B   D4,D0
    BEQ ENCRYPTION   
    BRA WRITE_LETTER                 
ENCRYPTION:
    MOVE.B D1,D4
    ADD.W  #1,NCHANGES
WRITE_LETTER:    
    MOVE.B D4,(A2)
    ADD.W  #1,A2
    
    DBRA D2,LECT_PHRASE
    
    SUB.B  #1,D3            *LECTURA COMPLETA, HEMOS LEIDO 3 VECES?-->FINAL
    CMPI.B  #0,D3
    BEQ FINAL
    BRA LECT_DICT_IN        
FINAL:

    END    START        ; last line of source


*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
