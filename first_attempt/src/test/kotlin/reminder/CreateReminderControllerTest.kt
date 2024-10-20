import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity
import reminder.CreateReminderController
import reminder.Pet
import reminder.ReminderDateTimeCalculator
import reminder.ReminderRequest
import reminder.ReminderResponse
import reminder.ReminderService
import reminder.TimeOffset
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class CreateReminderControllerTest {

    @Test
    fun createReminder_shouldReturnCompleteResponseCorrectly() {
        val createReminderController = CreateReminderController(ReminderService(
            reminderRepository = mockk(relaxUnitFun = true),
            reminderDateTimeCalculator = ReminderDateTimeCalculator()
        ))
        val request = ReminderRequest(
            vetEmail = "vet@example.com",
            timeOffset = TimeOffset(amount = 2, unit = "months"),
            weekDay = "Monday",
            petName = "Buddy",
            petBirthday = "2018-04-23",
            ownerEmail = "owner@example.com",
            reminderType = "check-up",
            message = "Reminder for Buddy's regular check-up."
        )
        val response: ResponseEntity<ReminderResponse> = createReminderController.createReminder(request)

        // Compute the expected reminderDateTime
        var expectedReminderDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        expectedReminderDate = expectedReminderDate.plusMonths(2)
        while (expectedReminderDate.dayOfWeek != DayOfWeek.MONDAY) {
            expectedReminderDate = expectedReminderDate.plusDays(1)
        }
        val expectedReminderDateTime = expectedReminderDate.format(DateTimeFormatter.ISO_LOCAL_DATE)

        val expectedResponse = ReminderResponse(
            vetEmail = "vet@example.com",
            reminderDateTime = expectedReminderDateTime,
            pet = Pet(name = "Buddy", birthday = "2018-04-23", ownerEmail = "owner@example.com"),
            reminderType = "check-up",
            message = "Reminder for Buddy's regular check-up."
        )

        assertEquals(expectedResponse, response.body)
    }
}