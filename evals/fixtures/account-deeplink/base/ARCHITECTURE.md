# Architecture

The platform route parser supplies an account identifier to both cold-start and warm-start handlers. Treat that identifier as untrusted, validate its canonical form, and enforce resource-level authorization independently of login state before opening account data.
