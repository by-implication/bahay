(ns bahay.portfolio
  (:require
   [bahay.om-style :as os]
   [om.dom :as dom]
   [om.next :as om :refer [defui]]))

(defui Service
  static om/Ident
  (ident [this {:keys [service/id]}]
    [:service/by-id id])
  static om/IQuery
  (query [this]
    [:service/id :service/name]))

(defui Project
  static om/Ident
  (ident [this {:keys [project/id]}]
    [:project/by-id id])
  static om/IQuery
  (query [this]
    [:project/id
     :project/name
     :project/ownership
     {:project/services (om/get-query Service)}]))

(defui Portfolio
  static om/IQuery
  (query [this]
    [{:projects (om/get-query Project)}])
  Object
  (render [this]
    (dom/div nil "projects")))

(def view (om/factory Portfolio))
