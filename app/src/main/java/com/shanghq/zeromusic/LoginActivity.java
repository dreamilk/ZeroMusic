package com.shanghq.zeromusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.shanghq.zeromusic.Utils.RealmUtils;
import com.shanghq.zeromusic.bean.UserBean;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity {

    private Button LoginButton;

    private TextInputEditText editTextId;
    private TextInputEditText editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setNavigationBarColor(getColor(R.color.colorPrimary));


        editTextId = findViewById(R.id.et_id);
        editTextPassword = findViewById(R.id.et_password);

        LoginButton = findViewById(R.id.login);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RealmUtils.openRealm();
                RealmUtils.Login("1", editTextId.getText().toString(), editTextPassword.getText().toString());
                RealmUtils.closeRealm();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
