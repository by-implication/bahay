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
            :color "#777"
            :font-weight 300
            :line-height 1.5}]
    [:h1 :h2 :h3 :h4 :h5 :h6 {:color "#333"}]
    [:a {:color t/accent}]
    [:hr {:border-top :none
          :border-bottom-color "#ddd"
          :margin-top (px 16)
          :margin-bottom (px 16)}]
    [:ul {:list-style :none
          :padding 0
          :margin 0}]
    [:.semitrans-text {:opacity 0.4}]))
