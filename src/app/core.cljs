(ns app.core
  (:require [cljs.js :as cljsjs]
            [clojure.string :as string]
            [app.examples :as examples]
            [goog.crypt.base64 :as base64]))

(enable-console-print!)

(defn unicode-escape [s]
  (-> s
      (js/encodeURIComponent s)
      (string/replace #"%([0-9A-F]{2})" (fn [[c match]]
                                          (.fromCharCode js/String (str "0x" match))))))

(defn unicode-unescape [s]
  (-> (js/Array.prototype.map.call s (fn [c]
                                       (as-> c c
                                             (.charCodeAt c 0)
                                             (.toString c 16)
                                             (str "00" c)
                                             (.slice c -2)
                                             (str "%" c))))
      (.join "")
      (js/decodeURIComponent)))

(def b64-encode-unicode (comp base64/encodeString unicode-escape))

(def b64-decode-unicode (comp unicode-unescape base64/decodeString))


(doseq [char (.from js/Array (str examples/emoji examples/keyboard-symbols))]
  (assert (= char (b64-decode-unicode (b64-encode-unicode char)))))


(cljsjs/compile-str (cljsjs/empty-state) (str "(println ["
                                              \"
                                              examples/emoji
                                              examples/keyboard-symbols
                                              \"
                                              "])") "cljsjs_test" {:source-map true}
                    (fn [{:keys [value error]}]
                      (if error
                        (println (str "Error: " error))
                        (do
                          (println (str "Compiled JavaScript: " value))
                          (println "Result:")
                          (println (js/eval value))))))