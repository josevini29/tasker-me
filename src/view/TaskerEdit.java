/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;
import model.Json;
import model.Task;
import taskerme.ManipJson;
import taskerme.TaskerMe;
import util.DataFormat;

/**
 *
 * @author Jose Vinicius
 */
public class TaskerEdit extends javax.swing.JDialog {

    DataFormat data = new DataFormat();
    public ArrayList<Task> tasks = new ArrayList<>();
    ManipJson manip = new ManipJson();
    Task taskEditing;

    public TaskerEdit(java.awt.Frame parent, boolean modal, Task taskEditing) {
        super(parent, modal);
        initComponents();
        jrbDateTime.setSelected(true);
        try {
            MaskFormatter maskData = new MaskFormatter("##/##/#### ##:##:##");
            maskData.install(txtDateTime);
            txtDateTime.setText(data.AmericaToBrasil(new Date()));
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        try {
            MaskFormatter maskData2 = new MaskFormatter("##:##:##");
            maskData2.install(txtTime);
            txtTime.setText(data.AmericaToBrasilTime(new Date()));
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        jsnInterval.setValue(30);
        jsnInterval2.setValue(30);
        jrbDateTime.setSelected(true);
        alterPanel();
        this.taskEditing = taskEditing;
    }

    public void saveTasks() throws IOException, Exception {
        try {
            int value = (int) jsnInterval.getValue();
            if (value > 300) {
                JOptionPane.showMessageDialog(this, "Limite de intervalo máximo é 300 minutos.");
                return;
            }
            if (value < 1) {
                JOptionPane.showMessageDialog(this, "Limite de intervalo minímo é 1 minutos.");
                return;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Valor de intervalo inválido.");
            return;
        }

        tasks = manip.readJson().getTasks();

        if (jrbDateTime.isSelected()) {
            if (taskEditing == null) {
                Task task = new Task();
                task.setId(data.getId());
                task.setMessage(txtMessage.getText());
                task.setRemember(ckbRemember.isSelected());
                task.setInterval((int) jsnInterval.getValue());
                task.setDate(data.StringToDate(txtDateTime.getText()));
                task.setDtAlert(data.StringToDate(txtDateTime.getText()));
                task.setType("D");
                tasks.add(task);
            } else {
                tasks.get(taskEditing.getIdTable()).setId(data.getId());
                tasks.get(taskEditing.getIdTable()).setMessage(txtMessage.getText());
                tasks.get(taskEditing.getIdTable()).setRemember(ckbRemember.isSelected());
                tasks.get(taskEditing.getIdTable()).setInterval((int) jsnInterval.getValue());
                tasks.get(taskEditing.getIdTable()).setDate(data.StringToDate(txtDateTime.getText()));
                tasks.get(taskEditing.getIdTable()).setDtAlert(data.StringToDate(txtDateTime.getText()));
                tasks.get(taskEditing.getIdTable()).setType("D");
            }
        } else {

            ArrayList<Integer> dayWeek = new ArrayList<>();
            if (jtbDay1.isSelected()) {
                dayWeek.add(0);
            }
            if (jtbDay2.isSelected()) {
                dayWeek.add(1);
            }
            if (jtbDay3.isSelected()) {
                dayWeek.add(2);
            }
            if (jtbDay4.isSelected()) {
                dayWeek.add(3);
            }
            if (jtbDay5.isSelected()) {
                dayWeek.add(4);
            }
            if (jtbDay6.isSelected()) {
                dayWeek.add(5);
            }
            if (jtbDay7.isSelected()) {
                dayWeek.add(6);
            }

            if (dayWeek.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informar dia da semana é obrigatório.");
                return;
            }

            if (taskEditing == null) {
                Task task = new Task();
                task.setId(data.getId());
                task.setMessage(txtMessage.getText());
                task.setRemember(ckbRemember2.isSelected());
                task.setInterval((int) jsnInterval2.getValue());
                task.setDate(data.StringToDate("01/01/1900 " + txtTime.getText()));
                task.setDtAlert(null);
                task.setType("S");
                task.setDayWeek(dayWeek);
                tasks.add(task);
            } else {
                tasks.get(taskEditing.getIdTable()).setId(data.getId());
                tasks.get(taskEditing.getIdTable()).setMessage(txtMessage.getText());
                tasks.get(taskEditing.getIdTable()).setRemember(ckbRemember2.isSelected());
                tasks.get(taskEditing.getIdTable()).setInterval((int) jsnInterval2.getValue());
                tasks.get(taskEditing.getIdTable()).setDate(data.StringToDate("01/01/1900 " + txtTime.getText()));
                tasks.get(taskEditing.getIdTable()).setDtAlert(null);
                tasks.get(taskEditing.getIdTable()).setType("S");
                tasks.get(taskEditing.getIdTable()).setDayWeek(dayWeek);
            }
        }

        Json json = new Json();
        json.setTasks(tasks);
        json.setStart(true);

        manip.writeJson(json);
        TaskerMe.updateTask(json);
        this.dispose();
    }

    public void alterPanel() {
        if (jrbDateTime.isSelected()) {
            jpDatetime.setVisible(true);
            jpPeriodic.setVisible(false);
        } else {
            jpDatetime.setVisible(false);
            jpPeriodic.setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupPeriodic = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMessage = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jrbPeriodic = new javax.swing.JRadioButton();
        jrbDateTime = new javax.swing.JRadioButton();
        jpDatetime = new javax.swing.JPanel();
        ckbRemember = new javax.swing.JCheckBox();
        jsnInterval = new javax.swing.JSpinner();
        txtDateTime = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jbtExit = new javax.swing.JButton();
        jbtSave = new javax.swing.JButton();
        jpPeriodic = new javax.swing.JPanel();
        ckbRemember2 = new javax.swing.JCheckBox();
        jsnInterval2 = new javax.swing.JSpinner();
        txtTime = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jtbDay1 = new javax.swing.JToggleButton();
        jtbDay2 = new javax.swing.JToggleButton();
        jtbDay3 = new javax.swing.JToggleButton();
        jtbDay4 = new javax.swing.JToggleButton();
        jtbDay5 = new javax.swing.JToggleButton();
        jtbDay7 = new javax.swing.JToggleButton();
        jtbDay6 = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tarefa");
        getContentPane().setLayout(null);

        txtMessage.setColumns(20);
        txtMessage.setFont(new java.awt.Font("Noto Mono", 0, 14)); // NOI18N
        txtMessage.setLineWrap(true);
        txtMessage.setRows(5);
        jScrollPane1.setViewportView(txtMessage);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 40, 570, 187);

        jLabel1.setFont(new java.awt.Font("Noto Mono", 0, 12)); // NOI18N
        jLabel1.setText("Mensagem");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 19, 56, 15);

        groupPeriodic.add(jrbPeriodic);
        jrbPeriodic.setText("Periodico");
        jrbPeriodic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbPeriodicActionPerformed(evt);
            }
        });
        getContentPane().add(jrbPeriodic);
        jrbPeriodic.setBounds(20, 340, 80, 23);

        groupPeriodic.add(jrbDateTime);
        jrbDateTime.setText("Data/Hora");
        jrbDateTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbDateTimeActionPerformed(evt);
            }
        });
        getContentPane().add(jrbDateTime);
        jrbDateTime.setBounds(20, 280, 80, 23);

        jpDatetime.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jpDatetime.setLayout(null);

        ckbRemember.setFont(new java.awt.Font("Noto Mono", 0, 12)); // NOI18N
        ckbRemember.setText("Relembrar");
        ckbRemember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckbRememberActionPerformed(evt);
            }
        });
        jpDatetime.add(ckbRemember);
        ckbRemember.setBounds(10, 10, 90, 23);
        jpDatetime.add(jsnInterval);
        jsnInterval.setBounds(180, 10, 50, 20);

        txtDateTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDateTimeActionPerformed(evt);
            }
        });
        jpDatetime.add(txtDateTime);
        txtDateTime.setBounds(320, 10, 140, 20);

        jLabel2.setFont(new java.awt.Font("Noto Mono", 0, 12)); // NOI18N
        jLabel2.setText("Data/Hora");
        jpDatetime.add(jLabel2);
        jLabel2.setBounds(250, 10, 63, 20);

        jLabel3.setFont(new java.awt.Font("Noto Mono", 0, 12)); // NOI18N
        jLabel3.setText("Intervalo");
        jpDatetime.add(jLabel3);
        jLabel3.setBounds(110, 10, 70, 20);

        getContentPane().add(jpDatetime);
        jpDatetime.setBounds(110, 270, 470, 40);

        jbtExit.setFont(new java.awt.Font("Noto Mono", 0, 14)); // NOI18N
        jbtExit.setText("Sair");
        jbtExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtExitActionPerformed(evt);
            }
        });
        getContentPane().add(jbtExit);
        jbtExit.setBounds(370, 430, 100, 25);

        jbtSave.setFont(new java.awt.Font("Noto Mono", 0, 14)); // NOI18N
        jbtSave.setText("Salvar");
        jbtSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtSaveActionPerformed(evt);
            }
        });
        getContentPane().add(jbtSave);
        jbtSave.setBounds(480, 430, 100, 25);

        jpPeriodic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jpPeriodic.setLayout(null);

        ckbRemember2.setFont(new java.awt.Font("Noto Mono", 0, 12)); // NOI18N
        ckbRemember2.setText("Relembrar");
        ckbRemember2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckbRemember2ActionPerformed(evt);
            }
        });
        jpPeriodic.add(ckbRemember2);
        ckbRemember2.setBounds(10, 10, 90, 23);
        jpPeriodic.add(jsnInterval2);
        jsnInterval2.setBounds(180, 10, 50, 20);

        txtTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimeActionPerformed(evt);
            }
        });
        jpPeriodic.add(txtTime);
        txtTime.setBounds(300, 10, 80, 20);

        jLabel4.setFont(new java.awt.Font("Noto Mono", 0, 12)); // NOI18N
        jLabel4.setText("Hora");
        jpPeriodic.add(jLabel4);
        jLabel4.setBounds(260, 10, 30, 20);

        jLabel5.setFont(new java.awt.Font("Noto Mono", 0, 12)); // NOI18N
        jLabel5.setText("Dias");
        jpPeriodic.add(jLabel5);
        jLabel5.setBounds(50, 50, 30, 20);

        jLabel6.setFont(new java.awt.Font("Noto Mono", 0, 12)); // NOI18N
        jLabel6.setText("Intervalo");
        jpPeriodic.add(jLabel6);
        jLabel6.setBounds(110, 10, 70, 20);

        jtbDay1.setText("D");
        jpPeriodic.add(jtbDay1);
        jtbDay1.setBounds(90, 50, 40, 23);

        jtbDay2.setText("S");
        jpPeriodic.add(jtbDay2);
        jtbDay2.setBounds(130, 50, 40, 23);

        jtbDay3.setText("T");
        jpPeriodic.add(jtbDay3);
        jtbDay3.setBounds(170, 50, 40, 23);

        jtbDay4.setText("Q");
        jpPeriodic.add(jtbDay4);
        jtbDay4.setBounds(210, 50, 40, 23);

        jtbDay5.setText("Q");
        jpPeriodic.add(jtbDay5);
        jtbDay5.setBounds(250, 50, 40, 23);

        jtbDay7.setText("S");
        jpPeriodic.add(jtbDay7);
        jtbDay7.setBounds(330, 50, 40, 23);

        jtbDay6.setText("S");
        jpPeriodic.add(jtbDay6);
        jtbDay6.setBounds(290, 50, 40, 23);

        getContentPane().add(jpPeriodic);
        jpPeriodic.setBounds(110, 330, 470, 80);

        setSize(new java.awt.Dimension(608, 518));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ckbRememberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckbRememberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ckbRememberActionPerformed

    private void jrbPeriodicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbPeriodicActionPerformed
        alterPanel();
    }//GEN-LAST:event_jrbPeriodicActionPerformed

    private void jrbDateTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbDateTimeActionPerformed
        alterPanel();
    }//GEN-LAST:event_jrbDateTimeActionPerformed

    private void txtDateTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDateTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDateTimeActionPerformed

    private void jbtSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtSaveActionPerformed
        try {
            saveTasks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_jbtSaveActionPerformed

    private void jbtExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbtExitActionPerformed

    private void ckbRemember2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckbRemember2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ckbRemember2ActionPerformed

    private void txtTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimeActionPerformed

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
            java.util.logging.Logger.getLogger(TaskerEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TaskerEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TaskerEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TaskerEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TaskerEdit dialog = new TaskerEdit(new javax.swing.JFrame(), true, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox ckbRemember;
    private javax.swing.JCheckBox ckbRemember2;
    private javax.swing.ButtonGroup groupPeriodic;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtExit;
    private javax.swing.JButton jbtSave;
    private javax.swing.JPanel jpDatetime;
    private javax.swing.JPanel jpPeriodic;
    private javax.swing.JRadioButton jrbDateTime;
    private javax.swing.JRadioButton jrbPeriodic;
    private javax.swing.JSpinner jsnInterval;
    private javax.swing.JSpinner jsnInterval2;
    private javax.swing.JToggleButton jtbDay1;
    private javax.swing.JToggleButton jtbDay2;
    private javax.swing.JToggleButton jtbDay3;
    private javax.swing.JToggleButton jtbDay4;
    private javax.swing.JToggleButton jtbDay5;
    private javax.swing.JToggleButton jtbDay6;
    private javax.swing.JToggleButton jtbDay7;
    private javax.swing.JFormattedTextField txtDateTime;
    private javax.swing.JTextArea txtMessage;
    private javax.swing.JFormattedTextField txtTime;
    // End of variables declaration//GEN-END:variables
}
