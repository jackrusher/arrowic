(ns arrowic.core
  (:require [clojure.reflect :refer [reflect]])
  (:use [arrowic.style]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; minimalist wrapper around mxGraph

(def ^:dynamic *graph* nil)

;; TODO default stylesheet should be used, rather than creating a new
;; style for every insert, but not sure how to merge default style
;; with per-node style requests if done that way.
(defn create-graph
  "Return a new empty graph."
  []
  (doto (com.mxgraph.view.mxGraph.)
    (.setCellsLocked true)))

(defn insert-vertex!
  "Inserts vertex `v` into *graph*. The string representation of `v` is used both for its label and as its identity."
  [v & options]
  (assert (or (nil? options) (even? (count options)))
          "The options to insert-vertex! must be an even number of key-value pairs.")
  (let [vs (str v)]
    ;;this, Object parent, String id, Object value, double x, double y, double width, double height, String style
    (.insertVertex *graph*
                   (.getDefaultParent *graph*)
                   nil vs 20 20 (* 16 (count vs)) 40
                   (make-style-string (merge default-vertex-style (apply hash-map options))))))

(defn insert-edge!
  "Inserts an edge into *graph* between `v1` and `v2` with optional `edge-label`."
  [v1 v2 & options]
  (assert (or (nil? options) (even? (count options)))
          "The options to insert-edge! must be an even number of key-value pairs.")
  (let [options (merge default-edge-style (apply hash-map options))]
    ;;this, Object parent, String id, Object value, Object source, Object target, String style
    (.insertEdge *graph*
                 (.getDefaultParent *graph*)
                 nil
                 (or (str (:label options)) "")
                 v1
                 v2
                 (make-style-string (dissoc options :label)))))

(defmacro with-graph
  "Evaluates `body` in a context within which *graph* is bound to `graph`, returning `graph`."
  [graph & body]
  `(binding [*graph* ~graph]
     (.beginUpdate (.getModel *graph*))
     ~@body
     (doto (com.mxgraph.layout.hierarchical.mxHierarchicalLayout.
                *graph*
                javax.swing.SwingConstants/NORTH)
       (.setFineTuning true)
       (.setUseBoundingBox true)
       (.execute (.getDefaultParent *graph*)))
     (.endUpdate (.getModel *graph*))
     *graph*))

(defn as-swing-component
  "Return a Swing component suitable for use in a GUI."
  [graph]
  (doto (com.mxgraph.swing.mxGraphComponent. graph)
    (.setCenterPage true)))

(defn as-svg
  "Return `graph` as a string containing an SVG document depicting the graph."
  [graph]
  (-> (com.mxgraph.util.mxCellRenderer/drawCells
       graph nil 1 nil
       (proxy [com.mxgraph.util.mxCellRenderer$CanvasFactory] []
         (createCanvas [w h]
           (let [doc  (com.mxgraph.util.mxDomUtils/createSvgDocument w h)
                 root (.getDocumentElement doc)]
             ;; insert a white rectangle for the background
             (.insertBefore root
                            (doto (.createElement doc "rect")
                              (.setAttribute "width" (str w))
                              (.setAttribute "height" (str h))
                              (.setAttribute "fill" "#ffffff"))
                            (.getFirstChild root))
             (doto (com.mxgraph.canvas.mxSvgCanvas. doc)
               (.setEmbedded true))))))
      (.getDocument)
      (com.mxgraph.util.mxXmlUtils/getXml)))

(defn as-data
  "Return `graph` as a sequence of maps describing each edge and vertex."
  [graph]
  (mapv (fn [cell]
          {:value (.getValue cell)
           :vertex? (.isVertex cell)
           :geometry (let [g (.getGeometry cell)]
                       {:height (.getHeight g)
                        :width (.getWidth g)
                        :x (.getX g)
                        :y (.getY g)
                        :points (mapv #(vector (.getX %) (.getY %)) (.getPoints g))})
           :style (reduce #(apply (partial assoc %1) (clojure.string/split %2 #"="))
                          {}
                          (remove empty? (-> (.getStyle cell) (clojure.string/split #";"))))})
        (.getChildCells graph (.getDefaultParent graph))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Swing viewer fns

(defn view
  "Request `viewer` to show a layout of `graph`."
  [viewer graph]
  (let [cp (.getContentPane viewer)]
    (.removeAll cp)
    (.setLayout cp (java.awt.BorderLayout.))
    (.add cp (as-swing-component graph) (java.awt.BorderLayout/CENTER)))
  (.pack viewer)
  (.setVisible viewer true)
  graph)

(defn create-viewer
  "Create a new Swing GUI viewer showing `graph`."
  [graph]
  (let [frm (javax.swing.JFrame. "viewgraph")]
    (view frm graph)
    frm))

(comment

;; grab some words from the UNIX dictionary
(def words
  (into [] (clojure.string/split-lines (slurp "/usr/share/dict/words"))))

;; make a random graph full of words
(defn random-graph []
  (with-graph (create-graph)
    (let [vertices (repeatedly 10 #(insert-vertex! (rand-nth words)))]
      (doseq [edge-label (repeatedly 20 #(rand-nth words))]
        (insert-edge! (rand-nth vertices)
                      (rand-nth vertices)
                      :label edge-label)))))

(def graph (random-graph))

(def viewer (create-viewer graph))

(def another-graph (random-graph))

;; can change the graph this way:
(view viewer another-graph)

;; export a graph to SVG
(spit "example.svg" (as-svg another-graph))

  )
