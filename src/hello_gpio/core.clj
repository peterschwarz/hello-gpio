(ns hello-gpio.core
  (:require [clojure.core.async :as a :refer [go <! >! timeout chan]])
  (:import [com.pi4j.io.gpio GpioController GpioFactory GpioPin GpioPinDigitalOutput PinState RaspiPin]))

(defn blink
  []
  (let [gpio (GpioFactory/getInstance)
        pin (.provisionDigitalOutputPin gpio RaspiPin/GPIO_01 "MyLed" PinState/HIGH)
        ch (chan 1)]
    (a/>!! ch :start)
    (go (loop []
         (if-let [control (<! ch)]
           (do
             (.toggle pin)
             (<! (timeout 1000))
             (>! ch control)
             (recur))
           (do
             (.low pin)
             (.unprovisionPin gpio (into-array GpioPin [pin]))
             (.shutdown gpio)))))
    ch))

(defn stop! [gpio-control-chan]
  (a/close! gpio-control-chan))
