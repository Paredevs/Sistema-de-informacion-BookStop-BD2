package bookstop;

import java.util.List;

public class Clientes {

    int rut;
    String nombre;
    int numero;
    String direccion;
    List<Boletas> boletas;

    public void setRut(int rut) {
        this.rut = rut;
    }

    public int getRut() {
        return rut;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setBoletas(List<Boletas> boletas) {
        this.boletas = boletas;
    }
    public List<Boletas> getBoletas() {
        return boletas;
    }

    @Override
    public String toString() {
        
        return super.toString();
    }

}
