package reminder

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class Reminder(
    val id: String,
    val vetEmail: String,
    val scheduledDate: LocalDateTime,
    val petName: String,
    val petBirthday: LocalDate,
    val ownerEmail: String,
    val reminderType: ReminderType,
    val message: String,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(
            vetEmail: String,
            scheduledDate: LocalDateTime,
            petName: String,
            petBirthday: LocalDate,
            ownerEmail: String,
            reminderType: String,
            message: String,
            createdAt: LocalDateTime
        ): Reminder {
            return Reminder(
                UUID.randomUUID().toString(),
                vetEmail,
                scheduledDate,
                petName,
                petBirthday,
                ownerEmail,
                ReminderType.fromValue(reminderType),
                message,
                createdAt
            )
        }
    }
}


enum class ReminderType(val type: String) {
    TREAT_FOR_PARASITES("treat for parasites"),
    FOLLOW_UP("follow-up"),
    CHECK_UP("check-up"),
    SCREENING("screening");

    companion object {
        fun fromValue(value: String): ReminderType {
            return entries.find { it.type == value }
                ?: throw IllegalArgumentException("Invalid reminder type: $value")
        }
    }

}