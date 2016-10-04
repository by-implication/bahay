(ns bahay.company
  (:require
   [bahay.om-style :as os]
   [bahay.people :as people]
   [garden.units :refer [px percent]]
   [om.dom :as dom]
   [om.next :as om :refer [defui]]))

(defui Company
  static om/IQuery
  (query [this]
    [{:people (om/get-query people/Person)}])
  Object
  (render [this]
    (let [{:keys [people]} (om/props this)]
      (dom/div #js {:className "container"}
        (people/people-view {:people people})))))

(def company-view (om/factory Company))
