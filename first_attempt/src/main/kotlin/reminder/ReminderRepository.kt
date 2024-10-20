package reminder


interface ReminderRepository {
    fun save(reminder: Reminder)
}