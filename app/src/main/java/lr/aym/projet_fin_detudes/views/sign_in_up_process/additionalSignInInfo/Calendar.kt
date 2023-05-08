package lr.aym.projet_fin_detudes.views.sign_in_up_process.additionalSignInInfo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.maxkeppeker.sheets.core.models.base.SheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyCalendar(
    calendarState: SheetState,
    date: MutableState<String>
) {
    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date {
            date.value = it.toString()
        },
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            maxYear = LocalDate.now().year,
        )
    )
}