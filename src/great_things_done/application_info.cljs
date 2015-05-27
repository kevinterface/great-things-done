(ns great-things-done.application-info
  (:use-macros [great-things-done.macro :only (for-os)]))

(def ^:private osx-mail-applescript
  "tell application \"Mail\"
    set _sel to get selection
    set _links to {}
    set the selected_message to item 1 of the _sel
    set _messageURL to \"message://%3c\" & selected_message's message id & \"%3e\"
    set AppleScript's text item delimiters to return
  end tell

  return _messageURL")

(defmulti retrieve-current-app-data-osx
  #(:id %1))

(defmethod retrieve-current-app-data-osx "com.apple.mail" [info callback]
  (js/console.log "Been there")
  (let [applescript (js/require "applescript")]
    (.execString applescript
                 osx-mail-applescript
                 (fn [err res]
                   (when err
                     (throw (js/Error. "Error when retrieving data from Mail.app")))
                   (callback res)))))

(defn- get-current-app-info-osx
  "Return info about the current frontmost application on OSX"
  []
  (js/console.log "Been there2")
  (let [remote  (js/require "remote")
        nodobjc (js/require "nodobjc")]
    (.framework nodobjc "AppKit")
    (let [workspace (.NSWorkspace nodobjc "sharedWorkspace")
          app       (workspace "frontmostApplication")
          app-name  (str (app "localizedName"))
          app-id    (str (app "bundleIdentifier"))]
      {:name app-name
       :id   app-id})))

(defn get-current-app-info
  []
  (for-os
   "Mac OS X" (get-current-app-info-osx)))

(defn ^:export js-get-current-app-info
  []
  (clj->js (get-current-app-info)))

(defn get-current-app-data
  [fun]
  (let [info (get-current-app-info)]
    (for-os
     "Mac OS X" (retrieve-current-app-data-osx info fun))))

(defn ^:export js-get-current-app-data
  [fun]
  (clj->js (get-current-app-data fun)))