(ns bahay.core
    (:require
     [devtools.core :as devtools]
     [goog.dom :as gdom]
     [om.dom :as dom]
     [om.next :as om :refer-macros [defui]]
     ))

(devtools/install!)
(enable-console-print!)

(def app-state (atom {}))

(defui Root
  Object
  (render [this]
          (dom/div nil "om loaded")))

(def reconciler
  (om/reconciler {:state app-state}))

(om/add-root! reconciler
              Root
              (gdom/getElement "app"))

(defn main []
  (println "reloading"))
