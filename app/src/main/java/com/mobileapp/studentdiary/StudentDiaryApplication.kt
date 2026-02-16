package com.mobileapp.studentdiary

import android.app.Application
import com.mobileapp.studentdiary.data.ServiceLocator

class StudentDiaryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceLocator.init(this)
    }
}
