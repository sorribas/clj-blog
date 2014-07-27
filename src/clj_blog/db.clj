(ns clj-blog.db
  (:require [clojure.java.jdbc :as j]
            [honeysql.core :as sql]
            [honeysql.helpers :refer :all]))

(def db {:subprotocol "mysql"
         :subname "//127.0.0.1:3306/clj_blog"
         :user "root"
         :password ""})

(defn- now [] (new java.util.Date))

(defn- query [sql-map] 
  (j/query db (sql/format sql-map)))

(defn- query! [sql-map] 
  (j/execute! db (sql/format sql-map)))

(defn get-posts [] 
  (query (-> (select :*)
             (from :posts))))

(defn add-post [title content]
  (query! (-> (insert-into :posts)
            (columns :title :content :created)
            (values [[title content (now)]]))))

