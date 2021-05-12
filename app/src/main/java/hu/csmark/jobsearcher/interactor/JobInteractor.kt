package hu.csmark.jobsearcher.interactor

import android.content.Context
import hu.csmark.jobsearcher.database.JobDatabase
import hu.csmark.jobsearcher.interactor.event.GetJobsEvent
import hu.csmark.jobsearcher.model.*
import hu.csmark.jobsearcher.network.JobApi
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class JobInteractor @Inject constructor() {

    val jobApi = Retrofit.Builder()
        .baseUrl("http://api.dataatwork.org/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(JobApi::class.java)

    fun getJobs(limit: Number) {
        val foundJobs = this.jobApi.getAllJobs(limit)

        val event = GetJobsEvent()

        foundJobs.enqueue(object : Callback<List<Job>> {
            override fun onFailure(call: Call<List<Job>>, t: Throwable) {
                println(t.message)
            }

            override fun onResponse(
                call: Call<List<Job>>,
                response: Response<List<Job>>
            ) {
                event.jobs = response.body()
                EventBus.getDefault().post(event)
            }
        })
    }

    fun getSelectedJobByUuidAndParentUuid(selectedUuid: String, parentUuid: String) {
        val foundJob = this.jobApi.getJobByUuid(selectedUuid)
        val foundParentJob = this.jobApi.getParentJobByUuid(parentUuid)
        val foundSkills = this.jobApi.getRelatedSkillsByJobUuid(selectedUuid, 5)

        val event = GetJobsEvent()

        event.selectedJob = SelectedJob()

        foundJob.enqueue(object : Callback<Job> {
            override fun onFailure(call: Call<Job>, t: Throwable) {
                println(t.message)
            }

            override fun onResponse(call: Call<Job>, response: Response<Job>) {
                event.selectedJob!!.uuid = response.body()?.uuid
                event.selectedJob!!.title= response.body()?.title

                foundParentJob.enqueue(object : Callback<ParentJob> {
                    override fun onFailure(call: Call<ParentJob>, t: Throwable) {
                        println(t.message)
                    }

                    override fun onResponse(call: Call<ParentJob>, response: Response<ParentJob>) {
                        event.selectedJob!!.parentTitle = response.body()?.title
                        event.selectedJob!!.description= response.body()?.description

                        foundSkills.enqueue(object : Callback<JobWithSkills> {
                            override fun onFailure(call: Call<JobWithSkills>, t: Throwable) {
                                println(t.message)
                            }

                            override fun onResponse(
                                call: Call<JobWithSkills>,
                                response: Response<JobWithSkills>
                            ) {
                                event.selectedJob!!.skills = response.body()?.skills

                                EventBus.getDefault().post(event)
                            }
                        })
                    }
                })
            }
        })
    }

    fun getJobsAutocomplete(searchText: String, limit: Number) {
        val foundJobs = this.jobApi.getJobsAutocomplete(searchText, limit)

        val event = GetJobsEvent()

        foundJobs.enqueue(object : Callback<List<JobAutocomplete>> {
            override fun onFailure(call: Call<List<JobAutocomplete>>, t: Throwable) {
                println(t.message)
            }

            override fun onResponse(
                call: Call<List<JobAutocomplete>>,
                response: Response<List<JobAutocomplete>>
            ) {
                val jobs = mutableListOf<Job>()

                response.body()?.forEach{
                    val job = Job()
                    job.normalized_job_title = it.normalized_job_title
                    job.parent_uuid = it.parent_uuid
                    job.uuid = it.uuid.toString()
                    job.title = it.normalized_job_title

                    jobs.add(job)
                }

                event.jobs = jobs

                EventBus.getDefault().post(event)
            }
        })
    }

    fun getJobsRelatedJobsByUuid(uuid: String, limit: Number): RelatedJobTitles? {
        val relatedJobTitles: RelatedJobTitles? = null

        val foundRelatedJobTitles = this.jobApi.getRelatedJobsByJobUuid(uuid, limit)

        foundRelatedJobTitles.enqueue(object : Callback<RelatedJobTitles> {
            override fun onFailure(call: Call<RelatedJobTitles>, t: Throwable) {
                println(t.message)
            }

            override fun onResponse(
                call: Call<RelatedJobTitles>,
                response: Response<RelatedJobTitles>
            ) {
                // nothing to use, this is a mock function
            }
        })
        return relatedJobTitles
    }

    fun addJob(job: Job?) {
        if (job == null) {
            return
        }

        // Save to "remote db" (mock functionality, API does not supports POST)
        val createdJob = this.jobApi.addJob(job)

        val event = GetJobsEvent()

        createdJob.enqueue(object : Callback<Job> {
            override fun onFailure(call: Call<Job>, t: Throwable) {
                println(t.message)
            }

            override fun onResponse(
                call: Call<Job>,
                response: Response<Job>
            ) {
                // This is mock functionality, because the API does not supports POST
                // So we cant use the response.body(), use the param Job
                event.createdJob = job // "mocked" response
                EventBus.getDefault().post(event)
            }
        })
    }

    fun deleteJobByUuid(job: Job) {
        // Obviously this is a mock functionality too, API does not support DELETE
        val event = GetJobsEvent()

        this.jobApi.deleteJob(job.uuid).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                println(t.message)
            }

            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                event.deletedJobUuid = job.uuid // "mocked" response
                EventBus.getDefault().post(event)
            }
        })
    }

    fun saveJobLocally(job: Job?, context: Context) {
        // Save to local db, just for room usage
        val jobDao = JobDatabase.getInstance(context).jobDao()

        if (job != null) {
            jobDao.insertJob(job)
        }
    }

    fun deleteJobLocally(job: Job?, context: Context) {
        // Save to local db, just for room usage
        val jobDao = JobDatabase.getInstance(context).jobDao()

        if (job != null) {
            jobDao.deleteJob(job)
        }
    }

    fun addSkill(skill: Skill){
        jobApi.addSkill(skill)
    }
}