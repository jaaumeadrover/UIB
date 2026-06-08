(defun pertany(e l)
    (cond (equal e (car'(l)))(T) (null l))(nil)) (T cdr'(l))

;;VERSIÓ CLASSE
(defun pertany(e l ) 
    (cond ((null l) nil) 
        ((equal (car l)) e)) T   )
            (T (pertany e (cdr l)))



;;
(defun pertany(x l)
    (cond ((null l)nil)
        ((equal x (car l)) t)
        (t (pertany x (cdr l)))
    )
)

;;EXPONENT 2 3 -> 8
(defun exponencial(e exp)
    (cond
        ((equal exp 0) 1)   
        (T (* e (exponencial e (- exp 1)) ))        
    )
)



;;PERTANY 
(defun pertanys(x l)
    (cond ((null l)nil)
        ((equal x (car l)) T)
        (T (pertany x (cdr l)))
    )
)

;/FIBONACCI
(defun fibonacci (x)
    (cond
        ((equal x 0) 0)
        ((equal x 1) 1)
        (T (+ (fibonacci (- x 1)) (fibonacci (- x 2))))
    )
)

;;DIVIDIR
(defun dividir (x n)
    (cond
        ((< x n)0)
        (T (+ 1 (dividir (- x n) n)))
    )
)

;;PARELL v1
(defun parell (x)
    (cond
        ((equal (* (dividir x 2) 2)  x) T)
        (T nil)
    )
)


;;Retorna nombres senar de una llista
(defun senars(l)
    (cond
        ((null l) nil) ;;final llista
        ((parell (car l)) (senars (cdr l)))
        (T (cons (car l) senars((cdr l))))
    )
)

;;Eliminar primera aparició d'un element dins una llista
(defun borra (x l)
    (cond
        ((null l) nil) ;mentre no final
        ((equal x (car l)) (cdr l)) ; si trobam aparició retornam llista següent 
        (T (cons (car l) (borra x (cdr l))))
    )
)

;;Eliminar totes aparicions
(defun borraTot(x l)
    (cond
        ((null l) nil) ;mentre no final
        ((equal x (car l)) ((borraTot x (cdr l)))) ; si trobam aparició retornam llista següent 
        (T (cons (car l) (borraTot x (cdr l))))
    )
)

;;Retorna tots elements d'una llista menys el darrer
(defun rdc (l)
        (cond
           ((null (car (cdr l))) nil) 
            (T (cons (car l) (rdc (cdr l))))
        )
)

;;Afegeix un element al final d'una llista
(defun snoc(x l)
    (cond
        ((null l) (cons x nil))
        (T (cons (car  l) (snoc x (cdr l))))
    )
)

(defun snoc (e l)
    (cond 
        ;Lista vacia, poner elemento
        ((null l) (cons e l))
        ;PARA NO PERDER EL PRIMER ELEMENTO
        (t (cons (car l)(snoc e (cdr l))))
    )
)

;;Afegeix un element
(defun escala(x l)
    (cond
        ((null l) nil)
        (T (cons (* x (car l)) (escala x (cdr l))))
    )

)

;;Maxim d'una llista
(defun maxim(l)
    (cond
        ((null (cdr l)) (car l))
        ((> (car l) (maxim (cdr l))) (car (l)))
        (T (maxim (cdr l)))
    )
)
;;Minim d'una llista
(defun min(l)
    (cond
        ((null (cdr l)) (car l))
        ((< (car l) (min (cdr l)))(car l))
        (t (min (cdr l)))
    )
)
;;Ordena una llista
(defun ordena(l)
    (cond
        ((null (cdr l)) (car l))                
        (T (cons (min l) (ordena (borra (min l) l))))
    )
)



;;Invertir una llista
;; (a e i o u) --> (u o i e a)
(defun invertir(l)
    (cond
        ((null l)nil)
        (T (snoc (car l) (invertir (cdr l))))
    )
)

;;Ordenar llista
(defun ordena(l)
    (cond
        ((null l) nil)
        (T (cons (min l) (ordena  (borra (min l)  l) )))
    )
)

;;Borrar enesim element llista
(defun borrar(x l)
    (cond
    ((null l) nil)
    ((= x 1)  (cdr l))
    (T (cons (car l) (borrar (- 1 x) (cdr l))))
    )
)

;;Comptar número aparicions caràcter
(defun vegades(x l)
    (cond
        ((null l) 0)
        ((equal (car l) x) (+ 1 (vegades x (cdr l))))
        (T (vegades x (cdr l)))
    )
)

;;Comptar nombre àtoms dins llista
(defun atoms(l)
    (cond
        ((null l) 0)
        ((atom (car l)) (+ 1 (atoms (cdr l))))
        (T (+ (atoms (car l)) (atoms (cdr l))))
    )
)

;;Aplanar una llista
(defun aplanar(l)
    (cond
        ((null l) nil)
        ((listp (car l)) (cons (car (car l)) (aplanar (cdr (car (car l))))))
        (T (cons (car l) (aplanar (cdr l))))
    )
)

;;Aplanar llista v2
(defun aplanar(l)
    (cond 
        ((null l) nil)
        ((atom (car l)) (cons (car l) (aplanar (cdr l))))
        (T (append (aplanar (car l)) (aplanar (cdr l))))
    )
)

;;Treu n primers elements d'una llista
(defun treuN(n l)
    (cond 
        ((null l)nil)
        ((= n 0) l)
        (T (treuN (- n 1) (cdr l)))
    )
)

;;Torna n primers
(defun primers(n l)
    (cond
        ((null l)nil)
        ((= n 0) nil)
        (T (cons (car l) (primers (- n 1) (cdr l))))

    )
)

;;(inserta 'aqui 3 '(i jo que faig))
(defun inserta (x pos l)
    (cond
        ((null l) x)
        ((= pos 0) (cons pos (cdr l)))
        (T (cons (car l) (inserta x (- pos 1) (cdr l))))
    )
)