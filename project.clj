(defproject timeboxing-rocks "0.1.0-SNAPSHOT"
  :description "Simplest Timer Ever"
  :url "http://timeboxing.rocks"

  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2311"]]

  :plugins [[lein-cljsbuild "1.0.4-SNAPSHOT"]]

  :source-paths ["src"]

  :cljsbuild {
    :builds [{:id "timer"
              :source-paths ["src"]
              :compiler {
                :output-to "timer.js"
                :output-dir "out"
                :optimizations :advanced}}]})
