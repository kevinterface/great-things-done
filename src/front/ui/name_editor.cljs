(ns ui.name-editor
  (:use [jayq.core :only [$]])
  (:require [reagent.core :as reagent :refer [atom]]))


(defn- plain-name-editor
  [entity]
  [:div.entity-name
   {:id (str "entity-name-" (:id entity))
    :placeholder "Add a name"
    :class (if (empty? (:name entity))
             "empty"
             "")}
   (:name entity)])

(defn- make-editable
  [entity callback]
  (.editable ($ (str "#entity-name-" (:id entity)))
             "click"
             (clj->js {:callback (fn [event]
                                   (if (empty? (.-value event))
                                     (.html (.-target event)
                                            (.-old_value event))
                                     (when-not (= (.-old_value event)
                                                  (.-value event))
                                       (callback entity
                                                 (.-value event)))))})))

(defn- build
  [entity callback]
  (with-meta plain-name-editor
    {:component-did-mount #(make-editable entity
                                          callback)}))

(defn render
  [entity callback]
  [(build entity
          callback) entity])
