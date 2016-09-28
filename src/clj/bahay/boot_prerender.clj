(ns bahay.boot-prerender
  {:boot/export-tasks true}
  (:require
   [bahay.data :as bata]
   [bahay.kubo :as kubo]
   [bahay.people :as people]
   [boot.core :as boot :refer [deftask]]
   [boot.util :as util]
   [clojure.java.io :as io]
   [hiccup.page :as hiccup]
   [om.dom :as dom]
   [om.next :as om]))

(defn html-wrapper [html-string]
  (hiccup/html5
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:http-equiv "X-UA-Compatible"
             :content "IE=edge"}]
     [:title "By Implication"]
     (hiccup/include-css "/css/styles.css")]
    [:body
     [:div#app html-string]
     (hiccup/include-js "/main.js")]))

(defn- render-to-file!
  [out-file route params]
  (let [r (om/reconciler {:state (assoc kubo/init-state
                                   :current-view route)
                          :parser kubo/parser})
        c (om/add-root! r kubo/Root nil)
        html-string (dom/render-to-str c)]
    (doto out-file
      io/make-parents
      (spit (html-wrapper html-string)))))

(defn all-paths []
  (->> (second bahay.kubo/app-routes)
    (reduce
      (fn [paths [path route]]
        (if (vector? path)
          (let [[subpath _] path]
            (->> bahay.data/projects
              (reduce
                (fn [paths {:keys [project/id]}]
                  (conj paths
                    {:path (str subpath
                             (name id)
                             "/index.html")
                     :route :project
                     :params {:portfolio/id id}}))
                paths)))
          (do
            (conj paths
              {:path (str path
                       (when-not (clojure.string/blank? path) "/")
                       "index.html")
               #_(str path "index.html")
               :route route}))))
      [])))

(deftask om-prerender
  "Prerender frontend UI to index.html"
  []
  (let [tmp (boot/tmp-dir!)]
    (boot/with-pre-wrap fileset
      (require 'bahay.kubo :reload)
      (boot/empty-dir! tmp)
      (util/info "Prerendering...\n")
      (doseq [{:keys [path route params]} (all-paths)]
        (let [out-file (io/file tmp path)]
          (render-to-file! out-file route params)))
      (-> fileset
        (boot/add-resource tmp)
        boot/commit!))))
