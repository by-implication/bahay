(ns bahay.parser
  #?(:clj (:refer-clojure :exclude [read]))
  (:require
   [bahay.people :as people]
   [bahay.portfolio :as portfolio]
   [om.next :as om]))

(defmulti read om/dispatch)

(defmethod read :default
  [{:keys [state]} key _]
  {:value (get @state key)})

(defmethod read :people
  [{:keys [state]} key _]
  (let [st @state]
    {:value (om/db->tree (om/get-query people/Person)
              (get st key)
              st)}))

(defmethod read :projects
  [{:keys [state]} key _]
  (let [st @state]
    {:value (om/db->tree (om/get-query portfolio/Project)
              (get st key)
              st)}))

(defmulti mutate om/dispatch)

(defmethod mutate 'page/set
  [{:keys [state]} key {:keys [page-id]}]
  {:value {:keys [:current-view]}
   :action (fn []
             (println "yo")
             (swap! state assoc :current-view page-id))})
