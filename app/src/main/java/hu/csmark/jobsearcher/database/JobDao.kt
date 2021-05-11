package hu.csmark.jobsearcher.database

import androidx.room.*
import hu.csmark.jobsearcher.model.Job

@Dao
interface JobDao {
    @Query("SELECT * FROM job WHERE uuid = :uuid")
    fun getJobByUuid(uuid: String): Job

    @Query("SELECT * FROM job")
    fun getAllJobs(): List<Job>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertJob(job: Job)

    @Update
    fun updateJob(job: Job)

    @Delete
    fun deleteJob(job: Job)
}