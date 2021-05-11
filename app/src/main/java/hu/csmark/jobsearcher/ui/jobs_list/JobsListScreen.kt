package hu.csmark.jobsearcher.ui.jobs_list

import hu.csmark.jobsearcher.model.Job

interface JobsListScreen {
    fun showJobs(jobs: List<Job>)
    fun jobDeleted(jobUuid: String)
    fun showNetworkError(errorMessage: String)
}