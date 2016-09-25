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
     {:roles (om/get-query Role)}]))

(defui People
  static om/IQuery
  (query [this]
    [{:people (om/get-query Person)}])
  Object
  (render [this]
    (dom/div nil
      "Test")))

(def view (om/factory People))
