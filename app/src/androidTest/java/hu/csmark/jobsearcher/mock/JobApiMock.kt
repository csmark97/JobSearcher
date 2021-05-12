package hu.csmark.jobsearcher.mock

import hu.csmark.jobsearcher.model.*
import hu.csmark.jobsearcher.network.JobApi
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

class JobApiMock : JobApi {
    override fun getAllJobs(limit: Number?): Call<List<Job>> {
        val job1 = Job()
        job1.uuid = UUID.randomUUID().toString()
        job1.parent_uuid = UUID.randomUUID().toString()
        job1.normalized_job_title = "Job 1"
        job1.title = "Job 1 title"

        val job2 = Job()
        job2.uuid = UUID.randomUUID().toString()
        job2.parent_uuid = UUID.randomUUID().toString()
        job2.normalized_job_title = "Job 2"
        job2.title = "Job 2 title"

        val jobs = mutableListOf(job1, job2)

        return object : Call<List<Job>> {
            override fun execute(): Response<List<Job>> {
                return Response.success(jobs)
            }

            override fun enqueue(callback: Callback<List<Job>>) {

            }

            override fun isExecuted(): Boolean {
                return false
            }

            override fun cancel() {}

            override fun isCanceled(): Boolean {
                return false
            }

            override fun clone(): Call<List<Job>> {
                return this
            }

            override fun request(): Request? {
                return null
            }
        }
    }

    override fun addJob(body: Job): Call<Job> {
        TODO("Not yet implemented")
    }

    override fun getJobByUuid(uuid: String?): Call<Job> {
        val job1 = JobMock.getJob()

        val job2 = Job()
        job2.uuid = "UUID"
        job2.parent_uuid = UUID.randomUUID().toString()
        job2.normalized_job_title = "Job 2"
        job2.title = "title 2"

        val jobs = mutableListOf(job1, job2)

        return object : Call<Job> {
            override fun execute(): Response<Job> {
                var filteredJobs: Job? = null
                if (uuid != null) {
                    filteredJobs = jobs.find {
                        it.normalized_job_title?.equals(uuid) == true
                    }
                }

                return Response.success(filteredJobs)
            }

            override fun enqueue(callback: Callback<Job>) {

            }

            override fun isExecuted(): Boolean {
                return false
            }

            override fun cancel() {}

            override fun isCanceled(): Boolean {
                return false
            }

            override fun clone(): Call<Job> {
                return this
            }

            override fun request(): Request? {
                return null
            }
        }
    }

    override fun getParentJobByUuid(parent_uuid: String?): Call<ParentJob> {
        TODO("Not yet implemented")
    }

    override fun deleteJob(uuid: String?): Call<String> {
        TODO("Not yet implemented")
    }

    override fun getJobsAutocomplete(
        contains: String?,
        limit: Number?
    ): Call<List<JobAutocomplete>> {
        val job1 = JobAutocomplete()
        job1.uuid = UUID.randomUUID().toString()
        job1.parent_uuid = UUID.randomUUID().toString()
        job1.normalized_job_title = "Job 1"
        job1.suggestion = "suggestion"

        val job2 = JobAutocomplete()
        job2.uuid = UUID.randomUUID().toString()
        job2.parent_uuid = UUID.randomUUID().toString()
        job2.normalized_job_title = "Job 2"
        job2.suggestion = "suggestion"

        val jobs = mutableListOf(job1, job2)

        return object : Call<List<JobAutocomplete>> {
            override fun execute(): Response<List<JobAutocomplete>> {
                var filteredJobs: List<JobAutocomplete>? = null
                if (contains != null) {
                    filteredJobs = jobs.filter {
                        it.normalized_job_title?.contains(contains) == true
                    }
                }

                return Response.success(filteredJobs)
            }

            override fun enqueue(callback: Callback<List<JobAutocomplete>>) {

            }

            override fun isExecuted(): Boolean {
                return false
            }

            override fun cancel() {}

            override fun isCanceled(): Boolean {
                return false
            }

            override fun clone(): Call<List<JobAutocomplete>> {
                return this
            }

            override fun request(): Request? {
                return null
            }
        }
    }

    override fun getRelatedSkillsByJobUuid(uuid: String?, limit: Number?): Call<JobWithSkills> {
        TODO("Not yet implemented")
    }

    override fun getRelatedJobsByJobUuid(uuid: String?, limit: Number?): Call<RelatedJobTitles> {
        TODO("Not yet implemented")
    }

    override fun addSkill(body: Skill): Call<Skill> {
        TODO("Not yet implemented")
    }

}