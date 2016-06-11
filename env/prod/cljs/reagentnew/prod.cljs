(ns reagentnew.prod
  (:require [reagentnew.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
