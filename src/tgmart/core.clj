(ns tgmart.core
  (:use [clisk live])
  (:import [clisk Util]
           [java.io File]
           [javax.imageio ImageIO])
  (:require [tweegeemee.core :as tgm]))

;; a snippet based on mikea's code.  modified from telegenic so I
;; could use ffmpeg.  Also modified to allow for parallel runs on
;; different systems.
(defmacro render-animation
  "Macro to render a sequence of frames, looping with a symbolic frame counter.
   Example: (render-animation [i 10] (offset [(* i 10) 0.0 0.0] (vnoise)))"
  ([[key frame-start frame-end frame-step] src options]
    (when-not (symbol? key) (error "render-animation needs a symbol binding for the frame number"))
    `(let [opts#  (mapcat identity ~options)]
       (println "Starting...")
       (doseq [~key (range ~frame-start ~frame-end ~frame-step)] ;; loop over all frames, lazily. don't hold memory
         (let [im# (apply image ~src opts#)  ;; create the frame
               fn# (format (:filename ~options) ~key)]
           (apply show im# opts#) ;; show the latest frame
           (println "Image" ~key)
           (ImageIO/write im# "png" (File. fn#))))  ;; write the frame
       (println "Done."))))

;; rescale the data to frame it appropriately
;; NOTE: https://github.com/mikera/clisk/issues/15
(defn scale-for-vert-hd0
  "center at 0,0.  x ranges [-9/16,9/16], y [-1,1]"
  [fn]
  (->> fn
       (scale  [(/ 16 9 2) (/ 16 9 2)])
       (offset [-0.5 (/ -16 9 2)])))

(defn scale-for-vert-hd
  "center at 9/8,0.5. x ranges [0,9/16], y [0,1]"
  [fn]
  (->> fn
       (scale  [(/ 16 9) (/ 16 9)])))

;; easing-type functions.  use on the key to create a scalar per frame
(defn circle-cos [i max-range steps]
  (* max-range (Math/cos (* 2 Math/PI (/ i steps)))))
(defn circle-sin [i max-range steps]
  (* max-range (Math/sin (* 2 Math/PI (/ i steps)))))
(defn half-circle-cos [i max-range steps]
  (* max-range (Math/cos (* Math/PI (/ i steps)))))
(defn half-circle-sin [i max-range steps]
  (* max-range (Math/sin (* Math/PI (/ i steps)))))

;; ======================================================================
;; Animation creation code
(comment

  ;; ======================================================================
  ;; simple example usage
  (render-animation
   [i 0 60 1]
   (scale-for-vert-hd (offset [(circle-sin i 0.5 60)
                               (circle-cos i 0.5 60)
                               0.0]
                              (vnoise)))
   {:filename "art/a%04d.png"
    :width    (* 2 (/ 1080 10))
    :height   (* 2 (/ 1920 10))
    })

)
