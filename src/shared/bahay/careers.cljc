(ns bahay.careers
  (:require
   [bahay.om-style :as os]
   [bahay.people :as people]
   [garden.units :refer [px percent]]
   [om.dom :as dom]
   [om.next :as om :refer [defui]]
   ))

(defui RoleFeature
  static om/IQuery
  (query [this]
    (om/get-query people/Role))
  Object
  (render [this]
    (let [{:keys [role/label role/reqs]} (om/props this)]
      (dom/li nil
        (dom/h3 nil label)
        (dom/ul nil
          (->> reqs
            (map-indexed
              (fn [index req]
                (dom/li #js {:key (str "req" index)}
                  (dom/p nil req))))
            (vec)))))))

(def role-feature (om/factory RoleFeature
                    {:keyfn :role/id}))

(defui Careers
  static os/Style
  (style [this]
    [:.careers {:background-color :blue}])
  static om/IQuery
  (query [this]
    [{:available-positions (om/get-query people/Role)}])
  Object
  (render [this]
    (let [{:keys [available-positions]} (om/props this)]
      (dom/div #js {:className "container"}
        (dom/h1 nil "Work with us")
        (dom/div nil
          (dom/h2 nil "Available Positions")
          (dom/ul nil
            (mapv role-feature
              available-positions)))
        (dom/div nil
          (dom/h2 nil "Traits")
          (dom/ul nil
            (dom/li nil "Initiative")
            (dom/li nil "Driven")
            (dom/li nil "Should not hesitate to ask for help")))))))

(def careers-view (om/factory Careers))
