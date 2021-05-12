package hu.csmark.jobsearcher.ui.job_details

import hu.csmark.jobsearcher.model.SelectedJob

interface JobDetailsScreen {
    fun showJobDetails(selectedJob: SelectedJob)
    fun showNetworkError(errorMessage: String)
}