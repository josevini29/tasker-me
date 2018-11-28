/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskerme;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import model.Json;
import view.Dashboad;

/**
 *
 * @author Jose Vinicius
 */
public class TaskerMe {

    static SystemTray tray = SystemTray.getSystemTray();
    static TrayIcon trayIcon;
    static Json json = new Json();
    public static Thread thread = new Thread();

    public static void main(String[] args) {
        try {

            PopupMenu menu = new PopupMenu();
            MenuItem taskItem = new MenuItem("Tarefas");
            MenuItem exitItem = new MenuItem("Fechar");
            taskItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ev) {
                    try {
                        Dashboad dash = new Dashboad();
                        dash.setVisible(true);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            });
            exitItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ev) {
                    System.exit(0);
                }
            });
            menu.add(taskItem);
            menu.add(exitItem);
            Image image = Toolkit.getDefaultToolkit().getImage(TaskerMe.class.getResource("/taskerme/icon.png"));
            trayIcon = new TrayIcon(image, "TaskerMe", menu);
            trayIcon.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Dashboad dash = new Dashboad();
                        dash.setVisible(true);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            });
            tray.add(trayIcon);
            defineThread();
            json = new ManipJson().readJson();
            thread.start();
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo de dados não encontrado!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public static void updateTask(Json json) throws Exception {
        thread.interrupt();
        while (true) {
            if (!thread.isAlive()) {
                defineThread();
                TaskerMe.json = json;
                thread.start();
                break;
            }
        }
    }

    public static void alert(String title, String message) throws AWTException {
        trayIcon.displayMessage(title, message, MessageType.INFO);
    }

    public static void defineThread() {
        thread = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread iniciada");
                try {
                    while (!Thread.interrupted()) {
                        if (json.getTasks().size() < 1) {
                            break;
                        }
                        for (int i = 0; i < json.getTasks().size(); i++) {
                            if (json.getTasks().get(i).getDtAlert() != null) {
                                if (json.getTasks().get(i).getDtAlert().before(new Date())) {
                                    alert(json.getTasks().get(i).getTitle(), json.getTasks().get(i).getMessage());
                                    System.out.println("Alerta: " + json.getTasks().get(i).getTitle() + " -> " + json.getTasks().get(i).getMessage());
                                    if (json.getTasks().get(i).getRemember()) {
                                        GregorianCalendar gc = new GregorianCalendar();
                                        gc.setTime(new Date());
                                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                                        System.out.println(sdf.format(gc.getTime()));
                                        gc.add(Calendar.MINUTE, json.getTasks().get(i).getInterval());
                                        System.out.println(gc.getTime());
                                        json.getTasks().get(i).setDtAlert(gc.getTime());
                                    } else {
                                        json.getTasks().get(i).setDtAlert(null);
                                    }
                                    new ManipJson().writeJson(json);
                                }
                            }
                        }
                        Thread.sleep(1000);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    System.out.println("Thread interrompida");
                }
            }
        };
    }

}
