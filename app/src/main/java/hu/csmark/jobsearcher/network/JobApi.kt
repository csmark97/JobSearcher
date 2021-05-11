package hu.csmark.jobsearcher.network

import hu.csmark.jobsearcher.model.*
import retrofit2.Call
import retrofit2.http.*

interface JobApi {

    @GET("jobs")
    fun getAllJobs(@Query("limit") limit: Number?): Call<List<Job>>

    @POST("jobs")
    fun addJob(@Body body: Job): Call<Job>

    @GET("jobs/{uuid}")
    fun getJobByUuid(@Path("uuid") uuid: String?): Call<Job>

    @GET("jobs/{parent_uuid}")
    fun getParentJobByUuid(@Path("parent_uuid") parent_uuid: String?): Call<ParentJob>

    @DELETE("jobs/{uuid}")
    fun deleteJob(@Path("uuid") uuid: String?): Call<String>

    @GET("jobs/autocomplete")
    fun getJobsAutocomplete(@Query("contains") contains: String?, @Query("limit") limit: Number?): Call<List<JobAutocomplete>>

    @GET("jobs/{uuid}/related_skills")
    fun getRelatedSkillsByJobUuid(@Path("uuid") uuid: String?, @Query("limit") limit: Number?): Call<JobWithSkills>

    @GET("jobs/{uuid}/related_jobs")
    fun getRelatedJobsByJobUuid(@Path("uuid") uuid: String?, @Query("limit") limit: Number?): Call<RelatedJobTitles>

    @POST("skills")
    fun addSkill(@Body body: Skill): Call<Skill>
}