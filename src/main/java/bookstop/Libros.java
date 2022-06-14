package bookstop;

import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;


// imports

public class Libros {

    @BsonProperty(value = "isbn")
    private long isbn;
    private String titulo;
    private String nombre_del_autor;
    private String editorial;
    private String tipo_genero;
    private String tipo_subgenero;
    private int stock;
    private int precio;
    private List<Vendidos> vendidos;

    public Libros setIsbn(Long isbn){
        this.isbn=isbn;
        return this;
    }
    public Long getIsbn(){
        return isbn;
    }    
    public Libros setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }
    public String getTitulo() {
        return titulo;
    }
    public Libros setNombre_del_autor(String nombre_del_autor) {
        this.nombre_del_autor = nombre_del_autor;
        return this;
    }
    public String getNombre_del_autor() {
        return nombre_del_autor;
    }
    public Libros setEditorial(String editorial) {
        this.editorial = editorial;
        return this;
    }
    public String getEditorial() {
        return editorial;
    }
    public Libros setTipo_genero(String tipo_genero) {
        this.tipo_genero = tipo_genero;
        return this;
    }
    public String getTipo_genero() {
        return tipo_genero;
    }
    public Libros setTipo_subgenero(String tipo_subgenero) {
        this.tipo_subgenero = tipo_subgenero;
        return this;
    }
    public String getTipo_subgenero() {
        return tipo_subgenero;
    }
    public Libros setStock(int stock) {
        this.stock = stock;
        return this;
    }
    public int getStock() {
        return stock;
    }
    public Libros setPrecio(int precio) {
        this.precio = precio;
        return this;
    }
    public int getPrecio() {
        return precio;
    }

    public Libros setVendidos(List<Vendidos> vendidos){
        this.vendidos=vendidos;
        return this;
    }
    public List<Vendidos> getVendidos(){
        return vendidos;
    }
    
    

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Libro{");
        sb.append("isbn=").append(isbn);
        sb.append(", titulo=").append(titulo).append('\'');
        sb.append(", nombre del autor='").append(nombre_del_autor).append('\'');
        sb.append(", Edtorial='").append(editorial).append('\'');
        sb.append(", Tipo de genero='").append(tipo_genero).append('\'');
        sb.append(", Tipo de subgenero='").append(tipo_subgenero).append('\'');
        sb.append(", Stock='").append(stock).append('\'');
        sb.append(", Precio='").append(precio).append('\'');
        sb.append(", Vendidos='").append(vendidos).append('\'');


        sb.append('}');
        return sb.toString();
    }
    
}