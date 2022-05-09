package com.torresj.cliente.vedicorp.view.ui.usuario;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.torresj.cliente.vedicorp.R;
import com.torresj.cliente.vedicorp.model.Vendedor;
import com.torresj.cliente.vedicorp.utils.AdminBD.AdminBD;
import com.torresj.cliente.vedicorp.utils.DateSerializer;
import com.torresj.cliente.vedicorp.utils.TimeSerializer;
import com.torresj.cliente.vedicorp.utils.UString;
import com.torresj.cliente.vedicorp.utils.UValidador;
import com.torresj.cliente.vedicorp.view.activity.MapsActivity;
import com.torresj.cliente.vedicorp.view.activity.MenuActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;


public class UsuarioFragment extends Fragment {

    private Context mContext;
    private File f;

    private Button btnSubirImagen, btnCamara, btnGuardarDatos;
    private CircleImageView imageUser;
    private EditText edtNameUser, edtEmailUser;
    private TextInputLayout txtInputNameUser;
    private final static int LOCATION_REQUEST_CODE = 23;
    private String vend;
    private AdminBD adminBD;
    private SQLiteDatabase db;
    private String realPath;

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


    private void init(View view) {

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 101);
        }

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        }
        btnGuardarDatos = view.findViewById(R.id.btnGuardarDatos);
        btnSubirImagen = view.findViewById(R.id.btnSubirImagen);
        btnCamara = view.findViewById(R.id.btnCamara);
        imageUser = view.findViewById(R.id.imageUser);
        edtNameUser = view.findViewById(R.id.edtNameUser);
        edtEmailUser = view.findViewById(R.id.edtEmailUser);

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

        btnCamara.setOnClickListener(v -> {
            this.cargarCamara();
        });
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

        Cursor datos = adminBD.getReadableDatabase().rawQuery("Select correo,nombrearchivo FROM usuario WHERE usuario='" + vend + "'", null);

        if (datos.getCount() > 0) {
            while (datos.moveToNext()) {
                String correo = datos.getString(0);
                byte[] imagen = datos.getBlob(1);

                if (!UString.esNuloVacio(correo)) {
                    edtEmailUser.setText(correo);
                }

                if (!UValidador.esNulo(imagen)) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(imagen, 0, imagen.length);                    if (!UValidador.esNulo(bmp)) {
                        Log.i("Imagen", "no es nulo");
                    } else {
                        Log.i("Imagen", "es nulo");
                    }


                    imageUser.setImageBitmap(bmp);
                }
            }
        } else {
            db.execSQL("insert into usuario (usuario ) values (?)", new Object[]{vend});
            db.close();
        }
        datos.close();
    }

    private void guardarDatos() {
        if (validar()) {
            try {

                db = adminBD.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("correo", edtEmailUser.getText().toString());
                values.put("nombrearchivo", obtenerImagen(realPath));

                int id = db.update("usuario", values, "usuario=?", new String[]{vend});
                db.close();

                if (id == 1) {
                    successMessage("Estupendo! " + "Su información ha sido Actualizada con éxito en el sistema.");
                }

            } catch (Exception e) {
                warningMessage("Se ha producido un error : " + e.getMessage());
            }
        } else {
            errorMessage("Por favor, complete todos los campos del formulario");
        }

    }

    private byte[] obtenerImagen(String ruta) {
        byte[] imagen = new byte[0];
        try {
            FileInputStream fs = new FileInputStream(ruta);
            imagen = new byte[fs.available()];
            fs.read(imagen);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imagen;
    }

    private void cargarImagen() {
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        someActivityResultLauncher.launch(i);

    }

    private void cargarCamara() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (i.resolveActivity(mContext.getPackageManager()) != null) {
            someActivityResultLauncher.launch(i);
        }
    }


    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri uri = data.getData();

                        if (UValidador.esNulo(uri)) {
                            Bundle extras = data.getExtras();
                            Bitmap imgBitMap = (Bitmap) extras.get("data");
                            imageUser.setImageBitmap(imgBitMap);

                            Uri tempUri = getImageUri(mContext, imgBitMap);
                            realPath = getRealPathFromURIFoto(tempUri);
                            f = new File(realPath);
                        } else {
                            imageUser.setImageURI(uri);
                            realPath = getRealPathFromURI(uri);
                            f = new File(realPath);
                        }
                    }
                }
            });

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURIFoto(Uri uri) {
        String path = "";
        if (mContext.getContentResolver() != null) {
            Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

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