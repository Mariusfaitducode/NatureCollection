package fr.marscode.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.marscode.naturecollection.R
import fr.marscode.naturecollection.adapter.PlantAdapter

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        //Recuperer le recyclerview
        val horizonalRecyclerView = view?.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizonalRecyclerView?.adapter = PlantAdapter()

        return view
    }
}