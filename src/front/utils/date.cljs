; Copyright (c) 2015, Benjamin Van Ryseghem. All rights reserved.
; The use and distribution terms for this software are covered by the
; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
; which can be found in the file epl-v10.html at the root of this distribution.
; By using this software in any fashion, you are agreeing to be bound by
; the terms of this license.
; You must not remove this notice, or any other, from this software.

(ns utils.date)

(defn today
  []
  (js/Date.))

(defn now
  []
  (js/Date.))

(defn now-as-milliseconds
  "Return the current time"
  []
  (.now js/Date))

(defn index-of-today
  []
  (let [today (today)]
    (.dayOfYear (.-util js/window)
                today)))
