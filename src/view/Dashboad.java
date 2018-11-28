/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Json;
import model.Task;
import taskerme.ManipJson;
import taskerme.TaskerMe;
import util.DataFormat;

/**
 *
 * @author Jose Vinicius
 */
public class Dashboad extends javax.swing.JFrame {

    public ArrayList<Task> tasks = new ArrayList<>();
    DataFormat data = new DataFormat();
    ManipJson manip = new ManipJson();
    Integer idEditing = null;

    public Dashboad() throws IOException {
        initComponents();
        populateTasks();
        discardTask();
        dsThread.setText("Thread: " + (TaskerMe.thread.isAlive() ? "Online" : "Offline" ));
    }

    public void discardTask() {
        txtTitle.setText("");
        txtMessage.setText("");
        txtInterval.setText("30");
        chRemember.setSelected(true);
        txtDate.setText(data.AmericaToBrasil(new Date()));
        txtTitle.requestFocus();
        idEditing = null;
    }

    public void populateTasks() {
        try {
            tbTasks.getColumnModel().getColumn(0).setPreferredWidth(100);
            tbTasks.getColumnModel().getColumn(1).setPreferredWidth(100);
            tbTasks.getColumnModel().getColumn(2).setPreferredWidth(50);
            tbTasks.getColumnModel().getColumn(3).setPreferredWidth(50);
            tbTasks.getColumnModel().getColumn(4).setPreferredWidth(180);

            tasks = manip.readJson().getTasks();
            DefaultTableModel model = (DefaultTableModel) tbTasks.getModel();
            while (model.getRowCount() > 0) {
                model.removeRow(0);
            }
            for (int i = 0; i < tasks.size(); i++) {
                model.addRow(new Object[]{tasks.get(i).getTitle(), tasks.get(i).getMessage(), tasks.get(i).getRemember() ? "X" : "", tasks.get(i).getInterval(), data.AmericaToBrasil(tasks.get(i).getDate())});
                tasks.get(i).setIdTable(i);
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Arquivo base não encontrado, um novo será gerado");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public void saveTasks() throws IOException, Exception {
        try {
            int value = Integer.parseInt(txtInterval.getText());
            if (value > 300) {
                JOptionPane.showMessageDialog(this, "Limite de intervalo máximo é 300 minutos.");
                return;
            }
            if (value < 5) {
                JOptionPane.showMessageDialog(this, "Limite de intervalo minímo é 5 minutos.");
                return;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Valor de intervalo inválido.");
            return;
        }

        if (idEditing == null) {
            Task task = new Task();
            task.setId(data.getId());
            task.setTitle(txtTitle.getText());
            task.setMessage(txtMessage.getText());
            task.setRemember(chRemember.isSelected());
            task.setInterval(Integer.parseInt(txtInterval.getText()));
            task.setDate(data.StringToDate(txtDate.getText()));
            task.setDtAlert(data.StringToDate(txtDate.getText()));
            tasks.add(task);
        } else {
            tasks.get(idEditing).setId(data.getId());
            tasks.get(idEditing).setTitle(txtTitle.getText());
            tasks.get(idEditing).setMessage(txtMessage.getText());
            tasks.get(idEditing).setRemember(chRemember.isSelected());
            tasks.get(idEditing).setInterval(Integer.parseInt(txtInterval.getText()));
            tasks.get(idEditing).setDate(data.StringToDate(txtDate.getText()));
            tasks.get(idEditing).setDtAlert(data.StringToDate(txtDate.getText()));
        }
        Json json = new Json();
        json.setTasks(tasks);
        json.setStart(true);

        manip.writeJson(json);
        discardTask();
        TaskerMe.updateTask(json);
        populateTasks();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtTitle = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMessage = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        btDiscard = new javax.swing.JButton();
        btSave = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbTasks = new javax.swing.JTable();
        txtDate = new javax.swing.JTextField();
        chRemember = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        txtInterval = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btDelete = new javax.swing.JButton();
        btEdit = new javax.swing.JButton();
        dsThread = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tarefas");
        setMaximumSize(new java.awt.Dimension(660, 550));
        setMinimumSize(new java.awt.Dimension(660, 550));
        setPreferredSize(new java.awt.Dimension(660, 550));
        setResizable(false);
        setSize(new java.awt.Dimension(660, 550));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        getContentPane().add(txtTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 625, -1));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Título");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        txtMessage.setColumns(20);
        txtMessage.setRows(5);
        jScrollPane1.setViewportView(txtMessage);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 625, 77));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Mensagem");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        btDiscard.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btDiscard.setText("Descartar");
        btDiscard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDiscardActionPerformed(evt);
            }
        });
        getContentPane().add(btDiscard, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 200, -1, -1));

        btSave.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btSave.setText("Salvar");
        btSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveActionPerformed(evt);
            }
        });
        getContentPane().add(btSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 200, 87, -1));

        tbTasks.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tbTasks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Título", "Mensagem", "Relembrar", "Intervalo", "Data"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbTasks);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 625, 210));

        txtDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        getContentPane().add(txtDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 200, 210, -1));

        chRemember.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        chRemember.setText("Relembrar");
        chRemember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chRememberActionPerformed(evt);
            }
        });
        getContentPane().add(chRemember, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("Data/Hora");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 180, -1, -1));

        txtInterval.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        getContentPane().add(txtInterval, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 60, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("Intervalo");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, -1, -1));

        btDelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btDelete.setText("Excluir");
        btDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeleteActionPerformed(evt);
            }
        });
        getContentPane().add(btDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 470, -1, -1));

        btEdit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btEdit.setText("Editar");
        btEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditActionPerformed(evt);
            }
        });
        getContentPane().add(btEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 470, 70, -1));
        getContentPane().add(dsThread, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 200, 20));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveActionPerformed
        try {

            if (txtTitle.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Informe o título");
                return;
            }

            if (txtInterval.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Informe o intervalo");
                return;
            }

            if (txtDate.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Informe a data");
                return;
            }

            saveTasks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btSaveActionPerformed

    private void btDiscardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDiscardActionPerformed
        discardTask();
    }//GEN-LAST:event_btDiscardActionPerformed

    private void chRememberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chRememberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chRememberActionPerformed

    private void btDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeleteActionPerformed
        try {
            if (tbTasks.getSelectedRow() >= 0) {
                if (JOptionPane.showConfirmDialog(this, "Deseja realmente deletar este registro?", "Confirmação", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                    for (int i = 0; i < tasks.size(); i++) {
                        if (tasks.get(i).getIdTable() == tbTasks.getSelectedRow()) {
                            tasks.remove(i);
                        }
                    }
                    Json json = new Json();
                    json.setTasks(tasks);
                    json.setStart(true);

                    manip.writeJson(json);
                    populateTasks();
                    TaskerMe.updateTask(json);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btDeleteActionPerformed

    private void btEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditActionPerformed
        try {
            if (tbTasks.getSelectedRow() >= 0) {
                for (int i = 0; i < tasks.size(); i++) {
                    if (tasks.get(i).getIdTable() == tbTasks.getSelectedRow()) {

                        txtTitle.setText((String) tbTasks.getModel().getValueAt(i, 0));
                        txtMessage.setText((String) tbTasks.getModel().getValueAt(i, 1));
                        chRemember.setSelected(((String) tbTasks.getModel().getValueAt(i, 2)).equals("X"));
                        txtInterval.setText(((Integer) tbTasks.getModel().getValueAt(i, 3)).toString());
                        txtDate.setText((String) tbTasks.getModel().getValueAt(i, 4));
                        txtTitle.requestFocus();
                        idEditing = tasks.get(i).getIdTable();
                        txtTitle.requestFocus();
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btEditActionPerformed

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
            java.util.logging.Logger.getLogger(Dashboad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Dashboad().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Dashboad.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btDelete;
    private javax.swing.JButton btDiscard;
    private javax.swing.JButton btEdit;
    private javax.swing.JButton btSave;
    private javax.swing.JCheckBox chRemember;
    private javax.swing.JLabel dsThread;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbTasks;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtInterval;
    private javax.swing.JTextArea txtMessage;
    private javax.swing.JTextField txtTitle;
    // End of variables declaration//GEN-END:variables
}
