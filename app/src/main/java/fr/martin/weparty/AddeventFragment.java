package fr.martin.weparty;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddeventFragment extends Fragment {

    //créations des variables------
    private Button envoyerEvenementButton;
    private EditText titreEvenement;
    private EditText dateEvenement;
    private EditText lieuEvenement;
    private EditText descEvenement;
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
        dateEvenement = (EditText) view.findViewById(R.id.date);
        lieuEvenement = (EditText) view.findViewById(R.id.lieu);
        descEvenement = (EditText) view.findViewById(R.id.desc_evenement);
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


                Intent menuActivity = new Intent (getActivity(), MainActivity.class);//instanciation de la page visée
                menuActivity.putExtra("activity","first");//IMPORTANT, utilité dans MainActivity(lui permet de
                //connaitre l'activité précédente)
                startActivity(menuActivity);//départ vers la page visée

            }
        });
        //----------

    }


}
