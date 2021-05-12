package hu.csmark.jobsearcher.ui

import android.content.Context
import dagger.Module
import dagger.Provides
import hu.csmark.jobsearcher.interactor.JobInteractor
import hu.csmark.jobsearcher.ui.job_details.CreateJobPresenter
import hu.csmark.jobsearcher.ui.job_details.JobDetailsPresenter
import hu.csmark.jobsearcher.ui.jobs_list.JobsListPresenter
import java.util.concurrent.Executor
import javax.inject.Singleton

@Module
class UIModule(private val context: Context) {

    @Provides
    fun context() = context

    @Provides
    @Singleton
    fun jobListPresenter(jobInteractor: JobInteractor) = JobsListPresenter(jobInteractor)

    @Provides
    @Singleton
    fun jobDetailsPresenter(jobInteractor: JobInteractor) = JobDetailsPresenter(jobInteractor)

    @Provides
    @Singleton
    fun createJobPresenter(jobInteractor: JobInteractor)= CreateJobPresenter(jobInteractor)
}