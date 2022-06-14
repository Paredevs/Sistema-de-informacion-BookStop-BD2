package bookstop;

public class Vendidos {
    


    private int id_boleta;
    private int rut_cliente;
    private int fecha;
    private int hora;
    private String tipo_de_entrega;
    private int cantidad;
    public int precio_total;
    
  /*  public Vendidos(int id_boleta,int rut_cliente,int fecha,int hora,String tipo_de_entrega,int cantidad,int precio_total){
        this.id_boleta=id_boleta;
        this.rut_cliente=rut_cliente;
        this.fecha=fecha;
        this.hora=hora;
        this.tipo_de_entrega=tipo_de_entrega;
        this.cantidad=cantidad;
        this.precio_total=precio_total;

    }
    */
    public int getId_boleta() {
        return id_boleta;
    }
    public Vendidos setId_boleta(int id_boleta) {
        this.id_boleta = id_boleta;
        return this;
    }
    public int getRut_cliente() {
        return rut_cliente;
    }
    public Vendidos setRut_cliente(int rut_cliente) {
        this.rut_cliente = rut_cliente;
        return this;
    }
    public int getFecha() {
        return fecha;
    }
    public Vendidos setFecha(int fecha) {
        this.fecha = fecha;
        return this;
    }
    public int getHora() {
         return hora;
     }
    public Vendidos setHora(int hora) {
         this.hora = hora;
        return this;
        }
    public String getTipo_de_entrega() {
         return tipo_de_entrega;
     }
    public Vendidos setTipo_de_entrega(String tipo_de_entrega) {
        this.tipo_de_entrega = tipo_de_entrega;
        return this;
        }
    public int getCantidad() {
       return cantidad;
    }
    public Vendidos setCantidad(int cantidad) {
        this.cantidad = cantidad;
        return this;
    }
    public int getPrecio_total() {
        return precio_total;
    }
    public Vendidos setPrecio_total(int precio_total) {
        this.precio_total = precio_total;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Vendidos{");
        sb.append("id_boleta='").append(id_boleta).append('\'');
        sb.append("rut_cliente='").append(rut_cliente).append('\'');
        sb.append("fecha='").append(fecha).append('\'');
        sb.append("hora='").append(hora).append('\'');
        sb.append("tipo_de_entrega='").append(tipo_de_entrega).append('\'');
        sb.append("cantidad='").append(cantidad).append('\'');
        sb.append("precio_total='").append(precio_total).append('\'');
        sb.append('}');
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }
}
