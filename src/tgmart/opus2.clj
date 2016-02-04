(ns tgmart.opus2
  (:use [tgmart core]
        [clisk live])
  (:import [clisk Util]
           [java.io File]
           [javax.imageio ImageIO])
  (:require [tweegeemee.core :as tgm]))

(comment

  ;; ======================================================================
  ;; opus2a based on "landscape" :name "151211_023204_M.clj" :parents ["151208_163232_C.clj"]
  ;; ...this moves a bit too fast.  should make this about 4x slower...
  (render-animation
   [i 0 400 1]
   (vabs
    (vcos
     (v*
      (vabs
       (scale [-2.8437 0.8805000000000001]
              (x
               (vfrac
                (adjust-hsl
                 (offset
                  [(* (Math/sin (* 2 Math/PI (/ i 400))) 0.03)
                   (* (Math/cos (* 2 Math/PI (/ i 400))) 0.03)
                   (* (Math/sin (* 2 Math/PI (/ i 400))) 0.03)]
                  noise
                  )
                 (sigmoid
                  (adjust-hsl
                   (vround [2.1838 -1.5047 -1.3361 0.6155]) vsnoise)))))))
      [2.0727 1.3225 -0.9395])))
   {:filename "art/opus2a/a%04d.png"
    :height  (* (/ 1080 10) 5)
    :width   (* (/ 1920 10) 5)
    })

  ;; via twitter, https://twitter.com/nodename/status/694989814802751488
  ;; :name "160203_203104_M.clj" :parents ["160203_163143_C.clj"] :hash -781059262 :image-hash -1949814759
  (render-animation
   [i 0 800 1]
   (->>
    (vfrac (green-from-hsl (lerp (vconcat (gradient (offset [2.1641 -1.8963 1.3623 1.1235] (vcos (vpow (clamp -2.1979 [2.8877 -0.1482 -0.9508] [2.2707 -2.5016]) (v- pos 2.6432))))) (saturation-from-rgb (rgb-from-hsl [1.4505 -0.0455 2.0777]))) (v- (adjust-hue (vconcat (lightness-from-rgb (length pos)) 2.433) 2.0835) (adjust-hsl (alpha (t (sigmoid 1.7052))) (v- pos 2.6432))) (vabs (vround (vfrac (sigmoid (v* turbulence (lerp pos 2.0475 pos)))))))))
    (rotate (* 2 Math/PI (/ i 800)))
    (scale [(+ 0.5 (half-circle-sin i 1 800)) (+ 0.5 (half-circle-sin i 1 800))])
    (offset [-0.5 (* -0.5 (/ 9 16)) ])
    )
   {:filename "art/opus2b/a%04d.png"
    :width   (* (/ 1920 10) 5)
    :height  (* (/ 1080 10) 5)
    })

  ;; just the image
  (show
   (->>
    (vfrac (green-from-hsl (lerp (vconcat (gradient (offset [2.1641 -1.8963 1.3623 1.1235] (vcos (vpow (clamp -2.1979 [2.8877 -0.1482 -0.9508] [2.2707 -2.5016]) (v- pos 2.6432))))) (saturation-from-rgb (rgb-from-hsl [1.4505 -0.0455 2.0777]))) (v- (adjust-hue (vconcat (lightness-from-rgb (length pos)) 2.433) 2.0835) (adjust-hsl (alpha (t (sigmoid 1.7052))) (v- pos 2.6432))) (vabs (vround (vfrac (sigmoid (v* turbulence (lerp pos 2.0475 pos)))))))))
    (scale [1.0 1.0])
    (offset [-0.5 -0.5])
    )
   :width 720 :height 720
   )

)
