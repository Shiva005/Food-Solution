package com.bd.foodsolution.authentication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bd.foodsolution.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePassword extends AppCompatActivity {
    private Button update;
    private EditText newPassword, confirm;
    private FirebaseUser firebaseUser;
    Toolbar toolbar;
    //private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        toolbar = findViewById(R.id.toolbar_updatepass);
        setSupportActionBar(toolbar);
        this.setTitle("Update Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newPassword = findViewById(R.id.etNewPassword);
        confirm = findViewById(R.id.confirmEditText);
        update = findViewById(R.id.btnUpdatePassword);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String userPasswordNew = newPassword.getText().toString();
                final String confirmPasswordNew = confirm.getText().toString();

                if (!userPasswordNew.isEmpty() && !confirmPasswordNew.isEmpty()) {

                    firebaseUser.updatePassword(userPasswordNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (userPasswordNew.equals(confirmPasswordNew)) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UpdatePassword.this, "Password update successful", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(UpdatePassword.this, "Password Update Failed !!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(UpdatePassword.this, "Password not Matched !!", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {
                    Toast.makeText(UpdatePassword.this, "Enter Credentials !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
