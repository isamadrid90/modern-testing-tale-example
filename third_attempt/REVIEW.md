REVIEW
======

At the beginning the test didn't compile, it was fixed manually.

I removed this code from ReminderServiceTest, the function returns unit not a reminder
```kotlin
every { reminderRepository.save(any<Reminder>()) } returns reminder
```
The ReminderType was not correct 
```kotlin
 reminderType = ReminderType.VACCINATION
```

The test compiled but it failed

The annotations is correct but it's missing the @ExtendWith(MockKExtension::class) to make it work 
```kotlin
@MockK
    private lateinit var reminderService: ReminderService
```
The assert in CreateReminderControllerTest is not correct, the implementation returns a 200 not a 201

```kotlin
    assertEquals(HttpStatus.CREATED, response.statusCode)
```
Also the following assert is not correct, it's trying to compare two different things, the reminder a Reminder type
while the response.body is a ReminderResponse type.

```kotlin
    assertEquals(objectMapper.writeValueAsString(reminder), objectMapper.writeValueAsString(response.body))
```

The asserts in ReminderServiceTest are not very useful, just the second assert is enough

```kotlin
    assertNotNull(result)
    assertEquals(reminder, result)
```
And with this assert we're not ensuring that we save the correct information

```kotlin
    verify { reminderRepository.save(any<Reminder>()) }
```

Also the organization of the code make it confusing because it doesnt separate correctly the expectations

The reminder id is not correctly generated, it's a UUID and it's generated using the random function, to make ir specific we need to mock also that function

```
id = "1",
```

As it's using LocalDateTime.now, the createdAt it's never correct in the assert

```kotlin
 createdAt = LocalDateTime.now()
```

FIRST
====

Fast - Yes
Isolated - Yes
Repeatable - No, we're using LocalDateTime.now so on every execution the result will change
Self-validating - Yes
Timely - No, we did it when we already had the code

TEST DESIDERATA
======
Behaviour - No, I think the tests for the failures are not covered any behaviour
Structure insensitive - No, from the test we know how we're using the collaborators, if the code is refactored it could fail, better not to mock the ReminderDateTimeCalculator
Readable - It could be improved
Easy to write - Yes
Fast - Yes
Deterministic - No, we're still using the LocalDateTime.now
Automated - Yes
Isolated - Yes
Composable ??
Specific - Yes
Predict production - No, no error was tested, this separation os tests is better but they're incomplete
Inspiring - No
