package bookstop;

public class Boletas {
    
    int id_boleta;
    int fecha;
    int hora;
    String tipo_de_entrega;
    int cantidad;
    int precio_total;

    public void setId_boleta(int id_boleta) {
        this.id_boleta = id_boleta;
    }

    public int getId_boleta() {
        return id_boleta;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }

    public int getFecha() {
        return fecha;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getHora() {
        return hora;
    }

    public void setTipo_de_entrega(String tipo_de_entrega) {
        this.tipo_de_entrega = tipo_de_entrega;
    }

    public String getTipo_de_entrega() {
        return tipo_de_entrega;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setPrecio_total(int precio_total) {
        this.precio_total = precio_total;
    }

    public int getPrecio_total() {
        return precio_total;
    }

    @Override
    public String toString() {
       
        return super.toString();
    }
}
