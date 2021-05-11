package hu.csmark.jobsearcher.ui.jobs_list

import android.system.Os.listen
import hu.csmark.jobsearcher.interactor.JobInteractor
import hu.csmark.jobsearcher.interactor.event.GetJobsEvent
import hu.csmark.jobsearcher.model.Job
import hu.csmark.jobsearcher.ui.Presenter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.Executor
import javax.inject.Inject

class JobsListPresenter @Inject constructor(private val jobInteractor: JobInteractor) : Presenter<JobsListScreen?>() {

    override fun attachScreen(screen: JobsListScreen?) {
        super.attachScreen(screen)
        EventBus.getDefault().register(this)
    }

    override fun detachScreen() {
        EventBus.getDefault().unregister(this);
        super.detachScreen()
    }

    fun showJobs() {
        jobInteractor.getJobs(20)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: GetJobsEvent) {
        if (event.throwable != null) {
            event.throwable?.printStackTrace()
            if (screen != null) {
                screen?.showNetworkError(event.throwable?.message.orEmpty())
            }
        } else {
            if (screen != null && event.jobs != null) {
                screen?.showJobs(event.jobs as MutableList<Job>)
            }

            if (screen != null && event.deletedJobUuid != null) {
                screen!!.jobDeleted(event.deletedJobUuid!!)
            }
        }
    }
}