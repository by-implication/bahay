(ns bahay.style.layout
  (:require
   [bahay.style.theme :as t]
   [garden.units :refer [px percent s]]
   [garden.color :refer [rgba] :as color]
   [om.dom :as dom]
   ))

(def spacer (partial dom/div #js {:className "grow"} nil))

(def layout
  (list
    [:.stacked {:display 'flex
                :flex-shrink 0}
     [:&.v {:flex-direction 'column}]
     [:&.h {:flex-direction 'row}]
     [:&.stretched {:align-items 'stretch}]
     [:&.centered {:align-items 'center}]
     [:&.center-justified {:justify-content 'center}]
     [:&.centerized {:align-items 'center
                     :justify-content 'center}]
     [:&.guttered
      [:&.v>*+* {:margin-top (px 12)}]
      [:&.h>*+* {:margin-left (px 12)}]
      [:&.lightly
       [:&.v>*+* {:margin-top (px 8)}]
       [:&.h>*+* {:margin-left (px 8)}]]
      [:&.generously
       [:&.v>*+* {:margin-top (px 24)}]
       [:&.h>*+* {:margin-left (px 24)}]]]]
    [:.grow {:flex 1}]
    [:.padded {:padding [[(px 12) (px 16)]]
               :box-sizing 'border-box}]
    [:.relative {:position 'relative}]
    [:.fixed {:position 'fixed}]
    [:.container {:max-width (px 940)
                  :width (percent 100)
                  :margin-left 'auto
                  :margin-right 'auto}]
    [:.fill {:position 'absolute
             :width (percent 100)
             :height (percent 100)}]))
