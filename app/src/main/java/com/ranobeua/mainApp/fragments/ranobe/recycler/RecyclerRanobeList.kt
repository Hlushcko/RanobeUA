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
import com.ranobeua.base.firebase.ranobe.data.Ranobe
import com.ranobeua.mainApp.fragments.ranobe.ChapterFragment
import com.ranobeua.mainApp.fragments.ranobe.RanobeFragment
import com.ranobeua.mainApp.fragments.ranobe.recycler.viewModelConnector.ModelConnect
import java.lang.ref.WeakReference


class RecyclerRanobeList(): ListAdapter<Ranobe, RecyclerRanobeList.RecyclerHolder>(RanobeDiffCallback()) {

    private var activity: WeakReference<FragmentActivity>? = null
    private var model: ModelConnect? = null


    constructor(fragmentActivity: FragmentActivity) : this() {
        activity = WeakReference(fragmentActivity)
        model = ViewModelProvider(activity?.get()!!)[ModelConnect::class.java]
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        return RecyclerHolder(LayoutInflater.from(parent.context).inflate(R.layout.ranobe_preview, parent, false))
    }


    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        val ranobe = getItem(position)
        holder.setNameView(ranobe.originalName)
        holder.setYearView(ranobe.year)
        holder.clickNameRanobe {
            model?.setName(it!!)
            openRanobe()
        }

        if(position == itemCount - 1){
            model?.setNextRanobe(true)
        }
    }


    private fun openRanobe(){
        val frag = activity?.get()?.supportFragmentManager?.beginTransaction()
        frag?.addToBackStack(null)
        frag?.replace(R.id.mainFrame, RanobeFragment())
        frag?.commit()
    }


    class RecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var nameView: TextView? = null
        private var yearView: TextView? = null

        init {
            initView()
        }

        private fun initView(){
            nameView = itemView.findViewById(R.id.name)
            yearView = itemView.findViewById(R.id.year)
        }

        fun clickNameRanobe(callback: (String?) -> Unit){
            nameView?.setOnClickListener {
                callback(nameView!!.text.toString())
            }
        }

        fun setNameView(name: String){
            nameView?.text = name
        }

        fun setYearView(year: String){
            yearView?.text = year
        }

    }


    class RanobeDiffCallback : DiffUtil.ItemCallback<Ranobe>() {
        override fun areItemsTheSame(oldItem: Ranobe, newItem: Ranobe): Boolean {
            return oldItem.originalName == newItem.originalName
        }

        override fun areContentsTheSame(oldItem: Ranobe, newItem: Ranobe): Boolean {
            return oldItem == newItem
        }
    }

}