(ns bahay.util
  (:refer-clojure :exclude [use])
  (:require
   [om.dom :as dom]))

(def use
  #?(:cljs (partial js/React.createElement "use")
     :clj dom/use))


