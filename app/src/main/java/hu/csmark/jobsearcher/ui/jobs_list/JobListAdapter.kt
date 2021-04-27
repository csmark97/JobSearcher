package hu.csmark.jobsearcher.ui.jobs_list

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.csmark.jobsearcher.R
import hu.csmark.jobsearcher.interactor.JobInteractor
import hu.csmark.jobsearcher.model.Job
import hu.csmark.jobsearcher.ui.job_details.JobDetailsActivity
import kotlinx.android.synthetic.main.job_item.view.*

class JobListAdapter(val context: Context) : RecyclerView.Adapter<JobListAdapter.ViewHolder>() {

    var jobList = mutableListOf<Job>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.job_item, parent, false)
        return ViewHolder(view)
    }


    inner class ViewHolder(jobView: View) : RecyclerView.ViewHolder(jobView) {
        val tvJobTitle = jobView.tvJobTitle
        val cardView = jobView.cardView
        val btnDelete = jobView.btnDelete
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val job = jobList[position]
        holder.tvJobTitle.text = job.title

        holder.btnDelete.setOnClickListener {
            removeJob(holder.adapterPosition)
        }

        holder.cardView.setOnClickListener {
            val intent = Intent(context, JobDetailsActivity::class.java)
            intent.putExtra("JOB_UUID", job.uuid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return jobList.size
    }

    fun setJobs(jobs: List<Job>) {
        // TODO: use interactor
    }

    fun addJob(context: Job){
        // TODO: use interactor
    }

    fun removeJob(position: Int){
        // TODO: use interactor
    }

}