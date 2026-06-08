(defun aplanar(l)
    (cond
        ((null l) nil)
        ((listp (car l)) (append (aplanar (car l)) (aplanar (cdr l))))
        (T (cons (car l) (aplanar (cdr l))))
    )
)

(aplanar '(a (b c (d e) f) g))


(defun rotardreta(l)
    (cond 
        ((null (cdr l)) nil)    
        (T (cons (darrer l) (rdc l)))
    )
)
(defun rdc(l)
    (cond
        ((null (cdr l)) nil)
        (T (cons (car l) (rdc (cdr l))))
    
    )
)


(defun darrer(l)
    (cond
        ((null (cdr l)) (car l))
        (T (darrer (cdr l)))
    )
)





(defun rotaresquerra(l)
    (cond
        (T (list (cdr l) (car l)))    
    )
)

(rotaresquerra '(a b c d e f))



(defun maxLlista(l)

    (cond
        ((null (cdr l))(car l))
    
        (T (max (car l) (maxLlista (cdr l))))
    )
)

(defun max(x y)

    (cond
        ((>= x y) x)
        (T y)
    )
)


(defun sustituir(x l)
    (cond
        ((null l) nil)
        ((= (car x) (car l)) (substr x l (length x)))
        (T (cons (car l) (sustituir x (cdr l))))  
    )
)

(defun substr(x l n)
    (cond 
        ((= x (obtenirPorcio l n)) ())
    
    
    )


)


(defun obtenirPorcio(l n)

    (cond
        ((= n 1) (car l))
        (T (cons (car l) (obtenirPorcio (cdr l) (- n 1))))
    )


)


(defun pescalar(a b)
    (cond
        ((null a) 0)
        (T (+ (* (car a) (car b)) (pescalar (cdr a) (cdr b))))
    )


)




(defun union(a b)
    (cond
        ((null a) b)
        (T (cons (car a) (union (cdr a) b)))
    )
)

(defun diferencia(l x)

    (cond
    ((null l) nil)
    ((= (car l) (car x)) (diferencia (cdr l) (cdr x)))
    (T (cons (car l) (diferencia (cdr l) x)))
    )
)


(defun sustituir(x l)
    (cond 
        ((null l) nil)
        ((equal x (car l)) (cons 'XXXX (sustituir x (cdr l))))
        (T (cons (car l) (sustituir x (cdr l))))
    )
)




(defun indexa(i l)
    (indexa2 i l 1)
)

(defun indexa2(x l i)
    (cond
        ((null l) nil)
        ((= (car x) i) (cons (car l) (indexa2 (cdr x) (cdr l) (+ 1 i))))
        (T (indexa2 x (cdr l) (+ 1 i)))
    )
)