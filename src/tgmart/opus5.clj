(ns tgmart.opus5
  (:use [tgmart core]
        [clisk live]))

;; ======================================================================
(comment
 { :name "200315_003044_b.clj" :parents [] :hash 276265568 :image-hash -1097004999 :twitter-id 1238986179703386118
   :code (clisk.live/gradient (clisk.live/normalize clisk.live/vnoise))
  }
 )

;; ======================================================================
;; mkdir art/opus5a

;; ======================================================================
;; no change
(render-animation
 [i 0 1 1]

 (clisk.live/gradient
  (clisk.live/normalize
   clisk.live/vnoise))

 {:filename "art/opus5a/a.png"
  :height  (* (/ 720 1) 1)
  :width   (* (/ 720 1) 1)
  })

;; ======================================================================
;; opus5a
;; x = r sin theta cos phi
;; y = r sin theta sin phi
;; z = r cos theta
(let
    [N   500
     rev 11]
  (render-animation
   [i 0 N 1]

   ;; lots of "glitches" in the output...
   (offset
    [9.01 3.01 0.01] ;; lets see if slight offset helps glitches.  it did
    (clisk.live/gradient
     (clisk.live/normalize
      (offset
       [(* 0.2 (circle-sin i 1.0 N) (circle-cos i 1.0 (/ N 3)))
        (circle-cos i 0.2 N)
        (* 0.2 (circle-sin i 1.0 N) (circle-sin i 1.0 (/ N 3)))
        ]
       clisk.live/vnoise
       ) ;; offset
      )
     )
    ) ;; offset

 {:filename (str "art/opus5a/a-" rev "-%03d.png")
  :height  (* (/ 720 1) 1)
  :width (* (/ 720 1) 1) }))
