package com.example.renthouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseAppLifecycleListener;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Queue;

public class dis extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    TextView t1,t2,t3,t4,t5,t6,t7;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseDatabase firebaseDatabase1;

    DatabaseReference databaseReference1;
    AutoCompleteTextView house_city , house_area;

    ListView listView;
    AutoCompleteTextView city , area;
    Button button;
    public String h_city,h_area;
    addhouse a = new addhouse();

    ArrayList<String> city_list  = new ArrayList<>();
    ArrayList<String> area_list = new ArrayList<>();

    ArrayList<HouseDetail> all = new ArrayList<HouseDetail>();

    FirebaseListAdapter<String> adapter;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dis);

        button = findViewById(R.id.search);
        listView = findViewById(R.id.list);
        house_city = findViewById(R.id.house_city);
        house_area = findViewById(R.id.house_area);

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        navigationView = findViewById(R.id.navigation);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference().child("City");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                city_list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String a = String.valueOf(snapshot1.getRef().getKey());
                    String b = String.valueOf(snapshot.child(a).child("Area").getValue());
                    //Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
                    city_list.add(a);
                    Log.d("City", String.valueOf(city_list.size()));
                }
                area();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // below line is used to get
        // reference for our database.

        // calling method
        // for getting data.
        //getdata();
        house_city.setAdapter(new ArrayAdapter<String>(dis.this,
                android.R.layout.simple_list_item_1,city_list ));

        house_area.setAdapter(new ArrayAdapter<String>(dis.this,
                android.R.layout.simple_list_item_1, area_list));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(check()) {

                    Intent intent = new Intent(dis.this, listviewdisplay.class);
                    intent.putExtra("city", h_city);
                    intent.putExtra("area", h_area);
                    startActivity(intent);

                    //Toast.makeText(dis.this, "Hi", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }

    public void area(){

        area_list.clear();

        for(int i=0;i<city_list.size();i++) {
            firebaseDatabase1 = FirebaseDatabase.getInstance();
            databaseReference1 = firebaseDatabase1.getReference().child("City").
                    child(city_list.get(i)).child("Area");

            databaseReference1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String b = String.valueOf(snapshot1.getRef().getKey());
                        //String b = String.valueOf(snapshot.child(a).child("Area").getValue());
                        //Toast.makeText(getApplicationContext(), b, Toast.LENGTH_SHORT).show();
                        //list.add(a);
                        area_list.add(b);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public boolean check()
    {
        h_city = house_city.getText().toString();
        h_area = house_area.getText().toString();

        if(h_city.isEmpty())
        {
            house_city.setError("This is Required");
            return false;
        }

        if(h_area.isEmpty())
        {
            house_area.setError("This is Required");
            return false;
        }
        return true;
    }

    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.addhouse) {
            Intent intent = new Intent(dis.this,addhouse.class);
            startActivity(intent);
            finish();
        }

        if(id==R.id.House)
        {
            Intent intent = new Intent(dis.this,YourAddHouse.class);
            startActivity(intent);
            finish();
        }

        if(id==R.id.logout)
        {
            GoogleSignInClient googleSignInClient;

            FirebaseAuth firebaseAuth;

            // Initialize firebase auth
            firebaseAuth=FirebaseAuth.getInstance();

            // Initialize firebase user
            FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

            // Initialize sign in client
            googleSignInClient= GoogleSignIn.getClient(getApplicationContext()
                    , GoogleSignInOptions.DEFAULT_SIGN_IN);


            googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    // Check condition
                    if(task.isSuccessful())
                    {
                        // When task is successful
                        // Sign out from firebase
                        firebaseAuth.signOut();


                        Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.my_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.alert)
                .setTitle(R.string.app_name)
                .setMessage("Do you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}