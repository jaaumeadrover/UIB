-- AUTORS: Jaume Adrover Fernandez i Joan Balaguer Llagostera
-- ENLLAÇ VIDEO: https://www.youtube.com/watch?v=TD5m446NXqI&ab_channel=JoanBalaguer

with Ada.Text_IO;               use Ada.Text_IO;
with Ada.Strings.Unbounded;     use Ada.Strings.Unbounded;
with Ada.Text_IO.Unbounded_IO;  use Ada.Text_IO.Unbounded_IO;
with Ada.Task_Identification;   use Ada.Task_Identification;
with Ada.Numerics.Discrete_Random; 

with def_maitre;                use def_maitre;


procedure main is
   --Variables necessàries
    Nre_Fils : constant := 14;
    Fitxer_Personatges : constant String := "noms.txt";
    Random_Duration : Duration;
    type Custom is range 600..1300;
    --type Custom is range 750..1500;
    package Rand_Cust is new Ada.Numerics.Discrete_Random(Custom);
    use Rand_Cust;
    Seed : Generator;
    Num  : Custom;
    monitor    : maitre;

   task type client is
      entry Start (Nom_Personatge : in Unbounded_String;boolFumador : in Boolean);
   end client;

   task body client is
      Nom : Unbounded_String;
      Fumador : Boolean;
      Taula: Integer;
   begin
      --Inicialització
      accept Start (Nom_Personatge : in Unbounded_String;boolFumador : in Boolean) do
         Nom     := Nom_Personatge;
         Fumador := boolFumador;
      end Start;

      -- Semilla per al generador de nombres aleatrois
      Reset(Seed);
      -- Generam un nombre aleatori entre 500 i 1300
      Num := Random(Seed);
      -- Dividim el nombre aleatori entre 1000
      Random_Duration := Duration(Num) / 1000.0;
      delay(Random_Duration);
      -- Arribada a al restaurant
      -- Si la variable Fumador es True, executarem el codi pertinent a un comensal FUMADOR
      if(Fumador=True) then
         --Saludam
         Put_Line("BON DIA som en " & Nom & " i sóm fumador");
         --Demanam taula al maître
         monitor.demanaTaulaF(Nom,Taula);
         Put_Line("En " & Nom & " diu: Prendré el menú del dia. Som al saló "& Taula'img);
         delay(2.0);
         --Acabam de dinar
         Put_Line("En " & Nom & " diu: Ja he dinat, el compte per favor");
         monitor.senVa(Nom,Taula);
         Put_Line("En " & Nom & " SE'N VA");
      else
         --Saludam
         Put_Line("     BON DIA som en " & Nom & " i sóm NO fumador");
         --Demanam taula al maître
         monitor.demanaTaulaNoF(Nom,Taula);
         Put_Line("     En " & Nom & " diu: Prendré el menú del dia. Som al saló "& Taula'img);
         delay(2.0);
         --Acabam de dinar
         Put_Line("     En " & Nom & " diu: Ja he dinat, el compte per favor");
         monitor.senVa(Nom,Taula);
         Put_Line("     En " & Nom & " SE'N VA");
      end if;
   end client;

    subtype Index_Noms is Positive range Positive'First .. 1 + Nre_Fils;
    type Array_Noms is array (Index_Noms) of Unbounded_String;
    subtype Index_client is Positive range Positive'First .. Nre_Fils;
    type fils is array (Index_client) of client;
   --
   F                   : File_Type;          --File
   Noms                : Array_Noms;         --Array de nombres
   arrayClients        : fils;      --Array procesos enanos

begin
    -- Llegim arxiu de noms
    Open(F, In_File, Fitxer_Personatges);
    for I in Noms'Range loop
        Noms(I) := Ada.Strings.Unbounded.To_Unbounded_String(Get_Line(F));
    end loop;

    Close(F);
    --Init monitor
    monitor.init;

   --Bucle threads
    for I in fils'Range loop
      if((I mod 2)=0)then
         arrayClients(I).Start(Noms(Array_Noms'First + I),True);
      else
         arrayClients(I).Start(Noms(Array_Noms'First + I),False);
      end if;
    end loop;

end main;