package bookstop;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;



public class Menu {
    

    private JPanel panel;
    public JFrame window;


    public void execute() {

        createWindow();
        createGui();
        functionButtons();
        

    }

    

    private void createWindow() {
        
        window = new JFrame();
        window.setTitle("Bookstop - Menu");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setSize(600,800);
        window.setVisible(true);
        window.setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        window.add(panel);
       

    }
    private void createGui() {

        JButton button_sale = new JButton("Venta");
        JButton button_pedido = new JButton("Pedido");  // duki
        JButton button_borrado = new JButton("Borrado");

        button_sale.setBounds(240,100,130,40);
        button_pedido.setBounds(240,200,130,40);
        button_borrado.setBounds(240,300,130,40);

        button_sale.setFont(new Font("Chilanka", Font.PLAIN,25));
        button_pedido.setFont(new Font("Chilanka", Font.PLAIN,25));
        button_borrado.setFont(new Font("Chilanka", Font.PLAIN,25));
        
        panel.add(button_sale);
        panel.add(button_pedido);
        panel.add(button_borrado);
        panel.updateUI();

        button_sale.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Venta venta_window = new Venta();
                
            }});

          


    }
    private void functionButtons() {


    }
    
}
