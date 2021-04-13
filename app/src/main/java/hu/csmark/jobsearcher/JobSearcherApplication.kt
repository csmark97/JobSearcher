package hu.csmark.jobsearcher

import android.app.Application
import hu.csmark.jobsearcher.ui.UIModule

class JobSearcherApplication: Application() {

    lateinit var injector: JobSearcherApplicationComponent

    override fun onCreate() {
        super.onCreate()

        injector = DaggerJobSearcherApplicationComponent.builder().uIModule(UIModule(this)).build()
    }

}