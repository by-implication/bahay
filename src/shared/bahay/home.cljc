(ns bahay.home
  (:require
   #?@(:cljs
       [[goog.string :as gstring :refer [format]]
        [goog.string.format]])
   [bahay.om-style :as os]
   [bahay.portfolio :as portfolio]
   [bahay.services :as services]
   [garden.units :refer [px percent]]
   [om.dom :as dom]
   [om.next :as om :refer [defui]]))

(defui Home
  static os/Style
  (style [this]
    [:.home
     (os/get-style portfolio/Portfolio)
     (os/get-style services/Services)
     [:#hero {:text-align :center}
      [:h1 {:font-weight 300
            :font-size (px 64)}]]])
  static om/IQuery
  (query [this]
    [{:projects (om/get-query portfolio/Project)}
     {:services (om/get-query services/Service)}])
  Object
  (render [this]
    (let [{:keys [projects services]} (om/props this)]
      (dom/div #js {:className "home"}
        (dom/div #js {:id "hero"}
          (dom/h1 nil "We help people do more.")
          (dom/p nil "We enable people from all walks of life walk faster."))
        (portfolio/view {:projects projects})
        (services/services-view {:services services})))))

(def home-view (om/factory Home))
