(ns hello-gpio.core
  (:require [clojure.core.async :as a
             :refer [go <! >! timeout chan]]
            [gpio.core :refer :all]))

(defn blink
  [& {:keys [pin period]
      :or {pin 17 period 1000}}]
  (let [port (open-port pin :digital-result-format :boolean)
        ch (chan 1)]
    (a/>!! ch :start)
    (go (loop []
          (if-let [control (<! ch)]
            (do
              (write-value! port (not @port))
              (<! (timeout period))
              (>! ch control)
              (recur))
            (close! port))))
    ch))

(defn stop! [gpio-control-chan]
  (a/close! gpio-control-chan))
