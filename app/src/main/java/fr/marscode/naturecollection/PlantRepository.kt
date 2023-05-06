package fr.marscode.naturecollection

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import fr.marscode.naturecollection.PlantRepository.Singleton.databaseRef
import fr.marscode.naturecollection.PlantRepository.Singleton.plantList

class PlantRepository {

    object Singleton{
        //se connecter à la réference plants
        val databaseRef = FirebaseDatabase.getInstance().getReference("plants")

        //créer la liste contenant nos plantes
        val plantList = arrayListOf<PlantModel>()
    }

    fun updateData(callback: () -> Unit){
        // absorber les données récupérés dans la databaseRef -> liste de plantes

        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                //retirer les anviennes plantes
                plantList.clear()

                //recolter la liste
                for (ds in snapshot.children){
                    //construire objet plant
                    val plant = ds.getValue(PlantModel::class.java)

                    //verifier que la plante n'est pas null
                    if (plant != null){
                        plantList.add(plant)
                    }
                }
                // actionner le callback
                callback()
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }

    fun updatePlant(plant: PlantModel){
        databaseRef.child(plant.id).setValue(plant)
    }

}