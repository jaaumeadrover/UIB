-- AUTORS: Jaume Adrover Fernandez i Joan Balaguer Llagostera
-- ENLLAÇ VIDEO: https://www.youtube.com/watch?v=TD5m446NXqI&ab_channel=JoanBalaguer

with Ada.Text_IO;              use Ada.Text_IO;
with Ada.Strings.Unbounded;    use Ada.Strings.Unbounded;
with Ada.Text_IO.Unbounded_IO; use Ada.Text_IO.Unbounded_IO;
with Ada.Task_Identification;  use Ada.Task_Identification;

--DEFINICIÓ DEL MAÏTRE
package body def_maitre is
    --CONSTANTS
    capacitatSalons : constant :=3;

    protected body maitre is
        --PROCEDIMENT INIT: serveix per inicialitzar tots els atributs del monitor maïtre.
        procedure init is
        begin
            --Init array tipus, totes les taules comencen LLIURES
            for I in array_tipus'Range loop
                array_tipus(I):=tipusSalo'(LLIURE);
            end loop;
            --Init array capacitat, totes les taules comencen plenes
            for I in array_capacitat'Range loop
                array_capacitat(I):=3;
            end loop;
            Put_Line("++++++++++El maître està preparat");
            Put_Line("++++++++++Hi ha " & numSalons'img & " salons amb capacitat de "& capacitatSalo'img & " comensals cada un.");
        end init;

        --ENTRADA demanaTaulaF: procediment que simula el comportament d'una petició d'un procés fumador, controlada mitjançant la funció esPotEntrar().
        entry demanaTaulaF(Nom: in Unbounded_String; Taula: out Integer) when (esPotEntrar(Ada.Strings.Unbounded.To_Unbounded_String("FUMADOR"))>=0) is
            --Variables que utilitzarem
            trobat : Integer :=0;
            indx   : Integer :=1;
            idSalo : Integer :=0;
            final  : Integer :=0;
        begin
            
            --Cercam primer salo buit del tipus corresponent
            loop
                if array_capacitat(indx)>0 and (array_tipus(indx)=tipusSalo'(LLIURE) or array_tipus(indx)=tipusSalo'(FUMADOR)) then
                    idSalo:=indx;
                    trobat:=1;
                else
                    indx:=indx+1;
                end if;
                
                exit when trobat=1 or indx=numSalons+1;
            end loop;

            --Actualitzam tipus sala trobada a FUMADOR
            array_tipus(idSalo):=tipusSalo'(FUMADOR);
            Taula:=idSalo;
            array_capacitat(idSalo) := array_capacitat(idSalo) - 1;

            Put_Line("---------- En " & Nom & " té taula al saló de fumadors " & idSalo'img & ". Disponibilitat: " & array_capacitat(idSalo)'img);
            
        end demanaTaulaF;

        entry demanaTaulaNoF(Nom: in Unbounded_String;Taula: out Integer) when (esPotEntrar(Ada.Strings.Unbounded.To_Unbounded_String("NOFUMADOR"))>=0)is
        trobat : Integer :=0;
        indx   : Integer :=1;
        idSalo : Integer :=0;
        final  : Integer :=0;
        begin
            --Cercam primer salo buit del tipus corresponent
            loop
                if array_capacitat(indx)>0 and (array_tipus(indx)=tipusSalo'(LLIURE) or array_tipus(indx)=tipusSalo'(NOFUMADOR)) then
                    idSalo:=indx;
                    trobat:=1;
                else
                    indx:=indx+1;
                end if;
                exit when trobat=1 or indx=numSalons+1;
            end loop;

            Taula:= idSalo;
            array_tipus(idSalo):=tipusSalo'(NOFUMADOR);
            array_capacitat(idSalo) := array_capacitat(idSalo) - 1;

            Put_Line("********** En " & Nom & " té taula al saló de NO fumadors " & idSalo'img & ". Disponibilitat: " & array_capacitat(idSalo)'img);
            
        end demanaTaulaNoF;
        
        procedure senVa(Nom: in Unbounded_String;Sala: in Integer) is
            indx: Integer :=1;
            trobat: Integer :=0;
            salo : Integer:=1;
            tipusAnterior: Integer := 0;

        begin
            
            --Disminuim capacitat
            array_capacitat(Sala):=array_capacitat(Sala)+1;
            
            if array_tipus(Sala) = tipusSalo'(FUMADOR) then
                tipusAnterior := 1;
            end if;

            --Si tothom ha partit el tipus sirà lliure
            if(array_capacitat(Sala)=3) then
                array_tipus(salo):=tipusSalo'(LLIURE);
            end if;

            if tipusAnterior = 0 then
                Put_Line("---------- En " & Nom & " allibera la taula del saló " & salo'img &". Disponibilitat: " & array_capacitat(salo)'img & " Tipus: " & array_tipus(salo)'img);
            else
                Put_Line("********** En " & Nom & " allibera la taula del saló " & salo'img &". Disponibilitat: " & array_capacitat(salo)'img & " Tipus: " & array_tipus(salo)'img);
            end if;
            
        end senVa;


        -- Mètode que utilitzaràn els comensals per verificar si es pot entrar o no a una sala
        function esPotEntrar(Tipus : in Unbounded_String) return Integer is
            indx: Integer :=1;
            trobat: Integer:=0;
            -- Funcionament d'aquestes variables --> -1 = No hi ha cap sala amb alguna taula disponible per aquest tipus de comensal
            --                                        0 = Hi ha una sala lliure (no ocupada encara) per al comensal que la demana
            --                                        1 = Hi ha una sala del tipus que demana el comensal amb almenys una taula lliure
            resultatF : Integer :=-1;
            resultatNoF : Integer :=-1;
        begin
            for I in array_capacitat'Range loop
                if(array_capacitat(I)>0) then
                    if(array_tipus(I)=tipusSalo'(LLIURE)) then
                        resultatF:=0;
                        resultatNoF:=0;
                        trobat:=1;
                    else 
                        if(array_tipus(I)=tipusSalo'(FUMADOR)) then
                            resultatF:=1;
                        else
                            resultatNoF:=1;
                        end if;
                    end if;
                end if;
                exit when trobat=1;
            end loop;

            if(Tipus=Ada.Strings.Unbounded.To_Unbounded_String("FUMADOR")) then
                return resultatF;
            else
                return resultatNoF;
            end if;
        end esPotEntrar;
    end maitre;
    
end def_maitre;