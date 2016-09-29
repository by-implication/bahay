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
     :project/label
     :project/ownership
     :project/featured
     {:project/services (om/get-query Service)}])
  Object
  (render [this]
    (let [{:keys [project/id
                  project/label
                  project/ownership
                  project/services]} (om/props this)]
      (dom/div nil
        (dom/div nil label)
        (dom/div nil (name ownership))))))

(def project-view (om/factory Project))

(defui Portfolio
  static om/IQuery
  (query [this]
    [{:projects (om/get-query Project)}])
  Object
  (render [this]
    (let [{:keys [projects]} (om/props this)
          [featured & others] projects]
      #?(:cljs (js/console.log others))
      (dom/div nil
        (project-view featured)
        (apply dom/div nil
          (mapv (fn [p] (project-view p))
            others))))))

(def view (om/factory Portfolio))
