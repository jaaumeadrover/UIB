(defun comprimir (l)
(cond ((null l) nil)
(t (cons (compta (car l) l) (cons (car l) (comprimir (botar (car l) (cdr l))))))))
(defun botar (x l)
(cond ((null l) nil)
((equal x (car l)) (botar x (cdr l)))
(t l)))





(defun compta(x l)
    (cond
        ((null l)0)
        ((equal x (car l)) (+ 1 (compta x (cdr l))))
        (T (+ 0 (compta x (cdr l))))
    )
)