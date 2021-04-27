package hu.csmark.jobsearcher.ui.jobs_list

import hu.csmark.jobsearcher.interactor.JobInteractor
import hu.csmark.jobsearcher.ui.Presenter
import javax.inject.Inject

class JobsListPresenter @Inject constructor(private val jobInteractor: JobInteractor) : Presenter<JobsListScreen?>() {

    override fun attachScreen(screen: JobsListScreen?) {
        super.attachScreen(screen)
    }

    override fun detachScreen() {
        super.detachScreen()
    }

    fun showJobs(search: String) {
        screen?.showJobs(search)
    }
}