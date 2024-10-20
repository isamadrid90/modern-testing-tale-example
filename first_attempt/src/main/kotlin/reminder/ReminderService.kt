package reminder

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class ReminderService(
    private val reminderRepository: ReminderRepository,
    private val reminderDateTimeCalculator: ReminderDateTimeCalculator
) {

    fun createReminder(reminderRequest: ReminderRequest): Reminder {
        val reminder = Reminder.from(
            vetEmail = reminderRequest.vetEmail,
            scheduledDate = reminderDateTimeCalculator.calculateReminderDateTime(reminderRequest),
            petName = reminderRequest.petName,
            petBirthday = LocalDate.parse(reminderRequest.petBirthday),
            ownerEmail = reminderRequest.ownerEmail,
            reminderType = reminderRequest.reminderType,
            message = reminderRequest.message,
            createdAt = LocalDateTime.now()
        )

        reminderRepository.save(reminder)

        return reminder
    }
}