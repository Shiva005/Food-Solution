package com.bd.foodsolution;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FeedbackActivity extends AppCompatActivity {
    private EditText editText1,editText2,editText3;
    private Button button;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        toolbar = findViewById(R.id.toolbar_feedback);
        setSupportActionBar(toolbar);
        setTitle("Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText1 = findViewById(R.id.email_mainEmail);
        editText2 = findViewById(R.id.enter_subject);
        editText3 = findViewById(R.id.enter_message);
        button = findViewById(R.id.submit_id);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editText1.getText().toString();
                String[] email_name = email.split(",");

                String userSubject = editText2.getText().toString();
                String userMessage = editText3.getText().toString();

                if (TextUtils.isEmpty(editText2.getText().toString())){

                    editText2.setError("Enter subject");
                    return;
                }
                if (TextUtils.isEmpty(editText3.getText().toString()))
                {
                    editText3.setError("Enter your message");
                    return;
                }

                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_EMAIL, email_name);
                i.putExtra(Intent.EXTRA_SUBJECT, userSubject);
                i.putExtra(Intent.EXTRA_TEXT, userMessage);

                i.setType("message/rfc822");
                startActivity(Intent.createChooser(i,"Choose your option !"));
            }
        });
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