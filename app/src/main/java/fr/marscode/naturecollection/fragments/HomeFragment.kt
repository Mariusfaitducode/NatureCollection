package fr.marscode.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.marscode.naturecollection.MainActivity
import fr.marscode.naturecollection.PlantModel
import fr.marscode.naturecollection.R
import fr.marscode.naturecollection.adapter.PlantAdapter
import fr.marscode.naturecollection.adapter.PlantItemDecoration
import fr.marscode.naturecollection.PlantRepository.Singleton.plantList

class HomeFragment(private val context : MainActivity) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        //Créer une liste qui stocke les plantes




        //Recuperer le recyclerview
        val horizonalRecyclerView = view?.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizonalRecyclerView?.adapter = PlantAdapter(context, plantList, R.layout.item_horizontal_plant)

        val verticalRecyclerView = view?.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView?.adapter = PlantAdapter(context, plantList, R.layout.item_vertical_plant)
        verticalRecyclerView?.addItemDecoration(PlantItemDecoration())

        return view
    }
}