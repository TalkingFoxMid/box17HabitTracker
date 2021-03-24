package com.example.box17

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.box17.AddHabitActivity.AddHabitActivity
import com.example.box17.AddHabitActivity.HabitModifyDTO

class MainActivity : AppCompatActivity() {

    val habitList = mutableListOf<HabitItem>()
    var habitAdapter: HabitAdapter = HabitAdapter(habitList, this);
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recView = findViewById<RecyclerView>(R.id.recyclerView)

        recView.adapter = this.habitAdapter
        recView.layoutManager = LinearLayoutManager(this)
        recView.setHasFixedSize(true)
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recView.adapter as HabitAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                adapter.notifyDataSetChanged()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recView)




    }
    fun addElement(view: View) {
        val int = Intent(this, AddHabitActivity::class.java)
        startActivityForResult(int, 0)

    }
    fun updateAdapter() {
        this.habitList.sortBy { x -> x.habitPriority }
        this.habitAdapter.notifyDataSetChanged()
    }
    fun addNewHabit(habitCreateDTO: HabitModifyDTO) {
        this.habitList.add(HabitItem(habitCreateDTO))
        updateAdapter()

    }
    fun editHabit(habitCreateDTO: HabitModifyDTO) {
        this.habitAdapter.updateItem(habitCreateDTO)
        updateAdapter()
    }
    fun openHabitForEdit(id: Int) {
        val int = Intent(this, AddHabitActivity::class.java)
        int.putExtra("requestCode", 1);
        int.putExtra("dto", habitList[id].getDto(id))
        startActivityForResult(int, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var str = data?.getSerializableExtra("value") as? HabitModifyDTO

        when (resultCode) {
            0 -> if (str != null) addNewHabit(str)
            1 -> if (str != null) editHabit (str)
        }

    }

}