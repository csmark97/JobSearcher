package hu.csmark.jobsearcher.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "job")
class Job {
    @PrimaryKey(autoGenerate = false)
    var uuid: String? = null

    @ColumnInfo(name = "title")
    var title: String? = null

    @ColumnInfo(name = "normalized_job_title")
    var normalized_job_title: String? = null

    @ColumnInfo(name = "parent_uuid")
    var parent_uuid: String? = null
}
