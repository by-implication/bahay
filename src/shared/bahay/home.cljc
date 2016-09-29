(ns bahay.home
  (:require
   [bahay.om-style :as os]
   [bahay.portfolio :as portfolio]
   [om.dom :as dom]
   [om.next :as om :refer [defui]]))

(defui Home
  static os/Style
  (style [this]
    [:home (os/get-style portfolio/Portfolio)])
  static om/IQuery
  (query [this]
    [{:projects (om/get-query portfolio/Project)}
     {:services (om/get-query portfolio/Service)}])
  Object
  (render [this]
    (let [{:keys [projects services]} (om/props this)]
      (dom/div nil
        (portfolio/view {:projects projects})))))

(def home-view (om/factory Home))
