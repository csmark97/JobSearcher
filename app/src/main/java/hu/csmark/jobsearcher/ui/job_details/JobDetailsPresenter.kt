package hu.csmark.jobsearcher.ui.job_details

import hu.csmark.jobsearcher.ui.Presenter

class JobDetailsPresenter : Presenter<JobDetailsScreen?>() {

    override fun attachScreen(screen: JobDetailsScreen?) {
        super.attachScreen(screen)
    }

    override fun detachScreen() {
        super.detachScreen()
    }

    fun showJobDetails(search: String) {
        screen?.showJobDetails(search)
    }
}