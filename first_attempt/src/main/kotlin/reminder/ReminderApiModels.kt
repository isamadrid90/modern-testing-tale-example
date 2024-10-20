package reminder

data class ReminderRequest(
    val vetEmail: String,
    val timeOffset: TimeOffset,
    val weekDay: String,
    val petName: String,
    val petBirthday: String,
    val ownerEmail: String,
    val reminderType: String,
    val message: String
)

data class TimeOffset(
    val amount: Int,
    val unit: String
)

