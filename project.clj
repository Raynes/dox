(defproject dox "0.1.0"
  :description "A leiningen plugin to handle regenerating documentation."
  :url "https://github.com/Raynes/refheap/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in-leiningen true
  :dox {:in "doc/"}
  :dependencies [[me.raynes/conch "0.7.0"]
                 [me.raynes/fs "1.4.5"]])
