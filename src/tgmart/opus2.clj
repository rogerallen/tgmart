(ns tgmart.opus2
  (:use [tgmart core]
        [clisk live])
  (:import [clisk Util]
           [java.io File]
           [javax.imageio ImageIO])
  (:require [tweegeemee.core :as tgm]))

(comment

  ;; ======================================================================
  ;; opus2a based on FIXME
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

)
