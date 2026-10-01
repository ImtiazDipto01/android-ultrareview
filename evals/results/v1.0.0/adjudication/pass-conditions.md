1. **Profile setup — WITHDRAW AS OVER-BROAD**

   The accepted `FEATURE.md` bounds duplicate suppression to “while one is in flight.” The request’s “rapid repeated submission” is consistent with that boundary; neither it nor `ARCHITECTURE.md` establishes a permanently single-use controller.

   At repaired head `3ec59ed`, an in-flight reentrant `submit` returns immediately because `saving` is true. After successful return, `finally` has cleared `saving`, so a later call does save again. That second save is concretely representable, but rejecting this **post-completion invocation** exceeds the retained in-flight duplicate-submission defect. Assigning `navigated = true` again also does not demonstrate a second actual navigation in this model.

   **Smallest corrected B-condition:** “A second submission while the first save is in flight causes no additional save or completion/navigation; the original submission completes/navigates only after its save succeeds.” Preserve failure retryability. Keep the existing B ID and explicitly withdraw the post-success clause as a reviewer rubric correction.

2. **Order upload — RETAIN AS BLOCKING**

   `requests/order-upload.md` requires cancellation to remain cancellation. The accepted `FEATURE.md` requires cancellation to propagate to the scheduler, and `ARCHITECTURE.md` states that only retryable transport failures map to retry. None restricts cancellation to `InterruptedException`.

   At repaired head `c802b55`, take an unacknowledged order and a `Gateway.upload` implementation that throws `java.util.concurrent.CancellationException`. This fits the modeled interface. It bypasses `catch (InterruptedException)`, enters `catch (Exception)`, and returns `RETRY`. The acknowledgement remains unset. The scheduler therefore receives an ordinary retry result for cancellation.

   This is represented by the retained cancellation-swallowing defect: the original broad catch already caused this outcome, and the repair exempts only interruption. The existing interruption check establishes one cancellation case; it does not narrow the governing contract. No new lifecycle assumption or integration is needed, and no B-condition correction is warranted.

Both dispositions apply the isolated skill’s [bounded-condition rule](../../../../references/scoring-and-severity.md) and [reviewer-correction rule](../../../../references/rereview.md): path locking preserves supported requirements but cannot preserve a false blocker.

Evidence is static inspection of both complete local histories, diffs, implementations, checks, and supplied authority. No fixture code was executed or files modified. All supplied local skill references were read; the linked `worked-example.md` is absent from the packet.
