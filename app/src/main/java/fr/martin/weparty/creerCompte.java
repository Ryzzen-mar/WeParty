package fr.martin.weparty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class creerCompte extends AppCompatActivity {
    private EditText nomUtilisateurCreer;
    private EditText motPasseCreer;
    private EditText confirmerMotPasse;
    private Button creerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte);


        nomUtilisateurCreer   = (EditText)findViewById(R.id.nom_utilisateur_creer);
        motPasseCreer = (EditText)findViewById(R.id.mot_de_passe_compte);
        confirmerMotPasse = (EditText)findViewById(R.id.confirmer_mot_passe);
        creerButton = (Button)findViewById(R.id.creer_botton);


        creerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String nomUtilisateurCreerStr=nomUtilisateurCreer.getText().toString();
                String motPasseCreerStr=motPasseCreer.getText().toString();
                String confirmerMotPasseStr=confirmerMotPasse.getText().toString();

                //Si nom utilisateur correspond à celui dans BDD et si mot de passe correspond {
                    Intent connexion = new Intent (getApplicationContext(), connexion.class);
                    connexion.putExtra("activity","first");
                    startActivity(connexion);
                    finish();
                //}
            }
        });

    }
}