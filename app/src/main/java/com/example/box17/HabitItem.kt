package com.example.box17

import com.example.box17.AddHabitActivity.HabitModifyDTO
import com.example.box17.AddHabitActivity.HabitTypeEnum
import com.example.box17.AddHabitActivity.HabitsPriority

class HabitItem {
    var habitName: String
    var habitDescription: String = "default description"
    var habitType: HabitTypeEnum = HabitTypeEnum.GOOD
    var habitPriority: HabitsPriority = HabitsPriority.UNDEFINED
    var habitDayPeriod: Int = 0
    var habitRemain: Int = 0
    constructor(habitName: String) {
        this.habitName = habitName
    }
    constructor(habitCreateDTO: HabitModifyDTO) {
        this.habitName = habitCreateDTO.habitName
        this.habitDescription = habitCreateDTO.habitDescription
        this.habitPriority = habitCreateDTO.habitPriority
        this.habitType = habitCreateDTO.habitType
        this.habitDayPeriod = habitCreateDTO.habitDayPeriod
        this.habitRemain = habitCreateDTO.habitRemain
    }
    fun getDto(position: Int):HabitModifyDTO {
        var dto = HabitModifyDTO(habitName, habitDescription, habitPriority, habitType, position,
            habitDayPeriod, habitRemain)
        return dto
    }
}