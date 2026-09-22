# Architecture

Retryable work must use an operation identifier persisted with the work request. The app-provided `Ledger` implementation is durable across worker recreation and process death. Cancellation is control flow and must remain cancellation; only retryable transport failures map to retry.
