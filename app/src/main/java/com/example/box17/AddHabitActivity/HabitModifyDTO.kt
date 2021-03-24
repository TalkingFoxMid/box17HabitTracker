package com.example.box17.AddHabitActivity

import java.io.Serializable

class HabitModifyDTO(var habitName: String,
                     var habitDescription: String,
                     var habitPriority: HabitsPriority,
                     var habitType: HabitTypeEnum,
                     var habitId: Int = 0,
                     var habitDayPeriod: Int = 0,
                     var habitRemain: Int = 0
) : Serializable {

}
