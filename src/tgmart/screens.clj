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
    :scale [1.0 1.0]
    :offset [0.0 0.0]
    :code '(adjust-hsl [2.6874568911194254 -0.622670298103297] (scale 0.26433639079750065 (vfrac (vcos (adjust-hsl (radius (alpha (vsqrt (dot [-1.1139 0.3231] [0.7539 2.2466])))) (v+ (length (vcos pos)) (v+ [2.0296 -2.661] pos)))))))}
   ;; 1 - https://tweegeemee.com/i/171207_063205_b/code
   {:name "171207_063205_b"
    :seed 171106 ; from gist 
    :scale [1.0 1.0]
    :offset [0.0 0.0]
    :code `(min-component (vfrac (gradient spots)))}
   ;; 2 - https://tweegeemee.com/i/150821_143150_M/code 
   {:name "150821_143150_M"
    :seed 150821 ;; actually this is "pre-seed"  
    :scale [1.0 1.0]
    :offset [-0.2 0.0]
    :code `(vpow (length plasma) (sigmoid (gradient (green-from-hsl (green-from-hsl (rgb-from-hsl (v+ pos (blue-from-hsl [0.6035 -2.8447 0.9631]))))))))}
   ;; https://tweegeemee.com/i/151212_023122_N
   ;; https://tweegeemee.com/i/160125_053112_N
   ;; https://tweegeemee.com/i/160510_023059_N
   ;; 3 - https://tweegeemee.com/i/170102_233120_N
   {:name "170102_233120_N"
    :seed 1610 
    :scale [0.6 0.6]
    :offset [0.0 0.0]
    :code `(v* (saturation-from-rgb vturbulence) (adjust-hsl (length (x (normalize (gradient (clamp turbulence -0.8518 (vabs [-1.826 -2.5608])))))) (blue-from-hsl (green-from-hsl (vdivide (adjust-hue (vmod (alpha (max-component plasma)) vnoise) [2.0743 2.8701 -2.6032]) [2.0981 -1.072 1.4311])))))}
   ;;
   ])

(def screen-resolutions
  [{:W 3200 :H 2000} ;; 0 i9
   {:W 3840 :H 2160} ;; 1 4k
   {:W 2560 :H 1600} ;; 2 1440ish
   {:W 2560 :H 1440} ;; 3 1440p
   {:W 1920 :H 1080} ;; 4 1080p
   {:W 320 :H 200}   ;; 5 test
   ])

(defn gen
  "generate png file
   n = image index
   rn =  resolution index"
  [n rn]
  (let [seed (:seed (nth screen-codes n))
        name (:name (nth screen-codes n))
        scale_ (:scale (nth screen-codes n))
        offset_ (:offset (nth screen-codes n))
        code-n (:code (nth screen-codes n))
        code `(->>
               ~code-n
               (clisk.live/scale ~scale_)
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

(gen 3 4)

(doseq [rn (range 4)] (gen 3 rn))
