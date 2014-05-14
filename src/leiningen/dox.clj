(ns leiningen.dox
  (:require [me.raynes.conch :refer [programs]]
            [me.raynes.fs :as fs]))

(programs git mv rm ls)

(defn wildcard [s]
  (vec (fs/glob (str s "/*"))))

(defn mv-contents [dir out-dir]
  (apply mv (conj (wildcard dir) out-dir)))

(defn dox
  "Update documentation for the project."
  [project & args]
  (let [dir (fs/temp-dir "doxx")
        old-dir (or (get-in project [:dox :in])
                    (first args))]
    (try
      (mv-contents old-dir dir)
      (rm "-r" old-dir)
      (git "checkout" "gh-pages")
      (git "rm" "-rf")
      (mv-contents dir ".")
      (git "commit" "-m" "Updated docs")
      (println "*** gh-pages branch updated ***")
      (git "checkout" "-")
      (println "Run this to complete:")
      (println "git push origin gh-pages:gh-pages")
      (catch clojure.lang.ExceptionInfo e
        (prn (ex-data e))))))
