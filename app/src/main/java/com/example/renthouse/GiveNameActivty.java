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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class GiveNameActivty extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    TextView name , email , no , city , area  , bhk , address , price ;
    SliderView sliderView;
    ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
    ArrayList<HouseDetail> object;
    int position = 0;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        sliderView = findViewById(R.id.slider);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        no = findViewById(R.id.no);
        city = findViewById(R.id.city);
        area = findViewById(R.id.area);
        bhk = findViewById(R.id.bhk);
        address = findViewById(R.id.address);
        price = findViewById(R.id.price);

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


        //HouseDetail h = (HouseDetail) getIntent().getSerializableExtra("all");

        /*Bundle bundle = this.getIntent().getExtras();
        HouseDetail houseDetail = new HouseDetail();
        if (bundle != null) {
            houseDetail = (HouseDetail) bundle.getSerializable("all");
        }*/

        Intent intent = getIntent();
        object = (ArrayList<HouseDetail>) getIntent().getSerializableExtra("ARRAYLIST");
        position = getIntent().getIntExtra("position",0);
        //HouseDetail h = getIntent().getParcelableExtra("all");

       /* Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("bundle");
        List<HouseDetail> object = (List<HouseDetail>) args.getSerializable("all");*/

            for (int i=0;i<object.get(position).getImage().size();i++)
        {
            sliderDataArrayList.add(new SliderData(object.get(position).getImage().get(i)));
        }

        SliderAdapter adapter = new SliderAdapter(GiveNameActivty.this, sliderDataArrayList);

        name.setText(object.get(position).getOwnerNames());
        email.setText(object.get(position).getOwnerEmail());
        no.setText(object.get(position).getEmployeeContactNumber());
        city.setText(object.get(position).getCity());
        area.setText(object.get(position).getArea());
        bhk.setText(object.get(position).getBHK());
        address.setText(object.get(position).getAddress());;
        price.setText(object.get(position).getPrice()+"");
        Log.d("all123",object.get(position).getPrice()+"");
        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);

        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(3);

        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);

        // to start autocycle below method is used.
        sliderView.startAutoCycle();
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
            Intent intent = new Intent(GiveNameActivty.this,addhouse.class);
            startActivity(intent);
            finish();
        }

        if(id==R.id.search_house)
        {
            Intent intent = new Intent(GiveNameActivty.this,dis.class);
            startActivity(intent);
            finish();
        }

        if(id==R.id.House)
        {
            Intent intent = new Intent(GiveNameActivty.this,YourAddHouse.class);
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