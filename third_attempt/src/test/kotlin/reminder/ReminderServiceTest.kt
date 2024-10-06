import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import reminder.*
import java.time.LocalDate
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class ReminderServiceTest {

    @MockK
    private lateinit var reminderRepository: ReminderRepository

    @MockK
    private lateinit var reminderDateTimeCalculator: ReminderDateTimeCalculator

    private lateinit var reminderService: ReminderService

    @BeforeEach
    fun setUp() {
        reminderService = ReminderService(reminderRepository, reminderDateTimeCalculator)
    }

    @Test
    fun `should create reminder successfully`() {
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
            reminderType = ReminderType.VACCINATION,
            message = "Time for your pet's vaccination!",
            createdAt = LocalDateTime.now()
        )

        // Mock behavior
        every { reminderDateTimeCalculator.calculate(any()) } returns scheduledDate
        every { reminderRepository.save(any<Reminder>()) } returns reminder

        val result = reminderService.createReminder(request)

        assertNotNull(result)
        assertEquals(reminder, result)
        verify { reminderRepository.save(any<Reminder>()) }
    }
}