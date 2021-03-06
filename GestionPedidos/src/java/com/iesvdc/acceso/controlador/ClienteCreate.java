/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iesvdc.acceso.controlador;

import com.iesvdc.acceso.entidades.Cliente;
import com.iesvdc.acceso.vista.VistaHTML;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet para dar de alta un cliente en la BBDD.
 * Usamos los verbos GET y POST de HTTP para primero 
 * renderizar el formulario y luego capturar los datos 
 * del mismo. Todo con la misma URL.
 * @author juangu
 */
public class ClienteCreate extends HttpServlet {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionPedidosv2PU");    
    
    /**
     * Manejador del método <code>GET</code>.
     * Muestra el formulario para crear un cliente.
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // processRequest(request, response);
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            VistaHTML vh = new VistaHTML();            
            
            out.println(vh.renderCabecera());        
            out.println(vh.renderClienteForm());
            out.println(vh.renderPie());
        }
    }

    /**
     * Manejador del método <code>POST</code>.
     * Captura los parámetros del formulario que vemos con el GET.
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // processRequest(request, response);
        EntityManager em = emf.createEntityManager();
        String nombre = request.getParameter("nombre"), 
                apellido = request.getParameter("apellido"), 
                direccion = request.getParameter("direccion");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            VistaHTML vh = new VistaHTML();
            out.println(vh.renderCabecera());
            if (nombre!= null && apellido!=null && direccion!=null){
                // inserto en la BBDD
                Cliente c = new Cliente(null, nombre, apellido, direccion);
                if (!this.persist(c)) {
                    // mensaje de error
                    out.println(
                        vh.renderError("ClienteCreate Error:", "Error al insertar en la base de datos."));
                } else {
                    // mensaje de inserción correcta
                    out.println(
                        vh.renderSuccess("ClienteCreate:", "cliente insertado correctamente."));
                }
            } else {
                //mensaje de error
                out.println(
                    vh.renderError("ClienteCreate Error:", "Error en los parámetros de la petición."));
            }
            out.println(vh.renderPie());
        }
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

    public boolean persist(Object object) {
        boolean resultado=true;
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
            resultado=false;
        } finally {
            em.close();
        }
        return resultado;
    }

}
