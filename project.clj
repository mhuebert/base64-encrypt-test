(defproject base64-encode-test "0.0.0-SNAPSHOT"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.7.1"
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [org.clojure/clojurescript "0.0-SNAPSHOT"]]

  :plugins [[lein-cljsbuild "1.1.7"]]

  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    "target"]

  :source-paths ["src"]

  :cljsbuild {:builds [{:id           "dev"
                        :source-paths ["src"]
                        :compiler     {:main           "app.core"
                                       :output-to      "resources/public/js/compiled/app.js"
                                       :output-dir     "resources/public/js/compiled/out"
                                       :asset-path     "js/compiled/out"
                                       :language-in    :ecmascript5
                                       :source-map     true
                                       :optimizations  :none
                                       :parallel-build true}}]})
