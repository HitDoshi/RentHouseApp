package com.example.renthouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class listviewdisplay extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<HouseDetail> all = new ArrayList<HouseDetail>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> image;
    ArrayList<String> children;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listviewdisplay);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(listviewdisplay.this);
//                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));

        drawerLayout = findViewById(R.id.my_drawer_layout);
        navigationView = findViewById(R.id.navigation);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(listviewdisplay.this);
//                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //recyclerView.setLayoutManager(linearLayoutManager);
        String h_city = getIntent().getStringExtra("city");
        String h_area = getIntent().getStringExtra("area");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("City")
                .child(h_city).child("Area").child(h_area);

//        String name = String.valueOf(snapshot1.child("ownerNames").getValue());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                all.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    String name = String.valueOf(snapshot1.child("ownerNames").getValue());
                    String email = String.valueOf(snapshot1.child("ownerEmail").getValue());
                    String number = String.valueOf(snapshot1.child("employeeContactNumber").getValue());
                    String city = String.valueOf(snapshot1.child("city").getValue());
                    String area = String.valueOf(snapshot1.child("area").getValue());
                    String bhk = String.valueOf(snapshot1.child("bhk").getValue());
                    String address = String.valueOf(snapshot1.child("address").getValue());
                    Integer price = Integer.valueOf(String.valueOf(snapshot1.child("price").getValue()));

                    /*String i = String.valueOf(snapshot.child("image").getChildren());
                    Log.d("Image",i);
                    int temp=0;*/

                    children= snapshot1.child("image").getValue(new GenericTypeIndicator<ArrayList<String>>(){});

                 /*   Log.d("Size123", String.valueOf(children.get(0)));
                    Log.d("Size123", String.valueOf(children.get(1)));*/

                    /*for (DataSnapshot snapshot2 : snapshot1.child("image").getChildren()) {
                        //String value2 = (String) snapshot1.getKey();
                        String value = (String) snapshot2.getValue();
                        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
                        Log.d("link123",value);
                        image.add(value);
                        temp++;
                        // after getting the value we are setting
                        // our value to our text view in below line.
                        //sliderDataArrayList.add(new SliderData(value));
                    }*/
                    //ArrayList<String> image = String.valueOf(snapshot1.child("image").getValue());

                    HouseDetail houseDetail = new HouseDetail(name,email,number,city,area,bhk,address,children,price);

                    // Toast.makeText(getApplicationContext(), name +":", Toast.LENGTH_SHORT).show();
                    all.add(houseDetail);
                    Log.d("pre1","pre1");

                }

                /*HouseDetail h = new HouseDetail("a","a","a","a","a","a",1);
                List<HouseDetail> a = new ArrayList<>();
                a.add(h);*/
                listRecycler listRecycler = new listRecycler(listviewdisplay.this,all);

                recyclerView.setAdapter(listRecycler);

                Log.d("re","re");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d("pre","pre");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.addhouse) {
            Intent intent = new Intent(listviewdisplay.this,addhouse.class);
            startActivity(intent);
            finish();
        }

        if(id==R.id.search_house)
        {

        }

        if(id==R.id.House)
        {
            Intent intent = new Intent(listviewdisplay.this,YourAddHouse.class);
            startActivity(intent);
            finish();
        }

        if(id==R.id.logout)
        {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.alert)
                    .setTitle(R.string.app_name)
                    .setMessage("Are you sure you want to Logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
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

                                        // Display Toast
                                        Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.my_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}