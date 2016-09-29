(ns bahay.om-style
  (:require
   [om.next :as om]))

(defprotocol Style
  (style [this]))

(defn get-style
  "Gets the colocated style of either a component or a class"
  [x]
  #?(:clj ((:style (meta x)) x)
     :cljs (style x)))

#?(:clj
   (defn gen-css-namespace
     "Generate css namespace from om.next class.
  Replaces all dots with underscores."
     [class]
     (let [{:keys [component-name
                   component-ns]} (meta class)]
       (str
         (clojure.string/replace
           component-ns "." "_")
         "_"
         component-name))))

#?(:cljs
   (defn gen-css-namespace
     "Generate css namespace from om.next class.
  Replaces all dots with underscores."
     [class]
     (clojure.string/replace
       (.-name class) "$" "_")))

(defn localize
  "Given a class and keyword, namespace the keyword
  with the appropriate class namespace."
  [component-class css-classname]
  (str (if (keyword? css-classname) ".")
    (gen-css-namespace component-class)
    "_" (name css-classname)))
