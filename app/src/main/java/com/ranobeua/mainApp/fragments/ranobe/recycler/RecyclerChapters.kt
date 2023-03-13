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
import com.ranobeua.base.firebase.ranobe.data.Chapter
import com.ranobeua.mainApp.fragments.ranobe.ChapterFragment
import com.ranobeua.mainApp.fragments.ranobe.recycler.viewModelConnector.ModelConnect
import java.lang.ref.WeakReference


class RecyclerChapters() : ListAdapter<Chapter, RecyclerChapters.ChaptersAdapter>(ChaptersDiffCallback()) {

    private var activity: WeakReference<FragmentActivity>? = null
    private var model: ModelConnect? = null


    constructor(fragmentActivity: FragmentActivity) : this() {
        activity = WeakReference(fragmentActivity)
        model = ViewModelProvider(activity?.get()!!)[ModelConnect::class.java]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChaptersAdapter {
        return ChaptersAdapter(
            LayoutInflater.from(parent.context).inflate(R.layout.chapter_info, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ChaptersAdapter, position: Int) {
        val chapter = getItem(position)

        holder.setNameView(chapter.name)
        holder.clickChapter {
            model?.setChapterId(chapter.chapterId)
            openChapter()
        }
    }

    private fun openChapter() {
        val frag = activity?.get()?.supportFragmentManager?.beginTransaction()
        frag?.addToBackStack(null)
        frag?.replace(R.id.mainFrame, ChapterFragment())
        frag?.commit()
    }

    class ChaptersAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var nameChapter: TextView? = null
        private var downloadChapter: TextView? = null

        init {
            initView()
            observeButton()
        }

        private fun initView() {
            nameChapter = itemView.findViewById(R.id.nameChapter)
            downloadChapter = itemView.findViewById(R.id.downloadAndDeleteChapter)
        }

        private fun observeButton(){
            downloadChapter?.setOnClickListener {
                // TODO: коли додам room дописати логіку завантаження - видалення.
            }
        }


        fun clickChapter(callback: (String?) -> Unit) {
            nameChapter?.setOnClickListener {
                callback(nameChapter!!.text.toString())
            }
        }

        fun setNameView(name: String) {
            nameChapter?.text = name
        }

    }


    class ChaptersDiffCallback : DiffUtil.ItemCallback<Chapter>() {
        override fun areItemsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
            return oldItem == newItem
        }
    }

}