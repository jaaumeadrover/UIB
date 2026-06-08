with Ada.Text_IO,System.Atomic_Counters; use Ada.Text_IO,System.Atomic_Counters;

procedure Counter is
  n: Integer := 0;
  --initial value of the counter is 1--
  mutex: aliased System.Atomic_Counters.Atomic_Counter;
  pragma Volatile(n);
  task type Tasca_Comptador;
  task body Tasca_Comptador is
  --PREPROTOCOL  
  procedure lock is
    begin
      --INITIAL VALUE=1 THEN MUTEX =0
      --Decrements value of atomic counter, returns True when value reach zero
    while(not (System.Atomic_Counters.Decrement(mutex))) loop
    null;
    end loop;
  end lock; 
  --POSTPROTOCOL
  procedure unlock is
    begin    
    --Inicialitza a 1
      System.Atomic_Counters.Initialize(mutex);
  end unlock;
  begin
    for i in 1..5000000 loop
      lock;
      n := n + 1;
      unlock;
    end loop;
    Put_Line("----->> n �s " & Integer'Image(N));
  end Tasca_Comptador;
begin
  declare
    p, q : Tasca_Comptador;
  begin
    null;
  end;
  Put_Line("El valor de n �s " & Integer'Image(N));
end Counter;