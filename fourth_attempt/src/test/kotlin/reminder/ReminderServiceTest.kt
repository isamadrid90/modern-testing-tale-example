package reminder

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import reminder.ReminderDateTimeCalculator
import reminder.ReminderService

class ReminderServiceTest {

    private val reminderDateTimeCalculator: ReminderDateTimeCalculator = mockk()
    private val reminderService = ReminderService(reminderDateTimeCalculator)

    @Test
    fun `should calculate correct reminder date`() {
        val vetEmail = "vet@example.com"
        val timeToAdd = "30"
        val weekDay = "Monday"
        val petName = "Buddy"
        val birthday = "2020-01-01"
        val ownerEmail = "owner@example.com"
        val reminderType = "Check-up"
        val message = "Time for the annual check-up!"

        every { reminderDateTimeCalculator.calculateFutureDate(any(), any(), any()) } returns LocalDate.of(2023, 11, 1)

        val reminder = reminderService.createReminder(CreateReminderRequest(
            vetEmail, timeToAdd, weekDay, petName, birthday, ownerEmail, reminderType, message
        ))

        Assertions.assertNotNull(reminder)
        Assertions.assertEquals("Buddy", reminder.petName)
        Assertions.assertEquals("vet@example.com", reminder.vetEmail)

        verify(exactly = 1) { reminderDateTimeCalculator.calculateFutureDate(any(), any(), any()) }
    }

    @Test
    fun `should throw exception for invalid date format`() {
        val vetEmail = "vet@example.com"
        val timeToAdd = "invalid"
        val weekDay = "Monday"
        val petName = "Buddy"
        val birthday = "2020-01-01"
        val ownerEmail = "owner@example.com"
        val reminderType = "Check-up"
        val message = "Time for the annual check-up!"

        every { reminderDateTimeCalculator.calculateFutureDate(any(), any(), any()) } throws IllegalArgumentException("Invalid date format")

        val exception: IllegalArgumentException = Assertions.assertThrows(IllegalArgumentException::class.java) {
            reminderService.createReminder(
                CreateReminderRequest(
                    vetEmail, timeToAdd, weekDay, petName, birthday, ownerEmail, reminderType, message
                )
            )
        }

        Assertions.assertEquals("Invalid date format", exception.message)

        verify(exactly = 1) { reminderDateTimeCalculator.calculateFutureDate(any(), any(), any()) }
    }
}