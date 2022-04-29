package com.torresj.cliente.vedicorp.view.ui.pedido;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.torresj.cliente.vedicorp.R;

import com.torresj.cliente.vedicorp.model.Cliente;
import com.torresj.cliente.vedicorp.model.PedidoDetalle;
import com.torresj.cliente.vedicorp.model.Vendedor;
import com.torresj.cliente.vedicorp.utils.DateSerializer;
import com.torresj.cliente.vedicorp.utils.TimeSerializer;
import com.torresj.cliente.vedicorp.view.activity.MapsActivity;
import com.torresj.cliente.vedicorp.viewModel.ClienteViewModel;
import com.torresj.cliente.vedicorp.viewModel.adapter.ClienteAdapter;
import com.torresj.cliente.vedicorp.viewModel.interfaces.ClickListener;
import com.torresj.cliente.vedicorp.viewModel.utils.UtilParse;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class PedidoFragment extends Fragment implements SearchView.OnQueryTextListener, ClickListener {


    private ClienteViewModel clienteViewModel;
    private RecyclerView rcvClientes;
    private ClienteAdapter adapter;
    private SearchView svSearch;
    private String usuarioJson;
    private Context mContext;

    final Gson g = new GsonBuilder()
            .registerTypeAdapter(Date.class, new DateSerializer())
            .registerTypeAdapter(Time.class, new TimeSerializer())
            .create();
    SharedPreferences sp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pedido, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initViewModel();
        initAdapter();
        loadData();
        initListener();
    }

    private void init(View v) {
        rcvClientes = v.findViewById(R.id.rcvCliente);
        svSearch = v.findViewById(R.id.svSearchCliente);
    }

    private void initViewModel() {
        clienteViewModel = new ViewModelProvider(this).get(ClienteViewModel.class);
    }

    private void initAdapter() {
        adapter = new ClienteAdapter(new ArrayList<>(), this);
        rcvClientes.setLayoutManager(new GridLayoutManager(getContext(), 1));
        rcvClientes.setAdapter(adapter);
    }

    private void loadData() {
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());

        usuarioJson = sp.getString("UsuarioJson", null);
        if (usuarioJson != null) {
            final Vendedor u = g.fromJson(usuarioJson, Vendedor.class);
            this.clienteViewModel.obtenerCliente(u.getVen()).observe(getViewLifecycleOwner(), response -> {
                adapter.updateItems(response.getBody());
            });
        }
    }

    private void initListener() {
        svSearch.setOnQueryTextListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
           }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!newText.equals(null) && !newText.equals("")) {
            adapter.filter(newText);
            return false;
        }
        return false;
    }

    @Override
    public void clickItem(Cliente cliente, View v) {

        usuarioJson = sp.getString("UsuarioJson", null);
        if (usuarioJson != null) {
            final Vendedor u = g.fromJson(usuarioJson, Vendedor.class);
            this.clienteViewModel.validarZonaVentas(cliente.getNclte(), u.getVen()).observe(getViewLifecycleOwner(), response -> {
                if (response.getRpta() == 1) {
                    Bundle bundle = new Bundle();
                    String personJsonString = UtilParse.getGsonParser().toJson(cliente);
                    bundle.putString("cliente", personJsonString);

                    Navigation.findNavController(v).navigate(R.id.action_nav_pedido_to_nav_productos, bundle);
                } else {
                    toastIncorrecto("Validar Zona Venta" + response.getMessage(),v);
                }
            });
        }

    }

    public void toastIncorrecto(String msg, View v) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_error, (ViewGroup) v.findViewById(R.id.ll_custom_toast_error));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToast2);
        txtMensaje.setText(msg);

        Toast toast = new Toast(mContext);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    @Override
    public void clickProductoItem(PedidoDetalle pedidoDetalle, View v) {

    }

    @Override
    public void clickBoton(Cliente cliente, View v, String tipo) {
        Intent i = new Intent(getContext(), MapsActivity.class);
       // i.putExtra("cliente", cliente);
        startActivity(i);
        getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

}