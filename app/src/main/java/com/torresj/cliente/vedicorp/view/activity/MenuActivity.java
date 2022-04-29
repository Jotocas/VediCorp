package com.torresj.cliente.vedicorp.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.torresj.cliente.vedicorp.R;
import com.torresj.cliente.vedicorp.databinding.ActivityMenuBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuBinding binding;
    Toolbar myToolBar;
    TextView mcountTv;
    ImageButton mImageBtn;
    MenuItem cartIconMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenu.toolbar);

        myToolBar = findViewById(R.id.toolbar);
        myToolBar.setTitleTextColor(0xFFFFFFFF);

        mcountTv=findViewById(R.id.count_tv);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView =binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_cliente, R.id.nav_pedido, R.id.nav_producto)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        cartIconMenuItem = menu.findItem(R.id.bolsaCompras);
        View actionView = cartIconMenuItem.getActionView();

        if (actionView != null) {
            mcountTv=actionView.findViewById(R.id.count_tv);
            mImageBtn=actionView.findViewById(R.id.cart_ic_image);

            mcountTv.setText("0");
        }

        mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarBolsa();
            }
        });

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.cerrarSesion:
                this.logout();
                break;
            case R.id.bolsaCompras:
                this.mostrarBolsa();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void mostrarBolsa() {
        Intent i = new Intent(this, ProductosCarritoActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);

    }

    //Método para cerrar sesión
    private void logout() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("UsuarioJson");
        editor.apply();
        this.finish();
        this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }
}