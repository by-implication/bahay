(ns bahay.kubo
  #?(:clj (:refer-clojure :exclude [read]))
  #?(:cljs (:refer-clojure :exclude [println]))
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
     (def println js/console.log)))

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
      (println :current-view current-view (om/props this))
      (dom/div nil
        "Test"
        (case current-view
          :portfolio (portfolio-view)
          :people (people/view people)
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
     (om/add-root! reconciler
       Root
       (gdom/getElement "app"))

     (defn set-page! [matched]
       (om/transact! reconciler
         `[(page/set {:page-id (:handler matched)})
           :current-view]))

     (def history
       (pushy/pushy set-page!
         (partial bidi/match-route app-routes)))

     (pushy/start! history)))
