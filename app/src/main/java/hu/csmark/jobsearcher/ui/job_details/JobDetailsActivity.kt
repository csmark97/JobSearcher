package hu.csmark.jobsearcher.ui.job_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.csmark.jobsearcher.R
import hu.csmark.jobsearcher.injector
import hu.csmark.jobsearcher.interactor.JobInteractor
import hu.csmark.jobsearcher.model.SelectedJob
import kotlinx.android.synthetic.main.activity_job_details.*
import javax.inject.Inject

class JobDetailsActivity : AppCompatActivity(), JobDetailsScreen {

    @Inject
    lateinit var jobDetailsPresenter: JobDetailsPresenter

    @Inject
    lateinit var jobInteractor: JobInteractor

    lateinit var selectedJob: SelectedJob

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_details)

        injector.inject(this)

        val uuid = intent.getStringExtra("JOB_UUID")
        val parentUuid = intent.getStringExtra("JOB_PARENT_UUID")

        if (uuid != null && parentUuid != null) {
            jobDetailsPresenter.showJobDetails(uuid, parentUuid)
        }
    }

    override fun onStart() {
        super.onStart()
        jobDetailsPresenter.attachScreen(this)
    }

    override fun onStop() {
        super.onStop()
        jobDetailsPresenter.detachScreen()
    }

    override fun showJobDetails(selectedJob: SelectedJob) {
        this.selectedJob = selectedJob

        tv_title.text = selectedJob.title
        tv_parent_title.text = selectedJob.parentTitle
        tv_description.text = selectedJob.description
        tv_related_skill_1.text = selectedJob.skills?.get(0)?.skill_name ?: ""
        tv_related_skill_2.text = selectedJob.skills?.get(1)?.skill_name ?: ""
        tv_related_skill_3.text = selectedJob.skills?.get(2)?.skill_name ?: ""
        tv_related_skill_4.text = selectedJob.skills?.get(3)?.skill_name ?: ""
        tv_related_skill_5.text = selectedJob.skills?.get(4)?.skill_name ?: ""
    }

    override fun showNetworkError(errorMessage: String) {
        TODO("Not yet implemented")
    }
}