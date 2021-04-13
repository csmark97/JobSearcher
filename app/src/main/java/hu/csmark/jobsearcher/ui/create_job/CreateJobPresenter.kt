package hu.csmark.jobsearcher.ui.job_details

import hu.csmark.jobsearcher.ui.Presenter

class CreateJobPresenter : Presenter<CreateJobScreen?>() {

    override fun attachScreen(screen: CreateJobScreen?) {
        super.attachScreen(screen)
    }

    override fun detachScreen() {
        super.detachScreen()
    }

    fun createJob() {
        screen?.createJob()
    }
}