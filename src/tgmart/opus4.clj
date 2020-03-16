(ns tgmart.opus4
  (:use [tgmart core]
        [clisk live]))

;; { :name "200210_203123_M.clj" :parents ["200208_233119_M.clj"] :hash -627449611 :image-hash -2016435961
;;   :code (clisk.live/v+ clisk.live/pos (clisk.live/vround clisk.live/vnoise))
;; }

;; this just asjusts color...

;;(render-animation
;; [i 0 30 1]
;; (clisk.live/v+
;;  [(circle-sin i 1.1 30)
;;   (circle-cos i 1.1 30)
;;  (circle-sin i 1.4 30)
;;   ]
;;  clisk.live/pos
;;  (clisk.live/vround clisk.live/vnoise))
;; {:filename "art/opus4a/a%04d.png"
;;  :height  (* (/ 720 1) 1)
;;  :width   (* (/ 1280 1) 1)
;;  })

;; opus 4a
(let
 [N 900]
  (render-animation
   [i 0 N 1]
   (clisk.live/v+
    [(circle-sin i 0.5 N)
     (circle-cos i 0.5 N)
     (circle-sin i 0.5 N)]
    clisk.live/pos
    (clisk.live/vround
     (offset
      [(* (circle-sin i 0.1 N) (circle-cos i 0.1 (/ N 3)))
       (circle-cos i 0.1 N)
       (circle-sin i 0.2 N)
       ]
      clisk.live/vnoise)))
   {:filename "art/opus4a/a%04d.png"
    :height  (* (/ 720 1) 1)
    :width   (* (/ 720 1) 1)
    }))

;; opus 4b
(let
 [N 3]
  (render-animation
   [i 0 N 1]
   (clisk.live/v+
    [(circle-sin i 0.5 N)
     (circle-cos i 0.5 N)
     (circle-sin i 0.5 N)]
    clisk.live/pos
    (clisk.live/vround
     (offset
      [(* (circle-sin i 0.1 N) (circle-cos i 0.1 (/ N 3)))
       (circle-cos i 0.1 N)
       (circle-sin i 0.2 N)
       ]
      clisk.live/vnoise)))
   {:filename "art/opus4b/aaa%04d.png"
    :height  (* (/ 720 1) 1)
    :width   (* (/ 720 1) 1)
    }))
