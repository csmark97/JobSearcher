package hu.csmark.jobsearcher.mock

import hu.csmark.jobsearcher.model.Job
import java.util.*

class JobMock {

    companion object {
        fun getJob(): Job {
            val job1 = Job()
            job1.uuid = UUID.randomUUID().toString()
            job1.parent_uuid = UUID.randomUUID().toString()
            job1.normalized_job_title = "Job 1"
            job1.title = "Job 1 title"

            return job1
        }
    }
}