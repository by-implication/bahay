(ns bahay.kubo
    (:require
     #?@(:cljs [[devtools.core :as devtools]
                [goog.dom :as gdom]])
     [om.dom :as dom]
     [om.next :as om :refer [defui]]
     ))

#?(:cljs
   (do
     (devtools/install!)
     (enable-console-print!)))

(def app-state (atom {}))

(defui Root
  Object
  (render [this]
    (dom/div nil "om loaded")))

(def reconciler
  (om/reconciler {:state app-state}))

#?(:cljs
   (om/add-root! reconciler
     Root
     (gdom/getElement "app")))

