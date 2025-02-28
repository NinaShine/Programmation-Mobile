package com.example.tp2_suite

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp2_suite.data.PaysData

class ListePaysFragment : Fragment() {

    interface OnPaysSelectedListener {
        fun onPaysSelected(pays: Pays)
    }

    private var listener: OnPaysSelectedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnPaysSelectedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnPaysSelectedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_liste_pays, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recyclerView.adapter = PaysAdapter(PaysData.paysList) { pays ->
            Log.d("ListePaysFragment", "✅ Pays cliqué : ${pays.nom}")
            listener?.onPaysSelected(pays)
        }


        return view
    }
}
