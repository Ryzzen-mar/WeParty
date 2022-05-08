package fr.martin.weparty;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddeventFragment extends Fragment {

    //créations des variables------
    private Button envoyerEvenementButton;
    private EditText titreEvenement;
    private EditText dateEvenement;
    private EditText lieuEvenement;
    private EditText descEvenement;
    private ImageView pieceJointe;
    private ImageView previewImage;
    int SELECT_PICTURE = 200;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    evenement eventInfo;
    //--------

    @Nullable
    @Override

    //affichage du layout---------
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.creation_evenement,container,false);
    }
    //--------

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        //instanciation des variables------
        envoyerEvenementButton = (Button) view.findViewById(R.id.envoyer_evenement);
        titreEvenement   = (EditText) view.findViewById(R.id.titre_evenement);
        dateEvenement = (EditText) view.findViewById(R.id.date_evenement);
        lieuEvenement = (EditText) view.findViewById(R.id.lieu_evenement);
        descEvenement = (EditText) view.findViewById(R.id.desc_evenement);
        pieceJointe = (ImageView) view.findViewById(R.id.piece_jointe);
        previewImage = (ImageView) view.findViewById(R.id.previewImage);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("eventInfo");
        eventInfo = new evenement();
        //--------




        //Quand tu cliques sur le bouton envoyer de la page creation_evenement...------
        envoyerEvenementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                //récupération des valeurs des champs et conversion en strings
                String titreEvenementStr=titreEvenement.getText().toString();
                String dateEvenementStr=dateEvenement.getText().toString();
                String lieuEvenementStr=lieuEvenement.getText().toString();
                String descEvenementStr=descEvenement.getText().toString();
                //-------

                if (TextUtils.isEmpty(titreEvenementStr) && TextUtils.isEmpty(dateEvenementStr) && TextUtils.isEmpty(lieuEvenementStr) && TextUtils.isEmpty(descEvenementStr)) {
                    // if the text fields are empty
                    // then show the below message.
                    Toast.makeText(getActivity(), "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    // else call the method to add
                    // data to our database.
                    addDatatoFirebase(titreEvenementStr, dateEvenementStr, lieuEvenementStr,descEvenementStr);
                }


                Intent menuActivity = new Intent (getActivity(), MainActivity.class);//instanciation de la page visée
                menuActivity.putExtra("activity","first");//IMPORTANT, utilité dans MainActivity(lui permet de
                //connaitre l'activité précédente)
                startActivity(menuActivity);//départ vers la page visée

            }
        });
        //----------

        pieceJointe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

    }

    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    previewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }


    private void addDatatoFirebase(String titreEvenementStr, String dateEvenementStr, String lieuEvenementStr, String descEvenementStr) {
        // below 3 lines of code is used to set
        // data in our object class.
        eventInfo.setEventTitle(titreEvenementStr);
        eventInfo.setEventDate(dateEvenementStr);
        eventInfo.setEventPlace(lieuEvenementStr);
        eventInfo.setEventDesc(descEvenementStr);

        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(eventInfo);

                // after adding this data we are showing toast message.
                Toast.makeText(getContext(), "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(getContext(), "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
