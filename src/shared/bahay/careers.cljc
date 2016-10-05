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
      (dom/li #js {:className "grow"}
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

(defn blurb [title content]
  (dom/li #js {:className "grow"}
    (dom/h3 nil title)
    (dom/p nil content)))

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
          (dom/h2 nil "What we look for in people")
          (dom/ul #js {:className "h stacked guttered"}
            (blurb "Initiative" "We’re a small group with a lot to do, so there isn’t much looking-over-the-shoulder going on. We expect people to ask for work, instead of waiting for it to be given. Even better if they propose solutions to problems they see.")
            (blurb "A desire to grow" "We expect people to be good at what they do, but more importantly, take steps to get even better—even if it means asking for help from a colleague.")
            (blurb "Communication skills" "At By Implication, very few things are built solo. Teamwork is the way things go here, and good communication skills are absolutely important. Even more important is knowing to ask for help.")))
        (dom/div nil
          (dom/h2 nil "What we can offer")
          (dom/ul #js {:className "h stacked"}
            (blurb "Flexible hours" "The latest you can come in is 2 pm, the earliest you can leave is 5 pm. Some of our people work remotely as well! If that isn't enough for you, we don't know what will.")
            (blurb "A relaxed work environment" "We goof around a lot, play games, share stories. If there’s any place where you can be yourself, it’s in our office. A warning though, this is especially true for some of us, so things can get… unusual.")
            (blurb "Growth" "One of the things we look for in client work is challenge. If you find cutting-edge work exciting, then we might be a match made on the blade of a knife!")))
        (dom/div nil
          (dom/h2 nil "Available Positions")
          (dom/ul #js {:className "h stacked guttered"}
            (mapv role-feature
              available-positions)
            (role-feature
              {:role/id :else
               :role/label "Don't see your position here?"
               :role/reqs ["Convince us."]})))
        ))))

(def careers-view (om/factory Careers))
