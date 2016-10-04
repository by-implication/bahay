(ns bahay.people
  (:require
   [bahay.om-style :as os]
   [om.dom :as dom]
   [om.next :as om :refer [defui]]
   ))

(defui Role
  static om/Ident
  (ident [this {:keys [role/id]}]
    [:role/by-id id])
  static om/IQuery
  (query [this]
    [:role/id :role/name]))

(defui Person
  static os/Style
  (style [this]
    [:.person {:background-color :pink}])
  static om/Ident
  (ident [this {:keys [person/id]}]
    [:person/by-id id])
  static om/IQuery
  (query [this]
    [:person/id :person/name
     {:person/roles (om/get-query Role)}])
  Object
  (render [this]
    (let [{:keys [person/name person/roles]} (om/props this)]
      (dom/div #js {:className "person"}
        (:nick name)))))

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
        (mapv person-view
          people)))))

(def people-view (om/factory People))
