(ns bahay.boot-prerender
  {:boot/export-tasks true}
  (:require
   [bahay.kubo :as kubo]
   [bahay.people :as people]
   [boot.core :as c]
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
     (hiccup/include-css "css/styles.css")]
    [:body
     html-string
     (hiccup/include-js "main.js")]))

(defn- render-to-file!
  [out-file route]
  (let [r (om/reconciler {:state (assoc kubo/init-state
                                   :current-view route)
                          :parser kubo/parser})
        c (om/add-root! r kubo/Root nil)
        html-string (dom/render-to-str c)]
    (doto out-file
      io/make-parents
      (spit (html-wrapper html-string)))))

(c/deftask om-prerender
  "Prerender frontend UI to index.html"
  []
  (let [tmp (c/tmp-dir!)]
    (fn middleware [next-handler]
      (fn handler [fileset]
        (c/empty-dir! tmp)
        (doseq [[path route] (second kubo/app-routes)]
          (let [out-file (io/file tmp (str path
                                        (when-not (clojure.string/blank? path) "/")
                                        "index.html"))]
            (render-to-file! out-file route)))
        (-> fileset
          (c/add-resource tmp)
          c/commit!
          (next-handler))))))
