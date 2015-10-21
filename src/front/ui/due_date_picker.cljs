(ns ui.due-date-picker
  (:use [jayq.core :only [$]])
  (:require [gtd.settings :as settings]
            [reagent.core :as reagent :refer [atom]]))


(defn- plain-due-date-picker
  [project]
  (let [text (.format (.moment js/window
                               (:due-date project))
                      (settings/date-format))]
    [:div
     [:input.form-control
      {:id "project-due-date-picker"
       :placeholder "Add a due date"
       :value text}]
     [:div#clear-due-date
      [:i.fa.fa-times]]]))

(defn- plain-empty-due-date-picker
  [project]
  [:div
   [:input
    {:id "project-due-date-picker"
     :placeholder "Add a due date"
     :class "empty"}]])

(defn build
  [project callback]
  (with-meta (if (:due-date project)
               plain-due-date-picker
               plain-empty-due-date-picker)
    {:component-did-mount (fn []
                            (.click ($ :#clear-due-date)
                                    (fn []
                                      (.datepicker ($ :#project-due-date-picker)
                                                   "clearDates")))
                            (.on (.datepicker ($ :#project-due-date-picker)
                                              (clj->js {:todayBtn  "linked",
                                                        :autoclose true
                                                        :format {:toDisplay (fn [d _ _]
                                                                              (.format (.moment js/window
                                                                                                (js/Date. d))
                                                                                       (settings/date-format)))
                                                                 :toValue (fn [d, f, t]
                                                                            (js/Date. (.format (.moment js/window
                                                                                                        d
                                                                                                        (settings/date-format)))))}}))
                                 "changeDate"
                                 (fn [e]
                                   (callback project
                                             (.-date e)))))}))

(defn render
  [project callback]
  [:div.date-picker
   [(build project
          callback) project]])
