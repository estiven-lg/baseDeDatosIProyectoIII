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

        tabbedPane.addTab("My Sql 🐬", new MySqlView());
        tabbedPane.addTab("Postgress 🐘", new PostgresView());
        tabbedPane.addTab("Log", new LogView());

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex();
                if (selectedIndex >= 0) {

                    Component selectedComponent = tabbedPane.getSelectedComponent();
                    if (selectedComponent instanceof TabListener) {
                        ((TabListener) selectedComponent).onTabSelected();
                    }
                }
            }
        });

        add(tabbedPane, BorderLayout.CENTER);
    }

}
