package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Map;

public class Frame extends JFrame implements ActionListener {

    //TODO - refactor variables
    private JMenu menu, info,edycja,backup;
    private JMenuItem klient,zamowienie,elementy,towarMenu,dokumentacja,instrukcja,dodanie,usuniecie, edytowanie,saveBackUp,loadBackUp;
    private JMenuBar menuBar = new JMenuBar();
    private JRadioButtonMenuItem rbMenuItem;
    private Panel panel = new Panel();
    Controller controller = null;

    private static String dokumentacjaApp = "Created by (A+V)*L";
    private static String instrukcjaApp = "1.Uruchomic\n"+"2.Dodqc dane";

    @SuppressWarnings("unchecked")
    public Frame()
    {
        super("DateBase");
        controller = new Controller();
        String user = JOptionPane.showInputDialog( null, "Enter User Name");
        String password = JOptionPane.showInputDialog(null, "Enter Password" );
        while(user != null && password != null) {
            if(controller.logToDatabase(user,password)) {
                JOptionPane.showMessageDialog(this,"U r logged to database");
                break;
            } else {
                JOptionPane.showMessageDialog(this,"Incorrect parameters");
                user = JOptionPane.showInputDialog( null, "Enter User Name one more time");
                password = JOptionPane.showInputDialog(null, "Enter Password one more time" );
            }
        }
        menu = new JMenu("Menu");
        edycja = new JMenu("Edycja");
        info = new JMenu("Info");
        backup = new JMenu("BackUp");
        klient = new JMenuItem("Klient");
        klient.addActionListener(e -> {
            panel.setTable(controller.selectKlient());
        });
        zamowienie = new JMenuItem("Zamowienie");
        zamowienie.addActionListener(e -> {
            panel.setTable(controller.selectZamowienie());
        });
        elementy = new JMenuItem("Elementy");
        elementy.addActionListener(e -> {
            panel.setTable(controller.selectElementZamowienia());
        });
        towarMenu = new JMenuItem("Towar");
        towarMenu.addActionListener(e -> {
            panel.setTable(controller.selectTowar());
            this.pack();
        });
        dodanie = new JMenuItem("Dodanie");
        dodanie.addActionListener(e -> {
            Map<String, Object> map = openModifyFrame(controller.getDataBaseObjects().get(0).getAsMap());
            try {
                controller.addOrModifyDataBaseObject(map);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                handleException(throwables);
            }
            panel.setTable(controller.refreshTable());
        });
        edytowanie = new JMenuItem("Edytowanie");
        edytowanie.addActionListener(e-> {
            try {
                int id = panel.getSelectedObjectId();
                if(id == -1) {
                    System.out.println("No row selected");  //TODO owrapować wyjątki i to też w jakieś sensowne komunikaty
                } else {
                    Map<String, Object> map = openModifyFrame(controller.getObjectById(id).getAsMap());
                    controller.addOrModifyDataBaseObject(map);
                    panel.setTable(controller.refreshTable());
                }
            } catch(SQLException throwables) {
                throwables.printStackTrace();
                handleException(throwables);
            }
        });
        usuniecie = new JMenuItem("Usuniecie");
        usuniecie.addActionListener(e -> {
            int id = panel.getSelectedObjectId();
            try {
                if(id == -1) {
                    System.out.println("No row selected");
                } else {
                    controller.deleteObject(id);
                    panel.setTable(controller.refreshTable());
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                handleException(throwables);
            }
        });
        dokumentacja = new JMenuItem("Dokumentacja");
        dokumentacja.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,dokumentacjaApp);
        });
        instrukcja = new JMenuItem("Instrukcja");
        instrukcja.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,instrukcjaApp);
        });
        saveBackUp = new JMenuItem("BackUp");
        saveBackUp.addActionListener(e -> {
            BaseCommand baseCommand = new BaseCommand();
            baseCommand.doBackUp();
        });
        loadBackUp = new JMenuItem("LoadBackUp");
        loadBackUp.addActionListener(e -> {
            BaseCommand baseCommand = new BaseCommand();
            baseCommand.loadBackUp();
        });
        this.setLayout(new BorderLayout());
        //this.setContentPane(panel);
        menuBar.add(menu);
        menuBar.add(edycja);
        menuBar.add(info);
        menuBar.add(backup);
        menu.add(klient); menu.add(zamowienie); menu.add(elementy); menu.add(towarMenu);
        edycja.add(dodanie); edycja.add(edytowanie); edycja.addSeparator(); edycja.add(usuniecie);
        info.add(dokumentacja); info.add(instrukcja);
        backup.add(saveBackUp);backup.add(loadBackUp);
        this.add(menuBar);
        this.setJMenuBar(menuBar);
        this.setSize(1000,1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new Panel();

        this.panel.setPreferredSize(new Dimension(1000,1000));
        this.setContentPane(panel);

        this.setVisible(true);
    }

    private void handleException(Exception exception) {
        JOptionPane.showMessageDialog(null,exception.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
    }

    public Map<String, Object> openModifyFrame(Map<String,Object> map) {
        ModifyFrame modifyFrame = new ModifyFrame(map, this);
        Map<String, Object> returnValue = modifyFrame.showDialog();
        return returnValue;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
