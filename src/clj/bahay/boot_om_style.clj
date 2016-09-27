(ns bahay.boot-om-style
  {:boot/export-tasks true}
  (:require
   [bahay.kubo]
   [bahay.om-style :as os]
   [boot.core :as boot :refer [deftask]]
   [boot.pod :as pod]
   [boot.util :as util]
   [clojure.java.io :as io]))

(def processed (atom #{}))

(defn add-dep [env dep]
  (update-in env [:dependencies] (fnil conj []) dep))

(defn ns-tracker-pod []
  (->> '[[ns-tracker "0.3.0"] [org.clojure/tools.namespace "0.2.11"]]
    (assoc (boot/get-env) :dependencies)
    pod/make-pod))

(defonce garden-pods
  (pod/pod-pool
    (add-dep (boot/get-env) '[garden "1.3.0"])
    :init (fn [pod] (pod/require-in pod 'garden.core))))

(deftask om-style
  "Generate a stylesheet based on the
colocated styles of a root om.next class"
  [r root-class SYM sym "The root om.next class whose styles are to be generated"
   o output-to  PATH str "The output css file path relative to docroot."
   p pretty-print bool "Pretty print compiled CSS"]
  (let [tmp (boot/tmp-dir!)
        output-path (or output-to "main.css")
        out-file (io/file tmp output-path)
        src-paths (vec (boot/get-env :source-paths))
        ns-sym (symbol (namespace root-class))
        ns-pod (ns-tracker-pod)]
    (pod/with-eval-in ns-pod
      (require 'ns-tracker.core)
      (def cns (ns-tracker.core/ns-tracker ~src-paths)))
    (boot/with-pre-wrap fileset
      (boot/empty-dir! tmp)
      (let [initial (not (contains? @processed root-class))
            changed-ns (pod/with-eval-in ns-pod (cns))]
        (when (or initial (some #{ns-sym} changed-ns))
          (let [garden-pod (garden-pods :refresh)]
            (when initial (swap! processed conj root-class))
            (util/info "Compiling %s...\n" (.getName out-file))
            (io/make-parents out-file)
            (pod/with-eval-in garden-pod
              (require '~ns-sym)
              (garden.core/css
                {:pretty-print? ~pretty-print
                 :output-to ~(.getPath out-file)}
                (bahay.om-style/get-style ~root-class))))))
      (-> fileset
        (boot/add-resource tmp)
        boot/commit!))))
