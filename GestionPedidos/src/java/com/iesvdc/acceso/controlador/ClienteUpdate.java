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
import java.util.List;
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
 *
 * @author juangu
 */
public class ClienteUpdate extends HttpServlet {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionPedidosv2PU");

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            VistaHTML vh = new VistaHTML();

            EntityManager em = emf.createEntityManager();
            
            List<Cliente> lc = em.createNamedQuery("Cliente.findAll").getResultList();
                                
            out.println(vh.renderCabecera());
            out.println(vh.renderTitle("Actualizar un cliente:"));
            out.println(vh.renderSelectCliente(lc, false));
            out.println(vh.renderPie());
            
            em.close();
        }
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            EntityManager em = emf.createEntityManager();
            VistaHTML vh = new VistaHTML();
            out.println(vh.renderCabecera());
            
            Integer idCli=null;
            try {
                idCli = Integer.parseInt(request.getParameter("idCli"));
            } catch (NumberFormatException nfe) {
                // no es un número el ID que me pasan ATAQUE!!! INYECCION DE SQL ???!!!!
                
            }
            
            String nombre = request.getParameter("nombre"), 
                apellido = request.getParameter("apellido"), 
                direccion = request.getParameter("direccion");
            
            if (idCli==null) { // están intentando inyectar algo - pino de nuevo el formulario
                List<Cliente> lc = em.createNamedQuery("Cliente.findAll").getResultList();                                                
                out.println(vh.renderTitle("Actualizar un cliente:"));
                out.println(vh.renderSelectCliente(lc, false));                
            } else {
                if (nombre==null || apellido==null || direccion ==null) {
                    // pintar el formulario relleno para actualizar
                    Cliente c = em.find(Cliente.class, idCli);
                    if (c==null) {
                        // no está el ID en la BBDD, pinto otra vez el formulario
                        List<Cliente> lc = em.createNamedQuery("Cliente.findAll").getResultList();                                                
                        out.println(vh.renderTitle("Actualizar un cliente:"));
                        out.println(vh.renderSelectCliente(lc, false));
                    } else {
                        out.println(vh.renderClienteForm(c));
                    }
                } else {
                    // modifico el cliente en la BBDD
                    Cliente c = em.find(Cliente.class, idCli);
                    if (c==null) {
                        // no está el ID en la BBDD, pinto otra vez el formulario
                        List<Cliente> lc = em.createNamedQuery("Cliente.findAll").getResultList();                                                
                        out.println(vh.renderTitle("Actualizar un cliente:"));
                        out.println(vh.renderSelectCliente(lc, false));
                    } else {
                        c.setApellido(apellido);
                        c.setDireccion(direccion);
                        c.setNombre(nombre);
                        try {
                            em.getTransaction().begin();
                            em.merge(c);
                            em.getTransaction().commit();
                            out.println(vh.renderSuccess("Actualizar cliente", 
                                    "El cliente "+c.getApellido()+", "+c.getNombre()+" actualizado."));
                        } catch (Exception e) {
                            em.getTransaction().rollback();
                            out.println(vh.renderError("Actualizar cliente", 
                                    "El cliente "+c.getApellido()+", "+c.getNombre()+" no pudo ser actualizado."));
                        }
                    }
                } 
            }
            out.println(vh.renderPie());
            em.close();
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

    public void persist(Object object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

}
