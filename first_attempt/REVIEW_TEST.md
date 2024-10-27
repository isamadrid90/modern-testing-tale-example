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
What would happen if a controller throws an exception? The response will be 500 or another status code not just throwing the exception

FIRST
====

Fast - Yes
Isolated - Meh 
Repeatable - No, we'll still have LocalDateTime.now() inside createdAt
Self-validating - Yes
Timely - Actually yes, because the failures are not implemented yet, although the tests passes

TEST DESIDERATA
=======
Behaviour - No, the situation described in the errors is not really useful because springboot managed all the exceptions inside a controller returning a 500
Structure insensitive - No, the tests are checking directly the message of the exceptions
Readable - Yes
Easy to write - Yes
Fast - Yes
Deterministic - No, we're still using the LocalDateTime.now
Automated - Yes
Isolated - Yes
Composable - Yes
Specific - Yes
Predict production - No, they are better than the previous tests but not all the options are covered
Inspiring - Not really

The approach to improve those tests was trying to obtain all the responses from the AI Assistant.
It became tedious and quite boring
The immediate test result was better than the previous, but it's still not covering all the cases and
the tests were worst that the first time