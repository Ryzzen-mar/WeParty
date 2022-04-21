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

    private Button envoyerEvenementButton;
    private EditText titreEvenement;
    private EditText descEvenement;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.creation_evenement,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        envoyerEvenementButton = (Button) view.findViewById(R.id.envoyer_evenement);
        titreEvenement   = (EditText) view.findViewById(R.id.titre_evenement);
        descEvenement = (EditText) view.findViewById(R.id.desc_evenement);


        envoyerEvenementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String titreEvenementStr=titreEvenement.getText().toString();
                String descEvenementStr=descEvenement.getText().toString();

                //Si nom utilisateur correspond à celui dans BDD et si mot de passe correspond {
                Intent menuActivity = new Intent (getActivity(), MainActivity.class);
                menuActivity.putExtra("activity","first");
                startActivity(menuActivity);
                //}
            }
        });

    }


}
