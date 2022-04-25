package fr.martin.weparty;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.Collection;

public class creerCompte extends AppCompatActivity {

    private EditText nomUtilisateurCreer;
    private EditText motPasseCreer;
    private EditText confirmerMotPasse;
    private Button creerButton;


    FirebaseFirestore db;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte);


        nomUtilisateurCreer = (EditText) findViewById(R.id.nom_utilisateur_creer);
        motPasseCreer = (EditText) findViewById(R.id.mot_de_passe_compte);
        confirmerMotPasse = (EditText) findViewById(R.id.confirmer_mot_passe);
        creerButton = (Button) findViewById(R.id.creer_botton);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        creerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                addtoDB();
                createUser();
                Intent connexionActivity = new Intent(getApplicationContext(), connexion.class);
                startActivity(connexionActivity);
                finish();
            }
        });
    }

    public void createUser() {
        String email = nomUtilisateurCreer.getText().toString();
        String password = motPasseCreer.getText().toString();
        String passwordverif = confirmerMotPasse.getText().toString();

        if (TextUtils.isEmpty(email)) {
            nomUtilisateurCreer.setError("Email cannot be empty");
            nomUtilisateurCreer.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            motPasseCreer.setError("Password cannot be empty");
            motPasseCreer.requestFocus();
        } else if (TextUtils.isEmpty(passwordverif)) {
            confirmerMotPasse.setError("Password cannot be empty");
            confirmerMotPasse.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(creerCompte.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(creerCompte.this, connexion.class));
                    } else {
                        Toast.makeText(creerCompte.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void addtoDB() {

        final String email = nomUtilisateurCreer.getText().toString();
        final String password = motPasseCreer.getText().toString();
        final String passwordverif = confirmerMotPasse.getText().toString();

        if (email.isEmpty()) {
            nomUtilisateurCreer.setError("Email cannot be empty");
            return;
        } else if (password.isEmpty()) {
            motPasseCreer.setError("Password cannot be empty");
            return;
        } else if (passwordverif.isEmpty()) {
            confirmerMotPasse.setError("Password cannot be empty");
            return;
        } else {
            CollectionReference colRef = db.collection("Users");
            Users user = new Users(email, password, passwordverif);
            colRef.document().set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Data has been successfully added to the database", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}