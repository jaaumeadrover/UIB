
;APLANAR LLISTA COMPOSTA
(defun aplanar(l)
    (cond
        ((null l) nil)
        ((listp (car l)) (append (aplanar (car l)) (aplanar (cdr l))))
        (T (cons (car l) (aplanar (cdr l))))
    )
)
;AGAFAR N
(defun agafar-n(n l)
    (cond
        ((= 1 n) (car l))
        (T (agafar-n (- n 1) (cdr l)))
    )
)

;ROTAR DRETA
(defun rotardreta(l)
    (cons (darrer l) (rdc l))
)

(defun darrer(l)
    (cond
        ((null (cdr l))(car l))
        (T (darrer (cdr l)))
    )
)

(defun rdc(l)
    (cond
        ((null (cdr l))nil)
        (T (cons (car l) (rdc (cdr l))))    
    )
)

;ROTAR ESQUERRA
(defun rotaresquerra(l)
    (append (cdr l) (list (car l)))
)

;SUMAR PARELLES(índex comença en 1)
(defun sumarparells(l)
    (cond
        ((null l) 0)
        ((null (cdr l))0)
        (T (+ (car (cdr l)) (sumarparells (cdr (cdr l)))))
    )
)
;SUMAR SENARS
(defun sumarsenars(l)
    (cond
        ((null l) 0)
        (T (+ (car l) (sumarsenars (cdr (cdr l)))))
    )
)


;POSICIÓ D'UN ELEMENT DINS LA LLISTA
;(posicio 'a '(t 2 b c a f g))
(defun posicio(x l)
    (cond
        ((null l) nil)
        ((equal (car l) x) 1)
        (T (+ 1 (posicio x (cdr l))))
    )
)


(defun indexa(x l)
    (cond
        ((null x) nil)
        ((null l) nil)
        (T (cons (agafar-n (car x) l) (indexa (cdr x) l)))
    )
)

;BORRAR UN CARÀCTER DE TOTES LES LLISTES
(defun borrarl(x l)
    (cond
        ((null l) nil)
        ((listp (car l)) (cons (borrarl x (car l)) (borrarl x (cdr l))))
        ((equal x (car l)) (borrarl x (cdr l)))
        (T (cons (car l) (borrarl x (cdr l))))
    )
)

;INVERTIR UNA LLISTA I TOTES LES SUBLLISTES
(defun invertirtot (l)
(cond ((null l) nil)
((listp (car l)) (snoc (invertirtot (car l)) (invertirtot (cdr l))))
(t (snoc (car l) (invertirtot (cdr l))))))

;Dependència de concatenar
(defun snoc (x l)
(cond ((null l) (list x))
(t (cons (car l) (snoc x (cdr l))))))


(defun comprimir(l)
    (cond
        ((null l) nil)
        (T (cons (compta (car l) l) (cons (car l) (comprimir (seguent (car l) l))))
        )
    )
)

(defun seguent(x l)
    (cond
        ((null l)nil)
        ((equal x (car l)) (seguent x (cdr l)))
        (T (cons (car l) (seguent x (cdr l))))
    )

)

(defun compta(x l)
    (cond
        ((null l)0)
        ((equal x (car l)) (+ 1 (compta x (cdr l))))
        (T (+ 0 (compta x (cdr l))))
    )
)




(defun replicar (n x)
    (cond
        ((= n 0)nil)
        (T (cons x (replicar (- n 1) x)))
    )
)



(defun descomprimir(l)
    (cond
        ((null l) nil)
        (T (append (replicar (car l) (car (cdr l))) (descomprimir (cdr (cdr l)))))
    )
)


(defun decimal(l)
    (calcula '0 (reverse l))
)

(defun calcula(expo l)
    (cond
        ((null l) 0)
        ((= 1 (car l)) (+ (potencia 2 expo) (calcula (+ expo 1) (cdr l))))
        (T(+ 0 (calcula (+ expo 1) (cdr l))))
    )
)

(defun potencia(b n)
    (cond 
        ((= n 0) 1)
        (T (* b (potencia b (- n 1))))
    )
)

(defun invertirllista(l)
    (cond 
        ((null l) nil)
        ((null (cdr l)) (cons (car l) nil))
        (T (cons (invertirllista (cdr l)) (car l)))
    )
)

(defun binari(n)
    (reverse (binari2 n))
)

(defun binari2(n)
    (cond
        ((< n 2) (list n))
        (T (cons (mod n 2) (binari2 (truncate n 2))))
    )
)
(defun / (x y)
(cond ( (< x y) 0)
(t (+ 1 (/ (- x y) y)))))

(defun modul(n modulo)
    (cond
        ((< n modulo) n)
        (T (modul (- n modulo) modulo))
    )

)




(defun pescalar(l1 l2)
    (cond
    ((null l1) 0)
    (T (+ (* (car l1) (car l2)) (pescalar (cdr l1) (cdr l2))))
    )
)

(defun mult(l)
    (cond
        ((null l) '1)
        (T (* (car l) (mult (cdr l))))
    )
)


(defun compta2(x l)

    (cond
        ((null l)0)
        ((equal x (car l)) (+ 1 (compta2 x (cdr l))))
        (T (+ 0 (compta2 x (cdr l))))
    )

)

(defun replicar2(n x)

    (cond
        ((= n 1) x)
        (T (cons x (replicar2 (- n 1) x)))    
    )
)


(defun repetits(l1 l2)
    (cond
        ((null l1)nil)
        ((conte (car l1) l2) (cons (car l1) (repetits (cdr l1) l2)))
        (T (repetits (cdr l1) l2))
    )
)


(defun conte(x l)
    (cond
        ((null l)nil)
        ((equal x (car l) ) T)
        (T (conte x (cdr l)))
    )
)



(defun parelles(l1 l2)
    (cond
        ((null l1)nil)
        (T (append (combinacions (car l1) l2) (parelles (cdr l1) l2)))
    )
)

(defun combinacions(x l)
    (cond
        ((null l)nil)
        (T (cons (list  x (car l)) (combinacions x (cdr l))))
    )
)



(defun atoms(l)
    (cond
        ((null l)0)
        ((listp (car l))(+ (atoms (car l)) (atoms (cdr l))))
        (T (+ 1 (atoms (cdr l))))
    )


)

(defun primerDarrer(l)
    (list (car l) (darrer l))

)

(defun darrer(l)
    (cond
        ((null l)nil)    
        ((null (cdr l)) (car l))
        (T (darrer (cdr l)))
    )
)


(defun longitudL(l)
    (cond
        ((null l)0)
        (T (+ 1 (longitudL (cdr l))))
    )
)


(defun simnum(l)
    (and (not (integerp (car l))) (integerp (darrer l)))
)


(defun sustituir21(x l)
    (cond
        ((null l)nil)
        ((equal x (car l)) (cons 'XXXX (sustituir21 x (cdr l))))
        (T (cons (car l) (sustituir21 x (cdr l))))
    )
)


(defun union(l1 l2)
    (cond
        ((null l1) l2)
        (T (cons (car l1) (union (cdr l1) l2)))
    )
)



(defun diferencia(l1 l2)
    (cond
        ((null l2) l1)
        ((null l1) nil)
        ((equal (car l1) (car l2)) (diferencia (cdr l1) (cdr l2)))
        (T (cons (car l1) (diferencia (cdr l1) l2)))
    )
)


(defun minmax(l)
    (list (minim '999999 l) (maxim '0 l))
)

(defun minim(x l)
    (cond 
        ((null l)x)
        ((< (car l) x) (minim (car l) (cdr l)))
        (T (minim x (cdr l)))
    )
)
(defun maxim(x l)
    (cond 
        ((null l)x)
        ((> (car l) x) (maxim (car l) (cdr l)))
        (T (maxim x (cdr l)))
    )
)

(defun menors(l x)
    (cond
        ((null l)nil)
        ((>= (car l) x) (menors (cdr l) x))
        (T (cons (car l) (menors (cdr l) x)))
    
    )

)
;Els darrers dos arguments han de estar entre x_1 i x_2
(defun menor-major(l)
    (setq primer (car l))
    (setq segon  (car(cdr l)))
    (and (entre primer segon (penultim l))   (entre primer segon (darrer l)))
)

(defun menor-major2(primer segon penult ult)
    (and (entre primer segon penult) (entre primer segon ult))
)

(defun penultim(l)
    (cond
        ((null l)nil)
        ((null (cdr l)) nil)
        ((null (cdr (cdr l))) (car l))
        (T (penultim (cdr l)))
    )
)

(defun entre(primer segon n)
    (and (< n primer) (> n segon))
)




(defun cerca(x l)
    (cond
        ((null l)0)
        ((equal x (car (cdr l))) (+ (car l) (cerca x (cdr (cdr l)))))
        (T (+ 0 (cerca x (cdr (cdr l)))))
    )
)