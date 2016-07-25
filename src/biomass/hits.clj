(ns ^{:author "smnirven"
      :doc "Contains methods for making HITs API requests to MTurk"}
  biomass.hits
  (:require [biomass.request :refer :all]
            [biomass.response.hits :refer :all]
            [biomass.util :as util]
            [biomass.builder.builder :refer [convert-params]]
            [biomass.builder.schemas :refer :all]
            [schema.core :as s]))

(defn- send-and-parse
  [operation params]
  (let [resp (send-request operation params)]
    (when (= (:status resp) 200)
      (parse operation (:body resp)))))


(defn get-hit
  [hit-id]
  (send-and-parse "GetHIT" {:HITId hit-id}))

(defn get-reviewable-hits
  []
  (send-and-parse "GetReviewableHITs" {}))

(defn search-hits
  []
  (send-and-parse "SearchHITs" {}))

(defn get-hits-for-qualification-type
  [{:keys [qualification-type-id page-size page-number]}]
  ;;TODO: defaults for optional page-size and page-number params
  (send-request {:Operation "GetHITsForQualificationType"
                 :PageSize page-size
                 :PageNumber page-number}))


(defn register-hit-type
  [params]
  (send-and-parse "RegisterHITType" (convert-params (s/validate schema-RegisterHITType params))))


(defn create-hit
  [params]
  (send-and-parse "CreateHIT" (convert-params (s/validate schema-CreateHIT params))))

(defn disable-hit
  [hit-id]
  (send-and-parse "DisableHIT" {:HITId hit-id}))

(defn dispose-hit
  [hit-id]
  (send-and-parse "DisposeHIT" {:HITId hit-id}))
