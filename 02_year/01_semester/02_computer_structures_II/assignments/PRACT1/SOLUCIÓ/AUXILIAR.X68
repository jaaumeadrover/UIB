; =============================================================================
; TITLE    : AUXILIARY CODE
; AUTHOR   :Joan Balaguer, Jaume Adrover
; COMMENTS :
; HISTORY  :18/10/2021
; =============================================================================

; -----------------------------------------------------------------------------
STR2SEG
; CONVERTS A STRING OF 8 CHARS INTO THE 7-SEGMENT DISPLAYS WORDS SEQUENCE,
; READY TO BE SENT TO THE DISPLAYS.
; ACCEPTS CHARACTERS FROM A TO Z AND FROM a TO z AND SPACE. CAPITAL AND NON
; CAPITAL ARE DISPLAYED WITH THE SAME GRAPHIC.
; INPUT    - A0 POINTER TO THE STRING (8 CHAR)
;            A1 POINTER TO THE ADDRESS WHERE TO PUT THE WORDS SEQUENCE
; OUTPUT   - MEMORY FROM (A1) TO (A0+15) MODIFIED.
; MODIFIES - NO REGISTERS MODIFIED
; NOTE     - BEHAVIOR IN FRONT OF CHARS OUTSIDE RANGES A-Z AND a-z AND SPACE
;            IS UNDEFINED
; -----------------------------------------------------------------------------
.CONTADOR EQU 7 ;CONSTANT AMB NOMBRE ITERACIONS

            MOVEM.L D0-D1/A0-A2,-(A7)   ;PUSH,GUARDAM VALORS DINS PILA
            MOVE.W #.CONTADOR,D1        ;ASSIGNAM VALOR AL REGISTRE CONTADOR
            
.LOOP           

            MOVE.B (A0)+,D0             ;PASSAM UNA LLETRA 
            CMP #32,D0                  ;COMPROVAM SI ÉS L'ESPAI
            BEQ .ESPAI                  ;SI ÉS ESPAI BOTAM
            CMP #96,D0                  ;MIRAM SI ÉS MINÚSCULA (ASCII>96)
            BGT .MINUSCULA              ;SI EL CODI ÉS MINÚSCULA,BOTAM

            
.MAJUSCULA

            SUB.B #65,D0                ;RESTAM 65 A D0
            BRA .MOSTRA                 ;SALTAM AL MÈTODE DE VISUALITZACIÓ 
            
.ESPAI

            SUB.B #6,D0                 ;RESTAM 6 PER A SITUAR D0 AL FINAL DEL VECTOR
            BRA .MOSTRA                 ;ANAM AL MÈTODE QUE MOSTRARÀ LES LLETRES PER STR2SEG
            
.MINUSCULA

            SUB.B #97,D0                ;RESTAM EL VALOR DE A MINÚSCULA, PER TAL D'OBTENIR UN NOMBRE 0-28

.MOSTRA     

            LEA .CODISSEGM,A2           ;SITUAM EL CODI DE 7 SEGMENTS DE L'ABECEDARI A A2
            MOVE.B (A2,D0),(A1)         ;D0 ACTUA COM A ÍNDEX, SITUANT A A1 EL CODI DE LA LLETRA CORRESPONENT
            ADDQ #2,A1                  ;AVANÇAM A LA SEGÜENT POSICIÓ DELS VISUALITZADORS
            DBRA D1,.LOOP               ;DBRA QUE CONTÉ 7 ITERACIONS
            

            MOVEM.L (A7)+,D0-D1/A0-A2   ;POP, RETORNAM ELS VALORS ALS REGISTRES
            RTS                         ;RETORNAM AL PROGRAMA QUE HA CRIDAT LA SUBRUTINA
            


;VECTOR QUE CONTÉ L'ABECEDARI CODIFICAT TAL QUE ES VISUALITZEN ELS GRAFEMES EN ELS SEGMENTS DE 7 VISUALITZADORS
.CODISSEGM  DC.B $77,$7C,$39,$5E,$79,$71,$3D,$76,$30,$1E,$76,$38,$37,$37,$3F,$73    
            DC.B $3F,$50,$6D,$78,$3E,$3E,$3E,$76,$6E,$5B,$00
            DS.W 0
            
; -----------------------------------------------------------------------------
BITPOS
; GIVEN A BYTE, IT RETURNS THE POSITION (VALUE BETWEEN 0 AND 7) OF THE RIGHT-
; MOST BIT VALUED ONE. IF ALL BITS ARE ZERO, OUTPUTS 8
; INPUT    - D0.B THE BYTE
; OUTPUT   - D0.B THE BIT POSITION
; MODIFIES - NO REGISTER, ASIDE OF D0.B
; -----------------------------------------------------------------------------

.N          EQU 7                       ;VARIABLE CONTADOR DBRA


            MOVEM.L D1-D2,-(A7)         ;GUARDAM REGISTRES DINS LA PILA
            NOT.B D0                    ;FEIM UNA NOT DEL REGISTRE JA QUE MIRAREM ELS BITS D'ESQUERRA CAP A DRETA
            MOVE.B #.N,D1               ;ASSIGNAM CONTADOR
            CLR D2                      ;D2 SIRÀ EL REGISTRE DEL BIT POSICIONAT

.LOOP       BTST D2,D0                  ;SI EL BIT APUNTAT (D2) ES 1 SORTIM
            BNE .END            
            ADDQ.B #1,D2                ;AUGMENTAM CONTADOR         
            DBRA D1,.LOOP               ;BUCLE DBRA
            
.ZERO       MOVE.B #8,D0                ;SI D2=0, RETORNAM 8          
            MOVEM.L (A7)+,D1-D2         ;RETORNAM VALORS ALS REGISTRES
            RTS
            
.END        MOVE.B D2,D0                ;ASSIGNAM VALORS ALS REGISTRES
            MOVEM.L (A7)+,D1-D2         ;RETORNAM VALORS ALS REGISTRES
            RTS                         ;RETORNAM AL PROGRAMA QUE HA CRIDAT LA SUBRUTINA












*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
