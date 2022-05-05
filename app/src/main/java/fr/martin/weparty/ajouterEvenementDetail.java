package fr.martin.weparty;

import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class ajouterEvenementDetail extends AppCompatActivity {

    private TextView date;
    private TextView heure;
    private TextView lieu;
    private TextView type;
    private ImageView image;
    private ImageView favoris;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichage_detaille);

        //recuperation des objets de la page xml-------
        date   = (TextView) findViewById(R.id.date_detail);
        heure   = (TextView) findViewById(R.id.heure_detail);
        lieu   = (TextView) findViewById(R.id.lieu_detail);
        type   = (TextView) findViewById(R.id.type_detail);
        image  = (ImageView)findViewById(R.id.image_detail);
        favoris = (ImageView)findViewById(R.id.favoris_details);
        //----------




    }



}
