package com.erlangga.sygadis.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.erlangga.sygadis.R;
import com.erlangga.sygadis.local.LocalManager;
import com.erlangga.sygadis.login.LoginActivity;
import com.erlangga.sygadis.login.LoginResponse;
import com.erlangga.sygadis.network.ServerManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerActivity extends AppCompatActivity {

    Button scan;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        scan = findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IntentIntegrator(ScannerActivity.this).initiateScan();
            }
        });

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new LocalManager(ScannerActivity.this).removePreference(LoginActivity.SAVED_ADMIN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                String nip = result.getContents();
                String nipPemeriksa = new LocalManager(ScannerActivity.this)
                    .getObject(
                        LoginActivity.SAVED_ADMIN,
                        new LoginResponse(null, null),
                        LoginResponse.class
                    )
                    .getNip();
                PresensiRequest request = new PresensiRequest(nip, nipPemeriksa);
                ServerManager.INSTANCE.getServerApi()
                    .presensi(request)
                    .enqueue(new Callback<PresensiResponse>() {
                        @Override
                        public void onResponse(Call<PresensiResponse> call,
                            Response<PresensiResponse> response) {
                            if (response.code() == 200) {
                                int type = Integer.parseInt(response.body().getType());
                                if (type == 1) {
                                    Toast.makeText(ScannerActivity.this, "Checkin berhasil",
                                        Toast.LENGTH_LONG)
                                        .show();
                                } else {
                                    Toast.makeText(ScannerActivity.this, "Checkout berhasil",
                                        Toast.LENGTH_LONG)
                                        .show();
                                }
                            } else {
                                try {
                                    if (response.code() == 406) {
                                        Toast
                                            .makeText(ScannerActivity.this,
                                                "Checkout gagal, belum kerja 8 jam!",
                                                Toast.LENGTH_LONG)
                                            .show();
                                    } else if (response.code() == 409) {
                                        Toast
                                            .makeText(ScannerActivity.this,
                                                "Sudah checkout, silakan pulang!",
                                                Toast.LENGTH_LONG)
                                            .show();
                                    } else {
                                        Toast
                                            .makeText(ScannerActivity.this,
                                                "Gagal melakukan presensi", Toast.LENGTH_LONG)
                                            .show();
                                    }
                                } catch (Exception e) {
                                    Toast
                                        .makeText(ScannerActivity.this,
                                            "Gagal melakukan presensi", Toast.LENGTH_LONG)
                                        .show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<PresensiResponse> call, Throwable t) {
                            if (t.getMessage().contains("406")) {
                                Toast
                                    .makeText(ScannerActivity.this,
                                        "Checkout gagal, belum kerja 8 jam!", Toast.LENGTH_LONG)
                                    .show();
                            } else if (t.getMessage().contains("409")) {
                                Toast
                                    .makeText(ScannerActivity.this,
                                        "Sudah checkout, silakan pulang!", Toast.LENGTH_LONG)
                                    .show();
                            } else {
                                Toast
                                    .makeText(ScannerActivity.this,
                                        "Gagal melakukan presensi", Toast.LENGTH_LONG)
                                    .show();
                            }
                        }
                    });
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
