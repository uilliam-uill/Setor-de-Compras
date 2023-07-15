package PacotePrincipal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class TelaPrincipal extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
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

	public TelaPrincipal() {
		try {
			// Define o estilo "Nimbus" (substitua pelo estilo de sua preferência)
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(500, 5, 5, 5));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setContentPane(contentPane);

		int larguraDaTela = Toolkit.getDefaultToolkit().getScreenSize().width;
		int alturaDaTela = Toolkit.getDefaultToolkit().getScreenSize().height;

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, larguraDaTela, alturaDaTela);
		contentPane.add(layeredPane);

		JPanel panelMaximoInicial = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 736);

		Dimension tamanhoMaximoPanel = new Dimension(500, 500);
		panelMaximoInicial.setMaximumSize(tamanhoMaximoPanel);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 264, 697);
		contentPane.add(panel_1);
		panel_1.setBackground(new Color(185, 244, 226));
		panel_1.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(263, 0, 1103, 696);
		layeredPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(363, 214, 368, 313);
		panel.add(lblNewLabel);
		lblNewLabel.setIcon(
				new ImageIcon(TelaPrincipal.class.getResource("/imagens/Captura de tela 2023-07-07 11164.png")));

		JButton btnNewButton = new JButton("");
		btnNewButton.setToolTipText("CONTRATOS");
		btnNewButton.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/contrato (1).png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaContrato telaContrato = new telaContrato();
				telaContrato.setClosable(true);
				telaContrato.setVisible(true);
				telaContrato.setBounds(00, 100, 845, 638);
				// panel.add(telaContrato);
				panel.add(telaContrato);
				telaContrato.moveToFront();
				telaContrato.toFront();
				layeredPane.add(telaContrato, JLayeredPane.POPUP_LAYER);
			}
		});
		// btnNewButton.setIcon(new
		// ImageIcon(TelaPrincipal.class.getResource("/imagens/novo-documento
		// (1).png")));
		btnNewButton.setBounds(50, 64, 161, 144);
		panel_1.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/carrinho.png")));
		btnNewButton_1.setToolTipText("COMPRAS");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCompras TelaCompras = new TelaCompras();
				TelaCompras.setClosable(true);
				TelaCompras.setVisible(true);
				TelaCompras.setBounds(100, 100, 937, 530);
				// panel.add(TelaCompras);
				panel.add(TelaCompras);
				TelaCompras.moveToFront();
				TelaCompras.toFront();
				layeredPane.add(TelaCompras, JLayeredPane.PALETTE_LAYER);
			}
		});
		// btnNewButton_1.setIcon(new
		// ImageIcon(TelaPrincipal.class.getResource("/imagens/cartao-de-credito
		// (1).png")));
		btnNewButton_1.setBounds(50, 353, 161, 144);
		panel_1.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("FINANCEIRO");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		// btnNewButton_2.setIcon(new
		// ImageIcon(TelaPrincipal.class.getResource("/imagens/demonstracao-financeira.png")));
		btnNewButton_2.setBounds(10, 531, 216, 42);
		panel_1.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("?");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modePlanilha modePlanilha = new modePlanilha();
				modePlanilha.setClosable(true);
				modePlanilha.setVisible(true);
				modePlanilha.setBounds(100, 100, 528, 179);
				panel.add(modePlanilha);
				modePlanilha.moveToFront();
				modePlanilha.toFront();
				modePlanilha.setResizable(false);
				layeredPane.add(modePlanilha, JLayeredPane.POPUP_LAYER);
				modePlanilha.toFront();
				modePlanilha.requestFocus();
			}
		});
		btnNewButton_3.setBounds(201, 11, 53, 23);
		panel_1.add(btnNewButton_3);

	}

	public void abrirJanela() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
