(ns bahay.boot-prerender
  {:boot/export-tasks true}
  (:require
   [bahay.kubo :as kubo]
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
  [out-file]
  (let [html-string (dom/render-to-str ((om/factory kubo/Root)))]
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
        (let [out-file (io/file tmp "index.html")]
          (render-to-file! out-file)
          (-> fileset
            (c/add-resource tmp)
            c/commit!
            (next-handler)))))))
