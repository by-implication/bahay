(ns bahay.style
  (:require
   [bahay.style.theme :as t]
   [garden.units :refer [px percent s]]
   [garden.color :refer [rgba] :as color]
   [om.dom :as dom]
   ))

(def typography
  (list
    [:body {:font-family " \"SF UI\", \"Roboto\""
            :font-size (px 16)
            :color t/dark}]
    [:a {:color t/accent}]
    [:.semitrans-text {:opacity 0.4}]))