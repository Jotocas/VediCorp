package com.torresj.cliente.vedicorp.view.ui.producto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.torresj.cliente.vedicorp.R;

import com.torresj.cliente.vedicorp.model.Cliente;
import com.torresj.cliente.vedicorp.model.PedidoDetalle;
import com.torresj.cliente.vedicorp.viewModel.ProductoViewModel;
import com.torresj.cliente.vedicorp.viewModel.adapter.ProductoAdapter;
import com.torresj.cliente.vedicorp.viewModel.interfaces.ClickListener;

import java.util.ArrayList;

public class ProductoFragment extends Fragment implements   SearchView.OnQueryTextListener, ClickListener {


    private ProductoViewModel productoViewModel;
    private RecyclerView rcvProductos;
    private ProductoAdapter adapter;
    private SearchView svSearch;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_producto, container, false);
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
        rcvProductos = v.findViewById(R.id.rcvProductos);
        svSearch = v.findViewById(R.id.svSearch);
    }
    private void initViewModel() {
        productoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);
    }

    private void initAdapter() {
        adapter = new ProductoAdapter(new ArrayList<>(),false,null,mContext,this);
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
    public void onDestroyView() {
        super.onDestroyView();

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

    }

    @Override
    public void clickBoton(Cliente cliente, View v, String tipo) {

    }
}