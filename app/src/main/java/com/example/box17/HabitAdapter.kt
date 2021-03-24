package com.example.box17

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.box17.AddHabitActivity.HabitModifyDTO
import com.example.box17.AddHabitActivity.HabitTypeEnum
import com.example.box17.AddHabitActivity.HabitsPriority
import org.w3c.dom.Text

class HabitAdapter(var habits: MutableList<HabitItem>, var habitActivity: MainActivity) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>()
{
    var adapter: HabitAdapter = this

    class HabitViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


        val habitName = itemView.findViewById<TextView>(R.id.habitNameCard)
        val habitDescription = itemView.findViewById<TextView>(R.id.habitDescriptionCard)
//        val habitPriority = itemView.findViewById<TextView>(R.id.habitPriority)
        val emojiImageView: ImageView = itemView.findViewById<ImageView>(R.id.imageView)
        val star1: ImageView = itemView.findViewById(R.id.star1)
        val star2: ImageView = itemView.findViewById(R.id.star2)
        val star3: ImageView = itemView.findViewById(R.id.star3)
        val star4: ImageView = itemView.findViewById(R.id.star4)
        val star5: ImageView = itemView.findViewById(R.id.star5)
        val frequencyCard: TextView = itemView.findViewById(R.id.frequencyCard)
        val remainCard: TextView = itemView.findViewById(R.id.remainCard)


    }
    fun removeAt(value: Int) {
        this.habits.removeAt(value)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.habit,parent,false)
        return HabitViewHolder(view);
    }
    fun updateItem(habitModifyDTO: HabitModifyDTO) {
        val id = habitModifyDTO.habitId
        habits[id].habitDescription = habitModifyDTO.habitDescription
        habits[id].habitName = habitModifyDTO.habitName
        habits[id].habitType = habitModifyDTO.habitType
        habits[id].habitPriority = habitModifyDTO.habitPriority

    }
    override fun getItemCount(): Int {
        return habits.size
    }

    fun getDrawableFromEnum(type: HabitTypeEnum): Int {
        return when(type) {
            HabitTypeEnum.GOOD -> R.drawable.ic_angel
            HabitTypeEnum.BAD -> R.drawable.ic_demon
            else -> R.drawable.ic__f976
        }
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            habitActivity.openHabitForEdit(position)
            adapter.notifyDataSetChanged()
        }
        holder.habitName.text = habits[position].habitName;
        holder.habitDescription.text = habits[position].habitDescription
        holder.frequencyCard.text = "Every ${habits[position].habitDayPeriod.toString()} days."
        holder.remainCard.text = "Remain: ${habits[position].habitRemain}"

//        holder.habitPriority.text = habits[position].habitPriority.toString()
        holder.emojiImageView.setImageResource(getDrawableFromEnum(habits[position].habitType))
        when (habits[position].habitPriority) {
            HabitsPriority.HIGH -> {
                holder.star1.visibility = View.VISIBLE;
                holder.star2.visibility = View.VISIBLE;
                holder.star3.visibility = View.VISIBLE
                holder.star4.visibility = View.INVISIBLE
                holder.star5.visibility = View.INVISIBLE
            }
            HabitsPriority.MEDIUM -> {
                holder.star1.visibility = View.INVISIBLE;
                holder.star2.visibility = View.INVISIBLE;
                holder.star3.visibility = View.INVISIBLE
                holder.star4.visibility = View.VISIBLE
                holder.star5.visibility = View.VISIBLE
            }
            HabitsPriority.LOW -> {
                holder.star1.visibility = View.INVISIBLE;
                holder.star2.visibility = View.VISIBLE;
                holder.star3.visibility = View.INVISIBLE
                holder.star4.visibility = View.INVISIBLE
                holder.star5.visibility = View.INVISIBLE
            }
        }

    }
}