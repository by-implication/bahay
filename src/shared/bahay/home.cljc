(ns bahay.home
  (:require
   #?@(:cljs
       [[goog.string :as gstring :refer [format]]
        [goog.string.format]])
   [bahay.om-style :as os]
   [bahay.portfolio :as portfolio]
   [garden.units :refer [px percent]]
   [om.dom :as dom]
   [om.next :as om :refer [defui]]))

(defui ServiceFeature
  static os/Style
  (style [this]
    [:.service-feature])
  static om/IQuery
  (query [this]
    (om/get-query portfolio/Service))
  Object
  (render [this]
    (let [{:keys [service/id service/label service/icon-id]} (om/props this)]
      (dom/div #js {:className "service-feature v stacked grow centered"}
        (dom/svg #js {:className "big-icon"}
          #?(:cljs (js/React.createElement "use"
                     #js {:xlinkHref (str "icons/service-icons.svg#" icon-id)})
             :clj (dom/use
                    #js {:xlinkHref (str "icons/service-icons.svg#" icon-id)}
                    nil)))
        (dom/h3 nil label)
        ))))

(def service-feature (om/factory ServiceFeature
                       {:keyfn :service/id}))

(defui Services
  static os/Style
  (style [this]
    [:.services])
  static om/IQuery
  (query [this]
    [{:services (om/get-query portfolio/Service)}])
  Object
  (render [this]
    (let [{:keys [services]} (om/props this)]
      (dom/div #js {:className "services container"}
        (dom/h2 nil "Our Services")
        (dom/div #js {:className "h stacked generously guttered"}
          (mapv service-feature services))))))

(def services-view (om/factory Services))

(defui Home
  static os/Style
  (style [this]
    [:.home
     (os/get-style portfolio/Portfolio)
     [:#hero {:text-align :center}
      [:h1 {:font-weight 300
            :font-size (px 64)}]]])
  static om/IQuery
  (query [this]
    [{:projects (om/get-query portfolio/Project)}
     {:services (om/get-query portfolio/Service)}])
  Object
  (render [this]
    (let [{:keys [projects services]} (om/props this)]
      (dom/div #js {:className "home"}
        (dom/div #js {:id "hero"}
          (dom/h1 nil "We help people do more.")
          (dom/p nil "We enable people from all walks of life walk faster."))
        (portfolio/view {:projects projects})
        (services-view {:services services})))))

(def home-view (om/factory Home))
