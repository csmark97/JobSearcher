package hu.csmark.jobsearcher.interactor

import hu.csmark.jobsearcher.model.Job
import hu.csmark.jobsearcher.model.JobWithSkills
import hu.csmark.jobsearcher.model.RelatedJobTitles
import hu.csmark.jobsearcher.model.Skill
import hu.csmark.jobsearcher.network.JobApi
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

    fun getJobs(limit: Number): List<Job>? {
        var jobs: List<Job>? = null

        val foundJobs = this.jobApi.getAllJobs(limit)

        foundJobs.enqueue(object : Callback<List<Job>> {
            override fun onFailure(call: Call<List<Job>>, t: Throwable) {
                System.out.println(t.message)
            }

            override fun onResponse(
                call: Call<List<Job>>,
                response: Response<List<Job>>
            ) {
                println(response.body()?.get(0)?.title)
                // TODO
            }
        })
        return jobs
    }

    fun getJobsAutocomplete(searchText: String, limit: Number): List<Job>? {
        var jobs: List<Job>? = null

        val foundJobs = this.jobApi.getJobsAutocomplete(searchText, limit)

        foundJobs.enqueue(object : Callback<List<Job>> {
            override fun onFailure(call: Call<List<Job>>, t: Throwable) {
                println(t.message)
            }

            override fun onResponse(
                call: Call<List<Job>>,
                response: Response<List<Job>>
            ) {
                // TODO
            }
        })
        return jobs
    }

    fun getJobsRelatedSkillsByUuid(uuid: String, limit: Number): JobWithSkills? {
        var jobWithSkills: JobWithSkills? = null

        val foundJobWithSkills = this.jobApi.getRelatedSkillsByJobUuid(uuid, limit)

        foundJobWithSkills.enqueue(object : Callback<JobWithSkills> {
            override fun onFailure(call: Call<JobWithSkills>, t: Throwable) {
                println(t.message)
            }

            override fun onResponse(
                call: Call<JobWithSkills>,
                response: Response<JobWithSkills>
            ) {
                // TODO
            }
        })
        return jobWithSkills
    }

    fun getJobsRelatedJobsByUuid(uuid: String, limit: Number): RelatedJobTitles? {
        var relatedJobTitles: RelatedJobTitles? = null

        val foundRelatedJobTitles = this.jobApi.getRelatedJobsByJobUuid(uuid, limit)

        foundRelatedJobTitles.enqueue(object : Callback<RelatedJobTitles> {
            override fun onFailure(call: Call<RelatedJobTitles>, t: Throwable) {
                println(t.message)
            }

            override fun onResponse(
                call: Call<RelatedJobTitles>,
                response: Response<RelatedJobTitles>
            ) {
                // TODO
            }
        })
        return relatedJobTitles
    }

    fun deleteJobByUuid(uuid: String?){
        this.jobApi.deleteJob(uuid)
    }

    fun addJob(job: Job){
        jobApi.addJob(job)
    }

    fun addSkill(skill: Skill){
        jobApi.addSkill(skill)
    }
}