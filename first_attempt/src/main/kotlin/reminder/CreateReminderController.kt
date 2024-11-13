package reminder

import java.time.format.DateTimeFormatter


import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.format.DateTimeParseException

@RestController
class CreateReminderController(private val reminderService: ReminderService) {

    @PostMapping("/api/reminders")
    fun createReminder(@RequestBody request: ReminderRequest): ResponseEntity<Any> {
        return try {
            validateRequest(request)
            val reminder: Reminder = reminderService.createReminder(request)
            val response = with(reminder) {
                ReminderResponse(
                    vetEmail = vetEmail,
                    reminderDateTime = scheduledDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
                    pet = Pet(name = petName, birthday = petBirthday.toString(), ownerEmail = ownerEmail),
                    reminderType = reminderType.type,
                    message = message
                )
            }
            ResponseEntity(response, HttpStatus.OK)
        } catch (e: InvalidPetBirthdateException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        } catch (e: IllegalArgumentException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    private fun validateRequest(request: ReminderRequest) {
        try {
            LocalDate.parse(request.petBirthday, DateTimeFormatter.ISO_LOCAL_DATE)
        } catch (e: DateTimeParseException) {
            throw InvalidPetBirthdateException("Invalid pet birthdate format")
        }
    }
}

data class ReminderResponse(
    val vetEmail: String,
    val reminderDateTime: String,
    val pet: Pet,
    val reminderType: String,
    val message: String
)

data class Pet(
    val name: String,
    val birthday: String,
    val ownerEmail: String
)