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
import java.sql.SQLException;
import java.util.Enumeration;
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
public class ClienteDelete extends HttpServlet {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionPedidosv2PU");
    // EntityManager em; 

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
            out.println(vh.renderTitle("Eliminar un cliente:"));
            out.println(vh.renderSelectCliente(lc));
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
            
            String[] lista = request.getParameterValues("delCli");
          
            Cliente c;
            for (String id : lista){     
                c = em.find(Cliente.class, new Integer(id));
                // c = em.getReference(Cliente.class, Integer.parseInt(id));
                // c = em.find(Cliente.class, Integer.parseInt(id));
                try {
                    em.getTransaction().begin();
                    em.remove(c);
                    em.getTransaction().commit();
                    vh.renderSuccess("ClienteDelete", 
                        "El cliente "+c.getApellido()+", "+c.getNombre()+" ha sido eliminado");
                } catch (Exception e) {
                    // em.getTransaction().rollback();
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
                    vh.renderError("ClienteDelete", 
                        "El cliente "+c.getApellido()+", "+c.getNombre()+" no se puede eliminar");
                } finally {
                    em.flush();
                    em.close();
                }                                        
            } 
            
            out.println(vh.renderPie());
            // em.flush();
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
