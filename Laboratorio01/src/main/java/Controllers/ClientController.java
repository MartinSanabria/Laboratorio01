/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DaoModels.DaoClientes;
import DaoModels.DaoPedidos;
import Models.Clientes;
import Models.Pedidos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(name = "ClientController", urlPatterns = {"/ClientController"})
public class ClientController extends HttpServlet {

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
            out.println("<title>Servlet ClientController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ClientController at " + request.getContextPath() + "</h1>");
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
                int id=Integer.parseInt(request.getParameter("id"));
                DaoClientes clientDao = new DaoClientes();
                 //obtener los datos
                Clientes client = (Clientes) clientDao.buscarPorID(id);
                request.setAttribute("ClientEdit", client);
                 //falta traer la persona de la base de datos
                 //y pasarlo como atributo
                RequestDispatcher dispatcher2=request.getRequestDispatcher("/Cliente/edit.jsp");
                dispatcher2.forward(request,response);
                }
           }
        } catch (Exception e) {
            e.printStackTrace(); //
        }
        
            DaoClientes clientDao = new DaoClientes();
            //ir al modelo para acceder a los datos
            //obtener los datos
            List<Clientes> Clients = clientDao.consultaGeneral();
            
            //pasarlos a la vista
            request.setAttribute("clientes", Clients);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Cliente/ClientView.jsp");

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
        
         if (request.getParameter("action") != null){
            
            if(request.getParameter("action").equals("create")){
                
                 DaoClientes institutoDao = new DaoClientes();
                 Clientes cat=new Clientes(request.getParameter("nombre"), request.getParameter("direccion"),
                                           request.getParameter("telefono"),request.getParameter("email"));
                
                 institutoDao.agregar(cat);
                 String successMessage = "Cliente agregado satisfactoriamente";

                request.setAttribute("successMessage", successMessage);
                 
            } else if(request.getParameter("action").equals("update")){
                
                int idInstituto=Integer.parseInt(request.getParameter("id"));
               DaoClientes institutoDao = new DaoClientes();
                //obtener los datos
                Clientes client = (Clientes) institutoDao.buscarPorID(idInstituto);
                client.setNombre(request.getParameter("nombre"));
                client.setDireccion(request.getParameter("direccion"));
                client.setTelefono(request.getParameter("telefono"));
                client.setEmail(request.getParameter("email"));
                institutoDao.actualizar(client);
                 String successMessage = "Cliente actualizado satisfactoriamente";

                request.setAttribute("successMessage", successMessage);
                //ir al modelo para acceder a los datos
                //obtener los datos


           }else if(request.getParameter("action").equals("delete")){
                 int idCliente=Integer.parseInt(request.getParameter("id"));
           
                DaoClientes category = new DaoClientes();
                DaoPedidos product = new DaoPedidos();

                // Consultar productos por categoría
                List<Pedidos> productos = product.consultarPorCliente(idCliente);

                if (productos.isEmpty()) {
                    // No hay productos asociados, se puede eliminar la categoría
                    category.eliminar(idCliente);
                    String successMessage = "Cliente eliminada satisfactoriamente";
                    request.setAttribute("successMessage", successMessage);
                } else {
                    // Hay productos asociados, no se puede eliminar la categoría
                    String errorMessage = "Accion denegada, existen pedidos para este cliente.";
                    request.setAttribute("errorMessage", errorMessage);
                }
            }
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
