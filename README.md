# 'Arrowic

O frabjous day! Callooh! Callay! Yet another tiny, incomplete clojure
wrapper around a Java library!

In this case, the simplest
[directed graph](https://en.wikipedia.org/wiki/Directed_graph) viewer
possible for those situation where you are in the process of
interrogating some data and would like to how it looks as boxes and
arrows.

I love `graphviz` as much as the next former-AT&T Research employee,
but it's also rather nice to be able to generate an *interactive*
graph layout from _within_ `emacs` (or whatever your REPL of choice),
tinker with it by hand, then export it to SVG.

The default graph style looks like [this](example.svg). There is also
a rather large collection of styling options that are currently
undocumented. See the `arrowic.style` namespace to get some idea of
how to use styles.

If you’re building a Swing application (hopefully
using [seesaw](https://github.com/daveray/seesaw)), you can create a
Swing component to display your graph using the `as-swing-component`
function.

## Usage

I'm not currently pushing new versions of this library to Clojars, so
you should use it as a git dependency from `deps.edn`. Once you have
it in your project, require whichever parts of the public API you need:

``` clojure
(ns fancy-project
  (:require [arrowic.core :refer [create-graph graph-from-seqs insert-edge! insert-vertex! create-viewer]]))
```

The easiest way to get a quick graph from your data is probably
`graph-from-seqs`, to which you can pass a sequence of sequences of 2
or 3 items where the first two are nodes and the (optional) third is
an edge label. The return value of that function can be passed to
`create-viewer`, which will pop up a new window showing the graph:

``` clojure
(def viewer
  (create-viewer
   (graph-from-seqs [["Clojure" "Lisp" "is-a"]
                     ["Clojure" "functional programming" "supports-paradigm"]])))
```

If you would rather use the imperative API, a new empty graph can be
created using `create-graph`. The graph thus created can then be
mutated in place inside of a `with-graph` form using `insert-vertex!`
and `insert-edge!`.

``` clojure
(use '[arrowic.core])

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

;; you can re-use a viewer with a new graph like this:
(def another-graph (random-graph))

(view viewer another-graph)
```

It's also easy to export a graph as an SVG diagram:

``` clojure
(spit "example.svg" (as-svg another-graph))
```

## License

Copyright © 2018-2023 Jack Rusher

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
