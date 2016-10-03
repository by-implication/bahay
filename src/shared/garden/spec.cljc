(ns garden.spec
  (:require
   #?(:clj [clojure.spec :as s]
      :cljs [cljs.spec :as s])))

(s/def :garden/declaration map?)

(s/def :garden/rule
  (s/and
    vector?
    (s/cat
      :selector (s/+ (comp not coll?))
      :declaration (s/? :garden/declaration)
      :children (s/* (s/or
                       :list :garden/rule-list
                       :rule :garden/rule)))))

(s/def :garden/rule-list
  (s/and list?
    (s/coll-of
      (s/or
        :list :garden/rule-list
        :rule :garden/rule))))
