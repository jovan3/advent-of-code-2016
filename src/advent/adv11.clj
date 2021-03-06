(ns advent.adv11
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:gen-class))

(def data-file (io/file (io/resource "adv1")))
(def direction-value {:L 3 :R 1})
(def orientations [:north :east :south :west])
(def orientation-vectors
  {:north '(0 1)
   :east '(1 0)
   :south '(0 -1)
   :west '(-1 0)})

(defn direction [item]
  (first item))

(defn distance [item]
  (re-find #"\d+" item))

(defn sum-positions [pos1 pos2]
  (map #(+ (first %) (second %)) (map vector pos1 pos2)))

(defn new-orientation [orientation dir]
  (let [orientation-index (.indexOf orientations orientation)
        new-index (mod (+ orientation-index ((keyword (str dir)) direction-value)) 4)]
    (get orientations new-index)))

(defn new-position [position orient dist]
  (let [movement (map #(* (Integer. dist) %) (orient orientation-vectors))]
    (sum-positions position movement)))

(defn walk [steps position orientation]
  (if (= steps '())
    position
    (let [step (first steps)
          direction (direction step)
          distance (distance step)
          new-orientation (new-orientation orientation direction)
          new-pos (new-position position new-orientation distance)]
      (recur (rest steps) new-pos new-orientation))))     

(defn adv11 []
  (let [steps (str/split (slurp data-file) #", ")]
    (println "The distance is:" (reduce + (walk steps '(0 0) :north)) "blocks")))
