package fr.marscode.naturecollection.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import fr.marscode.naturecollection.MainActivity
import fr.marscode.naturecollection.PlantModel
import fr.marscode.naturecollection.PlantRepository
import fr.marscode.naturecollection.PlantRepository.Singleton.downloadURI
import fr.marscode.naturecollection.R
import java.util.UUID

class AddPlantFragment(
    private val context: MainActivity
) : Fragment(){

    private var file: Uri? = null
    private var uploadedImage:ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_add_plant, container, false)

        //recup uploadImage pour lui associer son composant

        uploadedImage = view?.findViewById(R.id.preview_image)

        //bouton qui charge image
        val pickupImageButton = view?.findViewById<Button>(R.id.upload_button)

        //clique --> ouvre image téléphone
        pickupImageButton?.setOnClickListener{ pickupImage()}

        //Bouron confirmer

        val confirmButton = view?.findViewById<Button>(R.id.confirm_button)
        confirmButton?.setOnClickListener { sendForm(view) }

        return view
    }

    private fun sendForm(view: View) {
        //heberger sur le bucket
        val repo = PlantRepository()
        repo.uploadImage(file!!){
            val plantName = view.findViewById<EditText>(R.id.name_input).text.toString()
            val plantDescription = view.findViewById<EditText>(R.id.description_input).text.toString()
            val grow = view.findViewById<Spinner>(R.id.grow_spinner).selectedItem.toString()
            val water = view.findViewById<Spinner>(R.id.water_spinner).selectedItem.toString()
            val downloadImageUrl = downloadURI

            //Creer un nouveau PlantModel
            val plant = PlantModel(
                UUID.randomUUID().toString(),
                plantName,
                plantDescription,
                downloadImageUrl.toString(),
                grow,
                water
            )
            //envoyer dans la BDD
            repo.insertPlant(plant)
        }



    }

    private fun pickupImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT

        //registerForActivityResult(Intent.createChooser(Intent(intent, "Select Picture")), 47)

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 47 && resultCode == Activity.RESULT_OK){

            //Verifier si les donnees sont nulles
            if ( data == null || data.data == null){ return }

            file = data.data

            //mettre a jour l'aperçu image
            uploadedImage?.setImageURI(file)



        }
    }
}