package hu.csmark.jobsearcher.ui.create_job

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.csmark.jobsearcher.R
import hu.csmark.jobsearcher.injector
import hu.csmark.jobsearcher.ui.job_details.CreateJobPresenter
import hu.csmark.jobsearcher.ui.job_details.CreateJobScreen
import javax.inject.Inject

class CreateJobActivity : AppCompatActivity(), CreateJobScreen {

    @Inject
    lateinit var createJobPresenter: CreateJobPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_job)

        injector.inject(this)
    }

    override fun onStart() {
        super.onStart()
        createJobPresenter.attachScreen(this)
    }

    override fun onStop() {
        super.onStop()
        createJobPresenter.detachScreen()
    }

    override fun createJob() {
        TODO("Not yet implemented")
    }
}