(ns bahay.om-style
  (:require
   [om.next :as om]))

(defprotocol Style
  (style [this]))

(defn get-style
  "Gets the colocated style of either a component or a class"
  [x]
  #?(:clj ((:style (meta x)) x)
     :cljs (style x))
  )
