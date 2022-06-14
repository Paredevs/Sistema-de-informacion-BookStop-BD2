package bookstop;


import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

public class Tabla extends JPanel{

    JTable table;
    

    public Tabla(String[] columnNames,Object[][] datos,int x,int y,int width,int height ){
        
        super(new GridLayout());
        setBackground(Color.YELLOW);
        setBounds(x,y,width,height);
        setOpaque(true);
        
        table = new JTable(datos, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
            return false;
        }};
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        table.getTableHeader().setFont(new Font("Chilanka", Font.PLAIN, 30));
        table.setFont(new Font("Chilanka", Font.PLAIN, 30));
        table.setRowHeight(30);
        table.setSize(900,900);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setSelectionForeground(Color.green);
        
        JScrollPane scrollPane = new JScrollPane(table);                          //Create the scroll pane and add the table to it.
        add(scrollPane);                                                          //Add the scroll pane to this panel.
    }


    
    
}
