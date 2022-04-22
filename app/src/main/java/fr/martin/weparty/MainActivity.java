package fr.martin.weparty;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;
public class MainActivity extends AppCompatActivity {

    String prevActivity;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        BottomNavigationView barreNav = findViewById(R.id.bottomNavigationView2);
        barreNav.setOnNavigationItemSelectedListener(navListener);
        Intent intent = getIntent();
        System.out.println(intent);
        String prevActivity = intent.getStringExtra("activity");
        System.out.println(prevActivity);
        if(! Objects.equals(prevActivity, "first")){

            System.out.println("dans la boucle!");
            Intent connexionActivity = new Intent (getApplicationContext(), connexion.class);
            startActivity(connexionActivity);
            finish();
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch(item.getItemId()) {

                        case R.id.home_nav:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.map_nav:
                            selectedFragment = new MapFragment();
                            break;
                        case R.id.add_nav:
                            selectedFragment = new AddeventFragment();
                            break;
                        case R.id.settings_nav:
                            selectedFragment = new SettingsFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return true;
                }
            };
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, connexion.class));
        }
    }
}