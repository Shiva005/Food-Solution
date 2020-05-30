package com.bd.foodsolution.authentication;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bd.foodsolution.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ShowProfileActivity extends AppCompatActivity {
    private ImageView profilePic;
    private TextView profileName, profileAddress, profileEmail;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);

        toolbar = findViewById(R.id.toolbar_showprofile);
        setSupportActionBar(toolbar);
        this.setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        profilePic = findViewById(R.id.ivProfilePic);
        profileName = findViewById(R.id.ProfileName);
        profileEmail = findViewById(R.id.tvProfileEmail);
        profileAddress = findViewById(R.id.tvProfileAddress);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(profilePic);
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                UserGetterSetter userProfile = dataSnapshot.getValue(UserGetterSetter.class);
                profileName.setText( userProfile.getUserName().toString());
                profileEmail.setText( userProfile.getUserEmail().toString());
                profileAddress.setText(  userProfile.getUserAddress().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ShowProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        progressDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}