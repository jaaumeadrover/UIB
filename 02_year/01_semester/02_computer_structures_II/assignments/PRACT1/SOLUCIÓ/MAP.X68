; =============================================================================
; TITLE    : MEMORY MAPPED HARD CODE
; AUTHOR   : Joan Balaguer, Jaume Adrover
; COMMENTS :
; HISTORY  : 08/10/2021
; =============================================================================

; -----------------------------------------------------------------------------
MAPINIT
; SHOWS HARDWARE WINDOW AND GETS MAPPING ADDRESSESG.
; INPUT    - D0.B BIT 0: SHOW HARDWARE WINDOW (1: YES, 0: NO)
;                 BIT 1: GET MAPPING ADDRESSES (1: YES, 0: NO)
;                 BIT 2-7: NOT USED.
;            A0 POINTER TO RAM ADDRESS TO STORE THE MAPPING ADDRESSES IN THIS
;               ORDER: 7-SEGMENT, LEDS, SWITCHES, BUTTONS.
;               IF D0.B BIT 1 IS ZERO, A0 IS NOT USED.
; OUTPUT   - NO OUTPUT THROUGH REGISTERS
;            RAM POINTED BY A0 IS MODIFIED IF D0.B BIT 1 IS 1
; MODIFIES - NONE
; -----------------------------------------------------------------------------
     
            BTST #0,D0                      ;COMPROVAM EL VALOR DEL PRIMER BIT DE D0
            BNE  .SHWHW                     ;SI EL PRIMER BIT VAL 1, MOSTRAM LA FINESTRA DE HARDWARE I BOTAM A .SHWHW
            BRA .BIT1_D0                    ;SI EL PRIMER BIT VAL 0, ANIREM A COMPROVAR EL VALOR DEL SEGON BIT I BOTAREM A .BIT1_D0
     
.SHWHW                                            
            MOVEM.L D0-D1,-(A7)             ;GUARDAM VALORS D0,D1 DINS PILA
            MOVE.B #32, D0                  
            CLR.B D1
            TRAP #15                        ;MOSTRAR LA FINESTRA DE HARDWARE AMB TRAP15
            MOVEM.L (A7)+,D0-D1             ;RESTAURAM VALORS D0,D1 GUARDATS ANTERIORMENT DINS LA PILA

.BIT1_D0
            MOVEM.L A0-A1/D0-D1,-(A7)       ;GUARDAM VALORS A0,A1 DINS PILA, FA FALTA EL VALOR DE D1?
            BTST #1,D0                      ;COMPROVAM EL VALOR DEL SEGON BIT DE D0
            BEQ .FIN
            LEA .VECT,A1                    ;CARREGAM LA DIRECCIÓ DE LA PRIMERA COMPONENT DEL VECTOR .VECT DINS A1
           
.GETMAPADDR
            MOVE.B (A1)+,D1                 ;MOVEM EL CONTINGUT DE LA COMPONENT QUE ESTIGUEM TRACTANT DEL VECTOR .VECT A D1 
            BEQ .FIN                        ;SI HEM ARRIBAT AL FINAL DEL VECTOR, ACABAM L'ITERACIÓ
            MOVE.B  #32,D0
            TRAP #15                        ;OBTENIM LES DIRECCIONS DE MAPATGE EN MEMÒRIA DEL PERIFÈRIC QUE ESTIGUEM TRACTANT
            MOVE.L D1,(A0)+                 ;GUARDAM LA DIRECCIÓ EN MEMÒRIA DEL PERIFÈRIC QUE ESTIGUEM TRACTANT
            BRA .GETMAPADDR
            
.FIN         
            MOVEM.L (A7)+,A0-A1/D0-D1       ;RESTAURAM VALORS DINS PILA
            RTS
            
.VECT         DC.B 1,2,3,7,0                ;VECTOR QUE CONTÉ ELS PARÀMETRES DEL TRAP15
              DS.W 0

; -----------------------------------------------------------------------------
MAPPRBIT
; PRINTS 'ZERO','ONE','TWO',...,'SEVEN' INTO THE 7-SEGMENT DISPLAYS DEPENDING
; ON THE BUTTON THAT IS PRESSED (ZERO IS THE RIGHTMOST, SEVEN IS THE LEFTMOST).
; PRINTS 'NONE' IF NO BUTTON IS PRESSED.
; INPUT    - A0 BUTTONS MAPPING ADDRESS
;            A1 7-SEGMENT DISPLAY MAPPING ADDRESS
; OUTPUT   - NONE THROUGH REGISTERS
; MODIFIES - NO REGISTER MODIFIED
; -----------------------------------------------------------------------------
CONTADOR EQU 7                        ;CONSTANT NOMBRE D'ITERACIONS

            MOVEM.L D0-D7/A0,-(A7)    ;GUARDAM VALORS REGISTRES DINS PILA
            MOVE.B (A0),D0            ;INTRODUIM DINS D0 EL CONTINGUT DE LA ADREÇA DELS BOTONS
            CLR D1                    ;INICIALITZAM D1 A 0(SERVEIX PER GUARDAR NOMBRES DELS BOTONS)
            MOVE.B #CONTADOR,D2       ;EMMAGATZEMAM A D2 EL NOMBRE D'ITERACIONS DEL BUCLE
            JSR BITPOS                ;SALTAM A BITPOS PER A OBTENIR A D0 EL NOMBRE DEL BOTÓ PITJAT
            
.LOOP       
            CMP D1,D0                 ;MIRAM QUIN BOTÓ HA ESTAT PITJAT
            BEQ DISPLAY               ;SI HA ESTAT PITJAT EL BOTÓ ANAM A DISPLAY
            ADDQ.B #1,D1              ;ANAM COMPROVANT BOTÓ A BOTÓ, AUGMENTANT EL VALOR DE D1
            DBRA D2,.LOOP             ;BUCLE DBRA
                        
            LEA NONE,A0               ;SI NO HEM PITJAT CAP BOTÓ, 'NONE' SERÀ MOSTRAT
            JSR STR2SEG               ;PASSAM STRING 'NONE' A 7 SEGMENTS
            MOVEM.L (A7)+,D0-D7/A0    ;RESTAURAM VALORS DELS REGISTRES
            RTS                       ;RETORNAM DE LA SUBRUTINA
            

DISPLAY   
            LEA .NUMBERS,A0           ;A0 CONTINDRÀ LA DIRECCIÓ DEL VECTOR DE NOMBRES
            MULU.W #8,D1              ;MULTIPLICAM EL NOMBRE OBTINGUT QUE SERVEIX D'ÍNDEX(No boto*8bytes)
            ADD.W D1,A0               ;FEIM AVANÇAR A A0 LES POSICIONS RESPECTIVES SEGONS EL BOTÓ PITJAT     
            JSR STR2SEG               ;CONVERTIM L'STRING OBTINGUT DESDE A0 A UNA VISUALITZACIÓ EN 7 SEGMENTS
            MOVEM.L (A7)+,D0-D7/A0    ;RESTAURAM ELS VALORS DELS REGISTRES
            RTS                       ;RETORNAM DE LA SUBRUTINA
            

.NUMBERS        DC.B   'ZERO    ','ONE     ','TWO     ','THREE   ','FOUR    ','FIVE    ','SIX     ','SEVEN   '
                DS.W    0
          
NONE            DC.B    'NONE    '
                DS.W    0


        
            
            
                         




























*~Font name~Courier New~
*~Font size~10~
*~Tab type~0~
*~Tab size~4~
