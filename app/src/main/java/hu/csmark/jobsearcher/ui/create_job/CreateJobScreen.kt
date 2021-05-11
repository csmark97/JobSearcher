package hu.csmark.jobsearcher.ui.job_details

import hu.csmark.jobsearcher.model.Job

interface CreateJobScreen {
    fun jobCreated(job: Job?)
    fun showNetworkError(errorMessage: String)
}