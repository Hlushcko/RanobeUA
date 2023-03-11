package com.ranobeua.mainApp.fragments.ranobe

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ranobeua.R


class RanobeListFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    private fun openRanobe(){
        val frag = activity?.supportFragmentManager?.beginTransaction()
        frag?.addToBackStack(null)
        frag?.replace(R.id.mainFrame, RanobeFragment())
        frag?.commit()
    }

}