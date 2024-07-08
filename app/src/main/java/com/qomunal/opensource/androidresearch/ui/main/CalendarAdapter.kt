package com.qomunal.opensource.androidresearch.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.qomunal.opensource.androidresearch.R
import com.qomunal.opensource.androidresearch.model.CalendarModel
import java.util.Calendar

class CalendarAdapter(private val mData : MutableList<CalendarModel>) :
    RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)
        return CalendarViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class CalendarViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        var date : TextView = mView.findViewById(R.id.date)
        var dot : ImageView = mView.findViewById(R.id.dot)
        var context: Context = mView.context

        fun bind(data: CalendarModel) {
            date.text = data.date.toString()

            date.setOnClickListener {
                Toast.makeText(context, dateBuilder(data.date, data.month+1, data.year), Toast.LENGTH_SHORT).show()
            }

            if (data.month == data.calendarCompare.get(Calendar.MONTH) && data.year == data.calendarCompare.get(Calendar.YEAR)){
                date.setTextColor(ContextCompat.getColor(context, R.color.date_true))
            }
            else date.setTextColor(ContextCompat.getColor(context, R.color.date_false))

            if (data.isToday){
                dot.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dot))
            }
        }

        private fun dateBuilder(tanggal : Int, bulan : Int, tahun : Int) : String{
            val tgl: String = if (tanggal.toString().length == 1){
                "0$tanggal"
            } else {
                ""+tanggal
            }
            val bln: String = if (bulan.toString().length == 1){
                "0$bulan"
            } else {
                ""+bulan
            }
            return "$tgl-$bln-$tahun"
        }

    }

}