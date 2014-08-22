(ns timeboxing.rocks)

(enable-console-print!)

;; Laptop monitor is 1440 x 900. Hardcoded those as the defaults for my own
;; benefit, but it will resize automatically anyway.
(def left 720)
(def top  400)
(def radius 350)
(def line-width 30)
(def canvas (.getElementById js/document "clock"))
(def ctx (.getContext canvas "2d"))

;; Pop up a 'do you want to leave' dialog while the timer is running to prevent
;; accidental closing.
;; Don't really care that this isn't the best way to do it, this isn't a
;; modular web app.
(set! (.-onbeforeunload js/window)
      (fn [_] "Currently timing. Are you sure?"))

(defn handle-resize
  []
  (let [w (.-innerWidth js/window)
        h (.-innerHeight js/window)
        cw (- w 20)
        ch (- h 20)]
    (set! (.-width canvas)  cw)
    (set! (.-height canvas) ch)
    (set! (.-width (.-style canvas))  (str cw "px"))
    (set! (.-height (.-style canvas)) (str ch "px"))
    (set! left   (/ w 2))
    (set! top    (/ h 2))
    (set! radius (- (min left top) 50))))

(set! (.-onresize js/window)
      handle-resize)

(def ticker (atom nil))

(def countdown-milliseconds
  (* (js/parseFloat (js/prompt "How many minutes?" "30"))
     60
     1000))

(def when-to-end (+ (.now js/Date) countdown-milliseconds))

(defn msecs-left->arc-pos
  "Convert number of milliseconds remaining to the relevant value for the
  canvas.context.arc function."
  [msecs]
  (let [mins #(* % 60 1000)
        multiple (if (>= msecs (mins 15)) ;; 15mins / 3pm pos is 0 for .arc
                   (/ (- msecs (mins 15)) (mins 30))
                   (+ (/ msecs (mins 30)) 1.5))]
    (* multiple Math/PI)))

(defn clear
  "Clear the canvas."
  []
  (.clearRect ctx 0 0 (.-width canvas) (.-height canvas)))

(defn redraw
  "For the given number of milliseconds left, redraw the outline."
  [msecs-left]
  (clear)
  (.beginPath ctx)
  (.arc ctx left top radius (* 1.5 Math/PI) (msecs-left->arc-pos msecs-left) false)

  (set! (.-lineWidth ctx) line-width)
  (.stroke ctx))

(defn timeout
  "Clear the ticker and unload handler then ring the bell."
  []
  (clear)
  (swap! ticker (fn [id] (js/clearInterval id) nil))
  (set! (.-onbeforeunload js/window) (fn [_] nil))
  (doto (.getElementById js/document "bell")
    (.load)
    (.play)))

(defn tick
  "Calculate time remaining and update the display. Recalculation is done not
  to avoid JS's setInterval problems (which are not a problem at this scale!),
  but rather to cope with a laptop being closed / going to sleep"
  []
  (let [remaining (Math/round (- when-to-end (.now js/Date)))]
    (if (<= remaining 0)
      (timeout)
      (redraw remaining))))

(handle-resize)
(reset! ticker (js/setInterval tick 1000))

