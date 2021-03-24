package com.example.box17.AddHabitActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.box17.R

class AddHabitActivity : AppCompatActivity() {
    var errorFunctions = listOf<(String) -> Boolean>(
            { str: String -> setErrorToEditTextView(habitName, "Habit must have a name", str)},
            { str: String -> setErrorToEditTextView(habitDescription, "Habit must have a description.", str)},
            { str: String -> setErrorToEditTextView(remainEdit, "Habit must have a count.", str)},
            { str: String -> setErrorToEditTextView(frequencyEdit, "Habit must have a period.", str)}
    )
    lateinit var spinnerAdapter: ArrayAdapter<CharSequence>
    lateinit var spinner: Spinner
    lateinit var habitName: EditText
    lateinit var habitDescription: EditText
    lateinit var radioGroup: RadioGroup
    lateinit var remainEdit: EditText
    lateinit var frequencyEdit: EditText
    var habitId: Int = -1
    lateinit var myButton: Button
    var request: Int? = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_habit)
        request = intent.getIntExtra("requestCode", 0)
        frequencyEdit = findViewById(R.id.frequencyEdit)
        remainEdit = findViewById(R.id.remainEdit)
        habitName = findViewById(R.id.habitNameEdit)
        myButton = findViewById(R.id.myButton)
        habitDescription = findViewById(R.id.habitDescriptionEdit)
        spinner = findViewById(R.id.spinner3)
        radioGroup = findViewById(R.id.radioGroupCases)
        var adapter = ArrayAdapter.createFromResource(
            this,
            R.array.priorities,
            android.R.layout.simple_spinner_item
        )
        changeButtonTextDependingOnRequestCode()
        this.spinnerAdapter = adapter
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        this.radioGroup.check(getRadioButtonNumberFromType(HabitTypeEnum.GOOD))
        if(request == 1) {
            var habitModifyDTO = intent.getSerializableExtra("dto") as HabitModifyDTO
            this.habitName.setText(habitModifyDTO.habitName)
            this.habitId = habitModifyDTO.habitId
            this.remainEdit.setText(habitModifyDTO.habitRemain.toString())
            this.frequencyEdit.setText(habitModifyDTO.habitDayPeriod.toString())
            this.habitDescription.setText(habitModifyDTO.habitDescription)
            this.radioGroup.check(getRadioButtonNumberFromType(habitModifyDTO.habitType))
            this.spinner.setSelection(getSpinnerSelectionFromPriority(habitModifyDTO.habitPriority))
        }
    }
    fun setErrorToEditTextView(editText: EditText, message: String, content: String): Boolean {
        if (content.isEmpty()) {
            editText.setError(message)
        }
        return content.isNotEmpty()
    }
    fun getSpinnerSelectionFromPriority(priority: HabitsPriority): Int {
        return when (priority) {
            HabitsPriority.LOW -> 0
            HabitsPriority.MEDIUM -> 1
            HabitsPriority.HIGH -> 2
            else -> 0
        }
    }
    fun changeButtonTextDependingOnRequestCode() {
        this.myButton.text = when (this.request) {
            0 -> "ADD"
            1 -> "MODIFY"
            else -> "???"
        }
    }
    fun getRadioButtonNumberFromType(habitTypeEnum: HabitTypeEnum): Int {
        return when (habitTypeEnum) {
            HabitTypeEnum.BAD -> R.id.badHabbitRadioButton
            HabitTypeEnum.GOOD -> R.id.goodHabbitRadioButton
        }
    }
    fun getEnumFromString(string: String?): HabitsPriority {
        return when (string) {
            "High" -> HabitsPriority.HIGH
            "Medium" -> HabitsPriority.MEDIUM
            "Low" -> HabitsPriority.LOW
            else -> HabitsPriority.LOW
        }
    }
    fun getHabitTypeFromString(string: String?) : HabitTypeEnum {
        return when(string) {
            "Good" -> HabitTypeEnum.GOOD
            "Bad" -> HabitTypeEnum.BAD
            else -> HabitTypeEnum.GOOD
        }
    }
    fun isNumber(value: String): Boolean {
        return true
    }
    fun onClick(view: View) {
        var description: String = habitDescription.text.toString()
        var name: String = habitName.text.toString()
        var period: String = frequencyEdit.text.toString()
        var remain: String = remainEdit.text.toString()
        var allFieldsAreOk = errorFunctions.zip(listOf(name, description, period, remain))
                .map { it.first(it.second) }
                .fold(true, {x, y -> x && y})
        if (!allFieldsAreOk)
            return
        var intent = Intent().apply {
            putExtra("value",
                    HabitModifyDTO(name,
                    description,
                    getEnumFromString(spinnerAdapter.getItem(spinner.selectedItemPosition).toString()),
                    getHabitTypeFromString(findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString()),
                            habitId, period.toInt(),remain.toInt()

            ))
        }
        setResult(this.request ?: 0, intent)
        finish()
    }

}