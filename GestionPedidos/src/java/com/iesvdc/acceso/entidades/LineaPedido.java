/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iesvdc.acceso.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juangu
 */
@Entity
@Table(name = "linea_pedido")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LineaPedido.findAll", query = "SELECT l FROM LineaPedido l"),
    @NamedQuery(name = "LineaPedido.findByIdLp", query = "SELECT l FROM LineaPedido l WHERE l.idLp = :idLp"),
    @NamedQuery(name = "LineaPedido.findByPrecio", query = "SELECT l FROM LineaPedido l WHERE l.precio = :precio"),
    @NamedQuery(name = "LineaPedido.findByCantidad", query = "SELECT l FROM LineaPedido l WHERE l.cantidad = :cantidad")})
public class LineaPedido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_lp", nullable = false)
    private Integer idLp;
    @Basic(optional = false)
    @Column(name = "precio", nullable = false)
    private float precio;
    @Basic(optional = false)
    @Column(name = "cantidad", nullable = false)
    private int cantidad;
    @JoinColumn(name = "id_pedido", referencedColumnName = "id_pedido")
    @ManyToOne
    private Pedido idPedido;
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    @ManyToOne
    private Producto idProducto;

    public LineaPedido() {
    }

    public LineaPedido(Integer idLp) {
        this.idLp = idLp;
    }

    public LineaPedido(Integer idLp, float precio, int cantidad) {
        this.idLp = idLp;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Integer getIdLp() {
        return idLp;
    }

    public void setIdLp(Integer idLp) {
        this.idLp = idLp;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Pedido getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Pedido idPedido) {
        this.idPedido = idPedido;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLp != null ? idLp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LineaPedido)) {
            return false;
        }
        LineaPedido other = (LineaPedido) object;
        if ((this.idLp == null && other.idLp != null) || (this.idLp != null && !this.idLp.equals(other.idLp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iesvdc.acceso.entidades.LineaPedido[ idLp=" + idLp + " ]";
    }
    
}
