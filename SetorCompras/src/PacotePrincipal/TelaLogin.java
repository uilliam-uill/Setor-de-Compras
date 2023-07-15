package PacotePrincipal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.text.ParseException;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class TelaLogin extends JFrame {

	private JPanel contentPane;
	private JFormattedTextField txtCpf;
	private JPasswordField txtSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin frame = new TelaLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	 public void fecharTela() {
	        // Fechar a tela de login
	        dispose();
	    }
	public TelaLogin() {
		try {
			// Define o estilo "Nimbus" (substitua pelo estilo de sua preferência)
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 607, 351);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		MaskFormatter cpff = null;
		try {
			cpff = new MaskFormatter("###.###.###-##");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JFormattedTextField txtCpf = new JFormattedTextField(cpff);
		txtCpf.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 212, 312);
		contentPane.add(panel);
		panel.setBackground(new Color(66, 141, 255));
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(TelaLogin.class.getResource("/imagens/do-utilizador (3).png")));
		lblNewLabel.setBounds(42, 65, 128, 149);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("CPF");
		lblNewLabel_1.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(292, 98, 29, 18);
		contentPane.add(lblNewLabel_1);
		
		//txtCpf = new JTextField();
		txtCpf.setBounds(331, 91, 177, 29);
		contentPane.add(txtCpf);
		txtCpf.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("SENHA");
		lblNewLabel_2.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(271, 154, 50, 20);
		contentPane.add(lblNewLabel_2);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(331, 148, 177, 29);
		contentPane.add(txtSenha);
		
		JButton btnNewButton = new JButton("  \r\nENTRAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cpf = txtCpf.getText();
				char[] charSenha = txtSenha.getPassword();	
				String Senha = new String(charSenha);
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");

					String url = "jdbc:mysql://localhost:3306/itaite";
					String usuario = "root";
					String senha = "5988";

					Connection conexao = DriverManager.getConnection(url, usuario, senha);
 
					PreparedStatement login = conexao
							.prepareStatement("SELECT * FROM usuarios WHERE cpf = ? AND senha = ?");
					login.setString(1, cpf);
					login.setString(2, Senha);
					login.execute();
					ResultSet rsLogin = login.executeQuery();
					if(rsLogin.next()){
						TelaPrincipal abrir = new TelaPrincipal();
					      abrir.abrirJanela();
					      fecharTela();
					}else {
						JOptionPane.showMessageDialog(null, "LOGIN E/OU SENHA INCORRETOS");
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setIcon(new ImageIcon(TelaLogin.class.getResource("/imagens/entrar.png")));
		btnNewButton.setBounds(371, 213, 108, 29);
		contentPane.add(btnNewButton);
	}
}
