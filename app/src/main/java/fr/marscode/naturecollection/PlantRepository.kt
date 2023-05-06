package fr.marscode.naturecollection

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.marscode.naturecollection.PlantRepository.Singleton.databaseRef
import fr.marscode.naturecollection.PlantRepository.Singleton.downloadURI
import fr.marscode.naturecollection.PlantRepository.Singleton.plantList
import fr.marscode.naturecollection.PlantRepository.Singleton.storageReference
//import fr.marscode.naturecollection.PlantRepository.Singleton.storageReference
import java.util.UUID

class PlantRepository {

    object Singleton{
        //lien pour accéder au bucket
        private val BUCKET_URL: String = "gs://nature-collection-fdb49.appspot.com"

        //se connecter à espace de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        //se connecter à la réference plants
        val databaseRef = FirebaseDatabase.getInstance().getReference("plants")

        //créer la liste contenant nos plantes
        val plantList = arrayListOf<PlantModel>()

        //contenir lien de l'image courante
        var downloadURI: Uri? = null
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

    //créer fonction pour envoyer fichier sur storage
    fun uploadImage(file: Uri, callback: () -> Unit){
        //verifier fichier non null
        if (file != null){
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = storageReference.child(fileName)
            val uploadTask = ref.putFile(file)

            //démarrer la tache d'envoi
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>{ task->

                //Si problème lors de l'envoi
                if (!task.isSuccessful){
                    task.exception?.let { throw it }
                }
                return@Continuation ref.downloadUrl
            }).addOnCompleteListener{ task ->

                if (task.isSuccessful){
                    downloadURI = task.result
                    callback()
                }
            }
        }
    }

    //mise à jour
    fun updatePlant(plant: PlantModel){
        databaseRef.child(plant.id).setValue(plant)
    }

    //Insérer nouvelle plante
    fun insertPlant(plant: PlantModel){
        databaseRef.child(plant.id).setValue(plant)
    }

    //supprimer plant de la base
    fun deletePlant(plant: PlantModel)= databaseRef.child(plant.id).removeValue()



}