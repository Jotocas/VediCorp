package com.torresj.cliente.vedicorp.view.ui.usuario;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.torresj.cliente.vedicorp.R;
import com.torresj.cliente.vedicorp.model.Vendedor;
import com.torresj.cliente.vedicorp.utils.AdminBD.AdminBD;
import com.torresj.cliente.vedicorp.utils.DateSerializer;
import com.torresj.cliente.vedicorp.utils.TimeSerializer;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UsuarioFragment extends Fragment {

    private Context mContext;
    private File f;

    private Button btnSubirImagen, btnGuardarDatos;
    private CircleImageView imageUser;
    private EditText edtNameUser, edtEmailUser;
    private TextInputLayout txtInputNameUser;
    private final static int LOCATION_REQUEST_CODE = 23;
    private String vend;
    AdminBD adminBD;
    SQLiteDatabase db;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_usuario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

    }

/*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Gracias por conceder los permisos para " +
                            "leer el almacenamiento, estos permisos son necesarios para poder " +
                            "escoger tu foto de perfil", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No podemos realizar el registro si no nos concedes los permisos para leer el almacenamiento.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

 */

    private void init(View view) {

        btnGuardarDatos = view.findViewById(R.id.btnGuardarDatos);
        btnSubirImagen = view.findViewById(R.id.btnSubirImagen);
        imageUser = view.findViewById(R.id.imageUser);
        edtNameUser = view.findViewById(R.id.edtNameUser);
        edtEmailUser = view.findViewById(R.id.edtEmailUser);

        //TextInputLayout
        txtInputNameUser = view.findViewById(R.id.txtInputNameUser);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        final Gson g = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();
        String usuarioJson = sp.getString("UsuarioJson", null);
        if (usuarioJson != null) {
            final Vendedor usua = g.fromJson(usuarioJson, Vendedor.class);
            vend = usua.getVen();
            edtNameUser.setText(usua.getNom());
        }

        iniciarBD();

        btnSubirImagen.setOnClickListener(v -> {
            this.cargarImagen();
        });
        btnGuardarDatos.setOnClickListener(v -> {
            this.guardarDatos();
        });
        ///ONCHANGE LISTENEER A LOS EDITEXT
        edtNameUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputNameUser.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void iniciarBD() {
        adminBD = new AdminBD(mContext);
        db = adminBD.getWritableDatabase();

        if (db != null) {
            Toast.makeText(mContext, "Se ha creado la base de datos correctamente..", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Error al crear la base de datos..", Toast.LENGTH_SHORT).show();
        }

        Cursor datos = adminBD.getReadableDatabase().rawQuery("Select usuario FROM usuario WHERE usuario='" + vend + "'", null);

        if (datos.getCount() > 0) {

        } else {
            db.execSQL("insert into usuario (usuario ) values (?)", new Object[]{vend});
            db.close();
        }
        datos.close();
    }

    private void guardarDatos() {
        if (validar()) {
            try {

                LocalDateTime ldt = LocalDateTime.now(); //Para generar el nombre al archivo en base a la fecha, hora, año
                RequestBody rb = RequestBody.create(f, MediaType.parse("multipart/form-data")), somedata; //Le estamos enviando un archivo (imagen) desde el formulario
                String filename = "" + ldt.getDayOfMonth() + (ldt.getMonthValue() + 1) +
                        ldt.getYear() + ldt.getHour()
                        + ldt.getMinute() + ldt.getSecond(); //Asignar un nombre al archivo (imagen)
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", f.getName(), rb);
                // somedata = RequestBody.create("profilePh" + filename, MediaType.parse("text/plain")); //Le estamos enviando un nombre al archivo.

                db = adminBD.getWritableDatabase();
                db.execSQL("UPDATE usuario SET correo=?, nombrearchivo=? WHERE usuario=?", new Object[]{edtEmailUser.getText(),filename, vend});
                db.close();


            } catch (Exception e) {
                warningMessage("Se ha producido un error : " + e.getMessage());
            }
        } else {
            errorMessage("Por favor, complete todos los campos del formulario");
        }

    }

    private void cargarImagen() {
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        someActivityResultLauncher.launch(i);

    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Uri uri = data.getData();
                        imageUser.setImageURI(uri);

                        final String realPath = getRealPathFromURI(uri);
                        f = new File(realPath);
                    }
                }
            });

    private String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = mContext.getContentResolver().query(contentUri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = mContext.getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();

        int idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        String path = cursor.getString(idx);
        cursor.close();
        return path;
    }

    private boolean validar() {
        boolean retorno = true;
        String nombres, correo;
        nombres = edtNameUser.getText().toString();
        correo = edtEmailUser.getText().toString();

        if (this.f == null) {
            errorMessage("debe selecionar una foto de perfil");
            retorno = false;
        }
        if (nombres.isEmpty()) {
            txtInputNameUser.setError("Ingresar nombres");
            retorno = false;
        } else {
            txtInputNameUser.setErrorEnabled(false);
        }

        return retorno;
    }

    public void successMessage(String message) {
        new SweetAlertDialog(mContext,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Buen Trabajo!")
                .setContentText(message).show();
    }

    public void errorMessage(String message) {
        new SweetAlertDialog(mContext,
                SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText(message).show();
    }

    public void warningMessage(String message) {
        new SweetAlertDialog(mContext,
                SweetAlertDialog.WARNING_TYPE).setTitleText("Notificación del Sistema")
                .setContentText(message).setConfirmText("Ok").show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}