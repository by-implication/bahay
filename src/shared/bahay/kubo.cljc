(ns bahay.kubo
  #?(:clj (:refer-clojure :exclude [read]))
  (:require
   #?@(:cljs [[devtools.core :as devtools]
              [goog.dom :as gdom]])
   [bahay.parser :refer [read]]
   [bahay.data :as bata]
   [om.dom :as dom]
   [om.next :as om :refer [defui]]
   ))

#?(:cljs
   (do
     (devtools/install!)
     (enable-console-print!)))

(def init-state
  {;;:roles bata/roles
   :people bata/people})

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
     :person/link :person/writeup
     {:person/roles (om/get-query Role)}]))

(defui Root
  static om/IQuery
  (query [this]
    [{:people (om/get-query Person)}])
  Object
  (render [this]
    (dom/div nil "om loaded")))

(def parser
  (om/parser
    {:read read}))

(def reconciler
  (om/reconciler {:state init-state
                  :parser parser}))

#?(:cljs
   (om/add-root! reconciler
     Root
     (gdom/getElement "app")))

