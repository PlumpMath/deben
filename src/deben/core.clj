(ns deben.core
  (:import [java.io File]
           [java.util UUID])
  (:require [clojure.edn :as edn]
            [clj-leveldb :as l]
            [byte-streams :as bs]))

(def db
  (l/create-db
    (doto (File. (str "/tmp/" (UUID/randomUUID)))
      .deleteOnExit)
    {:key-encoder name
     :key-decoder (comp keyword bs/to-string)
     :val-decoder (comp edn/read-string bs/to-char-sequence)
     :val-encoder pr-str}))


(defn- nested-key [id keys]
  {:pre [(string? id) (every? keyword? keys)]}
  (apply str id keys))

;; new map

(def m {:a {:b 1 :c 2}})

(defn new-map! [id keyvals]
  (loop [ks {}
         kvs (seq keyvals)]
    (if-let [[k v] (first kvs)]
      (let [kk (nested-key id [k])]
        (if (map? v)
          (new-map! kk v)
          (l/put db kk v))
        (recur (assoc ks k kk) (rest kvs)))
      (l/put db id ks))))

(defn get [id k]
  (let [kk (nested-key id [k])]
    (l/get db kk)))


(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
