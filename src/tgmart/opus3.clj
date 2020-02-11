(ns tgmart.opus3
  (:use [tgmart core]
        [clisk live])
  (:import [clisk Util]
           [java.io File]
           [javax.imageio ImageIO])
  (:require [tweegeemee.core :as tgm]))

;; { :name "171207_063205_b.clj" :parents [] :hash 217353943 :image-hash -2000825249
;;   :code (clisk.live/min-component (clisk.live/vfrac (clisk.live/gradient clisk.live/spots)))
;;  }

;; this has an unfortunate glitch right at 0 and 3000.  Some artifacts
;; when sin==0.0 or cos==1.0.  Offsetting with v+ seems to resolve this
(render-animation [i 0 3000 1]
   (clisk.live/min-component
    (clisk.live/vfrac
     (clisk.live/gradient
      (offset
       (clisk.live/v+ [1.0 1.0 1.0]
                      [(circle-sin i 0.001 3000)
                       (circle-cos i 0.001 3000)
                       (circle-sin i 0.040 3000)
                       ])
         clisk.live/spots
         ))))
   {:filename "art/opus3a/a%04d.png"
    :height  (* (/ 1080 1) 1)
    :width   (* (/ 1920 1) 1)
    })
