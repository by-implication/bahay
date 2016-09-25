(def project 'bahay)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"resources"}
  :source-paths #{"src/shared" "src/cljs" "src/clj" "src/garden"}
  :dependencies '[[adzerk/boot-cljs "1.7.228-1" :scope "test"]
                  [adzerk/boot-cljs-repl "0.3.3" :scope "test"]
                  [adzerk/boot-reload "0.4.12" :scope "test"]
                  [com.cemerick/piggieback "0.2.1" :scope "test"]
                  [weasel                  "0.7.0" :scope "test"]
                  [pandeiro/boot-http "0.7.3" :scope "test"]

                  ;; clojure
                  [org.clojure/clojure "1.9.0-alpha12"]
                  [org.clojure/clojurescript "1.9.229"]
                  [org.clojure/core.async "0.2.391"]
                  [org.clojure/test.check "0.9.0" :scope "test"]
                  [org.clojure/tools.nrepl "0.2.12" :scope "test"]

                  ;; server
                  [hiccup "1.0.5"]

                  ;; styles
                  [danielsz/boot-autoprefixer "0.0.8"]
                  [garden "1.3.0"]
                  [org.martinklepsch/boot-garden "1.3.0-0"]

                  ;; shared
                  [org.omcljs/om "1.0.0-alpha45"]
                  [bidi "2.0.10"]

                  ;; client
                  [kibu/pushy "0.3.6"]
                  [binaryage/devtools "0.8.1" :scope "test"]
                  ])

(load-data-readers!)

(require
  '[adzerk.boot-cljs      :refer [cljs]]
  '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
  '[adzerk.boot-reload    :refer [reload]]
  '[bahay.boot-prerender :refer [om-prerender]]
  '[clojure.pprint :refer [pprint]]
  '[danielsz.autoprefixer :refer [autoprefixer]]
  '[org.martinklepsch.boot-garden :refer [garden]]
  '[pandeiro.boot-http    :refer [serve]]
  )

(deftask dev
  "Run app"
  []
  (comp
    ;; (reload)
    (om-prerender)
    (serve)
    (watch)
    (speak)
    (reload)
    (cljs-repl)
    (cljs :source-map true :optimizations :none)
    (garden :styles-var 'bahay.styles/base
      :output-to "css/styles.css")
    (autoprefixer :files ["styles.css"])))

