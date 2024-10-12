package view;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.TabListener;

public class MainMenu extends JFrame {

    public MainMenu() throws SQLException {
        setTitle("Renap");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Agregar las pestañas desde archivos separados
        tabbedPane.addTab("My Sql 🐬", new MySqlView());
        tabbedPane.addTab("Postgress 🐘", new PostgresView());  
        tabbedPane.addTab("Log", new LogView());

        
               // Agregar ChangeListener al tabbedPane
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex();
                if (selectedIndex >= 0) {
                    // Obtener la vista seleccionada y llamar a onTabSelected
                    Component selectedComponent = tabbedPane.getSelectedComponent();
                    if (selectedComponent instanceof TabListener) {
                        ((TabListener) selectedComponent).onTabSelected();
                    }
                }
            }
        });

        // Agregar el JTabbedPane al JFrame
        add(tabbedPane, BorderLayout.CENTER);
    }


}

