package hu.csmark.jobsearcher.ui.job_details

import android.content.Context
import com.google.android.material.snackbar.Snackbar
import hu.csmark.jobsearcher.interactor.JobInteractor
import hu.csmark.jobsearcher.interactor.event.GetJobsEvent
import hu.csmark.jobsearcher.model.Job
import hu.csmark.jobsearcher.ui.Presenter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class CreateJobPresenter @Inject constructor(private val jobInteractor: JobInteractor) : Presenter<CreateJobScreen?>() {

    override fun attachScreen(screen: CreateJobScreen?) {
        super.attachScreen(screen)
        EventBus.getDefault().register(this)
    }

    override fun detachScreen() {
        EventBus.getDefault().unregister(this);
        super.detachScreen()
    }

    fun createJob(job: Job?, context: Context) {
        jobInteractor.addJob(job)
        jobInteractor.saveJobLocally(job, context)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: GetJobsEvent) {
        if (event.throwable != null) {
            event.throwable?.printStackTrace()
            if (screen != null) {
                screen?.showNetworkError(event.throwable?.message.orEmpty())
            }
        } else {
            if (screen != null && event.createdJob != null) {
                screen!!.jobCreated(event.createdJob)
            }
        }
    }
}