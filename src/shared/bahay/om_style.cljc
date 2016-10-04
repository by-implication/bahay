(ns bahay.om-style
  (:require
   #?(:clj [clojure.spec :as s]
      :cljs [cljs.spec :as s])
   [garden.spec]
   [om.next :as om]))

(defprotocol Style
  (style [this]))

(defn get-style
  "Gets the colocated style of either a component or a class
  Adds the class as meta"
  [x]
  (let [ctor (if (om/component? x)
                (om/react-type x) x)
        css #?(:clj ((:style (meta ctor)) ctor)
               :cljs (style ctor))]
    (with-meta
      css
      {:component ctor})))

#?(:clj
   (defn gen-css-namespace
     "Generate css namespace from om.next class or component.
  Replaces all dots with underscores. "
     [x]
     (let [class (if (om/component? x)
                   (om/react-type x) x)
           {:keys [component-name
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

(defn qualify-selector
  "Given a class and keyword, qualify the keyword
  with the appropriate class namespace."
  [component-class css-classname]
  (str (if (keyword? css-classname) ".")
    (gen-css-namespace component-class)
    "_" (name css-classname)))
