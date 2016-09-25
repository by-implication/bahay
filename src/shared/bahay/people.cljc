(ns bahay.people
  (:require
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
  static om/Ident
  (ident [this {:keys [person/id]}]
    [:person/by-id id])
  static om/IQuery
  (query [this]
    [:person/id :person/name
     {:roles (om/get-query Role)}])
  Object
  (render [this]
    (let [{:keys [person/name person/roles]} (om/props this)]
      (dom/div nil
        (:nick name)))))

(def person-view (om/factory Person
                   {:keyfn :person/id}))

(defui People
  static om/IQuery
  (query [this]
    [{:people (om/get-query Person)}])
  Object
  (render [this]
    (let [{:keys [people]} (om/props this)]
      (dom/div nil
        (mapv person-view
          people)))))

(def view (om/factory People))
