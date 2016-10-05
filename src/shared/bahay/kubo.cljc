(ns bahay.kubo
  #?(:clj (:refer-clojure :exclude [read rem]))
  ;; #?(:cljs (:refer-clojure :exclude [println]))
  (:require
   #?@(:cljs [[devtools.core :as devtools]
              [goog.dom :as gdom]])
   [bahay.careers :as careers]
   [bahay.company :as company]
   [bahay.data :as bata]
   [bahay.home :as home]
   [bahay.om-style :as os]
   [bahay.parser :refer [read mutate]]
   [bahay.people :as people]
   [bahay.portfolio :as portfolio]
   [bahay.style :refer [typography]]
   [bahay.style.layout :as layout :refer [layout]]
   [bahay.style.theme :as t]
   [bidi.bidi :as bidi]
   [garden.units :refer [px percent]]
   [om.dom :as dom]
   [om.next :as om :refer [defui]]
   #?(:cljs [pushy.core :as pushy])
   ))

#?(:cljs
   (do
     (devtools/install!)
     (enable-console-print!)
     ;; below instead of enable-console-print!
     ;; for compatibility with devtools.
     #_(def println js/console.log)))

(def init-state
  {:current-view :home
   :services (vec (vals bata/services))
   :people bata/people
   :projects bata/projects
   :available-positions (mapv bata/roles
                          [:dev :des :mkt :pm])})

(def app-routes
  ["/" {"" :home
        ["portfolio/" :id] :project
        "company" :company
        "careers" :careers}])

(defui Toolbar
  static os/Style
  (style [this]
    [:.toolbar {:background :white
                :padding [[0 (px 32)]]
                :height (px 64)}
     [:a {:color t/dark
          :text-decoration :none}]])
  Object
  (render [this]
    (dom/div #js {:className "toolbar h stacked centered guttered"}
      (dom/a #js {:href "/"} "By Implication")
      (layout/spacer)
      (dom/a #js {:href "/company"} "Company")
      (dom/a #js {:href "/careers"} "Careers")
      (dom/a #js {:href "#"} "Contact"))))

(def toolbar-view (om/factory Toolbar))

(defui Root
  static os/Style
  (style [this]
    (list
      layout
      typography
      [:.big-icon {:width (px 96)
                   :height (px 96)}]
      [:body {:margin 0}]
      [:img {:max-width (percent 100)}]
      [:.bahay
       (os/get-style Toolbar)
       (os/get-style people/People)
       (os/get-style home/Home)
       (os/get-style careers/Careers)]))
  static om/IQuery
  (query [this]
    [:current-view
     {:company (om/get-query company/Company)}
     {:home (om/get-query home/Home)}
     {:careers (om/get-query careers/Careers)}])
  Object
  (render [this]
    (let [{:keys [current-view company home careers]} (om/props this)]
      (dom/div #js {:className "bahay"}
        (toolbar-view)
        (case current-view
          :home (home/home-view home)
          :company (company/company-view company)
          :careers (careers/careers-view careers)
          (dom/div nil "404"))
        (dom/footer #js {:style #js {:backgroundColor "#222"
                                     :height "200px"}}
          (dom/div #js {:className "container"}
            "I am a footer"))))))

(def parser
  (om/parser
    {:read read
     :mutate mutate}))

(def reconciler
  (om/reconciler {:state init-state
                  :parser parser}))

#?(:cljs
   (do
     (defn set-page! [matched]
       (js/console.log "matched!" matched)
       (om/transact! reconciler
         `[(page/set {:page-id ~(:handler matched)})
           :current-view]))

     (def history
       (pushy/pushy set-page!
         (partial bidi/match-route app-routes)))

     (pushy/start! history)

     #_
     (
      add-root! comes in last so pushy+bidi can determine
      proper route and set current-view before rendering
      )
     (om/add-root! reconciler
       Root
       (gdom/getElement "app"))))
