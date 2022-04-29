package com.torresj.cliente.vedicorp.view.ui.cliente;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.listener.OnDrawListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.torresj.cliente.vedicorp.R;

import com.torresj.cliente.vedicorp.model.DtoClienteProducto;
import com.torresj.cliente.vedicorp.model.DtoPedidoClienteResumen;
import com.torresj.cliente.vedicorp.model.Vendedor;
import com.torresj.cliente.vedicorp.utils.DateSerializer;
import com.torresj.cliente.vedicorp.utils.TimeSerializer;
import com.torresj.cliente.vedicorp.utils.UValidador;
import com.torresj.cliente.vedicorp.view.activity.DetalleComprasActivity;
import com.torresj.cliente.vedicorp.viewModel.VentasResumenViewModel;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ClienteFragment extends Fragment implements OnChartValueSelectedListener,
        OnDrawListener {
        private VentasResumenViewModel comprasResumenViewModel;

    PieDataSet pieDataSet = new PieDataSet(null, null);
    private PieChart pieChart;
    ArrayList<PieEntry> dataValues;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_cliente, container, false);
        pieChart= view.findViewById(R.id.pieChart);
        comprasResumenViewModel = new ViewModelProvider(this).get(VentasResumenViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DtoClienteProducto dto = new DtoClienteProducto();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        final Gson g = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();
        String usuarioJson = sp.getString("UsuarioJson", null);
        if (usuarioJson != null) {
            final Vendedor u = g.fromJson(usuarioJson, Vendedor.class);
            dto.setVen(u.getVen());

            this.comprasResumenViewModel.pedidoPorCliente(dto).observe(getViewLifecycleOwner(), response -> {
             getDataValues(response.getBody());

                if(!UValidador.esListaVacia(dataValues)){
                    pieDataSet.setValues(dataValues);
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);

                    PieData pieData=new PieData(pieDataSet);
                    pieChart.setData(pieData);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.setCenterText("Ventas del Dia");
                    pieChart.animate();

                    pieDataSet.setFormLineWidth(4);

                    pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                        @Override
                        public void onValueSelected(Entry e, Highlight h) {
                            if (e == null)
                                return;

                            int index=(int) h.getX();

                            Log.i("VAL SELECTED",
                                    "Value: " + e.getY() + ", index: " + h.getX() + ",seleccionado==>" + index
                                            + ", DataSet index: " + h.getDataSetIndex()
                                            + ", Cliente: " + dataValues.get(index).getLabel()
                            );

                            mostrarDetalle( dataValues.get(index).getLabel());
                        }

                        @Override
                        public void onNothingSelected() {

                        }
                    });
                }

            });
        }
    }

    private void mostrarDetalle(String cliente) {
        Intent i = new Intent(getContext(), DetalleComprasActivity.class);
        i.putExtra("cliente", cliente);
        startActivity(i);
        getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public ArrayList<PieEntry> getDataValues(List<DtoPedidoClienteResumen> resumen) {
        dataValues = new ArrayList<>();
        resumen.forEach(dtoPedidoClienteResumen -> {
            dataValues.add(new PieEntry(dtoPedidoClienteResumen.getMontoTotal().floatValue(), dtoPedidoClienteResumen.getClienteCodigo()));
        });

        return dataValues;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", xIndex: " + e.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onEntryAdded(Entry entry) {

    }

    @Override
    public void onEntryMoved(Entry entry) {

    }

    @Override
    public void onDrawFinished(DataSet<?> dataSet) {

    }
}