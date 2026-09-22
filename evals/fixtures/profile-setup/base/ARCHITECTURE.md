# Architecture

UI coordinators may navigate only after required persistence succeeds. Every asynchronous terminal path must clear transient loading state. Permission denial is an ordinary terminal result, not an exceptional state.

