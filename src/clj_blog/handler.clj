(ns clj-blog.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as res]
            [clostache.parser :as clostache]
            [clj-blog.db :as db]))

(defn- posts-markdown [] [])

(defn- render 
  ([tmplate] (render tmplate nil))
  ([tmplate data] 
  (clostache/render-resource 
    "templates/index.mustache" data {:template (slurp (str "src/" tmplate))})))

(defroutes app-routes
  (GET "/" [] 
       (render "templates/list.mustache" {:posts (db/get-posts)}))
  (GET "/add" []
       (render "templates/add.mustache"))
  (POST "/add" [title content]
        (db/add-post title content)
        (res/redirect "/"))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
