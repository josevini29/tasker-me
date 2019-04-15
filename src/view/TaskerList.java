/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Json;
import model.Task;
import taskerme.ManipJson;
import taskerme.TaskerMe;
import util.DataFormat;

/**
 *
 * @author Jose Vinicius
 */
public class TaskerList extends javax.swing.JFrame {

    public ArrayList<Task> tasks = new ArrayList<>();
    DataFormat data = new DataFormat();
    ManipJson manip = new ManipJson();
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension screen = tk.getScreenSize();

    private int sizePanelWidth = 355;
    private int sizePanelHeight = 240;

    public TaskerList() {
        initComponents();
        setExtendedState(TaskerList.MAXIMIZED_BOTH);
        generateList();
        this.setTitle("Lista de Tarefas" + " - Status: " + (TaskerMe.thread.isAlive() ? "Ok" : "Stopped"));
    }

    private void generateList() {

        try {

            jpMain.removeAll();

            JButton jbtAdd = new JButton();
            jbtAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/add.png"))); // NOI18N
            jbtAdd.setToolTipText("");
            jbtAdd.setBorder(null);
            jbtAdd.setBorderPainted(false);
            jbtAdd.setContentAreaFilled(false);
            jbtAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            jbtAdd.setFocusable(false);
            jbtAdd.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jbtAddActionPerformed(evt);
                }
            });
            jpMain.add(jbtAdd);
            jbtAdd.setBounds(screen.width - 150, screen.height - 150, 60, 60);            

            int maxPanelX = (int) screen.width / sizePanelWidth;
            int diffPanelX = screen.width % sizePanelWidth;
            int diffSpaceWidth = (int) diffPanelX / (maxPanelX * 2);
            if (diffSpaceWidth < 10) {
                maxPanelX--;
                diffSpaceWidth = (int) diffPanelX / (maxPanelX * 2);
            }

            int diffSpaceHeight = 20;

            int qtActualX = 0;
            int x = diffSpaceWidth;
            int y = 10;

            try {
                tasks = manip.readJson().getTasks();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Arquivo base não encontrado, um novo será gerado");
                ManipJson.createFile();
                return;
            }

            for (int i = 0; i < tasks.size(); i++) {

                JPanel jpTask = new JPanel();
                JLabel lblAditional = new JLabel();
                JLabel lblMessage = new JLabel();
                JLabel lblDateTime = new JLabel();
                JButton jbtDelete = new JButton();

                jpTask.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                jpTask.setLayout(null);

                lblMessage.setFont(new java.awt.Font("Noto Mono", 0, 14)); // NOI18N
                lblMessage.setText(tasks.get(i).getMessage());
                lblMessage.setVerticalAlignment(javax.swing.SwingConstants.TOP);
                lblMessage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                lblMessage.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
                lblMessage.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
                final Task task = tasks.get(i);
                lblMessage.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        openTaskerEdit(task);
                    }
                });
                jpTask.add(lblMessage);
                lblMessage.setBounds(10, 10, 330, 110);

                if (tasks.get(i).getType().equals("D")) {

                    lblDateTime.setFont(new java.awt.Font("Noto Mono", 0, 12)); // NOI18N
                    lblDateTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    lblDateTime.setText("Data/Hora: " + data.AmericaToBrasil(tasks.get(i).getDate()));
                    lblDateTime.setVerticalAlignment(javax.swing.SwingConstants.TOP);
                    lblDateTime.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
                    lblDateTime.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
                    lblDateTime.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
                    jpTask.add(lblDateTime);
                    lblDateTime.setBounds(10, 130, 330, 20);

                    lblAditional.setFont(new java.awt.Font("Noto Mono", 0, 12)); // NOI18N
                    lblAditional.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);                    
                    if (tasks.get(i).getRemember()) {                        
                        lblAditional.setText("Repetir a cada " + tasks.get(i).getInterval() + " minutos");
                    } else {                        
                        lblAditional.setText("");
                    }
                    lblAditional.setVerticalAlignment(javax.swing.SwingConstants.TOP);
                    lblAditional.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
                    lblAditional.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
                    lblAditional.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
                    jpTask.add(lblAditional);
                    lblAditional.setBounds(10, 160, 330, 20);

                } else if (tasks.get(i).getType().equals("S")) {

                    lblDateTime.setFont(new java.awt.Font("Noto Mono", 0, 12)); // NOI18N
                    lblDateTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    lblDateTime.setText("Hora: " + data.AmericaToBrasilTime(tasks.get(i).getDate()));
                    lblDateTime.setVerticalAlignment(javax.swing.SwingConstants.TOP);
                    lblDateTime.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
                    lblDateTime.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
                    lblDateTime.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
                    jpTask.add(lblDateTime);
                    lblDateTime.setBounds(10, 160, 330, 20);

                    lblAditional.setFont(new java.awt.Font("Noto Mono", 0, 12)); // NOI18N
                    lblAditional.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    String aditional = "";
                    String daysWeek[] = new String[]{"Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"};
                    for (Integer dayWeekNotif : tasks.get(i).getDayWeek()) {
                        aditional += ("," + daysWeek[dayWeekNotif - 1]);
                    }
                    aditional = aditional.substring(1);
                    lblAditional.setText(aditional);
                    lblAditional.setVerticalAlignment(javax.swing.SwingConstants.TOP);
                    lblAditional.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
                    lblAditional.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
                    lblAditional.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
                    jpTask.add(lblAditional);
                    lblAditional.setBounds(10, 130, 330, 20);

                }

                jbtDelete.setFont(new java.awt.Font("SimSun-ExtB", 0, 14)); // NOI18N
                jbtDelete.setText("Excluir");
                jbtDelete.setFocusable(false);
                jpTask.add(jbtDelete);
                jbtDelete.setBounds(240, 190, 100, 30);

                tasks.get(i).setIdTable(i);
                final String id = tasks.get(i).getId();
                jbtDelete.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        deleteTask(id);
                    }
                });

                jpTask.setLocation(x, y);
                jpTask.setSize(sizePanelWidth, sizePanelHeight);

                jpMain.add(jpTask);

                qtActualX++;
                x += (sizePanelWidth + diffSpaceWidth);
                if (qtActualX >= maxPanelX) {
                    x = diffSpaceWidth;
                    y += (sizePanelHeight + diffSpaceHeight);
                    qtActualX = 0;
                }

            }

            jpMain.setSize(jpMain.getSize().width, y);
            jbtAdd.setLocation(screen.width - 130, screen.height - 170);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }

    }

    public void deleteTask(String id) {
        try {
            tasks = manip.readJson().getTasks();
            if (JOptionPane.showConfirmDialog(this, "Deseja realmente deletar está tarefa?", "Confirmação", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                for (int i = 0; i < tasks.size(); i++) {
                    if (tasks.get(i).getId().equals(id)) {
                        tasks.remove(i);
                    }
                }
                Json json = new Json();
                json.setTasks(tasks);
                json.setStart(true);

                manip.writeJson(json);
                generateList();
                TaskerMe.updateTask();
            }
        } catch (Exception ex) {            
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public void openTaskerEdit(Task task) {
        TaskerEdit edit = new TaskerEdit(this, true, task);
        edit.setVisible(true);
        generateList();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpScroll = new javax.swing.JScrollPane();
        jpMain = new javax.swing.JPanel();
        jbtAdd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lista de Tarefas");

        jpScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jpScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jpMain.setLayout(null);

        jbtAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/add.png"))); // NOI18N
        jbtAdd.setToolTipText("");
        jbtAdd.setBorder(null);
        jbtAdd.setBorderPainted(false);
        jbtAdd.setContentAreaFilled(false);
        jbtAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtAdd.setFocusable(false);
        jbtAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtAddActionPerformed(evt);
            }
        });
        jpMain.add(jbtAdd);
        jbtAdd.setBounds(610, 170, 60, 60);

        jpScroll.setViewportView(jpMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 1105, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1121, 644));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtAddActionPerformed
        openTaskerEdit(null);
    }//GEN-LAST:event_jbtAddActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TaskerList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TaskerList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TaskerList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TaskerList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TaskerList().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtAdd;
    private javax.swing.JPanel jpMain;
    private javax.swing.JScrollPane jpScroll;
    // End of variables declaration//GEN-END:variables
}
