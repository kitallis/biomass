(ns biomass.util
  (:require [clj-time.format :as tf]))

(defn nil-or-integer
  [s]
  (when (seq s)
    (Integer/parseInt s)))

(defn nil-or-double
  [s]
  (when (seq s)
    (Double/parseDouble s)))

;;TODO: allow passing of formatter type, with default
(defn nil-or-datetime
  [s]
  (when (seq s)
    (tf/parse (tf/formatters :date-time-no-ms) s)))

(defn nil-or-boolean
  [s]
  (when (seq s)
    (Boolean/parseBoolean s)))

(defn restify-layout-params
  [params]
  (let [convert #(hash-map (keyword (str "HITLayoutParameter." %1 ".Name")) (name (first %2))
                           (keyword (str "HITLayoutParameter." %1 ".Value")) (second %2))]
    (reduce merge (map convert (range) params))))

(defn parse-zipped-xml
  [struct]
  (cond
    (map? struct) (assoc {} (:tag struct) (parse-zipped-xml (:content struct)))
    (sequential? struct) (let [mapped-content (map parse-zipped-xml struct)
                               content (if (= 1 (count mapped-content))
                                         (first mapped-content)
                                         (reduce merge {} mapped-content))]
                           content)
    :else struct))
