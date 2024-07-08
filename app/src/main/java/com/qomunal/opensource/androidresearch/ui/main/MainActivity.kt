package com.qomunal.opensource.androidresearch.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment
import com.qomunal.opensource.androidresearch.common.base.BaseActivity
import com.qomunal.opensource.androidresearch.databinding.ActivityMainBinding
import com.qomunal.opensource.androidresearch.model.CalendarModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val DAYS_COUNT = 35
    private val calendarList = mutableListOf<CalendarModel>()
    private val calendar = Calendar.getInstance()
    private var tahun: Int = -1
    private var monthOfYear: Int = -1
    private var adapter: CalendarAdapter = CalendarAdapter(calendarList)


    private val viewModel: MainViewModel by viewModels()

    private val router: MainRouter by lazy {
        MainRouter(this)
    }

    override fun setupViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initUI() {
        binding.apply {
            loadCalendar()

            tvMonthYear.setOnClickListener {
                showMonthYearPicker()
            }

            recyclerView.layoutManager = GridLayoutManager(applicationContext, 7)
            recyclerView.adapter = adapter
        }
    }

    override fun initObserver() {
        viewModel.apply {

        }
    }

    private fun showMonthYearPicker() {
        val dialogFragment = MonthYearPickerDialogFragment.getInstance(
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.YEAR)
        )
        dialogFragment.show(supportFragmentManager, null)

        dialogFragment.setOnDateSetListener { year, month ->
            tahun = year
            monthOfYear = month
            loadCalendar()
        }
    }

    private fun loadCalendar() {
        // ubah val ke var
        // inisialisasi variabel untuk setiap tanggal kalender
        val cells = mutableListOf<CalendarModel>()

        // pengecekan bila varuiabel tahun dan monthOfYear kosong (-1 hanya pengecoh)
        if (tahun != -1 && monthOfYear != -1) {
            // ubah obyek kalender ke tahun dan bulan yang diterima
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.YEAR, tahun)
        } else {
            // set variabel tahun dan monthOfYear ke tahun dan bulan sekarang
            tahun = calendar.get(Calendar.YEAR)
            monthOfYear = calendar.get(Calendar.MONTH)
        }
        // obyek untuk parse bulan dan tahun
        var sdf = SimpleDateFormat("MMMM,yyyy", Locale("in", "ID"))

        // format obyek calendar lalu split berdasarkan ,
        val dateToday = sdf.format(calendar.time).split(",")

        // set bulan dan tahun
        val monthYear = "${dateToday[0]} ${dateToday[1]}"

        // settext bulan ke textview month
        binding.tvMonthYear.text = monthYear

        // calendarToday
        // instansiasi obyek calendar pembanding
        val calendarCompare = Calendar.getInstance()

        // set bulan pada calendar pembanding ke monthOfYear
        calendarCompare.set(Calendar.MONTH, monthOfYear)

        // set tahun pada calendar pembanding ke tahun
        calendarCompare.set(Calendar.YEAR, tahun)

        // memnentukan kapan tanggal dimulai pada bulan
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1

        // pindah calendar ke awal minggu
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell)

        // obyek untuk parse tanggal
        sdf = SimpleDateFormat("dd-MM-yyyy", Locale("in", "ID"))

        // isi tanggal
        while (cells.size < DAYS_COUNT) {
            if (sdf.format(calendar.time).equals("08-07-2024")) {
                cells.add(
                    CalendarModel(
                        calendar.get(Calendar.DATE),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.YEAR),
                        calendarCompare,
                        true
                    )
                )
            } else {
                cells.add(
                    CalendarModel(
                        calendar.get(Calendar.DATE),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.YEAR),
                        calendarCompare,
                    )
                )
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        calendarList.clear()
        calendarList.addAll(cells)
        adapter.notifyDataSetChanged()
    }

}