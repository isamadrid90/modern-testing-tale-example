package reminder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalDate

class ReminderDateTimeCalculatorTest {

    private val reminderDateTimeCalculator = ReminderDateTimeCalculator()
    
    @Test
    fun `should calculate future date matching given weekday`() {
        val currentDate = LocalDate.of(2023, 10, 1)
        val timeToAdd = 30
        val weekDay = DayOfWeek.MONDAY
        
        val calculatedDate = reminderDateTimeCalculator.calculateFutureDate(currentDate, timeToAdd, weekDay)
        
        assertEquals(DayOfWeek.MONDAY, calculatedDate.dayOfWeek)
        assertTrue(calculatedDate.isAfter(currentDate))
    }
}