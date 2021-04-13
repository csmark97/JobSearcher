package hu.csmark.jobsearcher.ui

import android.content.Context
import dagger.Module
import dagger.Provides
import hu.csmark.jobsearcher.ui.job_details.CreateJobPresenter
import hu.csmark.jobsearcher.ui.job_details.JobDetailsPresenter
import hu.csmark.jobsearcher.ui.jobs_list.JobsListPresenter
import javax.inject.Singleton

@Module
class UIModule(private val context: Context) {

    @Provides
    fun context() = context

    @Provides
    @Singleton
    fun jobListPresenter() = JobsListPresenter()

    @Provides
    @Singleton
    fun jobDetailsPresenter() = JobDetailsPresenter()

    @Provides
    @Singleton
    fun createJobPresenter()= CreateJobPresenter()
}