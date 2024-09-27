REVIEW
======

In Reminder we need a ReminderType, in the response we use a string,
the solution has mixed both, we'll fix it manually because I'm sick of discussing

```kotlin
every { reminderService.createReminder(any()) } throws IllegalArgumentException("Invalid date format")

val exception = Assertions.assertThrows(IllegalArgumentException::class.java) {
    createReminderController.createReminder(request)
}

Assertions.assertEquals("Invalid date format", exception.message)
```
is it actually testing anything?

FIRST
====

Fast - Yes
Isolated - Meh 
Repeatable - Yes, I mean, we're testing that a mock works, it's 100% repetable
Self-validating - Yes
Timely - Actually yes, because the failures are not implemented yet, although the tests passes

TEST DESIDERATA
Behaviour - No, I think the tests for the failures are not covered any behaviour
Structure insensitive - No, the tests are checking directly the message of the exceptions
Readable - Yes
Easy to write - Yes
Fast - Yes
Deterministic - No, we're still using the LocalDateTime.now


Automated - Yes
Isolated - Yes
Composable ??
Specific - Yes
Predict production - No, they are better than the previous tests but not all the options are covered
Inspiring - Not really

The approach to improve those tests was trying to obtain all the responses from the AI Assistant.
It became tedious and quite boring
The immediate test result was better than the previous, but it's still not covering all the cases and
the tests was worst that the first time