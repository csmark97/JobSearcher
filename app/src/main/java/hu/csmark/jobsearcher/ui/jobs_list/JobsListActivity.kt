package hu.csmark.jobsearcher.ui.jobs_list

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import hu.csmark.jobsearcher.R
import hu.csmark.jobsearcher.database.JobDatabase
import hu.csmark.jobsearcher.injector
import hu.csmark.jobsearcher.interactor.JobInteractor
import hu.csmark.jobsearcher.model.Job
import hu.csmark.jobsearcher.ui.create_job.CreateJobActivity
import javax.inject.Inject

class JobsListActivity : AppCompatActivity(), JobsListScreen {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private var mergedJobs = mutableListOf<Job>()

    @Inject
    lateinit var jobsListPresenter: JobsListPresenter
    lateinit var jobListAdapter: JobListAdapter

    @Inject
    lateinit var jobInteractor: JobInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAnalytics = Firebase.analytics

        injector.inject(this)

        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val intent = Intent(this, CreateJobActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        jobsListPresenter.attachScreen(this)
        jobsListPresenter.showJobs()
    }

    override fun onStop() {
        super.onStop()
        jobsListPresenter.detachScreen()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = menu.findItem(R.id.menu_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mergedJobs = mutableListOf()

                if (query != null) {
                    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH) {
                        param(FirebaseAnalytics.Param.ITEM_ID, "SEARCHED")
                        param(FirebaseAnalytics.Param.ITEM_NAME, "SEARCHED")
                        param(FirebaseAnalytics.Param.CONTENT_TYPE, "text")
                        param(FirebaseAnalytics.Param.CONTENT, query)
                    }

                    jobInteractor.getJobsAutocomplete(query, 10)
                }

                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        })

        return true
    }

    override fun onResume() {
        super.onResume()
        val jobDao = JobDatabase.getInstance(this).jobDao()
        mergedJobs = mutableListOf()
        mergedJobs.addAll(jobDao.getAllJobs())
        initRecyclerView()
    }

    private fun initRecyclerView() {
        jobListAdapter = JobListAdapter(this);
        val rv: RecyclerView = findViewById(R.id.jobList)
        rv.adapter = jobListAdapter;
    }

    override fun showJobs(jobs: List<Job>) {
        // Add remote jobs from API
        mergedJobs.addAll(jobs)

        jobListAdapter.setJobs(mergedJobs)
    }

    override fun showNetworkError(errorMessage: String) {
        TODO("Not yet implemented")
    }

    override fun jobDeleted(jobUuid: String) {
        val parentLayout: View = findViewById(android.R.id.content)

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(parentLayout.windowToken, 0)

        Snackbar.make(parentLayout, "Job deleted", 3000).show()
    }
}