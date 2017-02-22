package com.grenoble.miage.maliste;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    private EditText editText ;
    Button button ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editText = (EditText) findViewById(R.id.activity_add_editText);
        /*button = (Button) findViewById(R.id.activity_add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                result.putExtra(MainActivity.NEW_VALUE, editText.getText().toString());
                setResult(Activity.RESULT_OK, result);
                AddActivity.this.finish();
            }
        });*/
    }

    public void addNewValue(View view) {
        if(editText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, R.string.mandatory_message, Toast.LENGTH_LONG).show();
        } else {
            Intent result = new Intent();
            result.putExtra(MainActivity.NEW_VALUE, editText.getText().toString());
            setResult(Activity.RESULT_OK, result);
            AddActivity.this.finish();
        }
    }
}
