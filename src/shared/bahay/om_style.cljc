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

(defn qualify
  "Given a class and keyword, qualify the keyword
  with the appropriate class namespace."
  [component-class css-classname]
  (str (if (keyword? css-classname) ".")
    (gen-css-namespace component-class)
    "_" (name css-classname)))

(defn qualify-css-data
  "recursively qualify the classnames of a garden data structure.
  Base case: [classname attr-map]
  Recursive case: [classname attr-map & children]
  children may include vectors as above, or lists of vectors."
  [css-data]
  )
