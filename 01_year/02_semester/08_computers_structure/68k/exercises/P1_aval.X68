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
    LEA.L PHRASE,A2
    MOVE.W #0,NCHANGES     *CLR
    MOVE.W #PHRASE_SIZE,D2  *INICIALIZAMOS CONTADOR LETRAS=28
    MOVE.B #DICT_SIZE,D3
    
LECT_PHRASE:
    MOVE.B (A2),D4    *LECTURA LETRA FRASE
    LEA.L DICT_IN,A0  *LOAD ADDRES DICT_IN
    LEA.L DICT_OUT,A1 *LOAD ADDRES DICT_OUT
    
    SUBQ.B #1,D3
LECT_DICT_IN:
    MOVE.B (A0)+,D0
    MOVE.B (A1)+,D1
    CMP.B  D0,D4
    BEQ ENCRYPTION
    DBRA D3,LECT_DICT_IN   
    BRA WRITE_LETTER                 
ENCRYPTION:
    MOVE.B D1,D4
    ADD.W  #1,NCHANGES
WRITE_LETTER:    
    MOVE.B D4,(A2)
    ADD.W  #1,A2
    MOVE.W #DICT_SIZE,D3
    DBRA D2,LECT_PHRASE
       
FINAL:

    END    START        ; last line of source



*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
