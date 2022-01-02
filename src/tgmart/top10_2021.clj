(ns tgmart.top10-2021
  (:use [tgmart core]
        [clisk live])
  (:import [java.io File]
           [javax.imageio ImageIO]))

;; make 4k versions of the Top 10 2021 tweegeemee images

(def top10-code
  [;;  1 https://tweegeemee.com/i/210524_223102_D
   '(clisk.live/vdivide (clisk.live/vdivide (clisk.live/turbulate 2.6915 (clisk.live/square velvet)) (clisk.live/rgb-from-hsl (clisk.live/gradient (clisk.live/v- velvet (clisk.live/green-from-hsl [1.1324 2.1784 2.9425 2.4113]))))) (clisk.live/rgb-from-hsl (clisk.live/gradient (clisk.live/v- velvet (clisk.live/green-from-hsl [1.1324 2.1784 2.9425 2.4113])))))
  ;;  2 https://tweegeemee.com/i/210516_053156_M
   '(clisk.live/vabs (clisk.live/vdivide (clisk.live/vfrac (clisk.live/rgb-from-hsl (clisk.live/gradient (clisk.live/v- snoise (clisk.live/green-from-hsl (clisk.live/height-normal (clisk.live/adjust-hue 2.6915 (clisk.live/blue-from-hsl [1.1324 2.1784 2.9425 2.4113])))))))) (clisk.live/rgb-from-hsl (clisk.live/gradient (clisk.live/v- turbulence (clisk.live/green-from-hsl snoise))))))
  ;;  3 https://tweegeemee.com/i/210527_023157_M
   '(clisk.live/vdivide (clisk.live/vdivide (clisk.live/turbulate 2.0353000000000003 (clisk.live/square (clisk.live/hsl-from-rgb vnoise))) (clisk.live/rgb-from-hsl (clisk.live/gradient (clisk.live/v- velvet (clisk.live/green-from-hsl [1.1324 2.1784 2.9425 2.4113]))))) (clisk.live/rgb-from-hsl (clisk.live/gradient (clisk.live/vmin velvet (clisk.live/green-from-hsl [1.1324 2.1784 2.9425 2.4113])))))
  ;;  4 https://tweegeemee.com/i/210109_053140_N
   '(clisk.live/adjust-hue (clisk.live/v+ (clisk.live/height-normal (clisk.live/length pos)) (clisk.live/min-component (clisk.live/vfrac (clisk.live/x (clisk.live/height (clisk.live/green-from-hsl (clisk.live/radius (clisk.live/v- grain vsplasma)))))))) (clisk.live/green-from-hsl (clisk.live/length (clisk.live/rgb-from-hsl [1.2886 -0.9347 -0.5233 2.7016]))))
  ;;  5 https://tweegeemee.com/i/210524_203242_M
   '(clisk.live/vdivide (clisk.live/vdivide (clisk.live/turbulate 3.8098 (clisk.live/square (clisk.live/hsl-from-rgb vnoise))) (clisk.live/rgb-from-hsl (clisk.live/gradient (clisk.live/v- velvet (clisk.live/green-from-hsl [1.1324 2.1784 2.9425 2.4113]))))) (clisk.live/rgb-from-hsl (clisk.live/gradient (clisk.live/v- velvet (clisk.live/green-from-hsl [1.1324 2.1784 2.9425 2.4113])))))
  ;;  6 https://tweegeemee.com/i/210601_233132_N
   '(clisk.live/hsl-from-rgb (clisk.live/average (clisk.live/vfrac (clisk.live/gradient vsnoise)) (clisk.live/vsqrt (clisk.live/x (clisk.live/vdivide wood (clisk.live/alpha (clisk.live/blue-from-hsl (clisk.live/radius [-1.6816 2.4889 -1.0574]))))))))
  ;;  7 https://tweegeemee.com/i/210325_073144_D
   '(clisk.live/adjust-hsl (clisk.live/offset [-2.8324000000000003 -3.1638 3.3456] (clisk.live/adjust-hue (clisk.live/theta (clisk.live/adjust-hsl (clisk.live/radius [-2.9213 -2.468 1.7222]) pos)) (clisk.live/adjust-hsl (clisk.live/radius [-2.9213 -2.468 1.7222]) (clisk.live/v+ [0.4683 -2.0103] pos)))) (clisk.live/y (clisk.live/min-component (clisk.live/red-from-hsl (clisk.live/offset [-4.1289 -2.5013 2.9636] (clisk.live/adjust-hsl (clisk.live/radius pos) (clisk.live/v+ [1.1114 -2.1127] pos)))))))
  ;;  8 https://tweegeemee.com/i/211201_163609_D
   '(clisk.live/lightness-from-rgb (clisk.live/green-from-hsl (clisk.live/gradient (clisk.live/green-from-hsl (clisk.live/gradient noise)))))
  ;;  9 https://tweegeemee.com/i/210522_023136_N
   '(clisk.live/vdivide (clisk.live/turbulate 2.6915 (clisk.live/square (clisk.live/hsl-from-rgb vnoise))) (clisk.live/rgb-from-hsl (clisk.live/gradient (clisk.live/v- velvet (clisk.live/green-from-hsl [1.1324 2.1784 2.9425 2.4113])))))
  ;; 10 https://tweegeemee.com/i/210114_143204_M
   '(clisk.live/adjust-hue (clisk.live/v+ (clisk.live/height-normal (clisk.live/length pos)) (clisk.live/min-component (clisk.live/vfrac (clisk.live/dot (clisk.live/vabs (clisk.live/adjust-hsl flecks (clisk.live/adjust-hue [-0.3039 -2.5964 2.3291 1.9305] (clisk.live/green-from-hsl pos)))) [2.17 -1.6577])))) (clisk.live/vfrac (clisk.live/length (clisk.live/y [1.2886 -0.9347 -0.5233 2.7016]))))])

(def top10-seeds [210417 210417 210417 201217 210417
                  210531 210306 211130 210417 201217])

;; 3840 x 2160 is 4k, but changing the aspect ratio just shows 
;; the top part of the image.  Let's try 2160 x 2160 and add 
;; black bars as post process?

;; one by one (range 10)
(doseq [n (range 2)]
  (let [code (nth top10-code n)
        seed (nth top10-seeds n)
        _ (clisk.live/seed-perlin-noise! seed)
        _ (clisk.live/seed-simplex-noise! seed)
        img (clisk.live/image (eval code) :width 2160 :height 2160)]
    (ImageIO/write img "png" (File. (format "art/top10_2021/%02d.png" n)))))

;; back to 3840 x 2160 but choose an offset so I can make this 
;; normalized height of screen is 0.56250 
;; 00_4k = offset [0.0 0.75] 
;; 01_4k = offset [0.0 0.75] 
;; 02_4k = offset [0.0 0.2]
;; 03_4k = offset [0.0 0.25]
;; 04_4k = offset [0.0 0.08]
;; 05_4k = offset [0.0 0.70]
;; 06_4k = offset [-0.25 -0.22]
;; 07_4k = offset [-0.25 -0.22]
;; 08_4k = offset [0.25 0.95]
;; 09_4k = offset [0.0 0.0]
(let [n 9
      code-n (nth top10-code n)
      code `(->>
             ~code-n
             (clisk.live/scale [1.0 1.0])
             (clisk.live/offset [0.0 0.0]))
      seed (nth top10-seeds n)
      _ (clisk.live/seed-perlin-noise! seed)
      _ (clisk.live/seed-simplex-noise! seed)
      img (clisk.live/image
           (eval code)
           :width 3840 :height 2160
           ;;:width 384 :height 216
           )]
    (ImageIO/write
     img "png"
     (File. (format "art/top10_2021/%02d_4k.png" n))))
