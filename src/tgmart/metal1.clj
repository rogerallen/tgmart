(ns tgmart.metal1
  (:use [tgmart core]
        [clisk live])
  (:import [java.io File]
           [javax.imageio ImageIO]))

;; going to make a big image for a costco
;; metal picture.  Just to see how it looks.
;; from the website: 12" x 12" is best at 3638 x 3638 pixels

;; seems like #3 is nicest out of this quick list:
;; 1) https://twitter.com/tweegeemee/status/675141250316681217
;; 2) https://twitter.com/tweegeemee/status/694921402214977536
;; 3) https://twitter.com/tweegeemee/status/692405144269488128
;; 4) https://twitter.com/tweegeemee/status/938659097196597248
;; 5) https://twitter.com/tweegeemee/status/1226967010732576768
;; 6) https://twitter.com/tweegeemee/status/1335456790365020160
;; 
;;  :name "160127_173218_N.clj" 
;;  :parents ["160127_163209_C.clj"] 
;;  :hash 1835634365 :image-hash -488048751
;;  :code (clisk.live/adjust-hsl (clisk.live/vcos (clisk.live/blue-from-hsl (clisk.live/dot clisk.live/pos (clisk.live/vsin (clisk.live/rgb-from-hsl (clisk.live/adjust-hue [1.009 0.4101 -0.8179] (clisk.live/v- [0.502 2.7223 -1.2887] (clisk.live/dot clisk.live/pos clisk.live/pos)))))))) (clisk.live/x (clisk.live/min-component (clisk.live/normalize (clisk.live/square (clisk.live/vsin (clisk.live/rgb-from-hsl (clisk.live/adjust-hue (clisk.live/green-from-hsl [1.009 0.4101 -0.8179]) (clisk.live/v- [0.502 2.7223 -1.2887] clisk.live/pos)))))))))
;;
;; will edit a bit in image editor just to give it some "pop", 
;; I think.

(def code '(clisk.live/adjust-hsl (clisk.live/vcos (clisk.live/blue-from-hsl (clisk.live/dot clisk.live/pos (clisk.live/vsin (clisk.live/rgb-from-hsl (clisk.live/adjust-hue [1.009 0.4101 -0.8179] (clisk.live/v- [0.502 2.7223 -1.2887] (clisk.live/dot clisk.live/pos clisk.live/pos)))))))) (clisk.live/x (clisk.live/min-component (clisk.live/normalize (clisk.live/square (clisk.live/vsin (clisk.live/rgb-from-hsl (clisk.live/adjust-hue (clisk.live/green-from-hsl [1.009 0.4101 -0.8179]) (clisk.live/v- [0.502 2.7223 -1.2887] clisk.live/pos))))))))))

;; at full size, this takes about 20 minutes to create
(let [img (image (eval code) :width 3638 :height 3638)]
  ;;(show img :width 200 :height 200)
  (ImageIO/write img "png" (File. "art/metal1.png"))
  )