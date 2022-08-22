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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class addhouse extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    TextInputEditText owner_username,owner_email,owner_contact,house_room,house_rentprice;
    Spinner house_flatetype ;
    AutoCompleteTextView house_city , house_area;
    Button upload;
    ImageView profile;
    String username;
    String email;
    String no;
    String city;
    String area;
    String room;
    String c;
    Integer price = 0;
    boolean e = false;
    int p=0;
    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    FirebaseDatabase firebaseDatabase1;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference1;

    FirebaseDatabase firebaseDatabase13;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference13;


    // creating a variable for
    // our object class
    HouseDetail houseDetail;
    ArrayList<String> city_list  = new ArrayList<>();
    ArrayList<String> area_list = new ArrayList<>();


    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addhouse);

        id();

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
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

        // Initialize firebase auth
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        // Check condition
        if (firebaseUser != null) {
            //displayToast(firebaseUser.getEmail());
            owner_username.setText(firebaseUser.getDisplayName());
            owner_email.setText(firebaseUser.getEmail());
            owner_contact.setText(firebaseUser.getPhoneNumber());
            //profile.setImageURI(firebaseUser.getPhotoUrl());
        }

        // below line is used to get the
        // instance of our FIrebase database.
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

        //Log.d("City1", String.valueOf(city_list.size()));
        //for (int i=0;i<city_list.size();i++) {
        // below line is used to get reference for our database.
            /*firebaseDatabase1 = FirebaseDatabase.getInstance();
            //databaseReference1 = firebaseDatabase1.getReference().child("City").child(city_list.get(0)).child("Area");

            databaseReference1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String b = String.valueOf(snapshot1.getRef().getKey());
                        //String b = String.valueOf(snapshot.child(a).child("Area").getValue());
                        Toast.makeText(getApplicationContext(), b, Toast.LENGTH_SHORT).show();
                        //list.add(a);
                        area_list.add(b);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });*/
        //}

        // initializing our object
        // class variable.

        owner_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        owner_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        owner_contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        house_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        house_area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        house_room.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        house_rentprice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();
                e = check();

                if (e){}
                //                  Toast.makeText(getApplicationContext(), "Successful Added", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Please fill all field", Toast.LENGTH_LONG).show();

                if (e) {

                    username = owner_username.getText().toString();
                    email = owner_email.getText().toString();
                    no = owner_contact.getText().toString();
                    city = house_city.getText().toString();
                    area = house_area.getText().toString();
                    room = house_room.getText().toString();
                    price = Integer.parseInt(house_rentprice.getText().toString());


                    /*houseDetail = new HouseDetail(username,email,no,city,area,
                            room,price);*/
                    houseDetail = new HouseDetail();
                    //addDatatoFirebase();
                    Intent intent = new Intent(addhouse.this, add_image_area.class);
                    intent.putExtra("username",username);
                    intent.putExtra("email",email);
                    intent.putExtra("no",no);
                    intent.putExtra("city",city);
                    intent.putExtra("area",area);
                    intent.putExtra("room",room);
                    intent.putExtra("price",price);

                    startActivity(intent);
                    /*Toast.makeText(getApplicationContext(), "Perfect",
                            Toast.LENGTH_SHORT).show();

                    databaseReference = firebaseDatabase.getReference().child("City")
                            .child(city).child("Area").child(area);

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snapshot1 : snapshot.getChildren())
                            {
                                String a = String.valueOf(snapshot1.getValue());
                                Toast.makeText(getApplicationContext(), a +":", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });*/
                }
            }
        });

        house_city.setAdapter(new ArrayAdapter<String>(addhouse.this,
                android.R.layout.simple_list_item_1, city_list));

        house_area.setAdapter(new ArrayAdapter<String>(addhouse.this,
                android.R.layout.simple_list_item_1, area_list));

    }

    private void id(){

        //TextInputEditText
        owner_username = findViewById(R.id.owner_username);
        owner_email = findViewById(R.id.owner_email);
        owner_contact = findViewById(R.id.owner_contact);
        house_city = findViewById(R.id.house_city);
        house_area = findViewById(R.id.house_area);
        house_room = findViewById(R.id.house_room);
        house_rentprice = findViewById(R.id.house_rentprice);

        //Spinner
        //house_flatetype = findViewById(R.id.house_flatetype);

        //Button
        upload = findViewById(R.id.upload);

        //imageview

        //profile = findViewById(R.id.profile);
    }

    private boolean check()
    {
        username = owner_username.getText().toString();
        email = owner_email.getText().toString();
        no = owner_contact.getText().toString();
        city = house_city.getText().toString();
        area = house_area.getText().toString();
        room = house_room.getText().toString();
        price = Integer.parseInt(house_rentprice.getText().toString());

        if(username.isEmpty())
        {
            owner_username.setError("This is Required");
            return false;
        }

        if(email.isEmpty())
        {
            owner_email.setError("This is Required");
            return false;
        }

        if(no.isEmpty())
        {
            owner_contact.setError("This is Required");
            return false;
        }

        if(city.isEmpty())
        {
            house_city.setError("This is Required");
            return false;
        }

        if(area.isEmpty())
        {
            house_area.setError("This is Required");
            return false;
        }

        if(room.isEmpty())
        {
            house_room.setError("This is Required");
            return false;
        }

        return true;
    }

    private void addDatatoFirebase() {
        // below 3 lines of code is used to set
        // data in our object class.
        houseDetail.setOwnerEmail(email);
        houseDetail.setOwnerName(username);
        houseDetail.setEmployeeContactNumber(no);
        houseDetail.setCity(city);
        houseDetail.setArea(area);
        houseDetail.setBHK(room);
        houseDetail.setPrice(price);

        firebaseDatabase13 = FirebaseDatabase.getInstance();
        databaseReference13 = firebaseDatabase13.getReference().child("City")
                .child(city).child("Area").child(area).push();

        //databaseReference.push().child("").setValue(houseDetail);

        // we are use add value event listener method
        // which is called with database reference.
        databaseReference13.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                //databaseReference.push().child("a").setValue(email);
                databaseReference13.setValue(houseDetail);

                // after adding this data we are showing toast message.
                Toast.makeText(getApplicationContext(), "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(getApplicationContext(), "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void area(){

        area_list.clear();

        for(int i=0;i<city_list.size();i++) {
            firebaseDatabase1 = FirebaseDatabase.getInstance();
            databaseReference1 = firebaseDatabase1.getReference().child("City").child(city_list.get(i)).child("Area");

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.addhouse) {

        }

        if(id==R.id.search_house)
        {
            Intent intent = new Intent(addhouse.this,dis.class);
            startActivity(intent);
            finish();
        }

        if(id==R.id.House)
        {
            Intent intent = new Intent(addhouse.this,YourAddHouse.class);
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

    @Override
    protected void onStart() {
        super.onStart();
        owner_contact.setText("");
        house_rentprice.setText("");
        house_room.setText("");
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