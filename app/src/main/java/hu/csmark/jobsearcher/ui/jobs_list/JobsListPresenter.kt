package hu.csmark.jobsearcher.ui.jobs_list

import hu.csmark.jobsearcher.ui.Presenter

class JobsListPresenter : Presenter<JobsListScreen?>() {

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