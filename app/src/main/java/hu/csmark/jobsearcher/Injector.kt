package hu.csmark.jobsearcher

import android.app.Activity

val Activity.injector: JobSearcherApplicationComponent
    get() {
        return (this.applicationContext as JobSearcherApplication).injector
    }