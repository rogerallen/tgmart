(ns tgmart.opus1
  (:use [clisk live])
  (:import [clisk Util]
           [java.io File]
           [javax.imageio ImageIO])
  (:require [tweegeemee.core :as tgm]))

;; working towards http://zine.electricobjects.com/artist-program
;; Still Images
;;
;; 1080px x 1920 pixels
;; 9:16 aspect ratio
;; PNGs or JPGs
;; 10MB maximum
;;
;; Video
;;
;; 1080px x 1920px
;; 9:16 aspect ratio (portrait)
;; 100MB max
;; H.264 codec
;; MP4 container
;; Frame rates of 24, 29.97, or 30 will look smoothest on the EO1
;; No audio stream (smaller files and prevents performance errors)
;; Very high bitrates (20K +) may cause video to stutter at loop
;; point. Experiment with lower bit rates if you notice your loops
;; have unsightly gaps.

;; some snippets based on mikea's code.  modified from telegenic so I
;; could use ffmpeg.
(def DEFAULT-OPTIONS
  {:filename "art/a%03d.png"
   :width (* (/ 1080 10) 3)
   :height (* (/ 1920 10) 3)})

(defmacro render-animation
  "Macro to render a sequence of frames, looping with a symbolic frame counter."
  ([[key frame-count] src]
    `(render-animation [~key ~frame-count] ~src))
  ([[key frame-count] src options]
    (when-not (symbol? key) (error "render-animation needs a symbol binding for the frame number"))
    (when-not (integer? frame-count) (error "render-animation needs an integer number of frames"))
    `(let [mopts# (merge DEFAULT-OPTIONS ~options)
           opts#  (mapcat identity mopts#)]
       (doseq [~key (range ~frame-count)]    ;; loop over all frames, lazily (doseq for mem?)
         (let [im# (apply image ~src opts#)  ;; create the frame
               fn# (format (:filename mopts#) ~key)]
           (apply show im# opts#)            ;; show the latest frame
           (ImageIO/write im# "png" (File. fn#))))))) ;; write the frame

;; put this in a file in the image directory to make a movie
;; > cat a.run
;; #!/bin/sh
;; printf "file '%s'\n" ./a*.png > a.list
;; ffmpeg \
;;   -f concat -i a.list \
;;   -r 30 \
;;   -c:v libx264 -preset slow -crf 22 \
;;   a.mp4

(defn scale-for-vert-hd0
  "center at 0,0.  x ranges [-9/16,9/16], y [-1,1]"
  [fn]
  (->> fn
       (scale  [(/ 16 9 2) (/ 16 9 2)])
       (offset [-0.5 (/ -16 9 2)])))

(defn scale-for-vert-hd
  "center at 9/8,0.5. x ranges [0,9/16], y [0,1]"
  [fn]
  (->> fn
       (scale  [(/ 16 9) (/ 16 9)])))

;; ======================================================================
;; Animation creation code
(comment

  ;; ======================================================================
  ;; pastel-dream
  ;; SAVE THIS -- really nice loop.
  (render-animation
   [i 3390] ;; 113 seconds
   (offset
    [0.0 0.0 0.0]     ;; use an offset in x-axis to scroll the image over time
    (scale-for-vert-hd
     (vpow
      (length plasma)
      (sigmoid
       (gradient
        (green-from-hsl
         (green-from-hsl
          (rgb-from-hsl
           (v+ (offset [(* (Math/cos (* 2 Math/PI (/ i 3390))) 0.15)
                        0.0
                        (* (Math/sin (* 2 Math/PI (/ i 3390))) 0.15)]
                       pos)
               (blue-from-hsl [0.6035 -2.8447 0.9631]))))))))))
   {:filename "art/pastel_dream/a%04d.png"
    :width    (* (/ 1080 10) 3)
    :height   (* (/ 1920 10) 3)})

  ;; ======================================================================
  ;; fun noise fn1
  ;; this is pretty good but not my favorite -- took 5.5 hours for 480 frames on MacBook Pro
  (render-animation
   [i 480]
    (scale-for-vert-hd
     ;; orangey (cross3 (adjust-hue (x (gradient (gradient (x (gradient (gradient spots)))))) (max-component (lightness-from-rgb (x (gradient (gradient (x (gradient spots)))))))) (gradient (min-component spots)))
     (offset
      [(* (Math/sin (* 2 Math/PI (/ 25 120))) 0.33)
       (* (Math/cos (* 2 Math/PI (/ 25 120))) 0.11)
       0.0]
      (adjust-hsl
       (adjust-hue
        (adjust-hsl
         (adjust-hsl (plasma
                      (offset
                       [0.0;;(* (Math/sin (* 2 Math/PI (/ i (/ 480 5)))) 0.07)
                        0.0;;(* (Math/cos (* 2 Math/PI (/ i (/ 480 7)))) 0.05)
                        (* (Math/sin (* 2 Math/PI (/ i 480))) 0.2)]
                       pos)
                      ) spots)
         (lightness-from-rgb [0.795 2.244 -0.63]))
        (max-component
         (lightness-from-rgb [0.795 2.244 -0.63])))
       (vplasma
        ;;(offset
        ;; [0.0
        ;;  0.0;(* (Math/cos (* 2 Math/PI (/ i 480))) 0.15)
        ;;  (* (Math/sin (* 2 Math/PI (/ i 480))) 0.5)]
        ;; pos)
        )
       )
      )
     )
   {:filename "art/fn1/a%04d.png"
    :width    (* (/ 1080 10) 3)
    :height   (* (/ 1920 10) 3)
    })

  ;; ======================================================================
  ;; fun noise fn2 -- 30s/frame. [ ] Go with 1000 frames over night
  (render-animation
   [i 1000]
    (scale-for-vert-hd
     ;; orangey
     (offset
      [0.0 ;(* (Math/sin (* 2 Math/PI (/ 25 120))) 0.33)
       0.0 ;(* (Math/cos (* 2 Math/PI (/ 25 120))) 0.11)
       0.0]
      (cross3
       (offset
        [0.0 ;(* (Math/sin (* 2 Math/PI (/ 25 120))) 0.33)
         0.0 ;(* (Math/cos (* 2 Math/PI (/ 25 120))) 0.11)
         (* (Math/sin (* 2 Math/PI (/ i 1000))) 0.04)]
        (adjust-hue
         (x
          (gradient
           (gradient
            (x (gradient (gradient spots))))))
         (max-component
         (lightness-from-rgb
          (x
           (gradient
            (gradient
             (x (gradient spots))))))))
        )
       (offset
        [(* (Math/sin (* 2 Math/PI (/ i 1000))) 0.04)
         (* (Math/cos (* 2 Math/PI (/ i 1000))) 0.02)
         0.0]
        (gradient (min-component spots))
        )
       )
      )
     )
   {:filename "art/fn2/a%04d.png"
    :width    (* (/ 1080 10) 3)
    :height   (* (/ 1920 10) 3)
    })

  ;; ======================================================================
  ;; fn3 :name "151130_023115_N.clj"
  ;; in process...
  (render-animation
   [i 10]
    (scale-for-vert-hd
     (adjust-hsl
      (offset
        [(* (Math/sin (* 2 Math/PI (/ i 10))) 0.1)
         (* (Math/cos (* 2 Math/PI (/ i 10))) 0.1)
         (* (Math/sin (* 2 Math/PI (/ i 10))) 0.1)]
        (adjust-hue
         (x (gradient (x (gradient (gradient spots)))))
         (max-component (hsl-from-rgb [0.795 2.244 -0.63])))
        )
      (gradient
       (offset
        [(* (Math/sin (* 2 Math/PI (/ i 10))) 0.04)
         (* (Math/cos (* 2 Math/PI (/ i 10))) 0.02)
         0.0]
        (gradient spots)
        )
       )
      )
     )
   {:filename "art/fn3/a%04d.png"
    :width    (* (/ 1080 10) 3)
    :height   (* (/ 1920 10) 3)
    })

)
