package PacotePrincipal;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.ImageIcon;

public class telaContrato extends JInternalFrame {
	private JTextField txtNome;
	private JTextField txtNumeroContrato;
	private JFormattedTextField cpfCnpj;
	private JTextField enderecoCidade;
	private JTextField enderecoRua;
	private JTextField enderecoBairro;
	private JTextField enderecoNumero;
	private JTextField txtContato;
	private JTextField itemEsp;
	private JTextField itemQuantidade;
	private JTextField itemValorUnit;
	private JTable table;
	private JFormattedTextField textField_2;
	private JTextField itemValorTot;
	private JTextField itemMedida;
	private JLabel lblSaldo;
	private JTextField itemNumber;
	private Double total;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					telaContrato frame = new telaContrato();
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
	private void updateTotalSum() {
		total = 0.0;

		int rowCount = table.getRowCount();
 
		for (int row = 0; row < rowCount; row++) {
			Object value = table.getValueAt(row, 5); // Índice da coluna "VALOR TOTAL"
			if (value instanceof Double) {
				total += (Double) value;
			}
		}

		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
		String valorFormatado = currencyFormat.format(total);
		lblSaldo.setText(valorFormatado);
	}

	public telaContrato() {
		setBounds(100, 100, 845, 638);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("NOME");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel.setBounds(152, 50, 39, 19);
		getContentPane().add(lblNewLabel);

		txtNome = new JTextField();
		txtNome.setBounds(201, 43, 327, 28);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);

		try {
			// Define o estilo "Nimbus" (substitua pelo estilo de sua preferência)
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		JLabel lblNewLabel_1 = new JLabel("NUMERO DO CONTRATO");
		lblNewLabel_1.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(41, 87, 150, 19);
		getContentPane().add(lblNewLabel_1);

		txtNumeroContrato = new JTextField();
		txtNumeroContrato.setBounds(201, 84, 235, 28);
		getContentPane().add(txtNumeroContrato);
		txtNumeroContrato.setColumns(10);

		JLabel lblCpfCnpj = new JLabel("CPF");
		lblCpfCnpj.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblCpfCnpj.setBounds(152, 181, 39, 14);
		getContentPane().add(lblCpfCnpj);

		cpfCnpj = new JFormattedTextField();
		cpfCnpj.setBounds(201, 171, 204, 28);
		getContentPane().add(cpfCnpj);
		cpfCnpj.setColumns(10);
		cpfCnpj.setFont(new Font("Arial", Font.PLAIN, 15));
		cpfCnpj.setBackground(new Color(255, 255, 255));
		cpfCnpj.setColumns(10);
		cpfCnpj.setVisible(true);

		JRadioButton rdbCpf = new JRadioButton("PESSOA FÍSICA");
		rdbCpf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblCpfCnpj.setText("CPF");
				cpfCnpj.setText("");
				cpfCnpj.setFormatterFactory(null);
				cpfCnpj.setValue(null);
				try {
					MaskFormatter formato = new MaskFormatter("###.###.###-##");
					cpfCnpj.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(formato));
				} catch (ParseException ex) {
					ex.printStackTrace();
				}
			}
		});
		rdbCpf.setFont(new Font("Calibri", Font.PLAIN, 15));
		rdbCpf.setBounds(184, 141, 128, 23);
		getContentPane().add(rdbCpf);

		JRadioButton rdbCnpj = new JRadioButton("PESSOA JURIDICA");
		rdbCnpj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblCpfCnpj.setText("CNPJ");
				cpfCnpj.setText("");
				cpfCnpj.setFormatterFactory(null);
				cpfCnpj.setValue(null);
				try {
					MaskFormatter formato = new MaskFormatter("##.###.###/####-##");
					cpfCnpj.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(formato));
				} catch (ParseException ex) {
					ex.printStackTrace();
				}
			}
		});
		rdbCnpj.setFont(new Font("Calibri", Font.PLAIN, 15));
		rdbCnpj.setBounds(344, 141, 140, 23);
		getContentPane().add(rdbCnpj);

		ButtonGroup grupoRdb = new ButtonGroup();
		grupoRdb.add(rdbCnpj);
		grupoRdb.add(rdbCpf);

		JLabel lblNewLabel_3 = new JLabel("ENDEREÇO");
		lblNewLabel_3.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(125, 242, 66, 14);
		getContentPane().add(lblNewLabel_3);

		MaskFormatter data = null;
		try {
			data = new MaskFormatter("##/##/####");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JLabel lblNewLabel_2 = new JLabel("DATA INICIAL");
		lblNewLabel_2.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(563, 46, 82, 14);
		getContentPane().add(lblNewLabel_2);

		JFormattedTextField txtDataInicial = new JFormattedTextField(data);
		txtDataInicial.setBounds(655, 43, 117, 28);
		getContentPane().add(txtDataInicial);
		txtDataInicial.setColumns(10);

		JLabel lblNewLabel_13 = new JLabel("DATA FINAL");
		lblNewLabel_13.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel_13.setBounds(564, 89, 73, 14);
		getContentPane().add(lblNewLabel_13);

		JFormattedTextField txtDataFinal = new JFormattedTextField(data);
		txtDataFinal.setBounds(655, 83, 116, 29);
		getContentPane().add(txtDataFinal);
		enderecoCidade = new JTextField();
		enderecoCidade.setBounds(201, 237, 198, 26);
		getContentPane().add(enderecoCidade);
		enderecoCidade.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("CIDADE");
		lblNewLabel_4.setFont(new Font("Calibri", Font.PLAIN, 11));
		lblNewLabel_4.setBounds(278, 225, 34, 14);
		getContentPane().add(lblNewLabel_4);

		enderecoRua = new JTextField();
		enderecoRua.setBounds(409, 237, 158, 26);
		getContentPane().add(enderecoRua);
		enderecoRua.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("RUA");
		lblNewLabel_5.setFont(new Font("Calibri", Font.PLAIN, 11));
		lblNewLabel_5.setBounds(477, 225, 23, 14);
		getContentPane().add(lblNewLabel_5);

		enderecoBairro = new JTextField();
		enderecoBairro.setBounds(577, 237, 86, 26);
		getContentPane().add(enderecoBairro);
		enderecoBairro.setColumns(10);

		JLabel lblNewLabel_6 = new JLabel("BAIRRO");
		lblNewLabel_6.setFont(new Font("Calibri", Font.PLAIN, 11));
		lblNewLabel_6.setBounds(603, 225, 34, 14);
		getContentPane().add(lblNewLabel_6);

		enderecoNumero = new JTextField();
		enderecoNumero.setBounds(677, 237, 86, 26);
		getContentPane().add(enderecoNumero);
		enderecoNumero.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("Nº");
		lblNewLabel_7.setFont(new Font("Calibri", Font.PLAIN, 11));
		lblNewLabel_7.setBounds(713, 225, 12, 14);
		getContentPane().add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("CONTATO");
		lblNewLabel_8.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel_8.setBounds(130, 279, 61, 14);
		getContentPane().add(lblNewLabel_8);

		txtContato = new JTextField();
		txtContato.setBounds(201, 274, 198, 28);
		getContentPane().add(txtContato);
		txtContato.setColumns(10);

		JLabel lblNewLabel_9 = new JLabel("ITEM");
		lblNewLabel_9.setFont(new Font("Calibri", Font.PLAIN, 10));
		lblNewLabel_9.setBounds(41, 308, 34, 14);
		getContentPane().add(lblNewLabel_9);

		itemEsp = new JTextField();
		itemEsp.setBounds(110, 323, 270, 30);
		getContentPane().add(itemEsp);
		itemEsp.setColumns(10);

		JLabel lblNewLabel_10 = new JLabel("ESPECIFICAÇÃO");
		lblNewLabel_10.setFont(new Font("Calibri", Font.PLAIN, 11));
		lblNewLabel_10.setBounds(201, 308, 73, 14);
		getContentPane().add(lblNewLabel_10);

		itemQuantidade = new JTextField();
		itemQuantidade.setBounds(390, 323, 110, 30);
		getContentPane().add(itemQuantidade);
		itemQuantidade.setColumns(10);

		JLabel lblNewLabel_11 = new JLabel("QUANTIDADE");
		lblNewLabel_11.setFont(new Font("Calibri", Font.PLAIN, 11));
		lblNewLabel_11.setBounds(414, 308, 86, 14);
		getContentPane().add(lblNewLabel_11);

		itemValorUnit = new JTextField();
		itemValorUnit.setBounds(523, 323, 86, 30);
		getContentPane().add(itemValorUnit);
		itemValorUnit.setColumns(10);

		JLabel lblNewLabel_12 = new JLabel("VALOR UNITARIO");
		lblNewLabel_12.setFont(new Font("Calibri", Font.PLAIN, 11));
		lblNewLabel_12.setBounds(527, 308, 82, 14);
		getContentPane().add(lblNewLabel_12);

		JButton btnNewButton = new JButton("ADD");
		btnNewButton.setIcon(new ImageIcon(telaContrato.class.getResource("/imagens/mais.png")));
		btnNewButton.setFont(new Font("Calibri", Font.PLAIN, 12));
		List<itensContrato> listaItens = new ArrayList<>();
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				DefaultTableModel tabela = (DefaultTableModel) table.getModel();

				int itemNumero = Integer.parseInt(itemNumber.getText());
				String espItem = itemEsp.getText();
				String medidaItem = itemMedida.getText();
				int quantidadeItem = Integer.parseInt(itemQuantidade.getText());
				Double itemUnitario = Double.parseDouble(itemValorUnit.getText());
				Double itemTotal = Double.parseDouble(itemValorTot.getText());

				itensContrato item = new itensContrato(itemNumero, espItem, medidaItem, quantidadeItem, itemUnitario,
						itemTotal);

				Object[] add = { item.getItem(), item.getEspecificacao(), item.getMedida(), item.getQuantidadeItem(),
						item.getValorUnitario(), item.getValorTotal() };

				if (itemEsp.getText().isEmpty() || itemQuantidade.getText().isEmpty()
						|| itemValorUnit.getText().isEmpty() || itemMedida.getText().isEmpty()
						|| itemValorTot.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "PREENCHA TODOS OS CAMPOS SE QUISER SALVAR");
				} else if (row == -1) {
					listaItens.add(item);
					tabela.addRow(add);
				} else {
					item.setEspecificacao(itemEsp.getText());
					item.setMedida(itemMedida.getText());
					item.setQuantidadeItem(Integer.parseInt(itemQuantidade.getText()));
					item.setValorUnitario(Double.parseDouble(itemValorUnit.getText()));
					item.setValorTotal(Double.parseDouble(itemValorTot.getText()));

					tabela.setValueAt(item.getItem(), row, 0);
					tabela.setValueAt(item.getEspecificacao(), row, 1);
					tabela.setValueAt(item.getMedida(), row, 2);
					tabela.setValueAt(item.getQuantidadeItem(), row, 3);
					tabela.setValueAt(item.getValorUnitario(), row, 4);
					tabela.setValueAt(item.getValorTotal(), row, 5);

					listaItens.set(row, item);
				}

				itemEsp.setText("");
				itemQuantidade.setText("");
				itemValorUnit.setText("");
				itemValorTot.setText("");
				itemNumber.setText("");
				itemMedida.setText("");
			}

		});
		// btnNewButton.setIcon(new
		// ImageIcon(telaContrato.class.getResource("/imagens/mais.png")));
		btnNewButton.setBounds(256, 364, 89, 23);
		getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("DEL");
		btnNewButton_1.setIcon(new ImageIcon(telaContrato.class.getResource("/imagens/bloquear.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tabela = (DefaultTableModel) table.getModel();
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "Selecione a linha que quer excluir");
				} else {
					int id = Integer.parseInt(table.getValueAt(row, 0).toString());
					if (JOptionPane.showConfirmDialog(null, "Tem certezar que quer remover?", "remover",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						((DefaultTableModel) table.getModel()).removeRow(row);
						listaItens.remove(row);
					}
				}
				itemEsp.setText("");
				itemMedida.setText("");
				itemNumber.setText("");
				itemQuantidade.setText("");
				itemValorTot.setText("");
				itemValorUnit.setText("");
			}
		});
		btnNewButton_1.setFont(new Font("Calibri", Font.PLAIN, 12));
		// btnNewButton_1.setIcon(new
		// ImageIcon(telaContrato.class.getResource("/imagens/bloquear.png")));
		btnNewButton_1.setBounds(533, 364, 89, 23);
		getContentPane().add(btnNewButton_1);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		JButton btnNewButton_2 = new JButton("SALVAR");
		btnNewButton_2.setIcon(new ImageIcon(telaContrato.class.getResource("/imagens/salvar-arquivo.png")));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = txtNome.getText();
				String numeroContrato = txtNumeroContrato.getText();
				String contato = txtContato.getText();
				String docUnico = cpfCnpj.getText();
				String datatInicio = txtDataInicial.getText();
				java.util.Date dtInicio = null;
				try {
					dtInicio = dateFormat.parse(datatInicio);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// java.sql.Timestamp sqlDtInicio = new java.sql.Timestamp(dtInicio.getTime());

				String dataFim = txtDataFinal.getText();
				java.util.Date dtFinal = null;
				try {
					dtFinal = dateFormat.parse(dataFim);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// java.sql.Timestamp sqlDtFinal = new java.sql.Timestamp(dtFinal.getTime());
				String rua = enderecoRua.getText();
				String bairro = enderecoBairro.getText();
				String cidade = enderecoCidade.getText();
				String numero = enderecoNumero.getText();

				DefaultTableModel tabela = (DefaultTableModel) table.getModel();

				try {
					Class.forName("com.mysql.cj.jdbc.Driver");

					String url = "jdbc:mysql://localhost:3306/itaite";
					String usuario = "root";
					String senha = "5988";

					Connection conexao = DriverManager.getConnection(url, usuario, senha);

					PreparedStatement insertContrato = conexao.prepareStatement(
							"INSERT INTO contrato (numero_do_contrato, nome, cpf_ou_cnpj, endereco, contato"
									+ ", enderecoRua, enderecoNumero, enderecoBairro, data_inicio, data_final, saldo) "
									+ "VALUES (?,?,?,?,?,?,?,?,?,?,?);",
							Statement.RETURN_GENERATED_KEYS);
					insertContrato.setString(1, numeroContrato);
					insertContrato.setString(2, nome);
					insertContrato.setString(3, docUnico);
					insertContrato.setString(4, cidade);
					insertContrato.setString(5, contato);
					insertContrato.setString(6, rua);
					insertContrato.setString(7, numero);
					insertContrato.setString(8, bairro);
					insertContrato.setDate(9, new java.sql.Date(dtInicio.getTime()));
					insertContrato.setDate(10, new java.sql.Date(dtFinal.getTime()));
					insertContrato.setDouble(11, total);
					insertContrato.execute();
					ResultSet rsId = insertContrato.getGeneratedKeys();
					int ultimoId = 0;
					if (rsId.next()) {
						ultimoId = rsId.getInt(1);
					}
					PreparedStatement insertItens = conexao.prepareStatement(
							"INSERT INTO item (especificacao,valor_unitario, valor_total,quantidade,medida,numero_item, contrato_id"
									+ ") VALUES " + "(?,?,?,?,?,?,?);");

					for (itensContrato item : listaItens) {
						insertItens.setString(1, item.getEspecificacao());
						insertItens.setDouble(2, item.getValorUnitario());
						insertItens.setDouble(3, item.getValorTotal());
						insertItens.setInt(4, item.getQuantidadeItem());
						insertItens.setString(5, item.getMedida());
						insertItens.setInt(6, item.getItem());
						insertItens.setInt(7, ultimoId);
						insertItens.addBatch();
					}

					insertItens.executeBatch();
					insertItens.clearBatch();
					JOptionPane.showMessageDialog(null, "SALVO COM SUCESSO");
					txtDataFinal.setText("");
					txtDataInicial.setText("");
					txtContato.setText("");
					txtNome.setText("");
					txtNumeroContrato.setText("");
					enderecoBairro.setText("");
					enderecoCidade.setText("");
					enderecoNumero.setText("");
					enderecoRua.setText("");
					txtDataFinal.setText("");
					txtDataInicial.setText("");
					itemEsp.setText("");
					itemQuantidade.setText("");
					itemValorUnit.setText("");
					itemMedida.setText("");
					itemNumber.setText("");
					itemValorTot.setText("");
					itemValorUnit.setText("");
					cpfCnpj.setText("");

					tabela.setRowCount(0);
					conexao.close();
					insertContrato.close();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		// btnNewButton_2.setIcon(new
		// ImageIcon(telaContrato.class.getResource("/imagens/salvar-arquivo.png")));
		btnNewButton_2.setBounds(356, 574, 134, 23);
		getContentPane().add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("SELECIONE A PLANILHA DE ITENS");
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser itensPlanilha = new JFileChooser();
				itensPlanilha.showOpenDialog(itensPlanilha);
				itensPlanilha.setVisible(true);
				File planilhaItensColetada = itensPlanilha.getSelectedFile();
				DefaultTableModel tabela = (DefaultTableModel) table.getModel();

				/*
				 * while (planilhaItensColetada == null) { // O usuário não selecionou um
				 * arquivo, repita o processo para selecionar // novamente
				 * itensPlanilha.showOpenDialog(itensPlanilha); itensPlanilha.setVisible(true);
				 * planilhaItensColetada = itensPlanilha.getSelectedFile(); }
				 */

				try {
					FileInputStream arquivo = new FileInputStream(planilhaItensColetada);
					Workbook workbook = new XSSFWorkbook(arquivo);
					org.apache.poi.ss.usermodel.Sheet planilha = workbook.getSheetAt(0);
					Iterator<Row> rowIterator = planilha.iterator();
					Double saldoInicial = 0.0;

					while (rowIterator.hasNext()) {
						Row linha = rowIterator.next();

						if (linha != null) {
							Cell cellItem = linha.getCell(0); // Célula A1
							Cell cellEspecificacao = linha.getCell(1);
							Cell cellMedida = linha.getCell(2); // Célula B1 // Célula B1
							Cell cellQuantidade = linha.getCell(3); // Célula C1
							Cell cellVUnitario = linha.getCell(4); // Célula C1
							Cell cellVTotal = linha.getCell(5); // Célula C1
							// Verifica se as células não são nulas antes de acessar os valores
							if (cellItem != null && cellEspecificacao != null && cellQuantidade != null
									&& cellVUnitario != null && cellVTotal != null) {

								int item = 0;
								String especificacao = cellEspecificacao.getStringCellValue();
								String medida = cellMedida.getStringCellValue();
								int quantidade = 0;
								double valorUnitario = 0;
								double valorTotal = 0;

								if (cellItem.getCellType() == CellType.NUMERIC) {
									item = (int) cellItem.getNumericCellValue();
								} else if (cellItem.getCellType() == CellType.STRING) {
									String itemStr = cellItem.getStringCellValue();
									try {
										item = Integer.parseInt(itemStr);
									} catch (NumberFormatException e1) {
										// A conversão falhou, mantém o valor padrão de 0
									}
								} else {
									item = 0;
								}

								if (cellQuantidade.getCellType() == CellType.NUMERIC) {
									quantidade = (int) cellQuantidade.getNumericCellValue();
								} else if (cellQuantidade.getCellType() == CellType.STRING) {
									String quantidadeStr = cellQuantidade.getStringCellValue();
									try {
										quantidade = Integer.parseInt(quantidadeStr);
									} catch (NumberFormatException e1) {
										// A conversão falhou, mantém o valor padrão de 0
									}
								} else {
									quantidade = 0;
								}

								if (cellVUnitario.getCellType() == CellType.NUMERIC) {
									valorUnitario = cellVUnitario.getNumericCellValue();
								} else if (cellVUnitario.getCellType() == CellType.STRING) {
									String valorUnitarioStr = cellVUnitario.getStringCellValue();
									try {
										valorUnitario = Double.parseDouble(valorUnitarioStr);
									} catch (NumberFormatException e1) {
										// A conversão falhou, mantém o valor padrão de 0
									}
								} else {
									// Caso em que a célula não contém um valor numérico ou string, define o valor
									// padrão de 0
									valorUnitario = 0;
								}

								if (cellVTotal.getCellType() == CellType.NUMERIC) {
									valorTotal = cellVTotal.getNumericCellValue();
								} else if (cellVTotal.getCellType() == CellType.STRING) {
									String valorTotalStr = cellVTotal.getStringCellValue();
									if (!valorTotalStr.equals("VALOR TOTAL")) {
										try {
											valorTotal = Double.parseDouble(valorTotalStr);
										} catch (NumberFormatException e2) {
											// A conversão falhou, mantém o valor padrão de 0
										}
									} else {
										valorTotal = 0;
									}
								}
								// Valor inicial de saldoInicial
								saldoInicial += valorTotal; // Operação de soma
								lblSaldo.setText(Double.toString(saldoInicial)); // Exibição do saldoAtualizado

								itensContrato itemContrato = new itensContrato(item, especificacao, medida, quantidade,
										valorUnitario, valorTotal);
								listaItens.add(itemContrato);

								Object colunas[] = { itemContrato.getItem(), itemContrato.getEspecificacao(),
										itemContrato.getMedida(), itemContrato.getQuantidadeItem(),
										itemContrato.getValorUnitario(), itemContrato.getValorTotal() };

								tabela.addRow(colunas);
								tabela.fireTableDataChanged();
							} else {
								JOptionPane.showMessageDialog(null, "NÃO FOI POSSOVEL LER A PLANILHA");
							}
						}
					}
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "Arquivo não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace(); // Opcional: imprime o stack trace para ajudar na depuração
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnNewButton_3.setBounds(439, 274, 206, 23);
		getContentPane().add(btnNewButton_3);

		itemValorTot = new JTextField();
		itemValorTot.setBounds(629, 323, 89, 30);
		getContentPane().add(itemValorTot);
		itemValorTot.setColumns(10);

		JLabel lblNewLabel_14 = new JLabel("VALOR TOTAL");
		lblNewLabel_14.setFont(new Font("Calibri", Font.PLAIN, 11));
		lblNewLabel_14.setBounds(640, 308, 61, 14);
		getContentPane().add(lblNewLabel_14);

		itemMedida = new JTextField();
		itemMedida.setBounds(733, 323, 86, 30);
		getContentPane().add(itemMedida);
		itemMedida.setColumns(10);

		JLabel lblNewLabel_15 = new JLabel("MEDIDA");
		lblNewLabel_15.setFont(new Font("Calibri", Font.PLAIN, 11));
		lblNewLabel_15.setBounds(753, 308, 46, 14);
		getContentPane().add(lblNewLabel_15);

		String[] colunas = { "ITEM", "ESPECIFICAÇÃO", "UND", "QTD", "VALOR UNIT", "VALOR TOTAL" };
		DefaultTableModel modeloInserir = new DefaultTableModel(colunas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			} // NAO EDITAR DADOS PELA TABELA
		};
		table = new JTable(modeloInserir);
		JScrollPane scrol = new JScrollPane(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				itemNumber.setText(table.getValueAt(row, 0).toString());
				itemEsp.setText(table.getValueAt(row, 1).toString());
				itemMedida.setText(table.getValueAt(row, 2).toString());
				itemQuantidade.setText(table.getValueAt(row, 3).toString());
				itemValorUnit.setText(table.getValueAt(row, 4).toString());
				itemValorTot.setText(table.getValueAt(row, 5).toString());
			}
		});
		TableModelListener tableModelListener = new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE || e.getType() == TableModelEvent.DELETE) {
					updateTotalSum();
				}
			}
		};
		table.getModel().addTableModelListener(tableModelListener);
		table.setBounds(0, 364, 829, 136);
		scrol.setBounds(10, 413, 829, 160);
		getContentPane().add(scrol);

		JLabel lblNewLabel_16 = new JLabel("SALDO:");
		lblNewLabel_16.setBounds(640, 388, 46, 14);
		getContentPane().add(lblNewLabel_16);

		lblSaldo = new JLabel("0");
		lblSaldo.setForeground(new Color(0, 0, 0));
		lblSaldo.setBounds(702, 388, 117, 14);
		getContentPane().add(lblSaldo);

		itemNumber = new JTextField();
		itemNumber.setBounds(10, 323, 86, 30);
		getContentPane().add(itemNumber);
		itemNumber.setColumns(10);

	}
}
