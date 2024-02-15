/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DaoModels;

import DBConection.Conexion;
import Models.Clientes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author martinsanabria
 */
public class DaoClientes {
    
     private Conexion CN;
    private Connection con;
    private PreparedStatement  ps;
    private ResultSet rs;
    
    public DaoClientes() {
    try {
         CN=new Conexion();
        } catch (Exception e) {
        }
    
    }
    
     public List consultaGeneral(){

        ArrayList<Clientes> lista=new ArrayList<>();
        String sql="select * from clientes";
        try {
            con=CN.getConection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                Clientes cliente=new Clientes();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEmail(rs.getString("email"));
                lista.add(cliente);
            }
        }catch (Exception e){

        }
        return lista;
    }
     
     
     public Clientes buscarPorID(int id){
        String sql="select * from clientes where id=?";
        Clientes cliente=new Clientes();
        try {
             ps=CN.getConection().prepareStatement(sql);
            ps.setInt(1,id);
            rs=ps.executeQuery();
            while (rs.next()){
               
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEmail(rs.getString("email"));
            
            }

        }catch (Exception e){

        }
        return cliente;
    }
     
     public boolean agregar(Clientes cliente){
        String sql="insert into clientes(nombre,direccion,telefono,email) "
                + " values(?,?,?,?)";
        try {
            ps=CN.getConection().prepareStatement(sql);
            ps.setString(1,cliente.getNombre());
            ps.setString(2,cliente.getDireccion());
            ps.setString(3,cliente.getTelefono());
            ps.setString(4,cliente.getEmail());
            int filasAfectadas=ps.executeUpdate();
            if(filasAfectadas>0){
                return true;
            }
        }catch (Exception e){

        }
        return false;
    }
       
        public boolean eliminar(int id){
        String sql="delete from clientes where id=?";
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
        
        public boolean actualizar(Clientes cliente){
        String sql="update clientes set nombre=?,direccion=?,telefono=?,email=? "
                + "  where id=?";
        try {
            ps=CN.getConection().prepareStatement(sql);
            ps.setString(1,cliente.getNombre());
            ps.setString(2,cliente.getDireccion());
            ps.setString(3,cliente.getTelefono());
            ps.setString(4,cliente.getEmail());
            ps.setInt(5,cliente.getId());
            int filasAfectadas=ps.executeUpdate();
            if(filasAfectadas>0){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
        
         public Map<String, Integer> obtenerTop10Clientes() {
        Map<String, Integer> top10Clientes = new HashMap<>();
        String sql = "SELECT c.nombre AS cliente, COUNT(p.id_cliente) AS num_pedidos " +
                     "FROM clientes c " +
                     "JOIN pedidos p ON c.id = p.id_cliente " +
                     "GROUP BY c.nombre " +
                     "ORDER BY num_pedidos ASC " +
                     "LIMIT 10";
        try {
            con = CN.getConection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String nombreCliente = rs.getString("cliente");
                int numPedidos = rs.getInt("num_pedidos");
                top10Clientes.put(nombreCliente, numPedidos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return top10Clientes;
    }
    
}
