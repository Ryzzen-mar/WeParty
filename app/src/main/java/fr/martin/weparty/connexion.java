package fr.martin.weparty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class connexion extends AppCompatActivity {

    private Button creerBouton;
    private Button connexionBouton;
    private TextView lien;
    private EditText nomUtilisateurConnexion;
    private EditText motPasseConnexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);


        creerBouton = (Button) findViewById(R.id.bout_creer_compte);
        connexionBouton = (Button) findViewById(R.id.bouton_connexion);
        nomUtilisateurConnexion   = (EditText)findViewById(R.id.nom_utilisateur);
        motPasseConnexion = (EditText)findViewById(R.id.mot_de_passe);
        TextView lien = findViewById(R.id.mot_oublie);

        lien.setMovementMethod(LinkMovementMethod.getInstance());

        creerBouton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent creerCompteActivity = new Intent (getApplicationContext(), creerCompte.class);
                startActivity(creerCompteActivity);
                finish();
            }
        });

        connexionBouton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String nomUtilisateurConnexionStr=nomUtilisateurConnexion.getText().toString();
                String motPasseConnexionStr=motPasseConnexion.getText().toString();
                System.out.println(nomUtilisateurConnexionStr);
                System.out.println(motPasseConnexionStr);
                //Si nom utilisateur correspond à celui dans BDD et si mot de passe correspond {
                    Intent menuActivity = new Intent (getApplicationContext(), MainActivity.class);
                    menuActivity.putExtra("activity","first");
                    startActivity(menuActivity);
                    finish();
                //}
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


}