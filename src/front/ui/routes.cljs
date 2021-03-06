; Copyright (c) 2015, Benjamin Van Ryseghem. All rights reserved.
; The use and distribution terms for this software are covered by the
; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
; which can be found in the file epl-v10.html at the root of this distribution.
; By using this software in any fashion, you are agreeing to be bound by
; the terms of this license.
; You must not remove this notice, or any other, from this software.

(ns ui.routes
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
            [secretary.core :as secretary :refer-macros [defroute]]
            [ui.core :as core])
  (:import goog.History))

(defroute "/project/:id" [id]
  (core/render-core id))

(defroute "/inbox" []
  (core/render-core "Inbox"))

(defroute "/today" []
  (core/render-core "today"))

(defroute "/next" []
  (core/render-core "next"))

(defroute "/scheduled" []
  (core/render-core "scheduled"))

(defroute "/scheduled" []
  (core/render-core "scheduled"))

(defroute "/someday" []
  (core/render-core "someday"))

(defroute "/projects" []
  (core/render-core "projects"))

(defn init
  []
  (defonce t (do
               (secretary/set-config! :prefix "#")
               (let [h (History.)]
                 (goog.events/listen h EventType/NAVIGATE #(secretary/dispatch! (.-token %)))
                 (doto h (.setEnabled true)))
               (secretary/dispatch! "/inbox"))))
