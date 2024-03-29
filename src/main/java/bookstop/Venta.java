package bookstop;


import static com.mongodb.client.model.Filters.eq;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.bson.Document;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;


public class Venta  {
   
    final Dimension SIZE = new Dimension(1920,1080);
    JFrame window;
    JFrame confirmacion_venta;
    JFrame crear_usuario;
    JPanel panel;

    Long ISBN;
   
    VentanaTabla ventanaTabla;
    Tabla tableModel; 

    Conexion conexion_busqueda;
    static final Font FUENTE = new Font("chilanka",Font.PLAIN,34);
    JButton button_ingresar;
    private JComboBox<String> comboBox_principal,comboBox_tipos;
    String[] columnNames_libro = {"ISBN","Titulo","Nombre del Autor","Editorial","Tipo de Genero","Tipo de subgenero","Stock","Precio"};
    String[] columnNames_ventas = {"ID boleta","RUT cliente","Fecha","Hora","Tipo de entrega","Cantidad","Precio total"};
    private String tipos[] = {"Titulos","Autores","Editoriales","Generos","Subgeneros","Precio"};
    String[] tipo_precio = {"Menor que $5000","Entre $5000 y $10000","Entre $10001 y $20000","Mayor que $20000"};
    MongoCollection<Libros> collection;
    IngresosDatosVenta ingresoVenta;
    Libros libro;
    
    
    public Venta(){
 
        createWindow();
        createGui();
        window.add(panel);
        
    }
    
private void createWindow() {

    window = new JFrame("Bookstop - Venta");
    window.setLayout(null);
    panel = new JPanel();
    panel.setLayout(null);
    panel.setBounds(0,0,1920,1080);
    window.setSize(SIZE);
    window.setVisible(true);
    window.setResizable(false);

    }

private void createGui() {

        JLabel fondo = new JLabel();
        
        ImageIcon imageIcon = new ImageIcon("/home/rodrigo/Desktop/Universidad/2022/Primer semestre/Base de Datos II/Taller 2/app/app/imagenes/images.jpeg"); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(1920, 1080,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back

        fondo.setIcon(imageIcon);
        fondo.setBounds(0, 0, 1920, 1080);

        JLabel label_isbn = new JLabel("Buscar por:");
        label_isbn.setForeground(Color.YELLOW);
        label_isbn.setBackground(new Color(0, 0, 0, 0));
        label_isbn.setBounds(200,450,500,50);
        label_isbn.setFont(FUENTE);

        
        comboBox_tipos = new JComboBox<String>(tipos);
        comboBox_tipos.setFont(FUENTE);
        comboBox_tipos.setBounds(200,490, 280, 50);
        comboBox_tipos.addActionListener (new ActionListener () {
          public void actionPerformed(ActionEvent e) {
              fill();
          }

  
      });
        comboBox_principal = new JComboBox<String>();
        comboBox_principal.setModel(new DefaultComboBoxModel<String>(getandsortValues("Titulo")));
       
        
        comboBox_principal.setBounds(500,490,950,50);
        comboBox_principal.setFont(FUENTE);

        AutoCompleteDecorator.decorate(comboBox_principal);
       
       
        

        button_ingresar = new JButton("Buscar");
        button_ingresar.setBounds(1500,490,230,50);
        button_ingresar.setFont(FUENTE);

        button_ingresar.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent arg0) {
                getValues();
                //System.out.println(comboBox_principal.getSelectedItem().toString());
            }});

        panel.add(label_isbn);
        panel.add(comboBox_tipos);
        panel.add(comboBox_principal);
        panel.add(button_ingresar);
        panel.add(fondo);
        panel.updateUI();
        
    }

public void fill(){

if(comboBox_tipos.getSelectedIndex()==0){
  comboBox_principal.setModel(new DefaultComboBoxModel<String>(getandsortValues("Titulo")));
}
if(comboBox_tipos.getSelectedIndex()==1){
  comboBox_principal.setModel(new DefaultComboBoxModel<String>(getandsortValues("Autor")));
}
if(comboBox_tipos.getSelectedIndex()==2){
  comboBox_principal.setModel(new DefaultComboBoxModel<String>(getandsortValues("Editorial")));
}
if(comboBox_tipos.getSelectedIndex()==3){
  comboBox_principal.setModel(new DefaultComboBoxModel<String>(getandsortValues("Genero")));
}
if(comboBox_tipos.getSelectedIndex()==4){
  comboBox_principal.setModel(new DefaultComboBoxModel<String>(getandsortValues("Subgenero")));
}

if(comboBox_tipos.getSelectedIndex()==5){
  
  comboBox_principal.setModel(new DefaultComboBoxModel<String>(tipo_precio));
  
}


}

private String[] getandsortValues(String value) {

  Conexion conexion = new Conexion("Libros");
  ArrayList<String> titulos =conexion.getstringValues(value); 
  Collections.sort(titulos, new Comparator<String>() {
          @Override
          public int compare(String s1, String s2) {
              return s1.compareToIgnoreCase(s2);
          }
      });

  return titulos.toArray(new String[titulos.size()]);
}

private void getValues(){

  conexion_busqueda = new Conexion("Libros");

  if(comboBox_tipos.getSelectedIndex()==0){

    libro = conexion_busqueda.collection_Libros.find(eq("titulo",comboBox_principal.getSelectedItem())).first(); 
    Object[][] datos = {{libro.getIsbn(),libro.getTitulo().toString(),libro.getNombre_del_autor(),libro.getEditorial() ,libro.getTipo_genero(),libro.getTipo_subgenero(),libro.getStock() ,libro.getPrecio()}}; 
    tableModel = new Tabla(columnNames_libro,datos,0,540,1920,540);
    JFrame vendidos_frame = new JFrame(libro.getTitulo()+" - BookStop");
    vendidos_frame.setSize(1800,540);
    vendidos_frame.setVisible(true);
    vendidos_frame.setContentPane(tableModel);
    ventaTabla();
  
  }

  if(comboBox_tipos.getSelectedIndex()==1){
  
    tableModel = new Tabla(columnNames_libro,listToObject(getDataPerValue("nombre_del_autor")),0,540,1920,540);
    ventanaTabla= new VentanaTabla("Busqueda por autor",tableModel);
    ventanaTabla.setSize(1800,540);
    ventanaTabla.setVisible(true);
    ventaTabla();
   
  }

  if(comboBox_tipos.getSelectedIndex()==2){
  
    tableModel = new Tabla(columnNames_libro,listToObject(getDataPerValue("editorial")),0,540,1920,540);
    ventanaTabla= new VentanaTabla("Busqueda por editorial",tableModel);
    ventanaTabla.setSize(1800,540);
    ventanaTabla.setVisible(true);
    ventaTabla();
     
  }

  if(comboBox_tipos.getSelectedIndex()==3){
  
    tableModel = new Tabla(columnNames_libro,listToObject(getDataPerValue("tipo_genero")),0,540,1920,540);
    ventanaTabla= new VentanaTabla("Busqueda por generos",tableModel);
    ventanaTabla.setSize(1800,540);
    ventanaTabla.setVisible(true);
    ventaTabla();

  }

  if(comboBox_tipos.getSelectedIndex()==4){
   
    tableModel = new Tabla(columnNames_libro,listToObject(getDataPerValue("tipo_subgenero")),0,540,1920,540);
    ventanaTabla =  new VentanaTabla("Busqueda por subgenero",tableModel);
    ventanaTabla.setVisible(true);
    ventaTabla();

  }

  if(comboBox_tipos.getSelectedIndex()==5){
   
    tableModel = new Tabla(columnNames_libro,listToObject(getdataPrecio()),0,540,1920,540);
    ventanaTabla =  new VentanaTabla("Busqueda por precio",tableModel);
    ventanaTabla.setVisible(true);
    ventaTabla();

  }


 /* 
   if((textfield_isbn.getText().length()!=0 && textfield_isbn.getText().length()==13) && textfield_cantidad.getText().length()!=0  && textfield_nombre_cliente.getText().length()!=0 && textfield_rut.getText().length()!= 0){
     System.out.println("entro");
       


      Libros libro_ejemplo = new Libros().setIsbn(0L).setTitulo("ejemplo").setVendidos(singletonList(new Vendidos().setId_boleta(0)));
        
    
        
        collection.insertOne(libro_ejemplo);
      Libros libro = collection.find(eq("isbn", 9509970667528L)).first();
     System.out.println("Libro encontrado:\t" + libro);
    
        
         update this grade: adding an exam grade
       List<Vendidos> vendidos = new ArrayList<>(libro.getVendidos());
       System.out.println(vendidos.get(0).getHora());  Par buscar en vendidos
      /vendidos.add(new Vendidos().setId_boleta(00000).setRut_cliente(0000));
        */
  /*      try {
            
            Libros libro = collection.find(eq("isbn",Long.parseLong(textfield_isbn.getText().toString()))).first();  
            Object[][] datos = {{libro.getIsbn(),libro.getTitulo().toString(),libro.getNombre_del_autor(),libro.getEditorial() ,libro.getTipo_genero(),libro.getTipo_subgenero(),libro.getStock() ,libro.getPrecio()}};
            Tabla tabla_principal = new Tabla(columnNames_libro,datos,0,540,1920,540);
            window.add(tabla_principal);
            panel.updateUI();
          
            if(libro.getVendidos()!=null){

                List<Vendidos> vendidos = new ArrayList<>(libro.getVendidos());
                //   System.out.println(vendidos.get(0).getHora()); // Para buscar en vendidos
                   //System.out.println(libro.getVendidos());
                //System.out.println("Existen ventas");
                Object[][] table = new Object[vendidos.size()][7];

                for(int x = 0; x < vendidos.size(); x++)
                {
                Vendidos currentObject = vendidos.get(x);
                table[x][0] = currentObject.getId_boleta();
                table[x][1]= currentObject.getRut_cliente();
                table[x][2]= currentObject.getFecha();
                table[x][3]= currentObject.getHora();
                table[x][4]= currentObject.getTipo_de_entrega();
                table[x][5]= currentObject.getCantidad();
                table[x][6]= currentObject.getPrecio_total();
                // Load the first value.
     
    
                }
              //  Object[][] datos_vendidos = {{vendidos.get(0).getId_boleta(),vendidos.get(0).getRut_cliente(),vendidos.get(0).getFecha(),vendidos.get(0).getHora(),vendidos.get(0).getTipo_de_entrega(),vendidos.get(0).getCantidad(),vendidos.get(0).getPrecio_total()}};
                
                Tabla tabla_vendidos = new Tabla(columnNames_ventas,table,0,540,1920,540);
                JFrame vendidos_frame = new JFrame("Vendidos");
                vendidos_frame.setSize(1800,540);
                vendidos_frame.setVisible(true);
                vendidos_frame.setContentPane(tabla_vendidos);

                if((libro.getStock()-Integer.parseInt(textfield_cantidad.getText()))>=0){
                  
            //        Object[][] datos_despues_de_venta = {{libro.getIsbn(),libro.getTitulo().toString(),libro.getNombre_del_autor(),libro.getEditorial() ,libro.getTipo_genero(),libro.getTipo_subgenero(),(libro.getStock()-Integer.parseInt(textfield_cantidad.getText())),libro.getPrecio()}};
              //      Tabla tabla_libro_vendido = new Tabla(columnNames_libro , datos_despues_de_venta,0,800,1920,540);        
                //    window.add(tabla_libro_vendido);
                  //  panel.updateUI();
                }else{
                    JFrame frame_stock = new JFrame();
                     JOptionPane.showMessageDialog(frame_stock, "STOCK INSUFICIENTE");
                }
            
            }else{
                JFrame emergente = new JFrame();
                JOptionPane.showMessageDialog(emergente, "No existen ventas");
            }
            
        } catch (NullPointerException e) {
            JFrame emergente = new JFrame();
            JOptionPane.showMessageDialog(emergente, "El libro no existe");
        }
        */
}

public void ventaTabla(){

  
    tableModel.table.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
          if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {

            try {

              ISBN=Long.parseLong(tableModel.table.getValueAt(tableModel.table.getSelectedRow(), 0).toString()); 
              libro = conexion_busqueda.collection_Libros.find(eq("isbn", ISBN)).first(); 
              System.out.println("ISBN: "+ISBN);
              ingresoVenta = new IngresosDatosVenta();
              ingresoVenta.execute(tableModel.table.getValueAt(tableModel.table.getSelectedRow(), 1).toString());
              ingresoVenta.button_ventasActuales.addActionListener(new ActionListener(){
              @Override
              public void actionPerformed(ActionEvent arg0) {

                libro = conexion_busqueda.collection_Libros.find(eq("isbn", ISBN)).first();
                if(libro.getVendidos()!=null){

                    libro = conexion_busqueda.collection_Libros.find(eq("isbn", ISBN)).first();
                    List<Vendidos> vendidos = new ArrayList<>(libro.getVendidos());
                    Object[][] table = new Object[vendidos.size()][7];
                    for(int x = 0; x < vendidos.size(); x++){
                       
                        Vendidos currentObject = vendidos.get(x);
                        table[x][0] = currentObject.getId_boleta();
                        table[x][1]= currentObject.getRut_cliente();
                        table[x][2]= setFecha(currentObject.getFecha());
                        table[x][3]= setHora(currentObject.getHora());
                        table[x][4]= currentObject.getTipo_de_entrega();
                        table[x][5]= currentObject.getCantidad();
                        table[x][6]= currentObject.getPrecio_total();
                    }

                    
                    Tabla tabla_vendidos = new Tabla(columnNames_ventas,table,0,540,1920,540);
                    JFrame vendidos_frame = new JFrame("Vendidos");
                    vendidos_frame.setSize(1800,540);
                    vendidos_frame.setVisible(true);
                    vendidos_frame.setContentPane(tabla_vendidos);

                }else{

                  JFrame emergente = new JFrame();
                  JOptionPane.showMessageDialog(emergente, "No existen ventas");

                }
      
              }});
              ingresoVenta.button_ingresar.addActionListener(new ActionListener(){
        
                  @Override
                  public void actionPerformed(ActionEvent arg0) {
                    
                    String[] values = ingresoVenta.getValues();
                    if(values[0].length()>0 && values[2].length()>0  && values[1].length()>0  && values[3].length()>0){

                      String nombre_cliente = values[0];
                      int numero_cliente = Integer.parseInt(values[1]);
                      int rut = Integer.parseInt(values[2]);
                      int cantidad = Integer.parseInt(values[4]);
                      int nuevoStock = libro.getStock()-cantidad;
                      String modalidad = values[3];
                      int boleta = (int) Math.floor(Math.random()*(999999-100000+1)+100000);  
                      int precio_total = libro.getPrecio()*cantidad;
    
                      if(libro.getStock()==0){

                        JFrame no_stock = new JFrame();
                        JOptionPane.showMessageDialog(no_stock, "NO HAY STOCK DEL LIBRO: "+libro.getTitulo());
                        return;

                      }else{

            
                      
                        if(nuevoStock<0){
                        
                          int dialogButton = JOptionPane.showConfirmDialog (null, "NO HAY SUFICIENTE STOCK\nDesea comprar los "+libro.getStock()+" restantes?","Advertencia",JOptionPane.YES_NO_OPTION);
  
                          if(dialogButton == JOptionPane.NO_OPTION) {
                            return;
                          }else{
                             nuevoStock = 0;
                             cantidad=cantidad-libro.getStock();
                          }
  
                        
                        }
                        
                      
                      

                      while(compruebaBoleta(boleta)){ //Comprueba de que el id_de_boleta no exista

                        boleta = (int) Math.floor(Math.random()*(999999-1+1)+1);  
                    
                      }

                      if(compruebaRut(rut)){ //Si el rut existe se agrega la boleta

                        Conexion conexion = new Conexion("Clientes");
                        DBObject listItem = new BasicDBObject("boletas", new BasicDBObject("id_boleta",boleta).append("fecha",getFecha()).append("hora", getHora()).append("tipo_de_entrega", modalidad).append("cantidad", cantidad).append("precio_total", precio_total));
                        conexion.collection_Clientes.updateOne(eq("rut",rut), new Document().append("$push", listItem)); //Se encarga de añadir la boleta a el rut si es que existe
                    
                      }else{//Si no, se crea un usuario

                            Conexion conexion_clientes = new Conexion("Clientes");

                            Clientes cliente_nuevo = new Clientes();
                            

                          
                            List<Boletas> lista_boletas_nuevo_cliente = new ArrayList<Boletas>();
                            Boletas boleta_cliente_nuevo = new Boletas();
                            boleta_cliente_nuevo.setId_boleta(boleta);
                            boleta_cliente_nuevo.setFecha(getFecha());
                            boleta_cliente_nuevo.setHora(getHora());
                            boleta_cliente_nuevo.setTipo_de_entrega(modalidad);
                            boleta_cliente_nuevo.setCantidad(cantidad);
                            boleta_cliente_nuevo.setPrecio_total(precio_total);

                            lista_boletas_nuevo_cliente.add(boleta_cliente_nuevo);

                            cliente_nuevo.setBoletas(lista_boletas_nuevo_cliente);

                            cliente_nuevo.setRut(rut);
                            cliente_nuevo.setNombre(nombre_cliente);
                            cliente_nuevo.setNumero(numero_cliente);
                            if(modalidad.equals("Presencial")){
                              cliente_nuevo.setDireccion("Av. BookStop");
                            }else{
                              cliente_nuevo.setDireccion("Universidad de La Serena");
                            }

                            conexion_clientes.collection_Clientes.insertOne(cliente_nuevo);

                            JFrame nuevo_usuario = new JFrame();
                            JOptionPane.showMessageDialog(nuevo_usuario, "Se ha añadido nuevo cliente");

                        
                        
                        
                    
                      }

                      if(libro.getVendidos()!=null){

                           
                          conexion_busqueda.collection_Libros.updateOne(Filters.eq("isbn",ISBN), Updates.set("stock",nuevoStock));
                          DBObject listItem = new BasicDBObject("vendidos", new BasicDBObject("id_boleta",boleta).append("rut_cliente",rut).append("fecha",getFecha()).append("hora", getHora()).append("tipo_de_entrega", modalidad).append("cantidad", cantidad).append("precio_total", libro.getPrecio()*cantidad));
                          conexion_busqueda.collection_Libros.updateOne(eq("isbn",ISBN), new Document().append("$push", listItem)); //Se añade una venta al libro
                          
                          JFrame emergente = new JFrame();
                          JOptionPane.showMessageDialog(emergente, libro.getTitulo()+" Vendido");
                          
                      }else{ //si el libro no tiene ventas, creamos un nuevo libro, traspasamos los atributos y añadimos su primera venta

                          
                          Libros libro_nuevo = new Libros();
                          libro_nuevo.setIsbn(ISBN);
                          libro_nuevo.setTitulo(libro.getTitulo());
                          libro_nuevo.setNombre_del_autor(libro.getNombre_del_autor());
                          libro_nuevo.setEditorial(libro.getEditorial());
                          libro_nuevo.setTipo_genero(libro.getTipo_genero());
                          libro_nuevo.setTipo_subgenero(libro.getTipo_subgenero());
                          libro_nuevo.setStock(nuevoStock);
                          libro_nuevo.setPrecio(libro.getPrecio());
                        
                      
                          List<Vendidos> lista_vendidos = new ArrayList<Vendidos>();
                          Vendidos nueva_venta = new Vendidos();
                  
                          nueva_venta.setId_boleta(boleta);
                          nueva_venta.setRut_cliente(rut);
                          nueva_venta.setFecha(getFecha());
                          nueva_venta.setHora(getHora());
                          nueva_venta.setTipo_de_entrega(modalidad);
                          nueva_venta.setCantidad(cantidad);
                          nueva_venta.setPrecio_total(libro.getPrecio()*cantidad);
                          lista_vendidos.add(nueva_venta);
                          libro_nuevo.setVendidos(lista_vendidos);
                      
                          //save the modified object
                          conexion_busqueda.collection_Libros.replaceOne(eq("isbn", ISBN), libro_nuevo); //Se crea el arreglo de vendidos y se añade la venta
                          JFrame emergente = new JFrame();
                          JOptionPane.showMessageDialog(emergente, libro.getTitulo()+" Vendido");

                      }

                      }
                      
                  }
                
                }});

            } catch (IndexOutOfBoundsException a) {

                JOptionPane.showMessageDialog(null,"No ha seleccionado una fila");

            }
              
          }
      }
  });
 
}

public Object[][] listToObject(List<Libros> data_object_list){

  Object[][] table = new Object[data_object_list.size()][8];

  for(int x = 0; x < data_object_list.size(); x++)
  {
  Libros currentObject = data_object_list.get(x);
  table[x][0] = currentObject.getIsbn();
  table[x][1]= currentObject.getTitulo();
  table[x][2]= currentObject.getNombre_del_autor();
  table[x][3]= currentObject.getEditorial();
  table[x][4]= currentObject.getTipo_genero();
  table[x][5]= currentObject.getTipo_subgenero();
  table[x][6]= currentObject.getStock();
  table[x][7]= currentObject.getPrecio();

  }
  
  return table;

}  

public List<Libros> getDataPerValue(String value){

  List<Libros> list_objects = new ArrayList<>();
  FindIterable<Libros> iterable = conexion_busqueda.collection_Libros.find(eq(value, comboBox_principal.getSelectedItem()));
  MongoCursor<Libros> cursor = iterable.iterator();
  while (cursor.hasNext()) {
    
    list_objects.add(cursor.next());
                   
   }  

        return list_objects;  

}

public List<Libros> getdataPrecio(){

  List<Libros> list_objects = new ArrayList<>();
  MongoCursor<Libros> cursor = conexion_busqueda.collection_Libros.find().iterator();
  while(cursor.hasNext()){
      Libros libro = cursor.next();
      if(libro != null){
          if(comboBox_principal.getSelectedIndex()==0){
            if(libro.getPrecio()<5000){
              list_objects.add(libro);
            }
          }
          if(comboBox_principal.getSelectedIndex()==1){
            if(libro.getPrecio()>=5000 && libro.getPrecio()<=10000){
              list_objects.add(libro);
            }
          }
          if(comboBox_principal.getSelectedIndex()==2){
            if(libro.getPrecio()>=10001 && libro.getPrecio()<=20000){
              list_objects.add(libro);
            }
          }
          if(comboBox_principal.getSelectedIndex()==3){
            if(libro.getPrecio()>20000){
              list_objects.add(libro);
            }
          }  
      }
  } 

  return list_objects;
}

public String setHora(int value) {

  String stringValue = String.valueOf(value);
  if(stringValue.length()==3){
    stringValue = "0"+stringValue.substring(0, 1)+":"+stringValue.substring(1, 3);
  }else{
    stringValue = stringValue.substring(0, 2)+":"+stringValue.substring(2, 4);
  }
  
  return stringValue;

}

public String setFecha(int value){

  String stringValue = String.valueOf(value);
    if(stringValue.length()==5){
        
        stringValue = "0"+stringValue.substring(0, 1)+"/"+stringValue.substring(1, 3)+"/"+stringValue.substring(3, 5);
        
    }else{
        stringValue = stringValue.substring(0, 2)+"/"+stringValue.substring(2, 4)+"/"+stringValue.substring(4, 6);
    }
    return stringValue;
}

public int getHora() {
  
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
    String value = dtf.format(LocalDateTime.now());
    int hora =  Integer.parseInt((value.substring(0, 2)+value.substring(3, 5)));
    return hora;
}

public int getFecha(){
    
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String value = dtf.format(LocalDateTime.now());
    int fecha =  Integer.parseInt((value.substring(0, 2)+value.substring(3, 5)+value.substring(8, 10)));
    return fecha;
}

public boolean compruebaBoleta(int numero_boleta){

  Conexion conexion = new Conexion("Clientes");
  
  MongoCursor<Clientes> cursor = conexion.collection_Clientes.find().iterator();
    while(cursor.hasNext()){
        Clientes document = (Clientes)cursor.next();
        if(document != null){
            if(document.getBoletas()!=null){

                List<Boletas> boletas = new ArrayList<>(document.getBoletas());
                for(int x = 0; x < boletas.size(); x++){
                  
                    Boletas boleta_actual = boletas.get(x);
                  // table[x][0] = currentObject.getId_boleta();
                  if(boleta_actual.getId_boleta()==numero_boleta){
                    return true;
                  }
                
                }
          }
        }
    }


  
  return false;
}

public boolean compruebaRut(int rut){

  
  Conexion conexion = new Conexion("Clientes");
  
  MongoCursor<Clientes> cursor = conexion.collection_Clientes.find().iterator();
    while(cursor.hasNext()){
        Clientes document = (Clientes)cursor.next();
        if(document != null){
            if(document.getRut()==rut){
                return true;
            }
                
        }
    }
  
  return false;
}
}
