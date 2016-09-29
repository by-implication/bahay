(ns bahay.kubo
  #?(:clj (:refer-clojure :exclude [read rem]))
  ;; #?(:cljs (:refer-clojure :exclude [println]))
  (:require
   #?@(:cljs [[devtools.core :as devtools]
              [goog.dom :as gdom]])
   [bahay.data :as bata]
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
     ;; below instead of enable-console-print!
     ;; for compatibility with devtools.
     #_(def println js/console.log)))

(def init-state
  {:current-view :portfolio
   :people bata/people
   :projects bata/projects})

(def app-routes
  ["/" {"" :portfolio
        ["portfolio/" :id] :project
        "people" :people
        "about" :about}])

(defui Toolbar
  static os/Style
  (style [this]
    [:.toolbar {:background :white
                :height (px 64)
                :padding [[0 (px 16)]]}
     [:a {:color t/dark
          :text-decoration :none}]])
  Object
  (render [this]
    (dom/div #js {:className "toolbar h stacked centered guttered"}
      (dom/a #js {:href "/"} "By Implication")
      (layout/spacer)
      (dom/a #js {:href "/people"} "People")
      (dom/a #js {:href "/projects"} "Projects"))))

(def toolbar-view (om/factory Toolbar))

(defui Root
  static os/Style
  (style [this]
    (list
      layout
      typography
      [:body {:margin 0}]
      [:.bahay
       (os/get-style Toolbar)
       (os/get-style people/People)]))
  static om/IQuery
  (query [this]
    [:current-view
     {:people (om/get-query people/Person)}
     {:projects (om/get-query portfolio/Project)}])
  Object
  (render [this]
    (let [{:keys [current-view people projects]} (om/props this)]
      (dom/div #js {:className "bahay"}
        (toolbar-view)
        (dom/div nil
          (dom/a #js {:href "/people"}
            "People"))
        (case current-view
          :portfolio (portfolio/view {:projects projects})
          :people (people/view {:people people})
          :about (dom/div nil "about")
          (dom/div nil "404"))))))

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
