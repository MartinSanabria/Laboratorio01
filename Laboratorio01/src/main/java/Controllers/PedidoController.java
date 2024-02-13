/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DaoModels.DaoPedidos;
import DaoModels.DaoClientes;
import Models.Clientes;
import Models.Pedidos;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author martinsanabria
 */
@WebServlet(name = "PedidoController", urlPatterns = {"/PedidoController"})
public class PedidoController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PedidoController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PedidoController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         try {
           if(request.getParameter("action")!=null){
                if(request.getParameter("action").equals("edit")){
                int idproducto=Integer.parseInt(request.getParameter("id"));
               DaoPedidos pedidos = new DaoPedidos();
                 //obtener los datos
                Pedidos pedido = (Pedidos) pedidos.buscarPorID(idproducto);
                request.setAttribute("pedidoEdit", pedido);
                
                DaoClientes clientes = new DaoClientes();
                    //ir al modelo para acceder a los datos
                    //obtener los datos

                List<Clientes> client = clientes.consultaGeneral();


                request.setAttribute("clientes", client);
                
                RequestDispatcher dispatcher2=request.getRequestDispatcher("/Pedido/edit.jsp");
                dispatcher2.forward(request,response);
                } else if (request.getParameter("action").equals("new")){
                    
                    DaoClientes client = new DaoClientes();
                    //ir al modelo para acceder a los datos
                    //obtener los datos
                    List<Clientes> clientes = client.consultaGeneral();


                    request.setAttribute("clientes", clientes);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/Pedido/create.jsp");

            // Envía la solicitud al dispatcher.
            dispatcher.forward(request, response);
                }
           }
        } catch (Exception e) {
            e.printStackTrace(); //
        }
        
            DaoPedidos pedidos = new DaoPedidos();
            DaoClientes clientes = new DaoClientes();
            //ir al modelo para acceder a los datos
            //obtener los datos
            List<Pedidos> pedidoList = pedidos.consultaGeneral();

         Map<Integer, Map<String, String>> pedidoData = new HashMap<>();

        for (Pedidos client : pedidoList) {
            // Obtener nombre de proveedor
            Clientes cliente = clientes.buscarPorID(client.getId_cliente());
            String nombreCliente = cliente.getNombre();


            Map<String, String> clientData = new HashMap<>();
            clientData.put("nombreCliente", nombreCliente);

            pedidoData.put(client.getId(), clientData);
        }

        request.setAttribute("pedidoData", pedidoData);
        request.setAttribute("clientlist", pedidoList);


            RequestDispatcher dispatcher = request.getRequestDispatcher("/Pedido/PedidoView.jsp");

            // Envía la solicitud al dispatcher.
            dispatcher.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
                  if (request.getParameter("action") != null){
            
            if(request.getParameter("action").equals("create")){
                DaoPedidos productNew = new DaoPedidos();
                DaoClientes proveedorFound = new DaoClientes();
    
                    if (!request.getParameter("cliente").isEmpty()) {
                        Clientes prov = proveedorFound.buscarPorID(Integer.parseInt(request.getParameter("cliente")));

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Formato de fecha esperado
                        java.util.Date utilDate = null;
                        try {
                            utilDate = dateFormat.parse(request.getParameter("fecha"));
                        } catch (ParseException e) {
                            e.printStackTrace(); // Manejo de errores
                        }

                        // Convertir java.util.Date a java.sql.Date
                        java.sql.Date fecha = new java.sql.Date(utilDate.getTime());

                        Pedidos productoNuevo = new Pedidos(prov.getId(), fecha, Double.parseDouble(request.getParameter("total")), 0);
                        productNew.agregar(productoNuevo);

                        String successMessage = "Producto agregado satisfactoriamente";

                        request.setAttribute("successMessage", successMessage);
                    } else {
                        String errorMessage = "Error de seleccion de clientes";

                        request.setAttribute("errorMessage", errorMessage);
                    }
               
                
            } else if(request.getParameter("action").equals("update")){
                
                int idproductos=Integer.parseInt(request.getParameter("id"));
                System.out.println("id pedido" + idproductos);
                DaoPedidos productUpdate = new DaoPedidos();
                Pedidos dataProduct = productUpdate.buscarPorID(idproductos);
                DaoClientes proveedorFound = new DaoClientes();
                if(!request.getParameter("cliente").isEmpty()){
                    
                      // Convertir la fecha de texto a Date
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Formato de fecha esperado
                        java.util.Date utilDate = null;
                        try {
                            utilDate = dateFormat.parse(request.getParameter("fecha"));
                        } catch (ParseException e) {
                            e.printStackTrace(); // Manejo de errores
                        }

                        // Convertir java.util.Date a java.sql.Date
                        java.sql.Date fecha = new java.sql.Date(utilDate.getTime());
                    
                                Clientes prov = proveedorFound.buscarPorID(Integer.parseInt(request.getParameter("cliente")));
                                dataProduct.setId_cliente(prov.getId());
                                dataProduct.setFecha(fecha);
                                dataProduct.setTotal(Double.parseDouble(request.getParameter("total")));
                                dataProduct.setEstado(Integer.parseInt(request.getParameter("estado")));

                                productUpdate.actualizar(dataProduct);
                            String successMessage = "Pedido actualizado satisfactoriamente";

                            request.setAttribute("successMessage", successMessage);
                     
                } else {
                    String errorMessage = "Error de seleccion de clientes";

                    request.setAttribute("errorMessage", errorMessage);
                }

           }else if(request.getParameter("action").equals("delete")){
            int idproducto=Integer.parseInt(request.getParameter("id"));
           
            DaoPedidos delete = new DaoPedidos();
            
            delete.eliminar(idproducto);
            String successMessage = "Pedido Eliminado satisfactoriamente";
                 
            request.setAttribute("successMessage", successMessage);
            }
        }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
       
           doGet(request,response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
