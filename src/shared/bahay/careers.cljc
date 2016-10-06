(ns bahay.careers
  (:require
   [bahay.om-style :as os]
   [bahay.people :as people]
   [bahay.util :as u]
   [garden.units :refer [px percent]]
   [om.dom :as dom]
   [om.next :as om :refer [defui]]
   ))

(defui RoleFeature
  static os/Style
  (style [this]
    [:.role-feature
     [:h3 {:margin-bottom 0
           :text-align :center}]
     [:.reqs {:font-size (px 12)}]])
  static om/IQuery
  (query [this]
    (om/get-query people/Role))
  Object
  (render [this]
    (let [{:keys [role/label role/reqs role/icon-id]} (om/props this)]
      (dom/li #js {:className "grow role-feature v stacked gutters centered"}
        (dom/svg #js {:className "icon icon-mid"}
          (u/use #js {:xlinkHref (str "#" icon-id)}))
        (dom/h3 nil label)
        (dom/ul #js {:className "reqs v stacked gutters"}
          (->> reqs
            (map-indexed
              (fn [index req]
                (dom/li #js {:key (str "req" index)}
                  req)))
            (vec)))))))

(def role-feature (om/factory RoleFeature
                    {:keyfn :role/id}))

(defn blurb
  ([title content]
   (blurb nil title content))
  ([icon-id title content]
   (dom/li #js {:className "blurb grow v stacked gutters centered"}
     (dom/svg #js {:className "icon icon-lrg"}
       (u/use #js {:xlinkHref (str "#" icon-id)}))
     (dom/h3 nil title)
     (dom/div nil
       (dom/p nil content)))))

(defui Careers
  static os/Style
  (style [this]
    [:.careers
     (os/get-style RoleFeature)
     [:.blurb
      [:h3 {:margin-bottom 0}]
      [:p {:margin 0}]]])
  static om/IQuery
  (query [this]
    [{:available-positions (om/get-query people/Role)}])
  Object
  (render [this]
    (let [{:keys [available-positions]} (om/props this)]
      (dom/div #js {:className "container careers"}
        (dom/h1 nil "Work with us")
        (dom/hr nil)
        (dom/div nil
          (dom/h2 nil "What we look for in people")
          (dom/ul #js {:className "h stacked gutters-lrg"}
            (blurb "steering" "Initiative" "We’re a small group with a lot to do, so there isn’t much looking-over-the-shoulder going on. We expect people to ask for work, instead of waiting for it to be given. Even better if they propose solutions to problems they see.")
            (blurb "plant" "A desire to grow" "We expect people to be good at what they do, but more importantly, take steps to get even better—even if it means asking for help from a colleague.")
            (blurb "comm" "Communication skills" "At By Implication, very few things are built solo. Teamwork is the way things go here, and good communication skills are absolutely important. Even more important is knowing to ask for help.")))
        (dom/hr nil)
        (dom/div nil
          (dom/h2 nil "What we can offer")
          (dom/ul #js {:className "h stacked gutters-lrg"}
            (blurb "clock"
              "Flexible hours"
              "The latest you can come in is 2 pm, the earliest you can leave is 5 pm. Some of our people work remotely as well! If that isn't enough for you, we don't know what will be.")
            (blurb "couch"
              "A relaxed work environment"
              "We goof around a lot, play games, share stories. If there’s any place where you can be yourself, it’s in our office. A warning though, this is especially true for some of us, so things can get… unusual.")
            (blurb "watercan"
              "Growth"
              "One of the things we look for in client work is challenge. If you find cutting-edge work exciting, then we might be a match made on the blade of a knife!")))
        (dom/hr nil)
        (dom/div nil
          (dom/h2 nil "Available Positions")
          (dom/ul #js {:className "v stacked gutters-lrg"}
            (mapv (fn [ps] (dom/div #js {:className "h stacked gutters-lrg"}
                             (mapv role-feature ps)))
              (partition-all 3
                (conj available-positions
                  {:role/id :else
                   :role/label "Don't see your position here?"
                   :role/icon-id "missing"
                   :role/reqs ["Convince us."]})))
            ))
        ))))

(def careers-view (om/factory Careers))
