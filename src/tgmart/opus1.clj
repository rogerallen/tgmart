(ns tgmart.opus1
  (:use [tgmart core]
        [clisk live])
  (:import [clisk Util]
           [java.io File]
           [javax.imageio ImageIO])
  (:require [tweegeemee.core :as tgm]))

;; working towards http://zine.electricobjects.com/artist-program
;;
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


;; ======================================================================
;; Animation creation code
(comment

  ;; ======================================================================
  ;; opus1a - pastel dream
  ;; SAVE THIS -- really nice loop.
  (render-animation
   [i 0 1 1]
   ;;[i 0 3390 1] ;; 3390/30Hz=113 seconds
   (scale-for-vert-hd
    (vpow
     (length plasma)
     (sigmoid
      (gradient
       (green-from-hsl
        (green-from-hsl
         (rgb-from-hsl
          (v+ (offset [(circle-cos i 0.15 3390)
                       0.0
                       (circle-sin i 0.15 3390)]
                      pos)
              (blue-from-hsl [0.6035 -2.8447 0.9631])))))))))
   {:filename "art/opus1a/a%04d.png"
    ;;:width    (* (/ 1080 10) 3)
    ;;:height   (* (/ 1920 10) 3)
    :width 1080 :height 1920
    })

  ;; ======================================================================
  ;; fun noise opus1b
  ;; this is pretty good but not my favorite -- took 5.5 hours for 480 frames on MacBook Pro
  (render-animation
   [i 0 480 1]
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
   {:filename "art/opus1b/a%04d.png"
    :width    (* (/ 1080 10) 3)
    :height   (* (/ 1920 10) 3)
    })

  ;; ======================================================================
  ;; opus1c - orange fun noise
  ;; 30s/frame.
  ;; [ ] Go with 1000 frames over night (got 870)
  ;; I like the circular movement, but the noise has to stay still or change MUCH less.
  ;; maybe try no cross3 offset?
  (render-animation
   [i 0 1000 1]
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
   {:filename "art/opus1c/a%04d.png"
    :width    (* (/ 1080 10) 3)
    :height   (* (/ 1920 10) 3)
    })

  ;; ======================================================================
  ;; opus1d based on "151130_023115_N.clj"
  ;; in process...
  (render-animation
   [i 0 300 1]
    (scale-for-vert-hd0
     (adjust-hsl
      (rotate
       (* 2 Math/PI (/ i -300))
      (adjust-hue
       (x (gradient (x (gradient (gradient spots)))))
       (max-component (hsl-from-rgb [0.795 2.244 -0.63])))
      )
      (rotate
       (* 2 Math/PI (/ i 300))
       (gradient (gradient spots))
       )
      )
     )
   {:filename "art/opus1d/a%04d.png"
    :width    (* (/ 1080 10) 3)
    :height   (* (/ 1920 10) 3)
    })

)
