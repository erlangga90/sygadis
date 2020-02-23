package com.erlangga.sygadis.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.erlangga.sygadis.R;
import com.erlangga.sygadis.admin.ScannerActivity;
import com.erlangga.sygadis.local.LocalManager;
import com.erlangga.sygadis.network.ServerManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static String SAVED_ADMIN = "saved-admin";

    private EditText nip;
    private EditText password;
    private Button masuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoginResponse loginResponse = new LocalManager(LoginActivity.this)
            .getObject(SAVED_ADMIN, new LoginResponse(null, null), LoginResponse.class);
        if (loginResponse.getNip() != null) {
            startActivity(new Intent(
                LoginActivity.this, ScannerActivity.class
            ));
            finish();
        }

        setContentView(R.layout.activity_login);

        nip = findViewById(R.id.nip);
        password = findViewById(R.id.password);
        masuk = findViewById(R.id.masuk);

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textNip = nip.getText().toString();
                String textPassword = password.getText().toString();
                ServerManager.INSTANCE.getServerApi().login(new LoginRequest(textNip, textPassword))
                    .enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call,
                            Response<LoginResponse> response) {
                            LoginResponse loginResponse = response.body();
                            if (loginResponse != null && loginResponse.getNip() != null) {
                                new LocalManager(LoginActivity.this)
                                    .putObject(SAVED_ADMIN, loginResponse);
                                Toast
                                    .makeText(LoginActivity.this, "Login Berhasil",
                                        Toast.LENGTH_SHORT)
                                    .show();
                                startActivity(new Intent(
                                    LoginActivity.this, ScannerActivity.class
                                ));
                                finish();
                            } else {
                                // ketika tidak terdaftar / error dari database 404
                                Toast
                                    .makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT)
                                    .show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            // ketika tidak terdaftar / error dari database
                            startActivity(new Intent(
                                LoginActivity.this, ScannerActivity.class
                            ));
                        }
                    });
            }
        });
    }


}
