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
import javax.inject.Inject

class JobListAdapter(var context: Context) : RecyclerView.Adapter<JobListAdapter.ViewHolder>() {

    var jobList = mutableListOf<Job>()

    val jobInteractor: JobInteractor = JobInteractor()

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
            removeJob(holder.adapterPosition, job)
        }

        holder.cardView.setOnClickListener {
            val intent = Intent(context, JobDetailsActivity::class.java)
            intent.putExtra("JOB_UUID", job.uuid)
            intent.putExtra("JOB_PARENT_UUID", job.parent_uuid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return jobList.size
    }

    fun setJobs(jobs: List<Job>) {
        jobList = jobs as MutableList<Job>
        notifyDataSetChanged()
    }

    fun addJob(context: Job){
        // TODO: use interactor
    }

    private fun removeJob(position: Int, job: Job) {
        jobInteractor.deleteJobByUuid(job)
        jobInteractor.deleteJobLocally(job, context)
        jobList.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

}