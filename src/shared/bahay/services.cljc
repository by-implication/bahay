(ns bahay.services
  (:require
   [bahay.om-style :as os]
   [bahay.util :as u]
   [garden.units :refer [px percent s]]
   [om.dom :as dom]
   [om.next :as om :refer [defui]]))

(defui Service
  static os/Style
  (style [this]
    [:.service-label {:display :inline-block}
     [:&+.service-label {:margin-left (px 8)}]])
  static om/Ident
  (ident [this {:keys [service/id]}]
    [:service/by-id id])
  static om/IQuery
  (query [this]
    [:service/id :service/label :service/icon-id])
  Object
  (render [this]
    (let [{:keys [service/id service/label]} (om/props this)]
      (dom/span #js {:className "service-label"} label))))

(def service-view (om/factory Service
                    {:keyfn :service/id}))

(defui ServiceFeature
  static os/Style
  (style [this]
    [:.service-feature
     [:h3 {:font-weight 300}]])
  static om/IQuery
  (query [this]
    (om/get-query Service))
  Object
  (render [this]
    (let [{:keys [service/id service/label service/icon-id]} (om/props this)]
      (dom/div #js {:className "service-feature v stacked grow centered"}
        (dom/svg #js {:className "icon icon-lrg"}
          (u/use #js {:xlinkHref (str "#" icon-id)}))
        (dom/h3 nil label)
        ))))

(def service-feature (om/factory ServiceFeature
                       {:keyfn :service/id}))

(defui Services
  static os/Style
  (style [this]
    [:.services (os/get-style ServiceFeature)])
  static om/IQuery
  (query [this]
    [{:services (om/get-query Service)}])
  Object
  (render [this]
    (let [{:keys [services]} (om/props this)]
      (dom/div #js {:className "services container"}
        (dom/h2 nil "Our Services")
        (dom/div #js {:className "h stacked generously guttered"}
          (mapv service-feature services))))))

(def services-view (om/factory Services))
