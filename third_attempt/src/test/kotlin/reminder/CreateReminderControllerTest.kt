import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import reminder.*
import java.time.LocalDate
import java.time.LocalDateTime

class CreateReminderControllerTest {

    @MockK
    private lateinit var reminderService: ReminderService

    private lateinit var createReminderController: CreateReminderController

    @BeforeEach
    fun setUp() {
        createReminderController = CreateReminderController(reminderService)
    }

    @Test
    fun `should create reminder and return created status`() {
        val request = ReminderRequest(
            vetEmail = "vet@example.com",
            timeOffset = TimeOffset(amount = 2, unit = "days"),
            weekDay = "Monday",
            petName = "Buddy",
            petBirthday = "2022-08-20",
            ownerEmail = "owner@example.com",
            reminderType = "Vaccination",
            message = "Time for your pet's vaccination!"
        )

        val scheduledDate = LocalDateTime.now().plusDays(2)
        val reminder = Reminder(
            id = "1",
            vetEmail = "vet@example.com",
            scheduledDate = scheduledDate,
            petName = "Buddy",
            petBirthday = LocalDate.parse("2022-08-20"),
            ownerEmail = "owner@example.com",
            reminderType = ReminderType.CHECK_UP,
            message = "Time for your pet's vaccination!",
            createdAt = LocalDateTime.now()
        )

        val objectMapper = ObjectMapper()

        // Mock behavior
        every { reminderService.createReminder(any<ReminderRequest>()) } returns reminder

        val response: ResponseEntity<Any> = createReminderController.createReminder(request)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(objectMapper.writeValueAsString(reminder), objectMapper.writeValueAsString(response.body))
    }
}