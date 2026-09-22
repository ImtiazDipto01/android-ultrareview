# Order upload PR intent

Retries for one order must reuse a stable idempotency key. Cancellation must remain cancellation rather than becoming an ordinary retry. Once an order is acknowledged, repeated worker delivery must not upload it again.
