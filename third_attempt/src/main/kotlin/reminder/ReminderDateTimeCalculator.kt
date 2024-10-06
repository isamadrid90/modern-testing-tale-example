package reminder

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters


class ReminderDateTimeCalculator {

    fun calculateReminderDateTime(request: ReminderRequest): LocalDateTime {
        // Compute the reminder date-time based on the current date and the time offset
        var reminderDate = LocalDate.now().atStartOfDay()
        reminderDate = when (request.timeOffset.unit) {
            "days" -> reminderDate.plusDays(request.timeOffset.amount.toLong())
            "months" -> reminderDate.plusMonths(request.timeOffset.amount.toLong())
            else -> reminderDate
        }

        // Adjust to the specified weekday
        reminderDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.valueOf(request.weekDay.uppercase())))
        return reminderDate
    }
}