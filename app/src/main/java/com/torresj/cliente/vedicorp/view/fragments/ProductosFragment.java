package com.torresj.cliente.vedicorp.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.torresj.cliente.vedicorp.R;
import com.torresj.cliente.vedicorp.model.Cliente;
import com.torresj.cliente.vedicorp.viewModel.Communication.Communication;
import com.torresj.cliente.vedicorp.viewModel.ProductoViewModel;
import com.torresj.cliente.vedicorp.viewModel.adapter.ProductoAdapter;
import com.torresj.cliente.vedicorp.viewModel.utils.UtilParse;

import java.util.ArrayList;


public class ProductosFragment extends Fragment  implements   SearchView.OnQueryTextListener {

    private Cliente cliente;
    private ProductoViewModel productoViewModel;
    private RecyclerView rcvProductos;
    private ProductoAdapter adapter;
    private SearchView svSearch;
    private Context mContext;

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
        adapter = new ProductoAdapter(new ArrayList<>(),true,cliente, mContext);
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

}