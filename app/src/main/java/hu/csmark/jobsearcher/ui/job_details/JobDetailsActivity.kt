package hu.csmark.jobsearcher.ui.job_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.csmark.jobsearcher.R
import hu.csmark.jobsearcher.injector
import hu.csmark.jobsearcher.ui.jobs_list.JobsListPresenter
import javax.inject.Inject

class JobDetailsActivity : AppCompatActivity(), JobDetailsScreen {

    @Inject
    lateinit var jobDetailsPresenter: JobDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_details)

        injector.inject(this)
    }

    override fun onStart() {
        super.onStart()
        jobDetailsPresenter.attachScreen(this)
    }

    override fun onStop() {
        super.onStop()
        jobDetailsPresenter.detachScreen()
    }

    override fun showJobDetails(search: String) {
        TODO("Not yet implemented")
    }
}