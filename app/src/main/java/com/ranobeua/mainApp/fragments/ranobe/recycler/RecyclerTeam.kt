package com.ranobeua.mainApp.fragments.ranobe.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.ranobeua.R
import com.ranobeua.base.firebase.ranobe.data.RanobeTeamInfo
import com.ranobeua.mainApp.fragments.ranobe.recycler.viewModelConnector.ModelConnect
import java.lang.ref.WeakReference


class RecyclerTeam() : ListAdapter<RanobeTeamInfo, RecyclerTeam.TeamAdapter>(TeamsDiffCallback()) {

    private var activity: WeakReference<FragmentActivity>? = null
    private var model: ModelConnect? = null


    constructor(fragmentActivity: FragmentActivity) : this() {
        activity = WeakReference(fragmentActivity)
        model = ViewModelProvider(activity?.get()!!)[ModelConnect::class.java]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamAdapter {
        return TeamAdapter(
            LayoutInflater.from(parent.context).inflate(R.layout.chapter_info, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TeamAdapter, position: Int) {
        val teamInfo = getItem(position)

        holder.setNameTeam(teamInfo.team)
        holder.clickTeam {
            model?.setTeamId(position)
        }
    }


    class TeamAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var nameTeam: TextView? = null
        private var downloadChapter: TextView? = null

        init {
            initView()
        }

        private fun initView() {
            nameTeam = itemView.findViewById(R.id.nameChapter)
            downloadChapter = itemView.findViewById(R.id.downloadAndDeleteChapter)
        }

        fun clickTeam(callback: (String?) -> Unit) {
            nameTeam?.setOnClickListener {
                callback(nameTeam!!.text.toString())
            }
        }

        fun setNameTeam(name: String) {
            nameTeam?.text = name
        }

    }


    class TeamsDiffCallback : DiffUtil.ItemCallback<RanobeTeamInfo>() {
        override fun areItemsTheSame(oldItem: RanobeTeamInfo, newItem: RanobeTeamInfo): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: RanobeTeamInfo, newItem: RanobeTeamInfo): Boolean {
            return oldItem == newItem
        }
    }
}