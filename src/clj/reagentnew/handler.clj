(ns reagentnew.handler
  (:require [compojure
             [core :refer [defroutes GET]]
             [route :refer [not-found resources]]]
            [config.core :refer [env]]
            [hiccup.page :refer [html5 include-css include-js]]
            [reagentnew.middleware :refer [wrap-middleware]]
            [ring.middleware.format :refer [wrap-restful-format]]))

(def mount-target
  [:div#app
      [:h3 "ClojureScript has not been compiled!"]
      [:p "please run "
       [:b "lein figwheel"]
       " in order to start the compiler"]])

(defn head []
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]
   (include-css (if (env :dev) "/css/site.css" "/css/site.min.css"))])

(def loading-page
  (html5
    (head)
    [:body {:class "body-container"}
     mount-target
     (include-js "/js/app.js")]))


(defroutes routes
  (GET "/" [] loading-page)
  (GET "/about" [] loading-page)

  (GET "/data" []
    ;; Put a Clojure data structure as the body of your API response. If the
    ;; client requests it as JSON (Accept: application/json) then
    ;; ring-middleware-format will render it as JSON.
    {:body {:vals [1 2 3 4]}})

  (resources "/")
  (not-found "Not Found"))

(def app (-> #'routes
             wrap-middleware

             ;; Make sure to add this middleware to render the JSON
             ;; It also works for parsing json coming from the client.
             wrap-restful-format))
