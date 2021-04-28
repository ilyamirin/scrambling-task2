(ns scramble-figwheel.core
    (:require [react]
              [react-dom]
              [sablono.core :as sab :include-macros true]))

(enable-console-print!)

(println "This text is printed from src/scramble-figwheel/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(defn scramble
  "Returns true if a portion of str1 characters can be rearranged to match str2, otherwise returns false."
  [str1 str2]
  (def freq1 (frequencies (str str1)))
  (def freq2 (frequencies (str str2)))
  (if-not (and (not (empty? freq2)) (clojure.set/subset? (set str2) (set (keys freq1))))
    false
    (nil? (some false? (map #(>= (get freq1 %) (get freq2 %)) (keys freq2))))))

(defn is-scrambled-button-on-click []
  (js/alert (if (scramble (.-value (.getElementById js/document "first-string"))
                          (.-value (.getElementById js/document "second-string")))
              "YES!"
              "NO!")
            ))

(defn hello-world [state]
  (sab/html [:div
             [:h1 "Check the scramb-ability of your strings!"]
             [:div
              [:label "First string " [:input {:type "text" :class "center" :id "first-string"}]]
              [:label "Second string " [:input {:type "text" :class "center" :id "second-string"}]]
              [:button {:name "is-scrambled-button"
                        :class "center"
                        :onClick #(is-scrambled-button-on-click )}
               "Can second string be scrambled?"]]]
            ))

(react-dom/render
 (hello-world app-state)
 (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
