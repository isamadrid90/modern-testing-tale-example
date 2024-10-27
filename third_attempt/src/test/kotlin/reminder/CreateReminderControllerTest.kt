package reminder

import io.mockk.every
import io.mockk.mockk

import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class CreateReminderControllerTest {
    
    private val reminderService: ReminderService = mockk()
    private val createReminderController = CreateReminderController(reminderService)
    
    @Test
    fun `should create a reminder successfully`() {
        val request = CreateReminderRequest(
            vetEmail = "vet@example.com",
            timeToAdd = "30",
            weekDay = "Monday",
            petName = "Buddy",
            birthday = "2020-01-01",
            ownerEmail = "owner@example.com",
            reminderType = "Check-up",
            message = "Time for the annual check-up!"
        )
        
        every { reminderService.createReminder(any()) } returns ReminderResponse(
            success = true,
            message = "Reminder created successfully"
        )
        
        val response: ResponseEntity<ReminderResponse> = createReminderController.createReminder(request)
        
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("Reminder created successfully", response.body?.message)
        
        verify(exactly = 1) { reminderService.createReminder(any()) }
    }

    @Test
    fun `should handle invalid date format exception`() {
        val request = CreateReminderRequest(
            vetEmail = "vet@example.com",
            timeToAdd = "invalid",
            weekDay = "Monday",
            petName = "Buddy",
            birthday = "2020-01-01",
            ownerEmail = "owner@example.com",
            reminderType = "Check-up",
            message = "Time for the annual check-up!"
        )

        every { reminderService.createReminder(any()) } throws IllegalArgumentException("Invalid date format")

        val exception: IllegalArgumentException = assertThrows(IllegalArgumentException::class.java) {
            createReminderController.createReminder(request)
        }

        assertEquals("Invalid date format", exception.message)
        
        verify(exactly = 1) { reminderService.createReminder(any()) }
    }
}