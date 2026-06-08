-- AUTORS: Jaume Adrover Fernandez i Joan Balaguer Llagostera
-- ENLLAÇ VIDEO: https://www.youtube.com/watch?v=TD5m446NXqI&ab_channel=JoanBalaguer

with Ada.Strings.Unbounded; use Ada.Strings.Unbounded;


--DECLARACIÓ MAÏTRE I ELS SEUS MÈTODES,VARIABLES
package def_maitre is
   --Tipus necessaris
   capacitatSalo : constant := 3;--Nombre de taules d'un saló
   numSalons     : constant := 3;--Nombre salons total
   --Enumerat de tipusSalo i enter capacitat(només pot agafar els valors 1...3)
   type tipusSalo is (LLIURE,FUMADOR,NOFUMADOR);
   type capacitat is range 0 .. 3;
   --Array Noms,Capacitat i Tipus
   type ArrayCapacitat is array (1 .. (numSalons)) of capacitat;
   type ArrayTipus is array (1 .. (numSalons))   of tipusSalo;

   protected type maitre is
      --Mètodes necessaris
      procedure init;
      entry demanaTaulaF(Nom: in Unbounded_String; Taula: out Integer);
      entry demanaTaulaNoF(Nom: in Unbounded_String;Taula: out Integer);
      function esPotEntrar(Tipus : in Unbounded_String) return Integer;
      procedure senVa(Nom: in Unbounded_String; Sala: in Integer);

   private
      --Atributs necessaris: arrays
      array_capacitat : ArrayCapacitat := (3,3,3); --Array que defineix la capacitat actual de cada saló
      array_tipus     : ArrayTipus := (tipusSalo'(LLIURE),tipusSalo'(LLIURE),tipusSalo'(LLIURE)); --Array que conté el tipus de cada saló (FUMADOR o NOFUMADOR o LLIURE)
   end maitre;
end def_maitre;