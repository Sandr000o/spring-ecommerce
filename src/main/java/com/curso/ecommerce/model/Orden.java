package com.curso.ecommerce.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ordenes")
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String numero;
    private Date fechaCreacion;
    private Date fechaRecepcion;
    private String imagen;
    private double total;

    @OneToOne(mappedBy = "orden")
    private DetalleOrden detalleOrden;

    @ManyToOne
    private Usuario usuario;


    public Orden() {
    }

    public Orden(Integer id, String numero, Date fechaCreacion, Date fechaRecepcion, String imagen, double total) {
        this.id = id;
        this.numero = numero;
        this.fechaCreacion = fechaCreacion;
        this.fechaRecepcion = fechaRecepcion;
        this.imagen = imagen;
        this.total = total;
    }

    public Orden(Integer id, String numero, Date fechaCreacion, Date fechaRecepcion, String imagen, double total, DetalleOrden detalleOrden, Usuario usuario) {
        this.id = id;
        this.numero = numero;
        this.fechaCreacion = fechaCreacion;
        this.fechaRecepcion = fechaRecepcion;
        this.imagen = imagen;
        this.total = total;
        this.detalleOrden = detalleOrden;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }


    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public DetalleOrden getDetalleOrden() {
        return detalleOrden;
    }

    public void setDetalleOrden(DetalleOrden detalleOrden) {
        this.detalleOrden = detalleOrden;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }



    @Override
    public String toString() {
        return "Orden" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaRecepcion=" + fechaRecepcion +
                ", imagen='" + imagen + '\'' +
                ", total=" + total;
    }
}
