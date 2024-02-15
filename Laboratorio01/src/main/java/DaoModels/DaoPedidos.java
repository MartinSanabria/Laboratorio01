/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DaoModels;

import DBConection.Conexion;
import Models.Clientes;
import Models.Pedidos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author martinsanabria
 */
public class DaoPedidos {

    private Conexion CN;
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public DaoPedidos() {
        try {
            CN = new Conexion();
        } catch (Exception e) {
        }

    }

    public List consultaGeneral() {

        ArrayList<Pedidos> lista = new ArrayList<>();
        String sql = "select * from pedidos";
        try {
            con = CN.getConection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Pedidos pedido = new Pedidos();
                pedido.setId(rs.getInt("id"));
                pedido.setId_cliente(rs.getInt("id_cliente"));
                pedido.setFecha(rs.getDate("fecha"));
                pedido.setTotal(rs.getDouble("total"));
                pedido.setEstado(rs.getInt("estado"));
                lista.add(pedido);
            }
        } catch (Exception e) {

        }
        return lista;
    }

    public Pedidos buscarPorID(int id) {
        String sql = "select * from pedidos where id=?";
        Pedidos pedido = new Pedidos();
        try {
            ps = CN.getConection().prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {

                pedido.setId(rs.getInt("id"));
                pedido.setId_cliente(rs.getInt("id_cliente"));
                pedido.setFecha(rs.getDate("fecha"));
                pedido.setTotal(rs.getDouble("total"));
                pedido.setEstado(rs.getInt("estado"));

            }

        } catch (Exception e) {

        }
        return pedido;
    }

    public boolean agregar(Pedidos pedido) {
        String sql = "insert into pedidos(id_cliente,fecha,total,estado) "
                + " values(?,?,?,?)";
        try {
            ps = CN.getConection().prepareStatement(sql);
            ps.setInt(1, pedido.getId_cliente());
            ps.setDate(2, pedido.getFecha());
            ps.setDouble(3, pedido.getTotal());
            ps.setInt(4, pedido.getEstado());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public boolean eliminar(int id) {
        String sql = "delete from pedidos where id=?";
        try {
            ps = CN.getConection().prepareStatement(sql);
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public boolean actualizar(Pedidos pedido) {
        String sql = "update pedidos set id_cliente=?,fecha=?,total=?,estado=? "
                + "  where id=?";
        try {
            ps = CN.getConection().prepareStatement(sql);
            ps.setInt(1, pedido.getId_cliente());
            ps.setDate(2, pedido.getFecha());
            ps.setDouble(3, pedido.getTotal());
            ps.setInt(4, pedido.getEstado());
            ps.setInt(5, pedido.getId());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public List consultarPorCliente(int cliente) {

        ArrayList<Pedidos> lista = new ArrayList<>();
        String query = "SELECT * FROM pedidos WHERE id_cliente = ?";
        try {
            ps = CN.getConection().prepareStatement(query);
            ps.setInt(1, cliente);
            rs = ps.executeQuery();
            while (rs.next()) {
                Pedidos pedido = new Pedidos();
                pedido.setId(rs.getInt("id"));
                pedido.setId_cliente(rs.getInt("id_cliente"));
                pedido.setFecha(rs.getDate("fecha"));
                pedido.setTotal(rs.getDouble("total"));
                pedido.setEstado(rs.getInt("estado"));
                lista.add(pedido);
            }
        } catch (Exception e) {

        }
        return lista;
    }

    public List<Map<String, Object>> obtenerPedidosPorMesYAnio() {
        List<Map<String, Object>> resultados = new ArrayList<>();
        String sql = "SELECT YEAR(fecha) AS anio, MONTH(fecha) AS mes, COUNT(*) AS total_pedidos "
                + "FROM pedidos "
                + "GROUP BY YEAR(fecha), MONTH(fecha)";
        try {
            con = CN.getConection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> resultado = new HashMap<>();
                resultado.put("anio", rs.getInt("anio"));
                resultado.put("mes", rs.getInt("mes"));
                resultado.put("total_pedidos", rs.getInt("total_pedidos"));
                resultados.add(resultado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultados;
    }

    public Map<Integer, List<Integer>> obtenerTop3MesesPorAnio() {
        List<Map<String, Object>> pedidosPorMesYAnio = obtenerPedidosPorMesYAnio();
        Map<Integer, List<Integer>> top3MesesPorAnio = new HashMap<>();
        for (Map<String, Object> resultado : pedidosPorMesYAnio) {
            int anio = (int) resultado.get("anio");
            int mes = (int) resultado.get("mes");
            int totalPedidos = (int) resultado.get("total_pedidos");
            if (!top3MesesPorAnio.containsKey(anio)) {
                top3MesesPorAnio.put(anio, new ArrayList<>());
            }
            List<Integer> top3Meses = top3MesesPorAnio.get(anio);
            if (top3Meses.size() < 3) {
                top3Meses.add(mes);
                top3Meses.sort((m1, m2) -> Integer.compare(obtenerTotalPedidosPorMesYAnio(m2, anio), obtenerTotalPedidosPorMesYAnio(m1, anio)));
            } else {
                for (int i = 0; i < 3; i++) {
                    int mesActual = top3Meses.get(i);
                    int pedidosMesActual = obtenerTotalPedidosPorMesYAnio(mesActual, anio);
                    if (totalPedidos > pedidosMesActual) {
                        top3Meses.set(i, mes);
                        break;
                    }
                }
                top3Meses.sort((m1, m2) -> Integer.compare(obtenerTotalPedidosPorMesYAnio(m2, anio), obtenerTotalPedidosPorMesYAnio(m1, anio)));
            }
        }
        return top3MesesPorAnio;
    }

    public int obtenerTotalPedidosPorMesYAnio(int mes, int anio) {
    return obtenerPedidosPorMesYAnio()
            .stream()
            .filter(resultado -> (int) resultado.get("anio") == anio && (int) resultado.get("mes") == mes)
            .findFirst()
            .map(resultado -> (int) resultado.get("total_pedidos"))
            .orElse(0);
}

}
