package hu.csmark.jobsearcher.ui.job_details

import hu.csmark.jobsearcher.interactor.JobInteractor
import hu.csmark.jobsearcher.interactor.event.GetJobsEvent
import hu.csmark.jobsearcher.model.Job
import hu.csmark.jobsearcher.ui.Presenter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class JobDetailsPresenter @Inject constructor(private val jobInteractor: JobInteractor) : Presenter<JobDetailsScreen?>() {

    override fun attachScreen(screen: JobDetailsScreen?) {
        super.attachScreen(screen)
        EventBus.getDefault().register(this)
    }

    override fun detachScreen() {
        EventBus.getDefault().unregister(this);
        super.detachScreen()
    }

    fun showJobDetails(uuid: String, parentUuid: String) {
        jobInteractor.getSelectedJobByUuidAndParentUuid(uuid, parentUuid)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: GetJobsEvent) {
        if (event.throwable != null) {
            event.throwable?.printStackTrace()
            if (screen != null) {
                screen?.showNetworkError(event.throwable?.message.orEmpty())
            }
        } else {
            if (screen != null && event.selectedJob != null) {
                screen?.showJobDetails(event.selectedJob!!)
            }
        }
    }
}