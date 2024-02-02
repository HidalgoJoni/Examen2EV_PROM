package com.example.examen2ev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {
    private Button btnCancelar, btnLogin;
    private EditText usuario, contraseña;
    private ImageView imgError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // encontramos los ids de todos los elementos
        btnCancelar = findViewById(R.id.btnCancelar);
        btnLogin = findViewById(R.id.btnLogin);
        usuario = findViewById(R.id.usuario);
        contraseña = findViewById(R.id.contraseña);
        imgError = findViewById(R.id.imgError);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usuario.getText().toString().equals("admin") && contraseña.getText().toString().equals("admin")){
                    Intent i = new Intent(LoginActivity.this, AdministracionActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    imgError.setImageDrawable(getDrawable(R.drawable.error));
                    imgError.setVisibility(View.VISIBLE);
                    usuario.setText("");
                    contraseña.setText("");
                }
            }
        });
    }
}