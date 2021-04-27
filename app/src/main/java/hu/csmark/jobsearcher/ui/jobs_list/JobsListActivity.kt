package hu.csmark.jobsearcher.ui.jobs_list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hu.csmark.jobsearcher.R
import hu.csmark.jobsearcher.injector
import hu.csmark.jobsearcher.interactor.JobInteractor
import hu.csmark.jobsearcher.ui.create_job.CreateJobActivity
import javax.inject.Inject

class JobsListActivity : AppCompatActivity(), JobsListScreen {

    @Inject
    lateinit var jobsListPresenter: JobsListPresenter
    lateinit var jobListAdapter: JobListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }

    override fun onStop() {
        super.onStop()
        jobsListPresenter.detachScreen()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        jobListAdapter = JobListAdapter(this);
        val rv: RecyclerView = findViewById(R.id.jobList)
        rv.adapter = jobListAdapter;
    }

    override fun showJobs(search: String) {
        TODO("Not yet implemented")
    }
}