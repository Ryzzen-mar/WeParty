package fr.martin.weparty;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.security.GeneralSecurityException;
import java.util.UUID;

import fr.martin.weparty.databinding.ActivityMainBinding;


public class AddeventFragment extends Fragment {


    //créations des variables------
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button envoyerEvenementButton;
    private EditText titreEvenement;
    private EditText dateEvenement;
    private EditText lieuEvenement;
    private EditText descEvenement;
    private ImageView image;
    private ProgressBar mProgressBar;
    private ImageView previewimage;
    private FirebaseStorage storage;
    private StorageTask mUploadTask;
    private StorageReference storageReference;
    private Uri mImageUri;
    FirebaseFirestore db;
    ActivityMainBinding binding;
    DatabaseReference mDatabaseRef;
    StorageReference mStorageRef;

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
        dateEvenement = (EditText) view.findViewById(R.id.date_evenement);
        lieuEvenement = (EditText) view.findViewById(R.id.lieu_evenement);
        descEvenement = (EditText) view.findViewById(R.id.desc_evenement);
        image = (ImageView) view.findViewById(R.id.piece_jointe);
        previewimage = (ImageView) view.findViewById(R.id.previewImage);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        db = FirebaseFirestore.getInstance();
        //--------

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
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
                uploadFile();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(getActivity()).load(mImageUri).into(previewimage);
        }
    }

    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(
                    "images/"
                            + UUID.randomUUID().toString());;

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Upload upload = new Upload(titreEvenement.getText().toString().trim(),lieuEvenement.getText().toString().trim(), dateEvenement.getText().toString().trim(),descEvenement.getText().toString().trim(),
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
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



