(defn countC [inp c] (count (filter #(= % c) (seq inp))))
(defn countChareN [inp i] (count (filter #(= (countC inp %) i) (seq inp))))
(defn countN [inp i] (count (filter #(> (countChareN % i) 0) inp)))
(defn blankifdiff [s1 s2 i] (if (= (nth s1 i) (nth s2 i)) (nth s1 i) ""))
(defn sameC [s1 s2]
  (reduce str (map #(blankifdiff s1 s2 %) (range 0 (count s1)))))
(defn otherDiff [s1 ss c]
  (filter #(= c (- (count %) (count (sameC s1 %)))) ss))

(let [inp (line-seq (java.io.BufferedReader. (java.io.FileReader. "input2.txt")))]
  (prn (* (countN inp 2) (countN inp 3)))
  (let [sim (filter #(> (count (otherDiff % inp 1)) 0) inp)]
    (prn (sameC (nth sim 0) (nth sim 1)))))

