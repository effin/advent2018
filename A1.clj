(defn firstrepeat
  ([inp] (firstrepeat 0 0 inp (hash-set)))
  ([f i inp vals] (let [newf (+ f (nth inp i))]
                    (if (not (nil? (get vals newf)))
                      newf
                      (recur newf (mod (+ i 1) (count inp)) inp (conj vals newf))))))
(let [inp (map bigdec (line-seq (java.io.BufferedReader. (java.io.FileReader. "input1.txt"))))]
  (prn (reduce + inp))
  (prn (firstrepeat inp)))

