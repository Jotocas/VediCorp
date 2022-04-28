package com.torresj.cliente.vedicorp.viewModel.utils;

import com.torresj.cliente.vedicorp.model.Cliente;
import com.torresj.cliente.vedicorp.model.DtoClienteProducto;
import com.torresj.cliente.vedicorp.model.PedidoDetalle;
import com.torresj.cliente.vedicorp.utils.UValidador;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Carrito {

    private static DtoClienteProducto detallePedidoCompleto=new DtoClienteProducto() ;


    //Método para agregar productos al carrito(bolsa)
    public static String agregarProducto(DtoClienteProducto detallePedido) {
        PedidoDetalle repetido =null;
        if(UValidador.esListaVacia(detallePedidoCompleto.getDetalle())){
            detallePedidoCompleto.setDetalle(new ArrayList<>());
        }else{
             repetido = detallePedidoCompleto.getDetalle().
                    stream()
                    .filter((p) -> detallePedido.getProducto().getCodigo().equals(p.getCodigo())).findAny()
                    .orElse(null);
        }

        if (!UValidador.esNulo(repetido)) {
            //dtoClienteProducto.getListaErrores()
            //        .add(new MensajeUsuario(tipo_mensaje.ERROR, "Ya existe un item con el codigo ingresado"));
           // return dtoClienteProducto;
        }

        PedidoDetalle pd = new PedidoDetalle();
        pd.setNumite("01");
        pd.setDcto(detallePedido.getCliente().getTdoc());
        pd.setCodigo(detallePedido.getProducto().getCodigo());
        pd.setCombo(detallePedido.getProducto().getCombo());
        pd.setDescri(detallePedido.getProducto().getDesc());
        pd.setTd(detallePedido.getCliente().getTd());
        pd.setTk("22");
        pd.setClte(detallePedido.getCliente().getClte());
        pd.setNclte(detallePedido.getCliente().getNclte());
        pd.setPaso("");

        pd.setCant(detallePedido.getProducto().getCantidad());
        pd.setPrecio(detallePedido.getProducto().getPrecio());
        pd.setIgv(detallePedido.getProducto().getIgv().setScale(2, RoundingMode.CEILING));
        pd.setTot1(detallePedido.getProducto().getPrecio()
                .multiply(new BigDecimal(detallePedido.getProducto().getCantidad().intValue())));

        pd.setTot1(pd.getTot1().setScale(2, RoundingMode.CEILING));
        pd.setStock(detallePedido.getProducto().getStock());
        pd.setReserva(detallePedido.getProducto().getReserva());
        pd.setStock2(new BigDecimal(pd.getStock().intValue() - pd.getReserva().intValue()));


        detallePedidoCompleto.getDetalle().add(pd);
        detallePedidoCompleto.setCliente(detallePedido.getCliente());
        calcular(detallePedidoCompleto);

        return "El Producto ha sido agregado al carrito con éxito";
    }

    //Método para eliminar un platillo del carrito(bolsa)
    public static void eliminar(final String idp) {
        PedidoDetalle dpE = null;
        for (PedidoDetalle dp : detallePedidoCompleto.getDetalle()) {
            if (dp.getCodigo().equals(idp) ){
                dpE = dp;
                break;
            }
        }
        if (dpE != null) {
            detallePedidoCompleto.getDetalle().remove(dpE);
            System.out.println("Se elimino el producto del detalle de pedido");
        }
    }

    //Método para conseguir los detalles del pedido
    public static DtoClienteProducto getDetallePedidos() {
        return detallePedidoCompleto;
    }

    //Método para limpiar
    public static void limpiar() {
        detallePedidoCompleto.getDetalle().clear();
    }

    public static void calcular(DtoClienteProducto dtoClienteProducto) {

        dtoClienteProducto.setSubtotal(BigDecimal.ZERO);
        dtoClienteProducto.setIgv(BigDecimal.ZERO);
        dtoClienteProducto.setTotal(BigDecimal.ZERO);
        dtoClienteProducto.setTina(BigDecimal.ZERO);

        for (PedidoDetalle pedidoDetalle : dtoClienteProducto.getDetalle()) {
            if (pedidoDetalle.getIgv().equals(BigDecimal.ZERO)) {
                dtoClienteProducto.setTina(dtoClienteProducto.getTina().add(pedidoDetalle.getTot1()));
            } else {
                BigDecimal v_igv = BigDecimal.ONE
                        .add(pedidoDetalle.getIgv().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));

                dtoClienteProducto.setSubtotal(dtoClienteProducto.getSubtotal()
                        .add(pedidoDetalle.getTot1().divide(v_igv, 2, RoundingMode.HALF_UP))
                        .setScale(2, RoundingMode.CEILING));

                dtoClienteProducto.setIgv(dtoClienteProducto.getIgv().add(pedidoDetalle.getTot1().subtract(pedidoDetalle
                        .getTot1().divide(v_igv, 2, RoundingMode.HALF_UP).setScale(2, RoundingMode.CEILING))));
            }
            dtoClienteProducto.setTotal(dtoClienteProducto.getTotal().add(pedidoDetalle.getTot1()));
        }
    }

    public static void calcularDesdeActivity(DtoClienteProducto dtoClienteProducto) {
        for (PedidoDetalle pd : dtoClienteProducto.getDetalle()) {
            pd.setTot1(pd.getPrecio()
                    .multiply(new BigDecimal(pd.getCant().intValue())));

            pd.setTot1(pd.getTot1().setScale(2, RoundingMode.CEILING));
            pd.setStock2(new BigDecimal(pd.getStock().intValue() - pd.getReserva().intValue()));
        }

        dtoClienteProducto.setSubtotal(BigDecimal.ZERO);
        dtoClienteProducto.setIgv(BigDecimal.ZERO);
        dtoClienteProducto.setTotal(BigDecimal.ZERO);
        dtoClienteProducto.setTina(BigDecimal.ZERO);

        for (PedidoDetalle pedidoDetalle : dtoClienteProducto.getDetalle()) {
            if (pedidoDetalle.getIgv().equals(BigDecimal.ZERO)) {
                dtoClienteProducto.setTina(dtoClienteProducto.getTina().add(pedidoDetalle.getTot1()));
            } else {
                BigDecimal v_igv = BigDecimal.ONE
                        .add(pedidoDetalle.getIgv().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));

                dtoClienteProducto.setSubtotal(dtoClienteProducto.getSubtotal()
                        .add(pedidoDetalle.getTot1().divide(v_igv, 2, RoundingMode.HALF_UP))
                        .setScale(2, RoundingMode.CEILING));

                dtoClienteProducto.setIgv(dtoClienteProducto.getIgv().add(pedidoDetalle.getTot1().subtract(pedidoDetalle
                        .getTot1().divide(v_igv, 2, RoundingMode.HALF_UP).setScale(2, RoundingMode.CEILING))));
            }
            dtoClienteProducto.setTotal(dtoClienteProducto.getTotal().add(pedidoDetalle.getTot1()));
        }
    }
}
