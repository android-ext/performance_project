package com.lianjia.devext.androidh5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import static com.lianjia.devext.androidh5.MainActivity.NAME_KEY;

public class FormActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int FROM_RESULT_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        findViewById(R.id.form_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.form_btn:
                EditText editText = findViewById(R.id.form_name);
                String result = editText.getText().toString();
                if (TextUtils.isEmpty(result)) {
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(NAME_KEY, result.trim());
                setResult(FROM_RESULT_CODE, intent);
                finish();
                break;
        }
    }
}
