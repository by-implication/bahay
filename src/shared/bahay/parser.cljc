(ns bahay.parser
  #?(:clj (:refer-clojure :exclude [read]))
  (:require [om.next :as om]))

(defmulti read om/dispatch)

(defmethod read :default
  [{:keys [state]} key _]
  {:value (get @state key)})

