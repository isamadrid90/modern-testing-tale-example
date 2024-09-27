import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import reminder.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CreateReminderControllerTest {

    @Test
    fun `createReminder should return complete response correctly`() {
        val reminderService: ReminderService = mockk(relaxUnitFun = true)
        val createReminderController: CreateReminderController = CreateReminderController(reminderService)

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
            reminderType = ReminderType.FOLLOW_UP,
            message = "Vet appointment for Buddy",
            createdAt = LocalDateTime.now()
        )

        val expectedResponse = with(reminder) {
            ReminderResponse(
                vetEmail = vetEmail,
                reminderDateTime = scheduledDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
                pet = Pet(name = petName, birthday = petBirthday.toString(), ownerEmail = ownerEmail),
                reminderType = reminderType.type,
                message = message
            )
        }

        every { reminderService.createReminder(any()) } returns reminder

        val response: ResponseEntity<Any> = createReminderController.createReminder(request)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(expectedResponse, response.body)
        verify { reminderService.createReminder(request) }
    }

    @Test
    fun `createReminder should handle invalid date format`() {
        val reminderService = mockk<ReminderService>()
        val controller = CreateReminderController(reminderService)
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

        every { reminderService.createReminder(any()) } throws InvalidPetBirthdateException("Invalid pet birthdate format")


        val response: ResponseEntity<Any> = controller.createReminder(request)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    fun `createReminder should handle missing required field`() {
        val reminderService: ReminderService = mockk(relaxUnitFun = true)
        val createReminderController: CreateReminderController = CreateReminderController(reminderService)

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
        val reminderService: ReminderService = mockk(relaxUnitFun = true)
        val createReminderController: CreateReminderController = CreateReminderController(reminderService)

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