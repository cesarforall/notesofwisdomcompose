package com.cesarforall.notesofwisdom

import android.app.Application
import com.cesarforall.notesofwisdom.data.AppContainer
import com.cesarforall.notesofwisdom.data.AppDataContainer

class NotesApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}