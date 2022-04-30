package com.torresj.cliente.vedicorp.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.torresj.cliente.vedicorp.R;
import com.torresj.cliente.vedicorp.model.Cliente;
import com.torresj.cliente.vedicorp.model.PedidoDetalle;
import com.torresj.cliente.vedicorp.viewModel.ProductoViewModel;
import com.torresj.cliente.vedicorp.viewModel.adapter.ProductoAdapter;
import com.torresj.cliente.vedicorp.viewModel.interfaces.ClickListener;
import com.torresj.cliente.vedicorp.viewModel.utils.UtilParse;

import java.util.ArrayList;


public class ProductosFragment extends Fragment  implements   SearchView.OnQueryTextListener, ClickListener {

    private Cliente cliente;
    private ProductoViewModel productoViewModel;
    private RecyclerView rcvProductos;
    private ProductoAdapter adapter;
    private SearchView svSearch;
    private Context mContext;
    TextView mcountTv;

    public ProductosFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getArguments();

        String personJsonString = extras.getString("cliente");
        cliente= UtilParse.getGsonParser().fromJson(personJsonString, Cliente.class);
       // Toast.makeText(getActivity().getApplicationContext(), "Cliente : " + clienteParam, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_productos, container, false);
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
        rcvProductos = v.findViewById(R.id.rcvProductosVenta);
        svSearch = v.findViewById(R.id.svSearchProductosVenta);
    }
    private void initViewModel() {
        productoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);
    }

    private void initAdapter() {
        adapter = new ProductoAdapter(new ArrayList<>(),true,cliente, mContext,this);
        rcvProductos.setLayoutManager(new GridLayoutManager(getContext(), 1));
        rcvProductos.setAdapter(adapter);
    }

    private void loadData() {
        this.productoViewModel.obtenerProducto("000").observe(getViewLifecycleOwner(), response -> {
            adapter.updateItems(response.getBody());
        });
    }
    private void initListener() {
        svSearch.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!newText.equals(null)  && !newText.equals("")) {
            adapter.filter(newText);
            return false;
        }
        return false;
    }

    @Override
    public void clickItem(Cliente cliente, View v) {

    }

    @Override
    public void clickProductoItem(PedidoDetalle pedidoDetalle, View v) {

    }

    @Override
    public void clickIncrementarItem(View v) {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mcountTv=toolbar.findViewById(R.id.count_tv);
        Integer cantidad=0;
        cantidad=Integer.parseInt(mcountTv.getText().toString());
        cantidad++;

        mcountTv.setText(String.valueOf(cantidad));

    }

    @Override
    public void clickBoton(Cliente cliente, View v, String tipo) {

    }
}