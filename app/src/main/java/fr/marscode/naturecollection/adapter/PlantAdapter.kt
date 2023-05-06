package fr.marscode.naturecollection.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.marscode.naturecollection.MainActivity
import fr.marscode.naturecollection.PlantModel
import fr.marscode.naturecollection.PlantPopup
import fr.marscode.naturecollection.PlantRepository
import fr.marscode.naturecollection.PlantRepository.Singleton.databaseRef
import fr.marscode.naturecollection.R

class PlantAdapter(
    val context: MainActivity,
    private val plantList: List<PlantModel>,
    private val layoutId: Int) : RecyclerView.Adapter<PlantAdapter.ViewHolder>(){

    //Boite pour ranger tout les composants à controler
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        //Image de la plante
        val plantImage = view.findViewById<ImageView>(R.id.image_item)
        val plantName: TextView? = view.findViewById<TextView>(R.id.name_item)
        val plantDescription: TextView? = view.findViewById<TextView>(R.id.description_item)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = plantList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //recuperer les informations de la plante
        val currentPlant = plantList[position]

        //Recup repository
        val repo = PlantRepository()

        //utiliser glide pour recuperer image avec lien
        Glide.with(context).load(Uri.parse(currentPlant.imageUrl)).into(holder.plantImage)

        //mettre à jour nom de la plante
        holder.plantName?.text = currentPlant.name

        //mettre à jour la description de la plante
        holder.plantDescription?.text = currentPlant.description

        // verifier si la plante a été liké
        if(currentPlant.liked){
            holder.starIcon.setImageResource(R.drawable.ic_star)
        }
        else{
            holder.starIcon.setImageResource(R.drawable.ic_unstar)
        }

        //rajouter interraction sur l'étoile
        holder.starIcon.setOnClickListener {

            //inverser le bouton de like
            currentPlant.liked = !currentPlant.liked

            //mettre à jour
            repo.updatePlant(currentPlant)
        }

        //rajouter interaction clique sur la plante
        holder.itemView.setOnClickListener{
            PlantPopup(this, currentPlant).show()
        }
    }
    //mettre à jour objet plante dans bdd


}