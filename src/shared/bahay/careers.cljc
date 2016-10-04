(ns bahay.careers
  (:require
   [bahay.om-style :as os]
   [garden.units :refer [px percent]]
   [om.dom :as dom]
   [om.next :as om :refer [defui]]
   ))

(defui Careers
  static os/Style
  (style [this]
    [:.careers {:background-color :blue}])
  Object
  (render [this]
    (dom/div #js {:className "container"}
      (dom/h1 nil "Work with us")
      (dom/div nil
        (dom/h2 nil "Traits")
        (dom/ul nil
          (dom/li nil "Initiative")
          (dom/li nil "Driven")
          (dom/li nil "Should not hesitate to ask for help"))))))

(def careers-view (om/factory Careers))
