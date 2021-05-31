package com.bd.foodsolution.authentication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bd.foodsolution.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;

public class Registration extends AppCompatActivity {
    private EditText userName, userPassword,userConfirmPass, userEmailAddress, userAddress;
    private Button regButton;
    private ImageView userProfilePic;
    String Useremail, name, address, password,confirm;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        this.setTitle("Register");
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();

        userProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });



        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    //Upload data to the database
                    String user_email = userEmailAddress.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                //EmailVerification();
                                sendUserData();
                                firebaseAuth.signOut();
                                Toast.makeText(Registration.this, "Successfully Registered.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registration.this, LoginActivity.class));
                                finish();
                            }else{
                                Toast.makeText(Registration.this, "Registration Failed!!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                userProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setupUIViews(){
        userName = findViewById(R.id.etUserName);
        userPassword = findViewById(R.id.etUserPassword);

        userConfirmPass = findViewById(R.id.confirmPass);

        userEmailAddress = findViewById(R.id.etUserEmail);
        regButton = findViewById(R.id.btnRegister);
        userAddress =findViewById(R.id.etAge);
        userProfilePic = findViewById(R.id.ivProfile);

    }

    private Boolean validate(){
        Boolean result = false;

        name = userName.getText().toString();
        password = userPassword.getText().toString();
        confirm = userConfirmPass.getText().toString();
        Useremail = userEmailAddress.getText().toString().trim();
        address = userAddress.getText().toString();


        if(name.isEmpty() || password.isEmpty() || Useremail.isEmpty() || address.isEmpty() || imagePath == null){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            if(!confirm.equals(password))
            {
                Toast.makeText(this, "Password and Email not Matched !!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                result = true;
            }
        }
        return result;
    }

/*
    private void EmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(Registration.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(Registration.this, LoginActivity.class));
                    }else{
                        Toast.makeText(Registration.this, "Verification mail has not been sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }*/

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://food-solution-a5cbb-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = firebaseDatabase.getReference(Objects.requireNonNull(firebaseAuth.getUid()));
        StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic");  //User id/Images/Profile Pic.jpg
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Registration.this, "Upload failed, try Again !!", Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                //Toast.makeText(Registration.this, "Upload successful!", Toast.LENGTH_SHORT).show();
            }
        });
        UserGetterSetter userProfile = new UserGetterSetter( name, Useremail,address);
        myRef.setValue(userProfile);
    }
}