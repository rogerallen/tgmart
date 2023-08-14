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
    :code `(min-component (vfrac (gradient spots)))}
   ;; 2 - https://tweegeemee.com/i/150821_143150_M/code 
   {:name "150821_143150_M"
    :seed 150821 ;; actually this is "pre-seed"
    :code `(vpow (length plasma) (sigmoid (gradient (green-from-hsl (green-from-hsl (rgb-from-hsl (v+ pos (blue-from-hsl [0.6035 -2.8447 0.9631])))))))) }
   ])

(def screen-resolutions
  [{:W 3200 :H 2000} ;; 0 i9
   {:W 3840 :H 2160} ;; 1 4k
   {:W 2560 :H 1600} ;; 2 1440ish
   {:W 2560 :H 1440} ;; 3 1440p
   {:W 1920 :H 1080} ;; 4 1080p
   {:W 320 :H 200}   ;; 5 test
   ])

(def screen-offsets
  [
   [0.0 0.0]
   [0.0 0.0]
   [-0.2 0.0]
  ])

(defn gen
  "generate png file
   n = image index
   rn =  resolution index"
  [n rn]
  (let [seed (:seed (nth screen-codes n))
        name (:name (nth screen-codes n))
        offset_ (nth screen-offsets n)
        code-n (:code (nth screen-codes n))
        code `(->>
               ~code-n
               (clisk.live/scale [1.0 1.0])
               (clisk.live/offset ~offset_))
        _ (clisk.live/seed-perlin-noise! seed)
        _ (clisk.live/seed-simplex-noise! seed)
        W (:W (nth screen-resolutions rn))
        H (:H (nth screen-resolutions rn))
        img (clisk.live/image (eval code) :width W :height H)
        img-filename (format "art/screens/%s_%dx%d.png" name W H) ]
    (ImageIO/write
     img "png"
     (File. img-filename))
    (println img-filename)))

;; ------ run this stuff -----

(gen 2 4)

(doseq [rn (range 5)] (gen 2 rn))
