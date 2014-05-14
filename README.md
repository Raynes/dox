# dox

dox is a leiningen plugin for updating `gh-pages` with new documentation, and
also creating it if it doesn't exist.

## Usage

First of all, you'll want some kind of documentation generator. My favorite is
[codox](https://github.com/weavejester/codox). The thing that matters is where
the documentation files are put after being generated. You'll want to set that
in `project.clj`:

```clojure
:dox {:in "somedocs/"}
```

This tells it to look in `somedocs/` for the documentation files. The default is
`doc/` which also happens to be codox's default!

Once all that's fine and dandy, just generate your docs and run `dox`:

```bash
$ lein do doc, dox
```

This will switch to (or create) the gh-pages branch, remove everything, add
these docs, commit, and then switch back. Pretty simple, eh?

## License

Copyright © 2014 Anthony Grimes

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
