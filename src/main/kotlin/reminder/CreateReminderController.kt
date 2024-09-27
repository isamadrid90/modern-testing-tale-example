package reminder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.format.DateTimeFormatter

@RestController
class CreateReminderController(val reminderService: ReminderService) {

    @PostMapping("/api/reminders")
    fun createReminder(@RequestBody request: ReminderRequest): ResponseEntity<ReminderResponse> {
        val reminder = reminderService.createReminder(request)

        // Create the response object
        val response = with(reminder) {
            ReminderResponse(
            vetEmail = vetEmail,
            reminderDateTime = scheduledDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
            pet = Pet(name = petName, birthday = petBirthday.format(DateTimeFormatter.ISO_LOCAL_DATE), ownerEmail = ownerEmail),
            reminderType = reminderType.type,
            message = message
        )
        } 

        return ResponseEntity.ok(response)
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