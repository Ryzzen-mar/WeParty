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

public class connexion extends AppCompatActivity {

    private Button creerBouton;
    private Button connexionBouton;
    private TextView lien;
    private EditText nomUtilisateurConnexion;
    private EditText motPasseConnexion;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);


        creerBouton = (Button) findViewById(R.id.bout_creer_compte);
        connexionBouton = (Button) findViewById(R.id.bouton_connexion);
        nomUtilisateurConnexion   = (EditText)findViewById(R.id.nom_utilisateur);
        motPasseConnexion = (EditText)findViewById(R.id.mot_de_passe);
        TextView lien = findViewById(R.id.mot_oublie);

        mAuth = FirebaseAuth.getInstance();
        lien.setMovementMethod(LinkMovementMethod.getInstance());

        connexionBouton.setOnClickListener(view -> {
            loginUser();
        });

        creerBouton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent creerCompteActivity = new Intent (getApplicationContext(), creerCompte.class);
                startActivity(creerCompteActivity);
                finish();
            }
        });

        lien.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent changePassActivity = new Intent (getApplicationContext(), changePass.class);
                startActivity(changePassActivity);
                finish();
            }
        });


    }

    private void loginUser() {
            String email = nomUtilisateurConnexion.getText().toString();
            String password = motPasseConnexion.getText().toString();

            if (TextUtils.isEmpty(email)){
                nomUtilisateurConnexion.setError("Email cannot be empty");
                nomUtilisateurConnexion.requestFocus();
            }else if (TextUtils.isEmpty(password)){
                motPasseConnexion.setError("Password cannot be empty");
                motPasseConnexion.requestFocus();
            }else{
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(connexion.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(connexion.this, MainActivity.class));
                        }else{
                            Toast.makeText(connexion.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }