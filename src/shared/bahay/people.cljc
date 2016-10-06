(ns bahay.people
  (:require
   [bahay.om-style :as os]
   [garden.units :refer [px percent]]
   [om.dom :as dom]
   [om.next :as om :refer [defui]]
   ))

(defui Role
  static om/Ident
  (ident [this {:keys [role/id]}]
    [:role/by-id id])
  static om/IQuery
  (query [this]
    [:role/id :role/label :role/icon-id :role/reqs]))

(defui Person
  static os/Style
  (style [this]
    [:.person {:display :inline-flex
               :width (px 128)}
     [:img {:display :block
            :width (px 96)
            :height (px 96)
            :border-radius (percent 50)
            :overflow :hidden}]])
  static om/Ident
  (ident [this {:keys [person/id]}]
    [:person/by-id id])
  static om/IQuery
  (query [this]
    [:person/id :person/display-name
     :person/image-url
     {:person/roles (om/get-query Role)}])
  Object
  (render [this]
    (let [{:keys [person/display-name person/roles
                  person/image-url]} (om/props this)]
      (dom/div #js {:className "person v stacked centered"}
        (dom/img #js {:src image-url})
        (dom/div nil display-name)))))

(def person-view (om/factory Person
                   {:keyfn :person/id}))

(defui People
  static os/Style
  (style [this]
    [:.people (os/get-style Person)])
  static om/IQuery
  (query [this]
    [{:people (om/get-query Person)}])
  Object
  (render [this]
    (let [{:keys [people]} (om/props this)]
      (dom/div #js {:className "people"}
        (->> people
          (sort-by :person/display-name)
          (mapv person-view))))))

(def people-view (om/factory People))
