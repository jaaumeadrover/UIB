(defun guarda-informacio ()
    (putprop 'spiro '((150 105) (144 96)) 'grans)
    (putprop 'spiro '((84 35 56)
            (80 33 53)
            (75 31 50)
            (72 29 48)
            (63 25 42)
            (60 23 40)
            (56 21 37)
            (52 19 35)
            (48 17 32)
            (45 16 30)
            (42 14 28)
            (40 13 27)
            (32 9 21)
            (30 8 20)
            (24 5 16)) 'petits)
    (putprop 'spiro 150 'rgran)
    (putprop 'spiro 52 'rpetit)
    (putprop 'spiro 3 'punt)
    (putprop 'spiro 0 'inici)
    (putprop 'spiro 1.8 'escala)
    (putprop 'spiro t 'interior)
    (putprop 'spiro 0 'x)
    (putprop 'spiro 0 'y)
    (putprop 'spiro 0.2 'pas)
)

; FUNCIONES PARA EL CAMBIO DE COLOR
(defun vermell ()
    (color 255 0 0)
)

(defun blau ()
    (color 0 0 255)
)

(defun verd ()
    (color 0 255 0)
)

(defun negre ()
    (color 0 0 0)
)

; Función para pasar a radianes
(defun radians (x)
    (* x (/ pi 180))
)

; Función que mueve el puntero del lápiz
(defun mou (x y)

    ; Una vez calculado los puntos donde pintar pasados por parámetro, debemos calcular el desplazamiento de estos
    ; debido al ángulo "inicio"
    (putprop 'spiro (+ (* x (cos (radians (get 'spiro 'inici)))) (* y (sin (radians (get 'spiro 'inici))))) 'x)
    (putprop 'spiro (+ (* (* x -1) (sin (radians (get 'spiro 'inici)))) (* y (cos (radians (get 'spiro 'inici))))) 'y)

    (move (realpart (round (+ 320 (* (get 'spiro 'escala) (get 'spiro 'x)))))
    (realpart (round (+ 187 (* (get 'spiro 'escala) (get 'spiro 'y))))))
)

; Función que pinta
(defun pinta (x y)

    ; Una vez calculado los puntos donde pintar pasados por parámetro, debemos calcular el desplazamiento de estos
    ; debido al ángulo "inicio"
    (putprop 'spiro (+ (* x (cos (radians (get 'spiro 'inici)))) (* y (sin (radians (get 'spiro 'inici))))) 'x)
    (putprop 'spiro (+ (* (* x -1) (sin (radians (get 'spiro 'inici)))) (* y (cos (radians (get 'spiro 'inici))))) 'y)

    (draw (realpart (round (+ 320 (* (get 'spiro 'escala) (get 'spiro 'x)))))
    (realpart (round (+ 187 (* (get 'spiro 'escala) (get 'spiro 'y))))))
)

(defun cercle2 (x y radi pas angle)
    (cond ((< angle 360)
    (pinta (+ x (* radi (cos (radians (+ angle pas)))))
    (+ y (* radi (sin (radians (+ angle pas))))))
    (cercle2 x y radi pas (+ angle pas)))
    (t t))
)

(defun cercle (x y radi segments)
    (mou (+ x radi) y)
    (cercle2 x y radi (/ 360 segments) 0)
)

; Almacena el valor del rgran en la propiedad del spiro y dibuja el circulo
(defun radigran (r)
    (putprop 'spiro r 'rgran)
    (cercle (get 'spiro 'x) (get 'spiro 'y) r 50)
)

; Almacena el valor del rpetit en la propiedad del spiro y dibuja el circulo
(defun radipetit (r)
    (putprop 'spiro r 'rpetit)

    ; Ya que el rpetit depende de rgran, debemos calcular la posicion en la que pintar el circulo
    (calculateX (get 'spiro 'rgran) (get 'spiro 'rpetit) 0 (radians (get 'spiro 'inici)))
    (calculateY (get 'spiro 'rgran) (get 'spiro 'rpetit) 0 (radians (get 'spiro 'inici)))
    (cercle (get 'spiro 'x) (get 'spiro 'y) r 50)
)

; SETTERS DE LAS PROPIEDADES DEL SPIRO
(defun punt (p)
    (putprop 'spiro p 'punt)
)

(defun inici (angle)
    (putprop 'spiro angle 'inici)
)

(defun escala (e)
    (putprop 'spiro e 'escala)
)

(defun rgran (r)
    (putprop 'spiro r 'rgran)
)

(defun rpetit (r)
    (putprop 'spiro r 'rpetit)
)

(defun pas (pas)
    (putprop 'spiro pas 'pas)
)

(defun interior (i)
    (putprop 'spiro i 'interior)
)

(defun posicio (x y)
    (putprop 'spiro x 'x)
    (putprop 'spiro y 'y)
)

; Función que calcula la coordenada x para dibujar el circulo pequeño a partir del grande
(defun calculateX (radigran radipetit tr a)
  (putprop 'spiro (* (- radigran radipetit) (sin a)) 'x)
  ;(putprop 'spiro (* (- radigran radipetit) (sin (radians a)) 'x))
)

; Función que calcula la coordenada y para dibujar el circulo pequeño a partir del grande
(defun calculateY (radigran radipetit tr a)
  (putprop 'spiro (* (- radigran radipetit) (cos a)) 'y)
  ;(putprop 'spiro (* (- radigran radipetit) (cos (radians a)) 'y))
)

; Función para reducir una fracción
(defun reduir (x y)
    (list (/ x (gcd x y)) (/ y (gcd x y)))
)

; Función que dibuja un spiro según si es interior o exterior
(defun spirograph (p gran petit tr inc inici)
    ; actualizar interior
    ; si es interior llamar mou i spirointerior (9)
    ; si no llamar mou i spiroexterior (10)
    (inici inici)
    (cond 
    ( (or (equal gran (caar (get 'spiro 'grans))) (equal gran (caar (cdr (get 'spiro 'grans))))) 
        (putprop 'spiro nil 'interior)
        ;calcular x
        (epitrocoideX gran petit tr p)
        ;calcular y
        (epitrocoideY gran petit tr p)
        ;mou
        (mou (get 'spiro 'x) (get 'spiro 'y))
        ; spiroExterior
        (spiroExterior p gran petit tr inc inici) 
    )
    (t  (putprop 'spiro t 'interior)
        ;calcular x
        (hipotrocoideX gran petit tr p)
        ;calcular y
        (hipotrocoideY gran petit tr p)
        ;mou
        (mou (get 'spiro 'x) (get 'spiro 'y))
        ; spiroExterior
        (spiroInterior p gran petit tr inc inici) )
    )
)
; Función para dibujar el spiro interior
(defun spiroInterior (p gran petit tr inc inici)
    (cond ((<= p 0) t)
    (t 
        ;calcular x
        (hipotrocoideX gran petit tr p)
        ;calcular y
        (hipotrocoideY gran petit tr p)
        ; pinta
        (pinta (get 'spiro 'x) (get 'spiro 'y))
        
        (spiroInterior (- p inc) gran petit tr inc inici)
    ))
)
; Función para dibujar el spiro exterior
(defun spiroExterior (p gran petit tr inc inici)
    (cond ((<= p 0) t)
    (t 
        ; calcular x
        (epitrocoideX gran petit tr p)
        ; calcular y
        (epitrocoideY gran petit tr p)
        ; pinta
        (pinta (get 'spiro 'x) (get 'spiro 'y))
        
        (spiroExterior (- p inc) gran petit tr inc inici)
        )
    )
)
; Función para calcular el hipotrocide para X
(defun hipotrocoideX (radigran radipetit tr a)
    (putprop 'spiro (+ (* (- radigran radipetit) (cos (/ (* radipetit a) radigran))) 
    (* tr (cos (* (- 1 (/ radipetit radigran)) a)))) 'x)
)
; Función para calcular el hipotrocide para Y
(defun hipotrocoideY (radigran radipetit tr a)
    (putprop 'spiro (- (* (- radigran radipetit) (sin (/ (* radipetit a) radigran))) 
    (* tr (sin (* (- 1 (/ radipetit radigran)) a)))) 'y)
)
; Función para calcular el epitrocoide para X
(defun epitrocoideX (radigran radipetit tr a)
    (putprop 'spiro (- (* (+ radigran radipetit) (cos (/ (* radipetit a) radigran))) 
    (* tr (cos (* (+ 1 (/ radipetit radigran)) a)))) 'x)
)
; Función para calcular el epitrocoide para Y
(defun epitrocoideY (radigran radipetit tr a)
    (putprop 'spiro (- (* (+ radigran radipetit) (sin (/ (* radipetit a) radigran))) 
    (* tr (sin (* (+ 1 (/ radipetit radigran)) a)))) 'y)
)
; Función para dibujar un spiro
(defun spiro (gran petit p inc inici)
    (inici inici)
    ; buscar forats per radi petit
    (setq forats (trobar-forats (get 'spiro 'petits) petit))
    (setq tr (* (- forats p) (/ petit forats)))
    ;(setq tr (- gran petit))
    ;(setq tr (+ (* d (sin (radians inici))) (* d (cos (radians inici)))))
    ;(* forats (cadr (reduir gran petit)))
    ;(print tr)
    (spirograph (* 2 pi (* (cadr (reduir gran petit)) (/ gran petit))) gran petit tr inc inici)
)

(defun trobar-forats (petits r)
    (cond
        ((equal (caar petits) r) (car (cdr (car petits))))
        (t (trobar-forats (cdr petits) r)))
)

(defun roda ()
    (spiro (get 'spiro 'rgran) (get 'spiro 'rpetit) (get 'spiro 'punt) (get 'spiro 'pas) (get 'spiro 'inici))
)

(defun spiropervoltes (gran petit p inc inici voltes)
    (inici inici)
    (setq forats (trobar-forats (get 'spiro 'petits) petit))
    (setq d (* (- forats p) (/ petit forats)))
    (setq tr (+ (* d (sin (radians inici))) (* d (cos (radians inici)))))
    ;(* forats (cadr (reduir gran petit)))
    (spirograph (ceiling (* (* (* 2 pi) voltes) (/ pi 2))) gran petit tr inc inici)
)
; 
(defun roda-voltes (n)
    (spiropervoltes (get 'spiro 'rgran) (get 'spiro 'rpetit) (get 'spiro 'punt) (get 'spiro 'pas) (get 'spiro 'inici) n)
)
; Función para generar un spiro con un determinado número de vueltas
(defun spiro-voltes (voltes gran petit p inc inici)
    (spiropervoltes gran petit p inc inici voltes)
)
; Devuelve el x elemento de una lista
(defun escogerParametro (l x)
    (cond ((equal x 1) (car l))
    (t (escogerParametro (cdr l) (- x 1))))
)
; Función para dibujar múltiples spiro
(defun spiros (l)
    (cond 
        ((null l) t)
        (t 
            (pas (escogerParametro (car l) 4))
            (spiro (escogerParametro (car l) 1) (escogerParametro (car l) 2) (escogerParametro (car l) 3) (escogerParametro (car l) 4) (escogerParametro (car l) 5)) 
            (spiros (cdr l))
        )
            
    )
)


;JUEGO DE PRUEBAS
(defun figura1 ()
    (cls)
    (vermell)
    (spiros '((105 63 1 0.5 0)
    (105 63 3 0.5 0)
    (105 63 5 0.5 0)))
    (verd)
    (spiros '((105 63 7 0.5 0)
    (105 63 9 0.5 0)
    (105 63 11 0.5 0)))
    (blau)
    (spiros '((105 63 13 0.5 0)
    (105 63 15 0.5 0)
    (105 63 17 0.5 0)))
)

(defun figura145grados ()
    (cls)
    (radigran 105)
    (radipetit 63)
    (inici 360)
    (cls)
    (vermell)
    (punt 1)(roda)
    (punt 3)(roda)
    (punt 5)(roda)
    (verd)
    (punt 7)(roda)
    (punt 9)(roda)
    (punt 11)(roda)
    (blau)
    (punt 13)(roda)
    (punt 15)(roda)
    (punt 17)(roda)
)

(defun hipo ()
    (escala 1.2)
    (radigran 105)
    (radipetit 40)
    (inici 0)
    (interior t)
    (cls)
    (vermell)
    (punt 7)(roda)
)

(defun epi ()
    (escala 0.8)
    (radigran 150)
    (radipetit 40)
    (inici 0)
    (interior nil)
    (cls)
    (vermell)
    (punt 5)(roda)
)