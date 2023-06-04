package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import controller.ControllerFornecedor;
import controller.ControllerFuncionario;
import controller.ControllerProduto;

public class Menu extends JFrame {

    private static final long serialVersionUID = 1L;
    
	public Menu() {
    	try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            initComponents();
        } 
		catch (Exception e) {
        	JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void initComponents() {

        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        jPanel2 = new JPanel();
        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jButton3 = new JButton();
        jButton4 = new JButton();
        jButton5 = new JButton();
        clockLabel = new JLabel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Menu de Controle");
        setBackground(new Color(255, 255, 255));
        setResizable(false);

        jPanel1.setBackground(new Color(0, 51, 51));
        jPanel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jPanel1.setForeground(new Color(255, 255, 255));

        jLabel1.setBackground(new Color(255, 255, 255));
        jLabel1.setFont(new Font("Segoe UI", 1, 24));
        jLabel1.setForeground(new Color(255, 255, 255));
        jLabel1.setText("MENU DE CONTROLE");

        jTable1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jTable1.setModel(new DefaultTableModel(
            new Object [][] {
            	null, null, null, null, null, null, null, null, null, null, null,
            	null, null, null, null, null, null, null, null, null, null, null
            },
            new String [] {
            		"Default1", "Default2", "Default3", "Default4"
            }
        ));
        jTable1.setEnabled(false);
        jTable1.setPreferredSize(new Dimension(488, 385));
        jScrollPane1.setViewportView(jTable1);

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
        );

        jButton1.setBackground(new Color(0, 0, 0));
        jButton1.setFont(new Font("Segoe UI", 1, 12));
        jButton1.setForeground(new Color(255, 255, 255));
        jButton1.setText("FUNCIONARIOS");
        jButton1.addActionListener(ControllerFuncionario.configFuncionario());

        jButton2.setBackground(new Color(0, 0, 0));
        jButton2.setFont(new Font("Segoe UI", 1, 12));
        jButton2.setForeground(new Color(255, 255, 255));
        jButton2.setText("FORNECEDORES");
        jButton2.addActionListener(ControllerFornecedor.configFornecedor());

        jButton3.setBackground(new Color(0, 0, 0));
        jButton3.setFont(new Font("Segoe UI", 1, 12));
        jButton3.setForeground(new Color(255, 255, 255));
        jButton3.setText("PRODUTOS");
        jButton3.addActionListener(ControllerProduto.configProduto());

        jButton4.setBackground(new Color(0, 0, 0));
        jButton4.setFont(new Font("Segoe UI", 1, 12));
        jButton4.setForeground(new Color(255, 255, 255));
        jButton4.setText("PERFIL");
        jButton4.addActionListener(Controller.perfil());

        jButton5.setBackground(new Color(0, 0, 0));
        jButton5.setFont(new Font("Segoe UI", 1, 12));
        jButton5.setForeground(new Color(255, 255, 255));
        jButton5.setText("LISTAR");
        jButton5.addActionListener(Controller.listar());

        clockLabel.setBackground(new Color(0, 0, 0));
        clockLabel.setFont(new Font("Arial", Font.BOLD, 16));
        clockLabel.setForeground(new Color(255, 255, 255));
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date now = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                clockLabel.setText(sdf.format(now));
            }
        });

        timer.start();

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(231, 231, 231))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(23, 23, 23))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(clockLabel)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(clockLabel)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }

    private JLabel clockLabel;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;
    private JLabel jLabel1;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane1;
    private JTable jTable1;

	public JButton getjButton1() {
		return jButton1;
	}

	public JButton getjButton2() {
		return jButton2;
	}

	public JButton getjButton3() {
		return jButton3;
	}

	public JButton getjButton4() {
		return jButton4;
	}

	public JButton getjButton5() {
		return jButton5;
	}

	public JTable getjTable1() {
		return jTable1;
	}
}
