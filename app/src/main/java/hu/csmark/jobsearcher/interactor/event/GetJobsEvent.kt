package hu.csmark.jobsearcher.interactor.event

import hu.csmark.jobsearcher.model.Job
import hu.csmark.jobsearcher.model.SelectedJob

data class GetJobsEvent(
    var code: Int = 0,
    var jobs: List<Job>? = null,
    var selectedJob: SelectedJob? = null,
    var createdJob: Job? = null,
    var deletedJobUuid: String? = null,
    var throwable: Throwable? = null
)