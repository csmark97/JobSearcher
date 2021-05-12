package hu.csmark.jobsearcher.test

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import hu.csmark.jobsearcher.database.JobDatabase
import hu.csmark.jobsearcher.mock.JobApiMock
import hu.csmark.jobsearcher.mock.JobMock
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals

@RunWith(AndroidJUnit4::class)
class JobTest {

    @Mock
    private lateinit var apiMock: JobApiMock

    private lateinit var database: JobDatabase

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            JobDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        apiMock = JobApiMock()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertJob() {
        val job = JobMock.getJob()
        database.jobDao().insertJob(job)

        val jobsInDatabase = database.jobDao().getAllJobs()

        assertTrue(jobsInDatabase.contains(job))
    }

    @Test
    fun getAllJobsDb() {
        val job = JobMock.getJob()
        database.jobDao().insertJob(job)

        val jobsInDatabase = database.jobDao().getAllJobs()

        assertEquals(1, jobsInDatabase.size)
    }

    @Test
    fun getAllJobsApi() {
        val response = this.apiMock.getAllJobs(2).execute()

        val jobs = response.body()

        assertTrue(response.isSuccessful)

        if (jobs != null) {
            assertEquals(2, jobs.size)
        }
    }

    @Test
    fun getAllJobsAutocompleteApi() {
        val response = this.apiMock.getJobsAutocomplete("2", 10).execute()

        val jobs = response.body()

        assertTrue(response.isSuccessful)

        if (jobs != null) {
            assertEquals(1, jobs.size)
        }
    }

    @Test
    fun getJobsByUuid() {
        val response = this.apiMock.getJobByUuid("UUID").execute()

        val job = response.body()

        assertTrue(response.isSuccessful)

        if (job != null) {
            assertEquals("title 2", job.title)
            assertEquals("UUID", job.uuid)
        }
    }
}