(ns bahay.portfolio
  (:require
   [bahay.om-style :as os]
   [garden.units :refer [px percent]]
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
    [:service/id :service/label])
  Object
  (render [this]
    (let [{:keys [service/id service/label]} (om/props this)]
      (dom/span #js {:className "service-label"} label))))

(def service-view (om/factory Service
                    {:keyfn :service/id}))

(defui Project
  static os/Style
  (style [this]
    [:.project (os/get-style Service)])
  static om/Ident
  (ident [this {:keys [project/id]}]
    [:project/by-id id])
  static om/IQuery
  (query [this]
    [:project/id
     :project/label
     :project/ownership
     :project/featured
     :project/accent
     :project/image-url
     {:project/services (om/get-query Service)}])
  Object
  (render [this]
    (let [{:keys [project/id
                  project/label
                  project/ownership
                  project/accent
                  project/image-url
                  project/services]} (om/props this)]
      (dom/div #js {:className "project"}
        (dom/div nil label)
        (dom/div nil (name ownership))
        (apply dom/div nil (mapv service-view services))
        (dom/div #js {:style {:background-color accent}}
          (dom/img #js {:src image-url}))))))

(def project-view (om/factory Project
                    {:keyfn :project/id}))

(defui Portfolio
  static os/Style
  (style [this]
    [:.portfolio (os/get-style Project)])
  static om/IQuery
  (query [this]
    [{:projects (om/get-query Project)}])
  Object
  (render [this]
    (let [{:keys [projects]} (om/props this)
          [featured & others] projects]
      #?(:cljs (js/console.log others))
      (dom/div #js {:className "portfolio container"}
        (dom/div #js {:className "v stacked generously guttered"}
          (project-view featured)
          (apply dom/div #js {:className "h stacked generously guttered"}
            (mapv (fn [p]
                    (dom/div #js {:className "grow"}
                      (project-view p)))
              others)))))))

(def view (om/factory Portfolio))
