package hu.csmark.jobsearcher.ui.create_job

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import hu.csmark.jobsearcher.R
import hu.csmark.jobsearcher.injector
import hu.csmark.jobsearcher.model.Job
import hu.csmark.jobsearcher.ui.job_details.CreateJobPresenter
import hu.csmark.jobsearcher.ui.job_details.CreateJobScreen
import kotlinx.android.synthetic.main.activity_create_job.*
import java.util.UUID.randomUUID
import javax.inject.Inject


class CreateJobActivity : AppCompatActivity(), CreateJobScreen {

    @Inject
    lateinit var createJobPresenter: CreateJobPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_job)

        injector.inject(this)

        val button: Button = findViewById<View>(R.id.btnAdd) as Button
        button.setOnClickListener {
            this.createJob()
        }

        val crashButton = Button(this)
        crashButton.text = "Crash!"
        crashButton.setOnClickListener {
            throw RuntimeException("Test Crash") // Force a crash
        }

        addContentView(crashButton, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))
    }

    override fun onStart() {
        super.onStart()
        createJobPresenter.attachScreen(this)
    }

    override fun onStop() {
        super.onStop()
        createJobPresenter.detachScreen()
    }

    private fun createJob() {
        val job = Job()
        job.title = et_title.text.toString()
        job.normalized_job_title = et_normalized.text.toString()
        job.uuid = randomUUID().toString()
        job.parent_uuid = randomUUID().toString()

        createJobPresenter.createJob(job, this)
    }

    override fun jobCreated(job: Job?) {
        val parentLayout: View = findViewById(android.R.id.content)

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(parentLayout.windowToken, 0)

        Snackbar.make(parentLayout, "Job created", 3000).show()
    }

    override fun showNetworkError(errorMessage: String) {
        TODO("Not yet implemented")
    }
}