package com.torresj.cliente.vedicorp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.torresj.cliente.vedicorp.R;
import com.torresj.cliente.vedicorp.model.DtoObtenerVendedorPorLogin;
import com.torresj.cliente.vedicorp.model.Vendedor;
import com.torresj.cliente.vedicorp.utils.DateSerializer;
import com.torresj.cliente.vedicorp.utils.ProgressBarGenerico;
import com.torresj.cliente.vedicorp.utils.TimeSerializer;
import com.torresj.cliente.vedicorp.viewModel.UsuarioViewModel;

import java.sql.Date;
import java.sql.Time;

public class LoginActivity extends BaseActivity {

    private UsuarioViewModel viewModel;
    private EditText edtUsuario, edtPassword;
    private Button btnIniciarSesion;
    private TextInputLayout txtInputUsuario, txtInputPassword;
    private TextView txtEditarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.initViewModel();
        this.init();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
    }

    private void init() {
        edtUsuario = findViewById(R.id.edtUsuario);
        edtPassword = findViewById(R.id.edtPassword);
        txtInputUsuario = findViewById(R.id.txtInputUsuario);
        txtInputPassword = findViewById(R.id.txtInputPassword);
        txtEditarUsuario = findViewById(R.id.txtEditarUsuario);

        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnIniciarSesion.setOnClickListener(v -> {
            try {
                if (validar()) {
                    ingresar();
                } else {
                    toastIncorrecto("Por favor, complete todos los campos.");
                }
            } catch (Exception e) {
                toastIncorrecto("Se ha producido un error al intentar loguearte : " + e.getMessage());
            }
        });

        txtEditarUsuario.setOnClickListener(v -> {
            Intent i = new Intent(this, EditarUsuarioActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
        });

        edtUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputUsuario.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void ingresar(){
        Toast.makeText(this, "Validando credenciales..", Toast.LENGTH_SHORT).show();
        ProgressBarGenerico.LoadProgress(this);

        DtoObtenerVendedorPorLogin envio= new DtoObtenerVendedorPorLogin();
        envio.setUser(edtUsuario.getText().toString());
        envio.setClave( edtPassword.getText().toString());

        viewModel.obtenerVendedorPorLogin(envio).observe(this, response -> {
            if (response.getRpta() == 1) {

                toastCorrecto(response.getMessage());
                Vendedor u = response.getBody();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                final Gson g = new GsonBuilder()
                        .registerTypeAdapter(Date.class, new DateSerializer())
                        .registerTypeAdapter(Time.class, new TimeSerializer())
                        .create();
                editor.putString("UsuarioJson", g.toJson(u, new TypeToken<Vendedor>() {
                }.getType()));
                editor.apply();
                edtUsuario.setText("");
                edtPassword.setText("");
                ProgressBarGenerico.HideProgreess();
                startActivity(new Intent(this, MenuActivity.class));

            } else {
                toastIncorrecto("Credenciales Inválidas" + response.getMessage());
            }
        });
    }

    private boolean validar() {
        boolean retorno = true;
        String usuario, password;
        usuario = edtUsuario.getText().toString();
        password = edtPassword.getText().toString();
        if (usuario.isEmpty()) {
            txtInputUsuario.setError("Ingrese su usuario");
            retorno = false;
        } else {
            txtInputUsuario.setErrorEnabled(false);
        }
        if (password.isEmpty()) {
            txtInputPassword.setError("Ingrese su contraseña");
            retorno = false;
        } else {
            txtInputPassword.setErrorEnabled(false);
        }
        return retorno;
    }
}