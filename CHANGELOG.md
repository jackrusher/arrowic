# Change Log
All notable changes to this project will be documented in this file. This change log follows the conventions of [keepachangelog.com](http://keepachangelog.com/).

## [Unreleased]

## [0.1.2] - 2023-12-22
### Added
- `graph-from-seqs` convenience function
### Changed
- moved to `deps.edn` from `project.clj`
- updated upstream deps
- improved default layout, especially edge placement

## [0.1.1] - 2018-12-01
### Fixed
- Fix use of `use` rather than `:use` that violated `ns` spec in
  Clojure `1.9`.

## [0.1.1-SNAPSHOT] - 2017-05-14
### Added
- edge and vertex style specifications
- `as-swing-component` to return an embeddable graph viewer for use
  with swing (and thus [seesaw](https://github.com/daveray/seesaw))
### Changed
- edge labels are now passed using the `:edge-label` style on edges
### Fixed
- Turn off broken interactive editing by default
- Use better layout method to avoid partially off-canvas labels

## 0.1.0 - 2017-02-23
- Initial version

[Unreleased]: https://github.com/your-name/arrowic/compare/0.1.1...HEAD
[0.1.1]: https://github.com/your-name/arrowic/compare/0.1.0...0.1.1
