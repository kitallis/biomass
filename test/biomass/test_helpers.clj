(ns biomass.test-helpers)

(defn find-in-response-with-path
  [[current-key & path] coll]
  (if path
    (->> coll
         (filter #(contains? % current-key))
         (map current-key)
         (map (partial find-in-response-with-path path))
         flatten)
    (filter #(contains? % current-key) coll)))

(defn hit-type-id-from-response
  [response]
  (->> [response]
       (find-in-response-with-path [:response :RegisterHITTypeResponse  :RegisterHITTypeResult :HITTypeId])
       first
       :HITTypeId
       first))

(defn register-hittype-request-validity-from-response
  [response]
  (->> [response]
       (find-in-response-with-path [:response :RegisterHITTypeResponse  :RegisterHITTypeResult :Request :IsValid])
       first
       :IsValid
       first))

(defn create-hit-request-validity-from-response
  [response]
  (->> [response]
       (find-in-response-with-path [:response :CreateHITResponse :HIT :Request :IsValid])
       first
       :IsValid
       first))

(defn hit-ids-from-search-hit-response
  [response]
  (->> [response]
       (find-in-response-with-path [:response :SearchHITsResponse :SearchHITsResult :HIT :HITId])
       (map :HITId)
       (map first)))

(defn hit-id-from-create-hit-response
  [response]
  (->> [response]
       (find-in-response-with-path [:response :CreateHITResponse :HIT :HITId])
       first
       :HITId
       first))

(defn disable-hit-validity-from-response
  [response]
  (->> [response]
       (find-in-response-with-path [:response :DisableHITResponse :DisableHITResult :Request :IsValid])
       first
       :IsValid
       first))