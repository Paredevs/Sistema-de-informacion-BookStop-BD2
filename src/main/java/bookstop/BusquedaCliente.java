package bookstop;


import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;



import com.mongodb.client.MongoCursor;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class BusquedaCliente {
    
    final Dimension SIZE = new Dimension(1920,1080);
    int ID_BOLETA;
    JFrame window;
    JPanel panel;
    VentanaTabla ventanaTabla;
    Tabla tableModel; 
    static final Font FUENTE = new Font("chilanka",Font.PLAIN,34);
    private JComboBox<String> comboBox_principal,comboBox_tipos;
    private String tipos[] = {"Nombre","RUT","ID boleta","Fecha"};
    String[] columnNames_libro = {"ISBN","Titulo","Nombre del Autor","Editorial","Tipo de Genero","Tipo de subgenero","Stock","Precio"};
    String[] columnNames_ventas = {"ID boleta","Fecha","Hora","Tipo de entrega","Cantidad","Precio total"};
    String[] tipo_precio = {"Menor que $5000","Entre $5000 y $10000","Entre $10001 y $20000","Mayor que $20000"};
    JButton button_ingresar;
    Conexion conexion_busqueda;
    Clientes cliente_actual;
    Boletas boleta_actual;

    public BusquedaCliente(){
 
        createWindow();
        createGui();
        window.add(panel);
        
    }
    

private void createWindow() {

    window = new JFrame("Bookstop - Boletas");
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
        comboBox_principal.setModel(new DefaultComboBoxModel<String>(getandsortValues("Nombre")));
       
        
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
              comboBox_principal.setModel(new DefaultComboBoxModel<String>(getandsortValues("Nombre")));
            }

            if(comboBox_tipos.getSelectedIndex()==1){
                comboBox_principal.setModel(new DefaultComboBoxModel<String>(getandsortValues("RUT")));
            }

            if(comboBox_tipos.getSelectedIndex()==2){
                comboBox_principal.setModel(new DefaultComboBoxModel<String>(getIntegerandSort("ID boleta")));
            }

            if(comboBox_tipos.getSelectedIndex()==3){
                comboBox_principal.setModel(new DefaultComboBoxModel<String>(getIntegerandSort("Fecha")));
            }

                 
            
}

private String[] getandsortValues(String value) {

    Conexion conexion = new Conexion("Clientes");

        ArrayList<String> clientes =conexion.getClienteValues(value); 
        Collections.sort(clientes, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
    if(value.equals("RUT")){
         return formatoRUT(clientes.toArray(new String[clientes.size()]));
        //return clientes.toArray(new String[clientes.size()]);
        //return formatoRUT(clientes.toArray(new String[clientes.size()]));
    }else{
        return clientes.toArray(new String[clientes.size()]);
    }
  
    

    
    
  }


private String[] getIntegerandSort(String value) {

    Conexion conexion = new Conexion("Clientes");
    
    if(value.equals("ID boleta")){
        ArrayList<String> boletas = new ArrayList<String>();
        MongoCursor<Clientes> cursor = conexion.collection_Clientes.find().iterator();
            while(cursor.hasNext()){
                Clientes document = (Clientes)cursor.next();
                if(document != null){
                    if(document.getBoletas()!=null){

                        List<Boletas> boletas_cliente_actual = new ArrayList<>(document.getBoletas());
                        for(int x = 0; x < boletas_cliente_actual.size(); x++){
                            
                            Boletas boleta_actual = boletas_cliente_actual.get(x);
        
                            boletas.add(String.valueOf(boleta_actual.getId_boleta()));
                        
                        
                        }
                }
                }
            }
            return boletas.toArray(new String[boletas.size()]);

    }else{

            ArrayList<String> fechas = new ArrayList<String>();
            MongoCursor<Clientes> cursor = conexion.collection_Clientes.find().iterator();
                while(cursor.hasNext()){
                    Clientes document = (Clientes)cursor.next();
                    if(document != null){
                        if(document.getBoletas()!=null){
    
                            List<Boletas> fechas_cliente_actual = new ArrayList<>(document.getBoletas());
                            for(int x = 0; x < fechas_cliente_actual.size(); x++){
                            
                                Boletas boleta_actual = fechas_cliente_actual.get(x);

                                fechas.add(String.valueOf(setFecha(boleta_actual.getFecha())));
                    
                            
                            }
                    }
                    }
                }
                Set<String> set = new HashSet<>(fechas);
                 fechas.clear();          
                 fechas.addAll(set);
        
                return fechas.toArray(new String[fechas.size()]);

    }
    
    
    
    
  }


  private void getValues(){

    conexion_busqueda = new Conexion("Clientes");
  
    if(comboBox_tipos.getSelectedIndex()==0){

       // ArrayList<String> boletas = new ArrayList<String>();
        List<Boletas> boletas_cliente_actual;
        MongoCursor<Clientes> cursor = conexion_busqueda.collection_Clientes.find().iterator();
            while(cursor.hasNext()){
                Clientes document = (Clientes)cursor.next();
                if(document != null){
                    if(document.getNombre().equals(comboBox_principal.getSelectedItem())){
                        if(document.getBoletas()!=null){

                            boletas_cliente_actual = new ArrayList<>(document.getBoletas());
                            tableModel = new Tabla(columnNames_ventas,listToObject(boletas_cliente_actual),0,540,1920,540);
                            ventanaTabla= new VentanaTabla("Busqueda por nombre",tableModel);
                            ventanaTabla.setSize(1800,540);
                            ventanaTabla.setVisible(true);
                             
                            
                        }
                    }
                }
            }
        
    }
    if(comboBox_tipos.getSelectedIndex()==1){

        String number = comboBox_principal.getSelectedItem().toString().replaceAll("[^0-9]", "");
        
        List<Boletas> boletas_cliente_actual;
        MongoCursor<Clientes> cursor = conexion_busqueda.collection_Clientes.find().iterator();
            while(cursor.hasNext()){
                Clientes document = (Clientes)cursor.next();
                if(document != null){
                    if(document.getRut()==Integer.valueOf(number)){
                        if(document.getBoletas()!=null){

                            boletas_cliente_actual = new ArrayList<>(document.getBoletas());
                            tableModel = new Tabla(columnNames_ventas,listToObject(boletas_cliente_actual),0,540,1920,540);
                            ventanaTabla= new VentanaTabla("Boletas del RUT "+comboBox_principal.getSelectedItem().toString(),tableModel);
                            ventanaTabla.setSize(1800,540);
                            ventanaTabla.setVisible(true);
                             
                            
                        }
                    }
                }
            }
    

    }
    if(comboBox_tipos.getSelectedIndex()==2){

        String number = comboBox_principal.getSelectedItem().toString().replaceAll("[^0-9]", "");
        
        List<Boletas> boletas_cliente_actual;
        MongoCursor<Clientes> cursor = conexion_busqueda.collection_Clientes.find().iterator();
            while(cursor.hasNext()){
                Clientes document = (Clientes)cursor.next();
                if(document != null){
                        if(document.getBoletas()!=null){

                            boletas_cliente_actual = new ArrayList<>(document.getBoletas());
                            for (int i = 0; i < boletas_cliente_actual.size(); i++) {
                                Boletas boleta = boletas_cliente_actual.get(i);
                                if(boleta.getId_boleta()==Integer.valueOf(number)){

                                    Object[][] table = new Object[1][6];
  
                                    Boletas currentObject = boletas_cliente_actual.get(i);
                                    table[0][0] = currentObject.getId_boleta();
                                    table[0][1]= setFecha(currentObject.getFecha());
                                    table[0][2]= setHora(currentObject.getHora());
                                    table[0][3]= currentObject.getTipo_de_entrega();
                                    table[0][4]= currentObject.getCantidad();
                                    table[0][5]= currentObject.getPrecio_total();
                                  
                                    tableModel = new Tabla(columnNames_ventas,table,0,540,1920,540);
                                    ventanaTabla= new VentanaTabla("Busqueda por ID boleta "+Integer.valueOf(number),tableModel);
                                    ventanaTabla.setSize(1800,540);
                                    ventanaTabla.setVisible(true);

                            }
                             
                        }
                    }
                }
            }
    

    }
    if(comboBox_tipos.getSelectedIndex()==3){


        String number = comboBox_principal.getSelectedItem().toString().replaceAll("[^0-9]", "");
        
        List<Boletas> boletas_cliente_actual;
        List<Boletas> boletas_validas_fecha = new ArrayList<>();
        
        MongoCursor<Clientes> cursor = conexion_busqueda.collection_Clientes.find().iterator();
            while(cursor.hasNext()){
                Clientes document = (Clientes)cursor.next();
                if(document != null){
                        if(document.getBoletas()!=null){

                            boletas_cliente_actual = new ArrayList<>(document.getBoletas());
                            for (int i = 0; i < boletas_cliente_actual.size(); i++) {
                                Boletas boleta = boletas_cliente_actual.get(i);
                                if(boleta.getFecha()==Integer.valueOf(number)){

                                    boletas_validas_fecha.add(boleta);

                                    

                            }
                             
                        }

                    }
                }
            }



                        tableModel = new Tabla(columnNames_ventas,listToObject(boletas_validas_fecha),0,540,1920,540);
                        ventanaTabla= new VentanaTabla("Busqueda por fecha "+Integer.valueOf(number),tableModel);
                        ventanaTabla.setSize(1800,540);
                        ventanaTabla.setVisible(true);


    }

    ventaTabla();

  }

  public void ventaTabla(){


    tableModel.table.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
  
              try {
  
                Conexion conexion_libro = new Conexion("Libros");
                ID_BOLETA=Integer.parseInt(tableModel.table.getValueAt(tableModel.table.getSelectedRow(), 0).toString());  
              //  System.out.println("ID boleta: "+ID_BOLETA);
                List<Vendidos> vendidos ;
                MongoCursor<Libros> cursor = conexion_libro.collection_Libros.find().iterator();
                while(cursor.hasNext()){
                    Libros document = (Libros)cursor.next();
                    if(document != null){
                        if(document.getVendidos()!=null){
                            vendidos = new ArrayList<>(document.getVendidos());
                            for (int i = 0; i < vendidos.size(); i++) {
                                Vendidos vendido_actual = vendidos.get(i);
                                if(vendido_actual.getId_boleta()==ID_BOLETA){

                                    Object[][] datos = {{document.getIsbn(),document.getTitulo().toString(),document.getNombre_del_autor(),document.getEditorial() ,document.getTipo_genero(),document.getTipo_subgenero(),document.getStock() ,document.getPrecio()}}; 
                                    tableModel = new Tabla(columnNames_libro,datos,0,540,1920,540);
                                    ventanaTabla= new VentanaTabla("Libro de la boleta "+ID_BOLETA,tableModel);
                                    ventanaTabla.setSize(1800,540);
                                    ventanaTabla.setVisible(true);
                                    break;
                                }
                            }
                        }
                    }
            }
                

              } catch (IndexOutOfBoundsException a) {
  
                  JOptionPane.showMessageDialog(null,"No ha seleccionado una fila");
  
              }
                
            }
        }
    });






















  }

 
  public Object[][] listToObject(List<Boletas> data_object_list){

    Object[][] table = new Object[data_object_list.size()][6];
  
    for(int x = 0; x < data_object_list.size(); x++)
    {
    Boletas currentObject = data_object_list.get(x);
    table[x][0] = currentObject.getId_boleta();
    table[x][1]= setFecha(currentObject.getFecha());
    table[x][2]= setHora(currentObject.getHora());
    table[x][3]= currentObject.getTipo_de_entrega();
    table[x][4]= currentObject.getCantidad();
    table[x][5]= currentObject.getPrecio_total();
  
    }
    
    return table;
  
  }  

private String[] formatoRUT(String[] array) {

    for (int i = 0; i < array.length; i++) {
        if(array[i].length()>5 &&array[i].length()==9){
            String rut = array[i];
            rut = rut.substring(0, 2)+"."+rut.substring(2, 5)+"."+rut.substring(5, 8)+"-"+rut.substring(8, 9);
          //  System.out.println(rut);
            array[i] = rut;
        } 
        if(array[i].length()>5 &&array[i].length()==8){
            String rut = array[i];
            rut = rut.substring(0, 1)+"."+rut.substring(1, 4)+"."+rut.substring(4, 7)+"-"+rut.substring(7, 8);
        //    System.out.println(rut);
            array[i] = rut;
        }    //20.167.075-6   9.822.711-3
    }
    return array;
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


  public String setHora(int value) {

    String stringValue = String.valueOf(value);
    if(stringValue.length()==3){
      stringValue = "0"+stringValue.substring(0, 1)+":"+stringValue.substring(1, 3);
    }else{
      stringValue = stringValue.substring(0, 2)+":"+stringValue.substring(2, 4);
    }
    
    return stringValue;
  
  }



}
