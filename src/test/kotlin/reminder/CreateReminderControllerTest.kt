import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import reminder.CreateReminderController
import reminder.ReminderRequest
import reminder.ReminderResponse
import reminder.ReminderService
import reminder.Reminder
import reminder.TimeOffset
import reminder.Pet
import reminder.ReminderType
import java.time.LocalDate
import java.time.LocalDateTime

class CreateReminderControllerTest {

    private val reminderService: ReminderService = mockk(relaxUnitFun = true)
    private val createReminderController: CreateReminderController = CreateReminderController(reminderService)

    @Test
    fun `createReminder should return complete response correctly`() {
        val request = ReminderRequest(
            vetEmail = "vet@example.com",
            timeOffset = TimeOffset(amount = 1, unit = "DAYS"),
            weekDay = "Monday",
            petName = "Buddy",
            petBirthday = "2020-01-01",
            ownerEmail = "owner@example.com",
            reminderType = "FOLLOW_UP",
            message = "Vet appointment for Buddy"
        )

        val reminder = Reminder(
            id = "1",
            vetEmail = "vet@example.com",
            scheduledDate = LocalDateTime.of(2023, 10, 14, 10, 0),
            petName = "Buddy",
            petBirthday = LocalDate.parse("2020-01-01"),
            ownerEmail = "owner@example.com",
            reminderType = ReminderType.FOLLOW_UP.type,
            message = "Vet appointment for Buddy",
            createdAt = LocalDateTime.now()
        )

        val expectedResponse = ReminderResponse(
            vetEmail = reminder.vetEmail,
            reminderDateTime = reminder.scheduledDate.toString(),
            pet = Pet(name = reminder.petName, birthday = reminder.petBirthday.toString(), ownerEmail = reminder.ownerEmail),
            reminderType = reminder.reminderType,
            message = reminder.message
        )

        every { reminderService.createReminder(any()) } returns reminder

        val response: ResponseEntity<ReminderResponse> = createReminderController.createReminder(request)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(expectedResponse, response.body)
        verify { reminderService.createReminder(request) }
    }

    @Test
    fun `createReminder should handle invalid date format`() {
        val request = ReminderRequest(
            vetEmail = "vet@example.com",
            timeOffset = TimeOffset(amount = 1, unit = "DAYS"),
            weekDay = "Monday",
            petName = "Buddy",
            petBirthday = "invalid-date-format",
            ownerEmail = "owner@example.com",
            reminderType = "FOLLOW_UP",
            message = "Vet appointment for Buddy"
        )
        every { reminderService.createReminder(any()) } throws IllegalArgumentException("Invalid date format")

        val exception = Assertions.assertThrows(IllegalArgumentException::class.java) {
            createReminderController.createReminder(request)
        }

        Assertions.assertEquals("Invalid date format", exception.message)
    }

    @Test
    fun `createReminder should handle missing required field`() {
        val request = ReminderRequest(
            vetEmail = "vet@example.com",
            timeOffset = TimeOffset(amount = 1, unit = "DAYS"),
            weekDay = "Monday",
            petName = "Buddy",
            petBirthday = "2020-01-01",
            ownerEmail = "owner@example.com",
            reminderType = "FOLLOW_UP",
            message = "" // Missing required field
        )
        every { reminderService.createReminder(any()) } throws IllegalArgumentException("Missing required field")

        val exception = Assertions.assertThrows(IllegalArgumentException::class.java) {
            createReminderController.createReminder(request)
        }

        Assertions.assertEquals("Missing required field", exception.message)
    }

    @Test
    fun `createReminder should handle invalid time offset`() {
        val request = ReminderRequest(
            vetEmail = "vet@example.com",
            timeOffset = TimeOffset(amount = -1, unit = "DAYS"), // Invalid offset
            weekDay = "Monday",
            petName = "Buddy",
            petBirthday = "2020-01-01",
            ownerEmail = "owner@example.com",
            reminderType = "FOLLOW_UP",
            message = "Vet appointment for Buddy"
        )
        every { reminderService.createReminder(any()) } throws IllegalArgumentException("Invalid time offset")

        val exception = Assertions.assertThrows(IllegalArgumentException::class.java) {
            createReminderController.createReminder(request)
        }

        Assertions.assertEquals("Invalid time offset", exception.message)
    }
}