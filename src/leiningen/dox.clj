(ns leiningen.dox
  (:require [me.raynes.conch :refer [programs]]
            [me.raynes.fs :as fs]
            [clojure.string :as s]))

(programs git mv rm)

(defn wildcard [s]
  (vec (fs/glob (str s "/*"))))

(defn mv-contents [dir out-dir]
  (apply mv (conj (wildcard dir) out-dir)))

(defn current-branch []
  (s/trim-newline (git "rev-parse" "--abbrev-ref" "HEAD")))

(defn ensure-gh-pages []
  (if (zero? @(:exit-code
               (git "show-ref" "--verify" "--quiet" "refs/heads/gh-pages"
                    {:throw false :verbose true})))
    (git "checkout" "gh-pages")
    (do (println "*** gh-pages branch not found -- creating it ***")
        (git "checkout" "--orphan" "gh-pages"))))

(defn dox
  "Update documentation for the project."
  [project & args]
  (let [dir (fs/temp-dir "doxx")
        old-dir (or (get-in project [:dox :in])
                    (first args)
                    "doc/")
        branch (current-branch)]
    (mv-contents old-dir dir)
    (rm "-r" old-dir)
    (ensure-gh-pages)
    (git "rm" "-rf" ".")
    (mv-contents dir ".")
    (git "add" "-Av" ".")
    (git "commit" "-m" "Updated docs")
    (println "*** gh-pages branch updated ***")
    (git "checkout" branch)
    (println "Run this to complete:")
    (println "git push origin gh-pages:gh-pages")))
