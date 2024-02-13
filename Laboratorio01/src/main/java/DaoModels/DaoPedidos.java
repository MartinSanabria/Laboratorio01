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
import java.util.List;

/**
 *
 * @author martinsanabria
 */
public class DaoPedidos {
    
    private Conexion CN;
    private Connection con;
    private PreparedStatement  ps;
    private ResultSet rs;
    
    public DaoPedidos() {
    try {
         CN=new Conexion();
        } catch (Exception e) {
        }
    
    }
    
     public List consultaGeneral(){

        ArrayList<Pedidos> lista=new ArrayList<>();
        String sql="select * from pedidos";
        try {
            con=CN.getConection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                Pedidos pedido=new Pedidos();
                pedido.setId(rs.getInt("id"));
                pedido.setId_cliente(rs.getInt("id_cliente"));
                pedido.setFecha(rs.getDate("fecha"));
                pedido.setTotal(rs.getDouble("total"));
                pedido.setEstado(rs.getInt("estado"));
                lista.add(pedido);
            }
        }catch (Exception e){

        }
        return lista;
    }
     
     
     public Pedidos buscarPorID(int id){
        String sql="select * from pedidos where id=?";
        Pedidos pedido=new Pedidos();
        try {
             ps=CN.getConection().prepareStatement(sql);
            ps.setInt(1,id);
            rs=ps.executeQuery();
            while (rs.next()){
               
                pedido.setId(rs.getInt("id"));
                pedido.setId_cliente(rs.getInt("id_cliente"));
                pedido.setFecha(rs.getDate("fecha"));
                pedido.setTotal(rs.getDouble("total"));
                pedido.setEstado(rs.getInt("estado"));
            
            }

        }catch (Exception e){

        }
        return pedido;
    }
     
     
     public boolean agregar(Pedidos pedido){
        String sql="insert into pedidos(id_cliente,fecha,total,estado) "
                + " values(?,?,?,?)";
        try {
            ps=CN.getConection().prepareStatement(sql);
            ps.setInt(1,pedido.getId_cliente());
            ps.setDate(2,pedido.getFecha());
            ps.setDouble(3,pedido.getTotal());
            ps.setInt(4,pedido.getEstado());
            int filasAfectadas=ps.executeUpdate();
            if(filasAfectadas>0){
                return true;
            }
        }catch (Exception e){

        }
        return false;
    }
       
        public boolean eliminar(int id){
        String sql="delete from pedidos where id=?";
        try {
            ps=CN.getConection().prepareStatement(sql);
            ps.setInt(1,id);
            int filasAfectadas=ps.executeUpdate();
            if(filasAfectadas>0){
                return true;
            }
        }catch (Exception e){

        }
        return false;
    }
        
        public boolean actualizar(Pedidos pedido){
        String sql="update pedidos set id_cliente=?,fecha=?,total=?,estado=? "
                + "  where id=?";
        try {
            ps=CN.getConection().prepareStatement(sql);
            ps.setInt(1,pedido.getId_cliente());
            ps.setDate(2,pedido.getFecha());
            ps.setDouble(3,pedido.getTotal());
            ps.setInt(4,pedido.getEstado());
            ps.setInt(5,pedido.getId());
            int filasAfectadas=ps.executeUpdate();
            if(filasAfectadas>0){
                return true;
            }
        }catch (Exception e){

        }
        return false;
    }
        
        
         public List consultarPorCliente(int cliente){

        ArrayList<Pedidos> lista=new ArrayList<>();
       String query = "SELECT * FROM pedidos WHERE id_cliente = ?";
        try {
            ps=CN.getConection().prepareStatement(query);
            ps.setInt(1,cliente);
            rs=ps.executeQuery();
            while(rs.next()){
               Pedidos pedido=new Pedidos();
                pedido.setId(rs.getInt("id"));
                pedido.setId_cliente(rs.getInt("id_cliente"));
                pedido.setFecha(rs.getDate("fecha"));
                pedido.setTotal(rs.getDouble("total"));
                pedido.setEstado(rs.getInt("estado"));
                lista.add(pedido);
            }
        }catch (Exception e){

        }
        return lista;
    }
}
