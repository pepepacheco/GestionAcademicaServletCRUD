/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iesvdc.acceso.vista;

import com.iesvdc.acceso.entidades.Cliente;
import java.util.List;

/**
 *
 * @author juangu
 */
public class VistaHTML {
    static String cabecera="<!DOCTYPE html>\n" +        
        "<html>\n" +
        "    <head>\n" +
        "        <title>Gestión de Pedidos v2</title>\n" +
        "        <meta charset=\"UTF-8\">\n" +
        "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
        "        <link href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\" rel=\"stylesheet\" />\n" +
        "        <script src=\"https://code.jquery.com/jquery-1.12.4.min.js\"> </script> \n" +
        "        <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\" ></script>\n" +
        "    </head>\n" +
        "    <body>\n" +
        "        <div class=\"container\">\n" +
        "            <h1>Gestión de pedidos on-line</h1>\n";            
    static String cuerpo = "            <div class=\"well\">\n" +
        "                Aqui va el menu principal\n" +
        "            </div>\n";
    static String pie = 
        "    <div class=\"btn btn-default\"> <a href=\"javascript:history.back()\"> Volver atrás </a> </div>"+
        "</div>\n" +
        "    </body>\n" +        
        "</html>";

    public VistaHTML() {
        
    }
    
    public String renderCabecera(){
        return cabecera;
    }
    
    public String renderCuerpo(){
        return cuerpo;
    }
    
    public String renderPie(){
        return pie;
    }

    public String renderClientes(List<Cliente> lc){
        String resultado="";
        
        resultado+="    <div class='well'>";
        resultado+="<table class='table'>";
        resultado+="<tr><th>ID</th><th>nombre</th><th>apellido</th><th>dirección</th></tr>";
        for (Cliente cli : lc){
            resultado+="<tr>";
            resultado+="<td>"+cli.getIdCliente()+"</td>";
            resultado+="<td>"+cli.getNombre()+"</td>";
            resultado+="<td>"+cli.getApellido()+"</td>";
            resultado+="<td>"+cli.getDireccion()+"</td>";
            resultado+="</tr>";
        }
        
        resultado+="</table>";
        resultado+="    </div>"; // del class=well
        return resultado;
    }
    
    public String renderTitle(String title) {
        return("<h3>Acceso a datos: "+title+"</h3>\n");
    }
    
    public String renderClienteForm(){
        String clienteForm = "<div class=\"well\">\n" +
"		<form role=\"form\" method=\"post\">\n" +
"		  <div class=\"form-group\">\n" +
"		    <label for=\"nombre\">Nombre:</label>\n" +
"		    <input type=\"nombre\" class=\"form-control\" name=\"nombre\">\n" +
"		  </div>	\n" +
"		  <div class=\"form-group\">\n" +
"		    <label for=\"apellido\">Apellidos:</label>\n" +
"		    <input type=\"apellido\" class=\"form-control\" name=\"apellido\">\n" +
"		  </div>	\n" +
"		  <div class=\"form-group\">\n" +
"		    <label for=\"direccion\">Dirección:</label>\n" +
"		    <input type=\"direccion\" class=\"form-control\" name=\"direccion\">\n" +
"		  </div>		  \n" +
"		  <button type=\"submit\" class=\"btn btn-default\">Submit</button>\n" +
"		</form>\n" +
"	</div>";
        return (clienteForm);
    }
    
    public String renderError(String title, String msg){
        String resultado = "";
        resultado += "<h3>"+title+"</h3>";
        resultado += "<div class=\"alert alert-danger\">\n" +
"  <strong>ERROR:</strong> "+msg+".\n" +
"</div>";
        return resultado;
    }
    
    public String renderSuccess(String title, String msg){
        String resultado = "";
        resultado += "<h3>"+title+"</h3>";
        resultado += "<div class=\"alert alert-success\">\n" +
"  <strong>EXITO:</strong> "+msg+".\n" +
"</div>";
        return resultado;
    }

    public String renderSelectCliente(List<Cliente> lc) {
        String resultado="";
        resultado+="<div class=\"well\">\n" +
"		<form role=\"form\" method=\"POST\">\n" +
"		   <div class=\"form-group\">\n" +
"			<label for=\"delCli\">Selecciona el cliente a borrar:</label>\n" +
"			<select multiple class=\"form-control\" id=\"delCli\" name=\"delCli\">\n";  

        for ( Cliente c : lc){
            resultado+= "<option value='"+c.getIdCliente()+"'> " +
               c.getApellido() + ", " + c.getNombre() + "</option>";
        }
        
        resultado+="			  </select>\n" +
"				</div>		  \n" +
"		  <button type=\"submit\" class=\"btn btn-default\">Submit</button>\n" +
"		</form>\n" +
"	</div>";
        return resultado;
    }
}
