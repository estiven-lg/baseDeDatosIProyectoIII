package com.umg.basedatosiproyectoiii;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import model.MySqlConnection;
import model.PostgressConnection;
import view.MainMenu;

public class BaseDatosIProyectoIII {

    public static void main(String[] args) throws IOException, SQLException {
        
      
        System.out.println(MySqlConnection.execute().getAllPersons());
        
        SwingUtilities.invokeLater(() -> {
            try {
                MainMenu frame = new MainMenu();
                frame.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(BaseDatosIProyectoIII.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }
}
