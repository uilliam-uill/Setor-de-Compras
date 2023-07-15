package PacotePrincipal;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class modePlanilha extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					modePlanilha frame = new modePlanilha();
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
	public modePlanilha() {
		setBounds(100, 100, 528, 179);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(modePlanilha.class.getResource("/imagens/Captura de tela 2023-07-03 22565.png")));
		lblNewLabel.setBounds(10, 21, 492, 111);
		getContentPane().add(lblNewLabel);

	}
}
