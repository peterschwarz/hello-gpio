# hello-gpio

A simple example which blinks an LED attached to GPIO pin 1 on a Raspberry PI 

## Usage

Run `lein repl` as root on your Raspberry PI ([Settng up leiningen on your PI](http://morrifeldman.blogspot.com/2013/10/clojure-on-raspberry-pi-emacs-and-nfs.html)):

    user=> (require '[hello-gpio.core :refer :all] :reload)
    nil
    user=> (def blinker (blink))
    #'user/blinker

And when you're bored with blinking

    user=> (stop! blinker)


## License

Copyright © 2014 Peter Schwarz

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
