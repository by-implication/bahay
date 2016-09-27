(ns bahay.boot-om-style
  {:boot/export-tasks true}
  (:require
   ;; [bahay.kubo]
   [bahay.om-style :as os]
   [boot.core :as boot :refer [deftask]]
   [clojure.java.io :as io]
   [garden.core :as g]
   [org.martinklepsch.boot-garden :refer [garden]]))

(deftask om-style
  "Generate a stylesheet based on the
colocated styles of a root om.next class"
  [r root-class SYM sym "The root om.next class whose styles are to be generated"
   o output-to  PATH str "The output css file path relative to docroot."
   p pretty-print bool "Pretty print compiled CSS"]
  (let [tmp (boot/tmp-dir!)
        output-path (or output-to "main.css")
        out-file (io/file tmp output-path)]
    (fn middleware [next-handler]
      (fn handler [fileset]
        (boot/empty-dir! tmp)
        (let [resolved-class (var-get (resolve root-class))
              style (os/get-style resolved-class)
              ]
          (io/make-parents out-file)
          (g/css {:pretty-print? pretty-print
                  :output-to (.getPath out-file)}
            style)
          (-> fileset
            (boot/add-resource tmp)
            boot/commit!
            (next-handler)))))))
