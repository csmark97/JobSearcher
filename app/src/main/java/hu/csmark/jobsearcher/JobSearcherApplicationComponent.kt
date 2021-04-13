package hu.csmark.jobsearcher

import dagger.Component
import hu.csmark.jobsearcher.ui.UIModule
import hu.csmark.jobsearcher.ui.create_job.CreateJobActivity
import hu.csmark.jobsearcher.ui.job_details.CreateJobPresenter
import hu.csmark.jobsearcher.ui.job_details.JobDetailsActivity
import hu.csmark.jobsearcher.ui.jobs_list.JobsListActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [UIModule::class])
interface JobSearcherApplicationComponent {
    fun inject(jobsListActivity: JobsListActivity)
    fun inject(jobDetailsActivity: JobDetailsActivity)
    fun inject(createJobActivity: CreateJobActivity)
}
