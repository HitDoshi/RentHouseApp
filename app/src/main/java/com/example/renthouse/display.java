package com.example.renthouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class display extends AppCompatActivity {

    // our Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our
    // Database Reference for Firebase.
    DatabaseReference databaseReference;
    AutoCompleteTextView house_city , house_area;

    ListView listView;
    AutoCompleteTextView city , area;
    Button button;
    String h_city,h_area;
    addhouse a = new addhouse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        button = findViewById(R.id.search);
        listView = findViewById(R.id.list);

        ArrayList<String> list = new ArrayList<>();

        // below line is used to get the instance
        // of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get
        // reference for our database.

        // calling method
        // for getting data.
        //getdata();
        house_city.setAdapter(new ArrayAdapter<String>(display.this,
                android.R.layout.simple_list_item_1,a.city_list ));

        house_area.setAdapter(new ArrayAdapter<String>(display.this,
                android.R.layout.simple_list_item_1, a.area_list));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                h_city = house_city.getText().toString();
                h_area = house_area.getText().toString();

                databaseReference = firebaseDatabase.getReference().child("City")
                        .child(h_city).child("Area").child(h_area);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for(DataSnapshot snapshot1 : snapshot.getChildren())
                        {
                            String a = String.valueOf(snapshot1.getValue());
                            Toast.makeText(getApplicationContext(), a +":", Toast.LENGTH_SHORT).show();
                            list.add(a);
                        }
                        ArrayAdapter adapter = new ArrayAdapter<String>(display.this
                                , android.R.layout.simple_list_item_1,list);
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

       /*DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("City");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    HouseDetail info = snapshot1.getValue(HouseDetail.class);
                    String txt = info.getEmployeeContactNumber()+" : "+info.getOwnerEmail();
                    Toast.makeText(getApplicationContext(), txt +":", Toast.LENGTH_SHORT).show();
                    list.add(txt);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }

    private void getdata() {

        // calling add value event listener method
        // for getting the values from database.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                String value = snapshot.getValue(String.class);
                //Toast.makeText(getApplicationContext(), value +"*", Toast.LENGTH_SHORT).show();

                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    HouseDetail info = snapshot1.getValue(HouseDetail.class);
                    assert info != null;
                    String txt = info.getEmployeeContactNumber()+" : "+info.getOwnerEmail();
                    Toast.makeText(getApplicationContext(), txt +":", Toast.LENGTH_SHORT).show();
                }

                // after getting the value we are setting
                // our value to our text view in below line.
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(getApplicationContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}