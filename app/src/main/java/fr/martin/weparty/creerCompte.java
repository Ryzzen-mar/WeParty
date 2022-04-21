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

public class creerCompte extends AppCompatActivity {

    private EditText nomUtilisateurCreer;
    private EditText motPasseCreer;
    private EditText confirmerMotPasse;
    private Button creerButton;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte);


        nomUtilisateurCreer   = (EditText)findViewById(R.id.nom_utilisateur_creer);
        motPasseCreer = (EditText)findViewById(R.id.mot_de_passe_compte);
        confirmerMotPasse = (EditText)findViewById(R.id.confirmer_mot_passe);
        creerButton = (Button)findViewById(R.id.creer_botton);
        mAuth = FirebaseAuth.getInstance();

        creerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent connexionActivity = new Intent (getApplicationContext(), connexion.class);
                startActivity(connexionActivity);
                finish();
            }
        });
    }

    private void createUser(){
        String email = nomUtilisateurCreer.getText().toString();
        String password = motPasseCreer.getText().toString();
        String passwordverif =  confirmerMotPasse.getText().toString();

        if (TextUtils.isEmpty(email)){
            nomUtilisateurCreer.setError("Email cannot be empty");
            nomUtilisateurCreer.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            motPasseCreer.setError("Password cannot be empty");
            motPasseCreer.requestFocus();
        }else if (TextUtils.isEmpty(passwordverif)) {
            confirmerMotPasse.setError("Password cannot be empty");
            confirmerMotPasse.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(creerCompte.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(creerCompte.this, connexion.class));
                    }else{
                        Toast.makeText(creerCompte.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}