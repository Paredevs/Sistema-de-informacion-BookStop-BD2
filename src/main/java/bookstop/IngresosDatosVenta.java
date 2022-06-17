package bookstop;

import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;

public class IngresosDatosVenta  {

    JPanel ingreJPanel;
    JLabel label_rut,label_tipoEntrega,label_cantidad;
    JTextField textField_rut,textField_cantidad;
    JComboBox<String> comboBox_entrega;
    JButton button_ingresar,button_ventasActuales;
    static final Font FUENTE = new Font("chilanka",Font.PLAIN,45);

    public void execute(String titulo) {

        JFrame ingresoDatosFrame = new JFrame("Ingreso datos de venta - "+titulo);
        ingresoDatosFrame.setSize(1920,1080);
        ingresoDatosFrame.setLayout(null);
        ingresoDatosFrame.setVisible(true);

        ingreJPanel = new JPanel(null);
        ingreJPanel.setBounds(0,0,1920,1080);
        
        JLabel fondo = new JLabel();
        
        ImageIcon imageIcon = new ImageIcon("/home/rodrigo/Desktop/Universidad/2022/Primer semestre/Base de Datos II/Taller 2/app/app/imagenes/images.jpeg"); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(1920, 1080,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back

        fondo.setIcon(imageIcon);
        fondo.setBounds(0, 0, 1920, 1080);

        label_rut = new JLabel("Rut:");
        label_rut.setBounds(20,40,150,50);
        label_rut.setFont(FUENTE);
        label_rut.setBackground(new Color(0, 0, 0, 0));
        label_rut.setForeground(Color.YELLOW);
    
        textField_rut = new JTextField();
        textField_rut.setColumns(10);
        textField_rut.setBounds(20,100,280,50);
        textField_rut.setFont(FUENTE);

        label_tipoEntrega = new JLabel("Tipo de entrega:");
        label_tipoEntrega.setBounds(20,190,400,50);
        label_tipoEntrega.setFont(FUENTE);
        label_tipoEntrega.setBackground(new Color(0, 0, 0, 0));
        label_tipoEntrega.setForeground(Color.YELLOW);

        String[] tipoEntrega = {"Presencial","Online"};
        comboBox_entrega = new JComboBox<String>(tipoEntrega);
        comboBox_entrega.setBounds(20,260,300, 50);
        comboBox_entrega.setFont(FUENTE);

        label_cantidad = new JLabel("Cantidad:");
        label_cantidad.setBounds(20,350,400,50);
        label_cantidad.setFont(FUENTE);
        label_cantidad.setBackground(new Color(0, 0, 0, 0));
        label_cantidad.setForeground(Color.YELLOW);

        textField_cantidad = new JTextField();
        textField_cantidad.setColumns(2);
        textField_cantidad.setBounds(20,410,100,50);
        textField_cantidad.setFont(FUENTE);

        button_ingresar = new JButton("Vender");
        button_ingresar.setBounds(1690, 900, 200, 50);
        button_ingresar.setFont(FUENTE);

        button_ventasActuales = new JButton("Vendidos actualmente");
        button_ventasActuales.setBounds(20, 900, 490, 50);
        button_ventasActuales.setFont(FUENTE);


        ingreJPanel.add(label_rut);
        ingreJPanel.add(textField_rut);
        ingreJPanel.add(label_tipoEntrega);
        ingreJPanel.add(comboBox_entrega);
        ingreJPanel.add(label_cantidad);
        ingreJPanel.add(textField_cantidad);
        ingreJPanel.add(button_ingresar);
        ingreJPanel.add(button_ventasActuales);
        ingreJPanel.add(fondo);
        
        
        

        ingresoDatosFrame.setContentPane(ingreJPanel);
        ingreJPanel.updateUI();
    }


    public String[] getValues() {

        String[] values = {textField_rut.getText(),comboBox_entrega.getSelectedItem().toString(),textField_cantidad.getText()};

        return values;
    }
    
}
