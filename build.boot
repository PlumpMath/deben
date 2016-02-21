(def project 'deben)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"resources" "src"}
          :source-paths   #{"test"}
          :dependencies   '[[org.clojure/clojure "1.8.0"]
                            [adzerk/boot-test "RELEASE" :scope "test"]
                            [factual/clj-leveldb "0.1.1"]
                            [byte-streams "0.1.13"]])

(task-options!
 pom {:project     project
      :version     version
      :description "Persist EDN on top of LevelDB"
      :url         "http://example/FIXME"
      :scm         {:url "https://github.com/yourname/deben"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}})

(deftask build
  "Build and install the project locally."
  []
  (comp (pom) (jar) (install)))

(require '[adzerk.boot-test :refer [test]])
