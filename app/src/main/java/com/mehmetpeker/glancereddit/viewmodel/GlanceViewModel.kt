package com.mehmetpeker.glancereddit.viewmodel

import androidx.lifecycle.ViewModel
import com.mehmetpeker.glancereddit.ui.widget.UniversityItem

class GlanceViewModel : ViewModel() {

    fun getUniversityList():List<UniversityItem>{
        return listOf(
            UniversityItem("A"),
            UniversityItem("B"),
            UniversityItem("C"),
            UniversityItem("D"),
            UniversityItem("E"),
            UniversityItem("F"),
            UniversityItem("G"),
        )

    }
}