(ns bahay.kubo
  #?(:clj (:refer-clojure :exclude [read]))
  ;; #?(:cljs (:refer-clojure :exclude [println]))
  (:require
   #?@(:cljs [[devtools.core :as devtools]
              [goog.dom :as gdom]])
   [bahay.data :as bata]
   [bahay.parser :refer [read mutate]]
   [bahay.people :as people]
   [bidi.bidi :as bidi]
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
   :people bata/people})

(def app-routes
  ["/" {"" :portfolio
        "people" :people
        "about" :about}])

(defui Portfolio
  Object
  (render [this]
    (dom/div nil "Portfolio")))

(def portfolio-view (om/factory Portfolio))

(defui Root
  static om/IQuery
  (query [this]
    [:current-view {:people (om/get-query people/Person)}])
  Object
  (render [this]
    (let [{:keys [current-view people]} (om/props this)]
      (dom/div nil
        "Test"
        (dom/div nil
          (dom/a #js {:href "people"}
            "People"))
        #?(:cljs (js/console.log current-view people))
        (case current-view
          :portfolio (portfolio-view)
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
