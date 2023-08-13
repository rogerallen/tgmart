;; create background screen images

(ns tgmart.screens
  (:use [tgmart core]
        [clisk live])
  (:import [java.io File]
           [javax.imageio ImageIO]))

(def screen-codes
  [;; 0 - https://tweegeemee.com/i/230615_013316_D/code
   {:name "230615_013316_D"
    :seed 230520
    :code '(adjust-hsl [2.6874568911194254 -0.622670298103297] (scale 0.26433639079750065 (vfrac (vcos (adjust-hsl (radius (alpha (vsqrt (dot [-1.1139 0.3231] [0.7539 2.2466])))) (v+ (length (vcos pos)) (v+ [2.0296 -2.661] pos)))))))}
   ;; 1 - https://tweegeemee.com/i/171207_063205_b/code
   {:name "171207_063205_b"
    :seed 171106 ; from gist
    :code `(min-component (vfrac (gradient spots)))}])

(def screen-resolutions
  [{:W 3200 :H 2000} ;; 0 i9
   {:W 3840 :H 2160} ;; 1 4k
   {:W 2560 :H 1600} ;; 2 1440ish
   {:W 2560 :H 1440} ;; 3 1440p
   {:W 1920 :H 1080} ;; 4 1080p
   {:W 320 :H 200}   ;; 5 test
   ])

(let [n  1 ;; image index
      rn 4 ;; resolution index
      seed (:seed (nth screen-codes n))
      name (:name (nth screen-codes n))
      code-n (:code (nth screen-codes n))
      code `(->>
             ~code-n
             (clisk.live/scale [1.0 1.0])
             (clisk.live/offset [0.0 0.0]))
      _ (clisk.live/seed-perlin-noise! seed)
      _ (clisk.live/seed-simplex-noise! seed)
      W (:W (nth screen-resolutions rn))
      H (:H (nth screen-resolutions rn))
      img (clisk.live/image (eval code) :width W :height H)]
  (ImageIO/write
   img "png"
   (File. (format "art/screens/%s_%dx%d.png" name W H))))
