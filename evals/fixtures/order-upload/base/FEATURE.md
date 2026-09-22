# Order upload

Upload a pending order using one stable idempotency key across worker recreation and retry. A transient transport failure returns retry, success returns success, and cancellation propagates to the scheduler.

