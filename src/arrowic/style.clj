(ns arrowic.style)

(def styles
  ;; keys
  {:align "align"
   :arc-size "arcSize"
   :autosize "autosize"
   :bendable "bendable"
   :cloneable "cloneable"
   :dash-pattern "dashPattern"
   :dashed "dashed"
   :deletable "deletable"
   :direction "direction"
   :edge-style "edgeStyle"
   :editable "editable"
   :elbow "elbow"
   :elbow-edge-style "elbowEdgeStyle"
   :end-arrow "endArrow"
   :end-fill "endFill"
   :end-size "endSize"
   :entity-relation-edge-style "entityRelationEdgeStyle"
   :entry-perimeter "entryPerimeter"
   :entry-x "entryX"
   :entry-y "entryY"
   :exit-perimeter "exitPerimeter"
   :exit-x "exitX"
   :exit-y "exitY"
   :fill-color "fillColor"
   :fill-opacity "fillOpacity"
   :flip-h "flipH"
   :flip-v "flipV"
   :foldable "foldable"
   :font-color "fontColor"
   :font-family "fontFamily"
   :font-size "fontSize"
   :font-style "fontStyle"
   :glass "glass"
   :gradient-color "gradientColor"
   :gradient-direction "gradientDirection"
   :horizontal "horizontal"
   :image "image"
   :image-align "imageAlign"
   :image-background "imageBackground"
   :image-border "imageBorder"
   :image-flip-h "imageFlipH"
   :image-flip-v "imageFlipV"
   :image-height "imageHeight"
   :image-vertical-align "imageVerticalAlign"
   :image-width "imageWidth"
   :indicator-color "indicatorColor"
   :indicator-gradient-color "indicatorGradientColor"
   :indicator-height "indicatorHeight"
   :indicator-image "indicatorImage"
   :indicator-shape "indicatorShape"
   :indicator-spacing "indicatorSpacing"
   :indicator-width "indicatorWidth"
   :label-background-color "labelBackgroundColor"
   :label-border-color "labelBorderColor"
   :label-position "labelPosition"
   :loop-edge-style "loopEdgeStyle"
   :loop-style "loopStyle"
   :movable "movable"
   :no-edge-style "noEdgeStyle"
   :no-label "noLabel"
   :opacity "opacity"
   :orthogonal "orthogonal"
   :orthogonal-edge-style "orthogonalEdgeStyle"
   :overflow "overflow"
   :perimeter "perimeter"
   :perimeter-spacing "perimeterSpacing"
   :port-constraint "portConstraint"
   :resizable "resizable"
   :rotation "rotation"
   :rounded "rounded"
   :routing-center-x "routingCenterX"
   :routing-center-y "routingCenterY"
   :segment "segment"
   :segment-edge-style "segmentEdgeStyle"
   :separator-color "separatorColor"
   :shadow "shadow"
   :shape "shape"
   :side-to-side-edge-style "sideToSideEdgeStyle"
   :source-perimeter-spacing "sourcePerimeterSpacing"
   :source-port "sourcePort"
   :spacing "spacing"
   :spacing-bottom "spacingBottom"
   :spacing-left "spacingLeft"
   :spacing-right "spacingRight"
   :spacing-top "spacingTop"
   :start-arrow "startArrow"
   :start-fill "startFill"
   :start-size "startSize"
   :stencil-flip-h "stencilFlipH"
   :stencil-flip-v "stencilFlipV"
   :stroke-color "strokeColor"
   :stroke-opacity "strokeOpacity"
   :stroke-width "strokeWidth"
   :swimlane-fill-color "swimlaneFillColor"
   :swimlane-line "swimlaneLine"
   :target-perimeter-spacing "targetPerimeterSpacing"
   :target-port "targetPort"
   :text-opacity "textOpacity"
   :top-to-bottom-edge-style "topToBottomEdgeStyle"
   :vertical-align "verticalAlign"
   :vertical-label-position "verticalLabelPosition"
   :white-space "whiteSpace"
   ;; VALUES
   ;; vertex shapes XXX should be strings
   :ellipse-perimeter com.mxgraph.view.mxPerimeter/EllipsePerimeter
   :hexagon-perimeter com.mxgraph.view.mxPerimeter/HexagonPerimeter
   :rectangle-perimeter com.mxgraph.view.mxPerimeter/RectanglePerimeter
   :rhombus-perimeter com.mxgraph.view.mxPerimeter/RhombusPerimeter
   :triangle-perimeter com.mxgraph.view.mxPerimeter/TrianglePerimeter
   ;; :edge-styles XXX duplicated, but might be better with same prefix?
   :edge-elbow "elbowEdgeStyle"
   :edge-entity-relation "entityRelationEdgeStyle"
   :edge-loop "loopEdgeStyle"
   :edge-side-to-side "sideToSideEdgeStyle"
   :edge-top-to-bottom "topToBottomEdgeStyle"
   :edge-orthogonal "orthogonalEdgeStyle"
   :edge-segment "segmentEdgeStyle"})

(defn make-style-string [style-map]
  (reduce
   (fn [out [k v]]
     (if-let [style-string (styles k)]
       (str out style-string "=" v ";" )
       (throw (Exception. (str "Unknown style parameter '" k "'!")))))
   ""
   style-map))

(def default-vertex-style
  {:shape "ellipse"
   :perimeter "ellipsePerimeter"
   :fill-color "#eeeeee;"
   :stroke-color "white"
   :font-color "black"
   :font-size 16})

(def default-edge-style
  {:rounded true
   :stroke-color "#999"
   :fill-color "black"
   :font-color "black"})

;; TODO argh, so many options
;;     // Settings for edges
;;     Map<String, Object> edge = new HashMap<String, Object>();
;;     edge.put(mxConstants.STYLE_ROUNDED, true);
;;     edge.put(mxConstants.STYLE_ORTHOGONAL, false);
;;     edge.put(mxConstants.STYLE_EDGE, "elbowEdgeStyle");
;;     edge.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
;;     edge.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
;;     edge.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
;;     edge.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
;;     edge.put(mxConstants.STYLE_STROKECOLOR, "#000000"); // default is #6482B9
;;     edge.put(mxConstants.STYLE_FONTCOLOR, "#446299");
;;     mxStylesheet edgeStyle = new mxStylesheet();
;;     edgeStyle.setDefaultEdgeStyle(edge);
;;     graph.setStylesheet(edgeStyle);

;;    9:	public static String STYLE_PERIMETER = "perimeter";
;;   16:	public static String STYLE_SOURCE_PORT = "sourcePort";
;;   23:	public static String STYLE_TARGET_PORT = "targetPort";
;;   31:	public static String STYLE_PORT_CONSTRAINT = "portConstraint";
;;   37:	public static String STYLE_OPACITY = "opacity";
;;   43:	public static String STYLE_FILL_OPACITY = "fillOpacity";
;;   49:	public static String STYLE_STROKE_OPACITY = "strokeOpacity";
;;   55:	public static String STYLE_TEXT_OPACITY = "textOpacity";
;;   67:	public static String STYLE_OVERFLOW = "overflow";
;;   76:	public static String STYLE_ORTHOGONAL = "orthogonal";
;;   82:	public static String STYLE_EXIT_X = "exitX";
;;   88:	public static String STYLE_EXIT_Y = "exitY";
;;   95:	public static String STYLE_EXIT_PERIMETER = "exitPerimeter";
;;  101:	public static String STYLE_ENTRY_X = "entryX";
;;  107:	public static String STYLE_ENTRY_Y = "entryY";
;;  114:	public static String STYLE_ENTRY_PERIMETER = "entryPerimeter";
;;  124:	public static String STYLE_WHITE_SPACE = "whiteSpace";
;;  130:	public static String STYLE_ROTATION = "rotation";
;;  138:	public static String STYLE_SWIMLANE_FILLCOLOR = "swimlaneFillColor";
;;  146:	public static String STYLE_FILLCOLOR = "fillColor";
;;  155:	public static String STYLE_GRADIENTCOLOR = "gradientColor";
;;  162:	 * gradient painting is done from the value of <code>STYLE_FILLCOLOR</code>
;;  163:	 * to the value of <code>STYLE_GRADIENTCOLOR</code>. Taking the example of
;;  164:	 * <code>DIRECTION_NORTH</code>, this means <code>STYLE_FILLCOLOR</code>
;;  166:	 * <code>STYLE_GRADIENTCOLOR</code> at top, with a gradient in-between.
;;  168:	public static String STYLE_GRADIENT_DIRECTION = "gradientDirection";
;;  176:	public static String STYLE_STROKECOLOR = "strokeColor";
;;  185:	public static String STYLE_SEPARATORCOLOR = "separatorColor";
;;  192:	public static String STYLE_STROKEWIDTH = "strokeWidth";
;;  204:	 * <code>STYLE_LABEL_POSITION</code>.
;;  206:	public static String STYLE_ALIGN = "align";
;;  221:	 * <code>STYLE_VERTICAL_LABEL_POSITION</code>.
;;  223:	public static String STYLE_VERTICAL_ALIGN = "verticalAlign";
;;  236:	 * <code>STYLE_ALIGN</code>.
;;  238:	public static String STYLE_LABEL_POSITION = "labelPosition";
;;  251:	 * <code>STYLE_VERTICAL_ALIGN</code>.
;;  253:	public static String STYLE_VERTICAL_LABEL_POSITION = "verticalLabelPosition";
;;  262:	public static String STYLE_IMAGE_ALIGN = "imageAlign";
;;  271:	public static String STYLE_IMAGE_VERTICAL_ALIGN = "imageVerticalAlign";
;;  277:	public static String STYLE_GLASS = "glass";
;;  289:	public static String STYLE_IMAGE = "image";
;;  296:	public static String STYLE_IMAGE_WIDTH = "imageWidth";
;;  303:	public static String STYLE_IMAGE_HEIGHT = "imageHeight";
;;  309:	public static String STYLE_IMAGE_BACKGROUND = "imageBackground";
;;  315:	public static String STYLE_IMAGE_BORDER = "imageBorder";
;;  321:	public static String STYLE_IMAGE_FLIPH = "imageFlipH";
;;  327:	public static String STYLE_IMAGE_FLIPV = "imageFlipV";
;;  333:	public static String STYLE_STENCIL_FLIPH = "stencilFlipH";
;;  339:	public static String STYLE_STENCIL_FLIPV = "stencilFlipV";
;;  346:	public static String STYLE_FLIPH = "flipH";
;;  349:	 * Variable: STYLE_FLIPV
;;  354:	public static String STYLE_FLIPV = "flipV";
;;  362:	public static String STYLE_NOLABEL = "noLabel";
;;  370:	public static String STYLE_NOEDGESTYLE = "noEdgeStyle";
;;  378:	public static String STYLE_LABEL_BACKGROUNDCOLOR = "labelBackgroundColor";
;;  386:	public static String STYLE_LABEL_BORDERCOLOR = "labelBorderColor";
;;  393:	public static String STYLE_INDICATOR_SHAPE = "indicatorShape";
;;  400:	public static String STYLE_INDICATOR_IMAGE = "indicatorImage";
;;  408:	public static String STYLE_INDICATOR_COLOR = "indicatorColor";
;;  417:	public static String STYLE_INDICATOR_GRADIENTCOLOR = "indicatorGradientColor";
;;  422:	public static String STYLE_INDICATOR_SPACING = "indicatorSpacing";
;;  427:	public static String STYLE_INDICATOR_WIDTH = "indicatorWidth";
;;  432:	public static String STYLE_INDICATOR_HEIGHT = "indicatorHeight";
;;  439:	public static String STYLE_SHADOW = "shadow";
;;  446:	public static String STYLE_SEGMENT = "segment";
;;  454:	public static String STYLE_ENDARROW = "endArrow";
;;  460:	 * See STYLE_ENDARROW.
;;  463:	public static String STYLE_STARTARROW = "startArrow";
;;  470:	public static String STYLE_ENDSIZE = "endSize";
;;  478:	public static String STYLE_STARTSIZE = "startSize";
;;  485:	public static String STYLE_SWIMLANE_LINE = "swimlaneLine";
;;  491:	public static String STYLE_ENDFILL = "endFill";
;;  497:	public static String STYLE_STARTFILL = "startFill";
;;  504:	public static String STYLE_DASHED = "dashed";
;;  516:	public static String STYLE_DASH_PATTERN = "dashPattern";
;;  525:	public static String STYLE_ROUNDED = "rounded";
;;  535:	public static String STYLE_ARCSIZE = "arcSize";
;;  543:	public static String STYLE_SOURCE_PERIMETER_SPACING = "sourcePerimeterSpacing";
;;  551:	public static String STYLE_TARGET_PERIMETER_SPACING = "targetPerimeterSpacing";
;;  561:	public static String STYLE_PERIMETER_SPACING = "perimeterSpacing";
;;  568:	public static String STYLE_SPACING = "spacing";
;;  575:	public static String STYLE_SPACING_TOP = "spacingTop";
;;  582:	public static String STYLE_SPACING_LEFT = "spacingLeft";
;;  589:	public static String STYLE_SPACING_BOTTOM = "spacingBottom";
;;  596:	public static String STYLE_SPACING_RIGHT = "spacingRight";
;;  601:	 * vertices. If the <code>STYLE_SHAPE</code> is <code>SHAPE_SWIMLANE</code>
;;  607:	public static String STYLE_HORIZONTAL = "horizontal";
;;  616:	public static String STYLE_DIRECTION = "direction";
;;  625:	public static String STYLE_ELBOW = "elbow";
;;  634:	public static String STYLE_FONTCOLOR = "fontColor";
;;  641:	public static String STYLE_FONTFAMILY = "fontFamily";
;;  647:	public static String STYLE_FONTSIZE = "fontSize";
;;  654:	public static String STYLE_FONTSTYLE = "fontStyle";
;;  660:	 * STYLE_RESIZABLE to disable manual sizing.
;;  662:	public static String STYLE_AUTOSIZE = "autosize";
;;  669:	public static String STYLE_FOLDABLE = "foldable";
;;  676:	public static String STYLE_EDITABLE = "editable";
;;  683:	public static String STYLE_BENDABLE = "bendable";
;;  690:	public static String STYLE_MOVABLE = "movable";
;;  697:	public static String STYLE_RESIZABLE = "resizable";
;;  704:	public static String STYLE_CLONEABLE = "cloneable";
;;  711:	public static String STYLE_DELETABLE = "deletable";
;;  718:	public static String STYLE_SHAPE = "shape";
;;  724:	public static String STYLE_EDGE = "edgeStyle";
;;  730:	public static String STYLE_LOOP = "loopStyle";
;;  737:	public static String STYLE_ROUTING_CENTER_X = "routingCenterX";
;;  744:	public static String STYLE_ROUTING_CENTER_Y = "routingCenterY";
