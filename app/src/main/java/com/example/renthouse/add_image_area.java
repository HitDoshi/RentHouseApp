package com.example.renthouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class add_image_area extends AppCompatActivity {

    SliderView sliderView;
    TextInputEditText house_address;
    //AutoCompleteTextView owner_area;
    // we are creating array list for storing our image urls.
    ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
    ArrayList<String> array = new ArrayList<String>();
    ProgressDialog progressDialog;
    ArrayList<Uri> mArrayUri;
    int PICK_IMAGE_MULTIPLE = 1;
    int position=0;
    Button upload;
    HouseDetail houseDetail;
    String username;
    String email;
    String no;
    String city;
    String area;
    String room;
    String address;
    String c;
    Integer price = 0;


    FirebaseDatabase firebaseDatabase13;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference13;
    StorageReference imageFolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image_area);
        sliderView = findViewById(R.id.slider);
        sliderView.setBackgroundResource(R.drawable.abc);

        house_address = findViewById(R.id.address);
        //owner_area = findViewById(R.id.house_area);
        upload = findViewById(R.id.upload);
        progressDialog  = new ProgressDialog(this);
        mArrayUri = new ArrayList<Uri>();

        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
        no = getIntent().getStringExtra("no");
        city = getIntent().getStringExtra("city");
        area = getIntent().getStringExtra("area");
        room = getIntent().getStringExtra("room");;
        price = getIntent().getIntExtra("price",0);

        //progressDialog.show();
        // Create a storage reference from our app
        imageFolder= FirebaseStorage.getInstance().getReference().child("ImageFolder");
        Log.d("a","1");

        sliderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mArrayUri.clear();
                sliderDataArrayList.clear();
                array.clear();
                // initialising intent
                Intent intent = new Intent();

                // setting type to select to be image
                intent.setType("image/*");

                // allowing multiple image to be selected
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);

                // adding the urls inside array list
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                progressDialog.setMessage("Please Wait A Minute...");
                address = house_address.getText().toString();

                StoreLink();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // When an Image is picked
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
            // Get the Image from data

            progressDialog.setCancelable(false);
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                int cout = data.getClipData().getItemCount();
                for (int i = 0; i < cout; i++) {
                    // adding imageuri in array
                    Uri imageurl = data.getClipData().getItemAt(i).getUri();
                    mArrayUri.add(imageurl);
                    sliderDataArrayList.add(new SliderData(imageurl.toString()));
                    //array.add(imageurl.toString());

                    Log.d("find","find");

                }

                // setting 1st selected image into image switcher
                //imageView.setImageURI(mArrayUri.get(0));
                // passing this array list inside our adapter class.
                SliderAdapter adapter = new SliderAdapter(add_image_area.this, sliderDataArrayList);

                sliderView.setBackgroundResource(0);
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
                position = 0;
            } else {
                Uri imageurl = data.getData();
                mArrayUri.add(imageurl);
                sliderDataArrayList.add(new SliderData(imageurl.toString()));
                //array.add(imageurl.toString());
                //imageView.setImageURI(mArrayUri.get(0));
                position = 0;

                /*Uri IndividualImage = imageurl;
                StorageReference ImageName = imageFolder.child("Image"+IndividualImage);
                Log.d("a","2");
                ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = String.valueOf(uri);
                                array.add(url);
                                Log.d("URL0",url);
                                //StoreLink(url);

                            }
                        });
                    }
                });*/

                // passing this array list inside our adapter class.
                SliderAdapter adapter = new SliderAdapter(add_image_area.this, sliderDataArrayList);

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

        } else {
            // show this if no image is selected
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }


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
        houseDetail.setAddress(address);
        houseDetail.setPrice(price);
        houseDetail.setImage(array);

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

                for(int i=0;i<mArrayUri.size();i++){
                   /* Uri IndividualImage = mArrayUri.get(i);
                    StorageReference ImageName = imageFolder.child("Image"+IndividualImage);
                    Log.d("a","2");
                    ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = String.valueOf(uri);
                                    Log.d("URL",url);
                                    //StoreLink(url);
                                }
                            });
                        }
                    });*/
                }

                //progressDialog.dismiss();
                // after adding this data we are showing toast message.
                Toast.makeText(getApplicationContext(), "data added", Toast.LENGTH_SHORT).show();
                house_address.setText("");
                sliderDataArrayList.clear();
                // passing this array list inside our adapter class.
                SliderAdapter adapter = new SliderAdapter(add_image_area.this, sliderDataArrayList);

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
                mArrayUri.clear();
                sliderView.setBackgroundResource(R.drawable.abc);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(getApplicationContext(), "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

/*
    firebaseDatabase13 = FirebaseDatabase.getInstance();
    databaseReference13 = firebaseDatabase13.getReference().child("City")
                .child(city).child("Area").child(area).push();
*/

    private void StoreLink(){

        for(int i=0;i<mArrayUri.size();i++) {

            Uri IndividualImage = mArrayUri.get(i);
            StorageReference ImageName = imageFolder.child("Image" + IndividualImage);
            ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = String.valueOf(uri);
                            array.add(url);
                            Log.d("URL1", url);

                            if(array.size()==sliderDataArrayList.size()) {
                                add();
                            }
                            //StoreLink(url);
                        }
                    });
                }
            });
        }
       /* Log.d("a","3");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("City").child(city).child("Area").child(area).child("ImgLink");
       *//* HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("Imglink",url);
*//*
        databaseReference.push().setValue(url);
        progressDialog.dismiss();*/

    }

    private void add()
    {
        houseDetail = new HouseDetail(username,email,no,city,area,
                room,address,array,price);
        houseDetail = new HouseDetail();

        if(array.size()==sliderDataArrayList.size()) {
            addDatatoFirebase();
        }
    }

}