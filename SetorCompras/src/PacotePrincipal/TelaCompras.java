package PacotePrincipal;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class TelaCompras extends JInternalFrame {
	private JTextField txtNome;
	private JTextField txtDoc;
	private JTextField txtContratoI;
	private JTextField txtContratoF;
	private JTextField txtSaldo;
	private JTextField txtContato;
	private JTextField enderecoCidade;
	private JTextField enderecoRua;
	private JTextField enderecoBairro;
	private JTextField enderecoNumero;
	private JTextField textField_10;
	private JTable table;
	private JTextField txtContrato;
	private int idContrato = 0;
	private JTextField textField;
	private JTable table_1;
	private JTextField txtItem;
	private JTextField txtUnitItem;
	private JTextField txtQtdDis;
	private JTextField txtQtdSol;
	private JTextField txtTotItem;
	private int quantidadeRestante;
	private JTable table_2;
	private JTable table_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCompras frame = new TelaCompras();
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

	private void atualizarCalculo() {
		String qtdSolText = txtQtdSol.getText();

		if (!qtdSolText.isEmpty()) {
			try {
				int quantidadePega = Integer.parseInt(qtdSolText);
				int quantidadeDisponivel = Integer.parseInt(txtQtdDis.getText());
				String unit = txtUnitItem.getText();

				if (unit != null) {
					String valorNumerico = unit.replaceAll("[^\\d.,]", "");
					valorNumerico = valorNumerico.replace(",", ".");

					try {
						Double valorUnitario = Double.parseDouble(valorNumerico);

						if (quantidadePega <= quantidadeDisponivel) {
							Double valorTotalCompra = valorUnitario * quantidadePega;
							NumberFormat formatoDinheiro = NumberFormat.getCurrencyInstance();
							String valorTotalFormatado = formatoDinheiro.format(valorTotalCompra);

							txtTotItem.setText(valorTotalFormatado);
							quantidadeRestante = quantidadeDisponivel - quantidadePega;
						}
					} catch (NumberFormatException e) {
						// Tratar o caso em que a conversão falhou (valorNumérico não é um número
						// válido)
						// Exemplo: exibir uma mensagem de erro ou fazer alguma ação apropriada
					}
				}
			} catch (NumberFormatException e) {
				// Tratar o caso em que a conversão falhou (qtdSolText ou txtQtdDis.getText()
				// não são números válidos)
				// Exemplo: exibir uma mensagem de erro ou fazer alguma ação apropriada
			}
		}
	}

	public TelaCompras() {
		setBounds(100, 100, 937, 530);
		getContentPane().setLayout(new CardLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel, "name_91930073152300");
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, "name_91937014613500");
		panel_1.setLayout(null);

		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, "name_175569024437800");
		panel_2.setLayout(null);

		JLabel lblNewLabel = new JLabel("NOME");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel.setBounds(35, 95, 39, 14);
		panel.add(lblNewLabel);

		JButton btnNewButton = new JButton("BUSCAR CONTRATO");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				panel_2.setVisible(false);
				panel_1.setVisible(true);
				DefaultTableModel tabela = (DefaultTableModel) table.getModel();
				tabela.setRowCount(0);

				try {
					Class.forName("com.mysql.cj.jdbc.Driver");

					String url = "jdbc:mysql://localhost:3306/itaite";
					String usuario = "root";
					String senha = "5988";

					Connection conexao = DriverManager.getConnection(url, usuario, senha);

					PreparedStatement selectContrato = conexao.prepareStatement("SELECT * FROM contrato");
					ResultSet rsContrato = selectContrato.executeQuery();

					String nome = "";
					String cpfcnpj = "";
					String contrato = "";
					Double saldo = 0.0;
					String contato = "";
					String dataFinal = "";
					while (rsContrato.next()) {
						nome = rsContrato.getString("nome");
						cpfcnpj = rsContrato.getString("cpf_ou_cnpj");
						contrato = rsContrato.getString("numero_do_contrato");
						saldo = rsContrato.getDouble("saldo");
						NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
						String saldoFormatado = currencyFormat.format(saldo);
						contato = rsContrato.getString("contato");
						dataFinal = rsContrato.getString("data_final");
						Object[] addColumn = { nome, cpfcnpj, contrato, saldoFormatado, contato, dataFinal };
						tabela.addRow(addColumn);
					}

					conexao.close();
					selectContrato.close();
					rsContrato.close();

				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setIcon(new ImageIcon(TelaCompras.class.getResource("/imagens/procurando-uma-pessoa.png")));
		btnNewButton.setFont(new Font("Calibri", Font.PLAIN, 15));
		btnNewButton.setBounds(5, 28, 173, 23);
		panel.add(btnNewButton);

		txtNome = new JTextField();
		txtNome.setBounds(91, 86, 305, 28);
		panel.add(txtNome);
		txtNome.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("CPF / CNPJ");
		lblNewLabel_1.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(405, 95, 66, 14);
		panel.add(lblNewLabel_1);

		txtDoc = new JTextField();
		txtDoc.setBounds(478, 86, 200, 28);
		panel.add(txtDoc);
		txtDoc.setColumns(10);

		JLabel lblNewLabel_14 = new JLabel("NUMERO DO ITEM");
		lblNewLabel_14.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel_14.setBounds(10, 238, 111, 14);
		panel_2.add(lblNewLabel_14);

		txtItem = new JTextField();
		txtItem.setBounds(131, 229, 86, 29);
		panel_2.add(txtItem);
		txtItem.setColumns(10);
		txtItem.setEditable(false);

		JLabel lblNewLabel_15 = new JLabel("VALOR UNITARIO");
		lblNewLabel_15.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel_15.setBounds(227, 238, 110, 14);
		panel_2.add(lblNewLabel_15);

		JLabel lblNewLabel_16 = new JLabel("ESPECIFICAÇÃO");
		lblNewLabel_16.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel_16.setBounds(25, 280, 96, 14);
		panel_2.add(lblNewLabel_16);

		JTextArea txtEsp = new JTextArea();
		panel_2.add(txtEsp);
		txtEsp.setBounds(131, 273, 305, 60);
		txtEsp.setLineWrap(true);
		txtEsp.setWrapStyleWord(true);
		txtEsp.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(txtEsp);
		scrollPane.setBounds(131, 273, 305, 60);
		panel_2.add(scrollPane);

		txtUnitItem = new JTextField();
		txtUnitItem.setBounds(334, 229, 104, 29);
		panel_2.add(txtUnitItem);
		txtUnitItem.setColumns(10);
		txtUnitItem.setEditable(false);

		JLabel lblNewLabel_17 = new JLabel("QUANTIDADE DISPONIVEL");
		lblNewLabel_17.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel_17.setBounds(448, 238, 166, 14);
		panel_2.add(lblNewLabel_17);

		txtQtdDis = new JTextField();
		txtQtdDis.setBounds(624, 229, 110, 29);
		panel_2.add(txtQtdDis);
		txtQtdDis.setColumns(10);
		txtQtdDis.setEditable(false);

		JLabel lblNewLabel_18 = new JLabel("QUANTIDADE SOLICITADA");
		lblNewLabel_18.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel_18.setBounds(448, 280, 166, 14);
		panel_2.add(lblNewLabel_18);

		txtQtdSol = new JTextField();
		txtQtdSol.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(javax.swing.event.DocumentEvent e) {
				atualizarCalculo();

			}

			@Override
			public void removeUpdate(javax.swing.event.DocumentEvent e) {
				// atualizarCalculo();

			}

			@Override
			public void changedUpdate(javax.swing.event.DocumentEvent e) {
				atualizarCalculo();

			}
		});
		txtQtdSol.setBounds(624, 271, 110, 29);
		panel_2.add(txtQtdSol);
		txtQtdSol.setColumns(10);

		JLabel lblNewLabel_19 = new JLabel("TOTAL");
		lblNewLabel_19.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel_19.setBounds(738, 238, 46, 14);
		panel_2.add(lblNewLabel_19);

		txtTotItem = new JTextField();
		txtTotItem.setBounds(789, 229, 86, 29);
		panel_2.add(txtTotItem);
		txtTotItem.setColumns(10);
		txtTotItem.setEditable(false);

		JLabel lblNewLabel_2 = new JLabel("CONTRATO");
		lblNewLabel_2.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(5, 139, 69, 14);
		panel.add(lblNewLabel_2);

		txtContratoI = new JTextField();
		txtContratoI.setBounds(280, 130, 137, 28);
		panel.add(txtContratoI);
		txtContratoI.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("INICIO");
		lblNewLabel_3.setBounds(323, 111, 46, 23);
		panel.add(lblNewLabel_3);

		txtContratoF = new JTextField();
		txtContratoF.setBounds(478, 130, 137, 28);
		panel.add(txtContratoF);
		txtContratoF.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("FIM");
		lblNewLabel_4.setBounds(532, 111, 46, 23);
		panel.add(lblNewLabel_4);

		txtSaldo = new JTextField();
		txtSaldo.setBounds(754, 130, 157, 28);
		panel.add(txtSaldo);
		txtSaldo.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("SALDO");
		lblNewLabel_5.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel_5.setBounds(698, 139, 46, 14);
		panel.add(lblNewLabel_5);

		txtContato = new JTextField();
		txtContato.setBounds(754, 86, 157, 28);
		panel.add(txtContato);
		txtContato.setColumns(10);

		JLabel lblNewLabel_13 = new JLabel("BUSCAR");
		lblNewLabel_13.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel_13.setBounds(49, 26, 49, 14);
		panel_2.add(lblNewLabel_13);

		textField = new JTextField();
		textField.setBounds(108, 17, 349, 29);
		panel_2.add(textField);
		textField.setColumns(10);

		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.setIcon(new ImageIcon(TelaCompras.class.getResource("/imagens/lupa.png")));
		btnNewButton_4.setBounds(467, 14, 49, 32);
		panel_2.add(btnNewButton_4);

		JLabel lblNewLabel_6 = new JLabel("CONTATO");
		lblNewLabel_6.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel_6.setBounds(688, 95, 61, 14);
		panel.add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("ENDEREÇO");
		lblNewLabel_7.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel_7.setBounds(5, 188, 69, 14);
		panel.add(lblNewLabel_7);

		enderecoCidade = new JTextField();
		enderecoCidade.setBounds(91, 183, 213, 28);
		panel.add(enderecoCidade);
		enderecoCidade.setColumns(10);

		enderecoRua = new JTextField();
		enderecoRua.setBounds(332, 183, 213, 28);
		panel.add(enderecoRua);
		enderecoRua.setColumns(10);
		
		List<Object> itensbuy = new ArrayList<>();

		enderecoBairro = new JTextField();
		enderecoBairro.setBounds(571, 183, 137, 28);
		panel.add(enderecoBairro);
		enderecoBairro.setColumns(10);

		enderecoNumero = new JTextField();
		enderecoNumero.setBounds(743, 183, 86, 28);
		panel.add(enderecoNumero);
		enderecoNumero.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("CIDADE");
		lblNewLabel_8.setBounds(173, 168, 46, 14);
		panel.add(lblNewLabel_8);

		JLabel lblNewLabel_9 = new JLabel("RUA");
		lblNewLabel_9.setBounds(425, 168, 46, 14);
		panel.add(lblNewLabel_9);

		JLabel lblNewLabel_10 = new JLabel("BAIRRO");
		lblNewLabel_10.setBounds(615, 168, 46, 14);
		panel.add(lblNewLabel_10);

		JLabel lblNewLabel_11 = new JLabel("   Nª");
		lblNewLabel_11.setBounds(771, 169, 46, 14);
		panel.add(lblNewLabel_11);

		txtContrato = new JTextField();
		txtContrato.setBounds(91, 126, 130, 28);
		panel.add(txtContrato);
		txtContrato.setColumns(10);

		JLabel lblNewLabel_12 = new JLabel("BUSCAR");
		lblNewLabel_12.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel_12.setBounds(33, 22, 54, 14);
		panel_1.add(lblNewLabel_12);

		String[] colunasItem = { "NUMERO DO ITEM", "ESPECIFICAÇÃO", "VALOR UNITARIO", "VALOR TOTAL", "QUANTIDADE",
				"MEDIDA" };
		DefaultTableModel item = new DefaultTableModel(colunasItem, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};// NAO EDITAR DADOS PELA TABELA
		table_1 = new JTable(item);
		JScrollPane scrollItens = new JScrollPane(table_1);
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table_1.getSelectedRow();
				txtItem.setText(table_1.getValueAt(row, 0).toString());
				txtEsp.setText(table_1.getValueAt(row, 1).toString());
				txtUnitItem.setText(table_1.getValueAt(row, 2).toString());
				txtQtdDis.setText(table_1.getValueAt(row, 4).toString());
			}
		});
		table_1.setBounds(0, 81, 921, 419);
		scrollItens.setBounds(0, 57, 921, 160);
		panel_2.add(scrollItens);

		String[] itemCompra = { "NUMERO DO ITEM", "ESPECIFICAÇÃO", "VALOR UNITARIO", "VALOR TOTAL", "QUANTIDADE" };
		DefaultTableModel compraItem = new DefaultTableModel(itemCompra, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table_2 = new JTable(compraItem);
		JScrollPane js = new JScrollPane(table_2);
		table_2.setBounds(0, 302, 921, 164);
		js.setBounds(0, 302, 921, 164);
		panel.add(js);
		compraItem.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {

			}
		});

		JButton btnNewButton_5 = new JButton("VOLTAR");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Tem certezar que quer remover?", "remover",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					panel_1.setVisible(false);
					panel_2.setVisible(false);
					panel.setVisible(true);
				}
			}
		});
		btnNewButton_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_5.setIcon(new ImageIcon(TelaCompras.class.getResource("/imagens/seta-curva-esquerda.png")));
		btnNewButton_5.setBounds(572, 20, 110, 23);
		panel_2.add(btnNewButton_5);

		JButton btnNewButton_6 = new JButton("");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtItem.getText().isEmpty() || txtEsp.getText().isEmpty() || txtUnitItem.getText().isEmpty()
						|| txtTotItem.getText().isEmpty() || txtQtdDis.getText().isEmpty()
						|| txtQtdSol.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "OS CAMPOS DEVEM ESTAR PREENCHIDOS ");
				} else {
					int rowSc = table_3.getSelectedRow();
					

					String item = txtItem.getText();
					String especifica = txtEsp.getText();
					String unitario = txtUnitItem.getText();
					String total = txtTotItem.getText();
					String disponivel = txtQtdDis.getText();
					String pego = txtQtdSol.getText();
					String valorNumerico = unitario.replaceAll("[^\\d.,]", "");
					valorNumerico = valorNumerico.replace(",", ".");
					Double unitarioP = Double.parseDouble(valorNumerico);
					int soli = Integer.parseInt(pego);
					int disp = Integer.parseInt(disponivel);

					DefaultTableModel tabela = (DefaultTableModel) table_3.getModel();
					DefaultTableModel tabelaItem = (DefaultTableModel) table_1.getModel();

					int row = table_1.getSelectedRow();
					int restante = disp - soli;
					Double restanteTotal = unitarioP * restante;
					NumberFormat formatoDinheiro = NumberFormat.getCurrencyInstance();
					String valorTotalFormatado = formatoDinheiro.format(restanteTotal);

					if (soli > disp) {
						JOptionPane.showMessageDialog(null, "QUANTIDADE SOLICITADA NÃO DISPONÍVEL");
					} else if (rowSc == -1) {
						Object[] linha = { item, especifica, unitario, total, pego };
						tabela.addRow(linha);
						itensbuy.add(null);
					} else {
						tabela.setValueAt(item, rowSc, 0);
						tabela.setValueAt(especifica, rowSc, 1);
						tabela.setValueAt(valorNumerico, rowSc, 2);
						tabela.setValueAt(total, rowSc, 3);
						tabela.setValueAt(soli, rowSc, 4);
					}

					txtItem.setText("");
					txtEsp.setText("");
					txtUnitItem.setText("");
					txtTotItem.setText("");
					txtQtdDis.setText("");
					txtQtdSol.setText("");
				}
			}
		});

		btnNewButton_6.setIcon(new ImageIcon(TelaCompras.class.getResource("/imagens/mais.png")));
		btnNewButton_6.setBounds(777, 273, 62, 23);
		panel_2.add(btnNewButton_6);

		String[] itens = { "NUMERO DO ITEM", "ESPECIFICAÇÃO", "VALOR UNITARIO", "VALOR TOTAL", "QUANTIDADE" };
		DefaultTableModel item3 = new DefaultTableModel(itens, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table_3 = new JTable(item3);
		JScrollPane item3JS = new JScrollPane(table_3);
		table_3.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = table_3.getSelectedRow();
					String itemStr = table_3.getValueAt(row, 0).toString();
					if (!itemStr.isEmpty()) {
						int item;
						try {
							item = Integer.parseInt(itemStr);
						} catch (NumberFormatException ex) {
							// Valor inválido, tratar o erro adequadamente
							System.out.println("Valor inválido para 'item'");
							return;
						}
						DefaultTableModel model = (DefaultTableModel) table_1.getModel();

						for (int i = 0; i < model.getRowCount(); i++) {
							int itemTabela;
							try {
								itemTabela = (int) model.getValueAt(i, 0);
							} catch (NumberFormatException ex) {
								// Valor inválido, tratar o erro adequadamente
								System.out.println("Valor inválido na tabela");
								continue;
							}
							if (item == itemTabela) {
								String espe = (String) model.getValueAt(i, 1);
								int quantidadeAtual;
								try {
									quantidadeAtual = Integer.parseInt(model.getValueAt(i, 4).toString());
								} catch (NumberFormatException ex) {
									// Valor inválido, tratar o erro adequadamente
									System.out.println("Valor inválido para 'quantidadeAtual'");
									continue;
								}
								String quantidadeAtualStr = String.valueOf(quantidadeAtual);
								String unitStr = model.getValueAt(i, 2).toString();
								txtQtdDis.setText(quantidadeAtualStr);
								txtUnitItem.setText(unitStr);
								txtEsp.setText(espe);
							}
						}
						txtQtdSol.setText(table_3.getValueAt(row, 4).toString());
						txtItem.setText(itemStr);
					}
				}
			}
		});

		item3JS.setBounds(0, 345, 814, 155);
		table_3.setBounds(0, 345, 814, 155);
		panel_2.add(item3JS);

		JButton btnNewButton_7 = new JButton("SALVAR");
		List<Object[]> listItens = new ArrayList<>();
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table_3.getModel();
				int row = table_3.getSelectedRow();
				int numeroItem = (int) table_3.getValueAt(row, 0);
				String especfica = (table_3.getValueAt(row, 2).toString());
				
			}
		});
		btnNewButton_7.setBounds(832, 366, 89, 23);
		panel_2.add(btnNewButton_7);

		JButton btnNewButton_8 = new JButton("");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtItem.getText().isEmpty() || txtQtdSol.getText().isEmpty()) {
					int row = table_3.getSelectedRow();
					DefaultTableModel model = (DefaultTableModel) table_1.getModel();
					((DefaultTableModel) table_3.getModel()).removeRow(row);
					itensbuy.remove(row);

				}
			}
		}); 
		btnNewButton_8.setIcon(new ImageIcon(TelaCompras.class.getResource("/imagens/bloquear.png")));
		btnNewButton_8.setBounds(849, 273, 62, 23);
		panel_2.add(btnNewButton_8);

		String[] colunas = { "NOME", "CPF / CNPJ", "CONTRATO", "SALDO", "CONTATO", "DATA FINAL CONTRATO" };
		DefaultTableModel contratos = new DefaultTableModel(colunas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};// NAO EDITAR DADOS PELA TABELA
		table = new JTable(contratos);
		JScrollPane scrollContratos = new JScrollPane(table);
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					panel_1.setVisible(false);
					panel.setVisible(true);

					int row = table.getSelectedRow();
					txtContrato.setText(table.getValueAt(row, 2).toString());
					String stringContrato = txtContrato.getText();
					DefaultTableModel tabela2 = (DefaultTableModel) table_1.getModel();
					DefaultTableModel tabela = (DefaultTableModel) table.getModel();
					DefaultTableModel tabela3 = (DefaultTableModel) table_2.getModel();
					tabela.setRowCount(0);
					tabela3.setRowCount(0);

					try {
						Class.forName("com.mysql.cj.jdbc.Driver");

						String url = "jdbc:mysql://localhost:3306/itaite";
						String usuario = "root";
						String senha = "5988";

						Connection conexao = DriverManager.getConnection(url, usuario, senha);

						PreparedStatement preencherCampos = conexao
								.prepareStatement("SELECT * FROM contrato WHERE numero_do_contrato = ?");
						preencherCampos.setString(1, stringContrato);
						preencherCampos.execute();
						ResultSet rsPreencher = preencherCampos.executeQuery();
						String nome = "";
						String cpfcnpj = "";
						String contrato = "";
						String cidadeEndereco = "";
						String bairroEndereco = "";
						String ruaEndereco = "";
						String numeroEndereco = "";
						String dataInicio = "";
						Double saldo = 0.0;
						String contato = "";
						String dataFinal = "";
						String saldoFormatado = "";
						while (rsPreencher.next()) {
							nome = rsPreencher.getString("nome");
							cpfcnpj = rsPreencher.getString("cpf_ou_cnpj");
							contrato = rsPreencher.getString("numero_do_contrato");
							contato = rsPreencher.getString("contato");
							dataFinal = rsPreencher.getString("data_final");
							cidadeEndereco = rsPreencher.getString("endereco");
							bairroEndereco = rsPreencher.getString("enderecoBairro");
							ruaEndereco = rsPreencher.getString("enderecoRua");
							numeroEndereco = rsPreencher.getString("enderecoNumero");
							dataInicio = rsPreencher.getString("data_inicio");
							idContrato = rsPreencher.getInt("id");
							saldo = rsPreencher.getDouble("saldo");
							NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
							saldoFormatado = currencyFormat.format(saldo);
						}

						PreparedStatement selectItens = conexao
								.prepareStatement("SELECT * FROM item WHERE contrato_id = ?");
						selectItens.setInt(1, idContrato);
						ResultSet rsItem = selectItens.executeQuery();

						int numeroItem = 0;
						String especificacao = "";
						Double valorUnit = 0.0;
						Double valorTot = 0.0;
						int quantidade = 0;
						String medida = "";
						NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
						NumberFormat integerFormat = NumberFormat.getIntegerInstance();

						while (rsItem.next()) {
							numeroItem = rsItem.getInt("numero_item");
							especificacao = rsItem.getString("especificacao");
							valorUnit = rsItem.getDouble("valor_unitario");
							valorTot = rsItem.getDouble("valor_total");
							quantidade = rsItem.getInt("quantidade");
							medida = rsItem.getString("medida");

							String valorUnitFormatted = currencyFormat.format(valorUnit);
							String valorTotFormatted = currencyFormat.format(valorTot);
							String quantidadeFormatted = integerFormat.format(quantidade);

							Object[] addItem = { numeroItem, especificacao, valorUnitFormatted, valorTotFormatted,
									quantidadeFormatted, medida };
							tabela2.addRow(addItem);
						}
						txtNome.setText(nome);
						txtDoc.setText(cpfcnpj);
						txtContato.setText(contato);
						txtContratoI.setText(dataInicio);
						txtContratoF.setText(dataFinal);
						enderecoCidade.setText(cidadeEndereco);
						enderecoBairro.setText(bairroEndereco);
						enderecoRua.setText(ruaEndereco);
						enderecoNumero.setText(numeroEndereco);
						txtSaldo.setText(saldoFormatado);

						conexao.close();
						preencherCampos.close();
						rsPreencher.close();

					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		table.setBounds(0, 70, 878, 430);
		scrollContratos.setBounds(0, 70, 921, 430);
		panel_1.add(scrollContratos);

		textField_10 = new JTextField();
		textField_10.setBounds(97, 11, 357, 32);
		panel_1.add(textField_10);
		textField_10.setColumns(10);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(TelaCompras.class.getResource("/imagens/lupa.png")));
		btnNewButton_1.setBounds(464, 11, 54, 32);
		panel_1.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("VOLTAR");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_1.setVisible(false);
				panel.setVisible(true);
				panel_2.setVisible(false);
			}
		});
		btnNewButton_2.setIcon(new ImageIcon(TelaCompras.class.getResource("/imagens/seta-curva-esquerda.png")));
		btnNewButton_2.setBounds(597, 16, 104, 23);
		panel_1.add(btnNewButton_2);

		DefaultTableModel tabela = (DefaultTableModel) table.getModel();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost:3306/itaite";
			String usuario = "root";
			String senha = "5988";

			Connection conexao = DriverManager.getConnection(url, usuario, senha);

			PreparedStatement selectContrato = conexao.prepareStatement("SELECT * FROM contrato");
			ResultSet rsContrato = selectContrato.executeQuery();

			String nome = "";
			String cpfcnpj = "";
			String contrato = "";
			Double saldo = 0.0;
			String contato = "";
			String dataFinal = "";
			while (rsContrato.next()) {
				nome = rsContrato.getString("nome");
				cpfcnpj = rsContrato.getString("cpf_ou_cnpj");
				contrato = rsContrato.getString("numero_do_contrato");
				saldo = rsContrato.getDouble("saldo");
				NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
				String saldoFormatado = currencyFormat.format(saldo);
				contato = rsContrato.getString("contato");
				dataFinal = rsContrato.getString("data_final");
				Object[] addColumn = { nome, cpfcnpj, contrato, saldoFormatado, contato, dataFinal };
				tabela.addRow(addColumn);
			}

			conexao.close();
			selectContrato.close();
			rsContrato.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		txtNome.setEditable(false);
		txtContato.setEditable(false);
		txtContrato.setEditable(false);
		txtContratoF.setEditable(false);
		txtContratoI.setEditable(false);
		txtDoc.setEditable(false);
		txtSaldo.setEditable(false);
		enderecoBairro.setEditable(false);
		enderecoCidade.setEditable(false);
		enderecoNumero.setEditable(false);
		enderecoRua.setEditable(false);

		JButton btnNewButton_3 = new JButton("BUSCAR ITEM");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtContrato.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "SELECIONE PRIMEIRAMENTE O CONTRATO");
				} else {
					panel.setVisible(false);
					panel_1.setVisible(false);
					panel_2.setVisible(true);
					DefaultTableModel tabela = (DefaultTableModel) table_1.getModel();
					tabela.setRowCount(0);
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");

						String url = "jdbc:mysql://localhost:3306/itaite";
						String usuario = "root";
						String senha = "5988";

						Connection conexao = DriverManager.getConnection(url, usuario, senha);

						PreparedStatement selectItens = conexao
								.prepareStatement("SELECT * FROM item WHERE contrato_id = ?");
						selectItens.setInt(1, idContrato);
						ResultSet rsItem = selectItens.executeQuery();

						int numeroItem = 0;
						String especificacao = "";
						Double valorUnit = 0.0;
						Double valorTot = 0.0;
						int quantidade = 0;
						String medida = "";
						NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
						NumberFormat integerFormat = NumberFormat.getIntegerInstance();

						while (rsItem.next()) {
							numeroItem = rsItem.getInt("numero_item");
							especificacao = rsItem.getString("especificacao");
							valorUnit = rsItem.getDouble("valor_unitario");
							valorTot = rsItem.getDouble("valor_total");
							quantidade = rsItem.getInt("quantidade");
							medida = rsItem.getString("medida");

							String valorUnitFormatted = currencyFormat.format(valorUnit);
							String valorTotFormatted = currencyFormat.format(valorTot);
							String quantidadeFormatted = integerFormat.format(quantidade);

							Object[] addItem = { numeroItem, especificacao, valorUnitFormatted, valorTotFormatted,
									quantidadeFormatted, medida };
							tabela.addRow(addItem);
						}
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});
		btnNewButton_3.setIcon(new ImageIcon(TelaCompras.class.getResource("/imagens/procurar.png")));
		btnNewButton_3.setFont(new Font("Calibri", Font.PLAIN, 15));
		btnNewButton_3.setBounds(5, 254, 173, 23);
		panel.add(btnNewButton_3);

	}
}