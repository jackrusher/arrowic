(ns arrowic.core)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; minimalist wrapper around mxGraph

(def ^:dynamic *graph* nil)

(defn create-graph
  "Return a new empty graph."
  []
  (com.mxgraph.view.mxGraph.))

(defn insert-vertex!
  "Inserts vertex `v` into *graph*. The string representation of `v` is used both for its label and as its identity."
  [v]
  (let [vs (str v)]
    (.insertVertex *graph* (.getDefaultParent *graph*) nil vs 20 20 (* 16 (count vs)) 40 "shape=ellipse;perimeter=ellipsePerimeter;fillColor=#eeeeee;strokeColor=white;fontColor=black;fontSize=16")))

(defn insert-edge!
  "Inserts an edge into *graph* between `v1` and `v2` with optional `edge-label`."
  [v1 v2 & edge-label]
  (.insertEdge *graph* (.getDefaultParent *graph*) nil (or (first edge-label) "") v1 v2 "strokeColor=#999;fillColor=black;fontColor=black"))

(defmacro with-graph
  "Evaluates `body` in a context within which *graph* is bound to `graph`, returning `graph`."
  [graph & body]
  `(binding [*graph* ~graph]
     (.beginUpdate (.getModel *graph*))
     ~@body
     (.execute (com.mxgraph.layout.hierarchical.mxHierarchicalLayout. *graph* javax.swing.SwingConstants/WEST)
               (.getDefaultParent *graph*))
     (.endUpdate (.getModel *graph*))
     *graph*))

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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Swing viewer fns

(defn view
  "Request `viewer` to show a layout of `graph`."
  [viewer graph]
  (let [cp (.getContentPane viewer)]
    (.removeAll cp)
    (.add cp (com.mxgraph.swing.mxGraphComponent. graph)))
  (.pack viewer)
  (.setVisible viewer true))

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
        (insert-edge! (rand-nth vertices) (rand-nth vertices) edge-label)))))

(def graph (random-graph))

(def viewer (create-viewer graph))

(def another-graph (random-graph))

;; can change the graph this way:
(view viewer another-graph)

;; export a graph to SVG
(spit "example.svg" (as-svg another-graph))

  )
