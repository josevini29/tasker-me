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
import model.Task;
import util.DataFormat;
import view.Notification;
import view.TaskerList;

/**
 *
 * @author Jose Vinicius
 */
public class TaskerMe {

    static SystemTray tray = SystemTray.getSystemTray();
    static TrayIcon trayIcon;
    static Json json = new Json();
    public static Thread thread = new Thread();
    static DataFormat data = new DataFormat();
    static boolean runing = false;

    public static void main(String[] args) {
        try {

            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Windows".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception ignored) {
            }

            /*TaskerList dash = new TaskerList();
            dash.setVisible(true);*/

            PopupMenu menu = new PopupMenu();
            MenuItem taskItem = new MenuItem("Tarefas");
            MenuItem exitItem = new MenuItem("Fechar");
            taskItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ev) {
                    try {
                        TaskerList dash = new TaskerList();
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
                        TaskerList dash = new TaskerList();
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
            trayIcon.displayMessage("Notificações estão ativas", "", MessageType.INFO);
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo de dados não encontrado!");            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public static void updateTask() throws Exception {
        thread.interrupt();
        TaskerMe.runing = false;
        while (true) {            
            if (!thread.isAlive()) {
                TaskerMe.json = new ManipJson().readJson();
                defineThread();                
                thread.start();
                break;
            }
        }
    }

    public static void alert(String message) throws AWTException {
        Notification not = new Notification(message);
        not.setVisible(true);
    }

    public static void defineThread() {
        thread = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread iniciada");
                TaskerMe.runing = true;
                try {
                    while (!Thread.interrupted()) {
                        if (json.getTasks().size() < 1 || !TaskerMe.runing) {
                            break;
                        }

                        for (int i = 0; i < json.getTasks().size(); i++) {
                            //Por data/hora definido
                            if (json.getTasks().get(i).getType().equals("D")) {
                                Task task = showMessageDateTime(json.getTasks().get(i));
                                if (task != null) {
                                    json.getTasks().set(i, task);
                                    new ManipJson().writeJson(json);
                                }
                            } else if (json.getTasks().get(i).getType().equals("S")) {
                                Task task = showMessagePeriodic(json.getTasks().get(i));
                                if (task != null) {
                                    json.getTasks().set(i, task);
                                    new ManipJson().writeJson(json);
                                }
                            }
                            
                            if (Thread.interrupted() || !TaskerMe.runing) {                                
                                break;
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

    public static Task showMessageDateTime(Task task) throws AWTException {
        if (task.getDtAlert() != null) {
            if (task.getDtAlert().before(new Date())) {
                alert(task.getMessage());
                System.out.println("Alerta: " + task.getMessage());
                if (task.getRemember()) {
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.setTime(new Date());
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                    System.out.println(sdf.format(gc.getTime()));
                    gc.add(Calendar.MINUTE, task.getInterval());
                    System.out.println(gc.getTime());
                    task.setDtAlert(gc.getTime());
                } else {
                    task.setDtAlert(null);
                }
                return task;
            }
        }
        return null;
    }

    public static Task showMessagePeriodic(Task task) throws AWTException, Exception {
        Calendar calNow = Calendar.getInstance();
        calNow.setTime(new Date());
        int dayWeekNow = calNow.get(Calendar.DAY_OF_WEEK);
        int dayNow = calNow.get(GregorianCalendar.DAY_OF_MONTH);
        int monthNow = calNow.get(GregorianCalendar.MONTH);
        int yearNow = calNow.get(GregorianCalendar.YEAR);
        for (Integer dayWeekNotif : task.getDayWeek()) {
            if (dayWeekNow == dayWeekNotif) {
                if (task.getDtAlert() == null) {
                    if (data.StringToDate(data.AmericaToBrasilDate(new Date()) + " " + data.AmericaToBrasilTime(task.getDate())).before(new Date())) {
                        alert(task.getMessage());
                        task.setDtAlert(new Date());
                        return task;
                    }
                } else {
                    GregorianCalendar calNotif = new GregorianCalendar();
                    calNotif.setTime(task.getDtAlert());
                    int dayNotif = calNotif.get(GregorianCalendar.DAY_OF_MONTH);
                    int monthNotif = calNotif.get(GregorianCalendar.MONTH);
                    int yearNotif = calNotif.get(GregorianCalendar.YEAR);
                    if (!(dayNotif == dayNow && monthNotif == monthNow && yearNotif == yearNow)) {                        
                        if (data.StringToDate(data.AmericaToBrasilDate(new Date()) + " " +  data.AmericaToBrasilTime(task.getDate())).before(new Date())) {
                            alert(task.getMessage());
                            task.setDtAlert(new Date());
                            return task;
                        }
                    }
                }
            }
        }

        return null;
    }

}
