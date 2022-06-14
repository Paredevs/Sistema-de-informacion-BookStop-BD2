package bookstop;

import javax.swing.JFrame;


import java.awt.Graphics;

public class VentanaTabla extends JFrame {

    Tabla tabla;

    public VentanaTabla(String titulo,Tabla tabla){
        this.tabla = tabla;
        setTitle(titulo);

    }
    


    public void paint(Graphics g) {
        
        setSize(1800,540);
        setContentPane(tabla);
        
   }
}
