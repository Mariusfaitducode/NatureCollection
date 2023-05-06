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

class HomeFragment(private val context : MainActivity) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        //Créer une liste qui stocke les plantes

        val plantlist = arrayListOf<PlantModel>()

        //Enregistrer dans la liste

        plantlist.add(
            PlantModel(
            "pissenlit",
            "il faut souffler",
            "https://cdn.pixabay.com/photo/2015/02/06/19/19/flower-626389_960_720.jpg",
            false
        )
        )

        plantlist.add(
            PlantModel(
                "rose",
                "fleur de l'amour",
                "https://cdn.pixabay.com/photo/2015/04/19/08/32/rose-729509_960_720.jpg",
                false
            )
        )

        plantlist.add(
            PlantModel(
                "cactus",
                "ça pique !",
                "https://cdn.pixabay.com/photo/2021/10/26/12/23/cactus-6743531_960_720.jpg",
                true
            )
        )

        plantlist.add(
            PlantModel(
                "tulipe",
                "de toutes les couleurs",
                "https://cdn.pixabay.com/photo/2018/05/01/13/53/tulips-3365630_960_720.jpg",
                false
            )
        )

        //Recuperer le recyclerview
        val horizonalRecyclerView = view?.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizonalRecyclerView?.adapter = PlantAdapter(context, plantlist, R.layout.item_horizontal_plant)

        val verticalRecyclerView = view?.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView?.adapter = PlantAdapter(context, plantlist, R.layout.item_vertical_plant)
        verticalRecyclerView?.addItemDecoration(PlantItemDecoration())

        return view
    }
}