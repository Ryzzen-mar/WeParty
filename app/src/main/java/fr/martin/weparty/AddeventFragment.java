package fr.martin.weparty;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.security.GeneralSecurityException;
import java.util.UUID;

import fr.martin.weparty.databinding.ActivityMainBinding;


public class AddeventFragment extends Fragment {

    //créations des variables------
    private Button envoyerEvenementButton;
    private EditText titreEvenement;
    private EditText dateEvenement;
    private EditText lieuEvenement;
    private EditText descEvenement;
    private ImageView image;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    Uri ImageUri;
    FirebaseFirestore db;
    ActivityMainBinding binding;
    ActivityResultLauncher<String> mGetContent;

    //--------

    @Override

    //affichage du layout---------
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.creation_evenement, container, false);
    }
    //--------

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        //instanciation des variables------
        envoyerEvenementButton = (Button) view.findViewById(R.id.envoyer_evenement);
        titreEvenement = (EditText) view.findViewById(R.id.titre_evenement);
        dateEvenement = (EditText) view.findViewById(R.id.date);
        lieuEvenement = (EditText) view.findViewById(R.id.lieu);
        descEvenement = (EditText) view.findViewById(R.id.desc_evenement);
        image = (ImageView) view.findViewById(R.id.imageView);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        db = FirebaseFirestore.getInstance();
        //--------

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            ImageUri = result;
                        }
                    }
                });


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
                uploadPicture();
            }
        });

        //Quand tu cliques sur le bouton envoyer de la page creation_evenement...------
        envoyerEvenementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    addtoDB();
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                }
                Intent menuActivity = new Intent(getActivity(), MainActivity.class);//instanciation de la page visée
                menuActivity.putExtra("activity", "first");//IMPORTANT, utilité dans MainActivity(lui permet de
                //connaitre l'activité précédente)
                startActivity(menuActivity);//départ vers la page visée

            }
        });
    }

    private void uploadPicture() {

        final String randomkey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("image/*" + randomkey);
        riversRef.putFile(ImageUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        Toast.makeText(getActivity().getApplicationContext(), "Image téléchargée", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Erreur", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void addtoDB() throws GeneralSecurityException {

        //récupération des valeurs des champs et conversion en strings
        String titreEvenementStr = titreEvenement.getText().toString();
        String dateEvenementStr = dateEvenement.getText().toString();
        String lieuEvenementStr = lieuEvenement.getText().toString();
        String descEvenementStr = descEvenement.getText().toString();
        //-------

        if (titreEvenementStr.isEmpty()) {
            titreEvenement.setError("Ce champ ne peut pas être vide");
            titreEvenement.requestFocus();

        } else if (dateEvenementStr.isEmpty()) {
            dateEvenement.setError("Ce champ ne peut pas être vide");
            dateEvenement.requestFocus();

        } else if (lieuEvenementStr.isEmpty()) {
            lieuEvenement.setError("Ce champ ne peut pas être vide");
            lieuEvenement.requestFocus();

        } else if (descEvenementStr.isEmpty()) {
            descEvenement.setError("Ce champ ne peut pas être vide");
            descEvenement.requestFocus();
        } else {
            CollectionReference colRef = db.collection("Evenements");
            Evenement Event = new Evenement(titreEvenementStr, dateEvenementStr, lieuEvenementStr, descEvenementStr);
            colRef.document().set(Event).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity().getApplicationContext(), "L'événement a bien été ajouté", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Erreur" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}



