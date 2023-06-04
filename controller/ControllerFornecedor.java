package controller;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import exception.ServiceException;
import model.entity.Endereco;
import model.entity.Fornecedor;
import model.service.Controll;
import model.service.FornecedorService;
import util.Validar;

public class ControllerFornecedor extends Controller {
	
	@SuppressWarnings("unchecked")
	public static ActionListener configFornecedor() {
		ActionListener action = (e) -> {
			try {
				Object[] options = {"PROCURAR", "CADASTRAR", "ATUALIZAR", "DELETAR"};
				int response = JOptionPane.showOptionDialog(
						null, 
						"Selecione uma operação", 
						"CRUD", 
						JOptionPane.DEFAULT_OPTION, 
						JOptionPane.QUESTION_MESSAGE, 
						null, 
						options, 
						options[0]);
				
				
				switch (response) {
					case 0 -> {
						String cnpj = JOptionPane.showInputDialog("Digite o CNPJ do fornecedor");
						cnpj = Validar.validarCNPJ(cnpj);
						if (cnpj != null) {
							context.getFornecedor().setCnpj(cnpj);
							service = new FornecedorService();
							context.setFornecedor((Fornecedor)service.procurar(context.getFornecedor()));
							if (context.getFornecedor() != null) {
								JOptionPane.showMessageDialog(null, "Fornecedor encontrado:\n\n" + context.getFornecedor());
							}
						}
					}
					case 1 -> {
						context.getCadFornecedor().setVisible(true);
					}
					case 2 -> {
						String cnpj = JOptionPane.showInputDialog("Digite o CNPJ do fornecedor");
						cnpj = Validar.validarCNPJ(cnpj);
						if (cnpj != null) {
							context.getFornecedor().setCnpj(cnpj);
							service = new FornecedorService();
							context.setFornecedor((Fornecedor)service.procurar(context.getFornecedor()));
							if (context.getFornecedor() != null) {
								context.getUpdateFornecedor().getjTextField1().setText(context.getFornecedor().getCnpj());;
								context.getUpdateFornecedor().getjTextField2().setText(context.getFornecedor().getIe());
								context.getUpdateFornecedor().getjTextField3().setText(context.getFornecedor().getNome());
								context.getUpdateFornecedor().getjTextField4().setText(context.getFornecedor().getEndereco().getCep());
								context.getUpdateFornecedor().getjTextField5().setText(context.getFornecedor().getEndereco().getLogradouro());
								context.getUpdateFornecedor().getjTextField6().setText(context.getFornecedor().getEndereco().getBairro());
								context.getUpdateFornecedor().getjTextField7().setText(context.getFornecedor().getEndereco().getCidade());
								context.getUpdateFornecedor().getjTextField8().setText(context.getFornecedor().getEndereco().getComplemento());
								context.getUpdateFornecedor().getjTextField9().setText(context.getFornecedor().getEndereco().getPais());
								context.getUpdateFornecedor().getjTextField10().setText(String.valueOf(context.getFornecedor().getEndereco().getNumero()));
								context.getUpdateFornecedor().getjTextField11().setText(context.getFornecedor().getEndereco().getUf());
								context.getUpdateFornecedor().setVisible(true);
							}
						}
					}
					case 3 -> {
						String cnpj = JOptionPane.showInputDialog("Digite o CNPJ do fornecedor");
						cnpj = Validar.validarCNPJ(cnpj);
						if (cnpj != null) {
							context.getFornecedor().setCnpj(cnpj);
							service = new FornecedorService();
							context.setFornecedor((Fornecedor)service.procurar(context.getFornecedor()));
							int confirm = JOptionPane.showConfirmDialog(null, "Deseja realmente efetuar a exclusão deste Fornecedor ?\n\n" + context.getFornecedor());
							if (confirm == 0) {
								if (service.excluir(context.getFornecedor())) {
									JOptionPane.showMessageDialog(null, "O fornecedor selecionado foi excluido!");
									service = new FornecedorService();
									context.setFornecedor(new Fornecedor());
									List<Fornecedor> fornecedores = (List<Fornecedor>) service.listar(Controll.EXCEPTION);
									if (fornecedores != null) {
										context.getMenu().getjTable1().setModel(new DefaultTableModel(
												new Object[][] {},
												new String[] {"CNPJ", "IE", "EMPRESA", "ENDEREÇO"}
												));
										DefaultTableModel model = (DefaultTableModel) context.getMenu().getjTable1().getModel();
										fornecedores.forEach(fornecedor -> {
											model.addRow(new Object[] {fornecedor.getCnpj(), fornecedor.getIe(), fornecedor.getNome(), fornecedor.getEndereco().toString()});
										});
									}
								}
							}
						}
					}
					default -> {
						break;
					}
				}
			}
			catch (ServiceException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
				if (ex.getMessage().contains("Não há")) {
					context.getMenu().getjTable1().setModel(new DefaultTableModel(
							new Object [][] {
				            	null, null, null, null, null, null, null, null, null, null, null,
				            	null, null, null, null, null, null, null, null, null, null, null
				            },
				            new String [] {
				            		"Default1", "Default2", "Default3", "Default4"
				            }
							));
				}
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		};
		return action;
	}

	@SuppressWarnings("unchecked")
	public static ActionListener cadFornecedor() {
		ActionListener action = (e) -> {
			try {
				String cnpj = context.getCadFornecedor().getjTextField1().getText();
				String ie = context.getCadFornecedor().getjTextField2().getText();
				String nome = context.getCadFornecedor().getjTextField3().getText();
				String cep = context.getCadFornecedor().getjTextField4().getText();
				String logradouro = context.getCadFornecedor().getjTextField5().getText();
				String bairro = context.getCadFornecedor().getjTextField6().getText();
				String cidade = context.getCadFornecedor().getjTextField7().getText();
				String complemento = context.getCadFornecedor().getjTextField8().getText();
				String pais = context.getCadFornecedor().getjTextField9().getText();
				var numero = context.getCadFornecedor().getjTextField10().getText();
				String uf = context.getCadFornecedor().getjTextField11().getText();
				
				if (cnpj.equals("") || ie.equals("") || nome.equals("") || cep.equals("") || logradouro.equals("") || bairro.equals("") || cidade.equals("") || pais.equals("") || uf.equals("") || numero.equals("")) {
					throw new NullPointerException("Os campos não podem estar vazios!");
				}
				else {
					cnpj = Validar.validarCNPJ(cnpj);
					if (cnpj != null) {
						ie = Validar.validarIE(ie);
						if (ie != null) {
							service = new FornecedorService();
							context.setFornecedor(new Fornecedor(cnpj, ie, nome, new Endereco(cep, logradouro, bairro, complemento, cidade, uf, pais, Integer.parseInt(numero))));
							int confirm = JOptionPane.showConfirmDialog(null, "Confirme os dados do fornecedor:\n\n" + context.getFornecedor());
							if (confirm == 0) {
								if (service.inserir(context.getFornecedor())) {
									JOptionPane.showMessageDialog(null, "Fornecedor cadastrado com sucesso!");
									context.getCadFornecedor().setVisible(false);
									service = new FornecedorService();
									List<Fornecedor> fornecedores = (List<Fornecedor>) service.listar(Controll.EXCEPTION);
									if (fornecedores != null) {
										context.getMenu().getjTable1().setModel(new DefaultTableModel(
												new Object[][] {},
												new String[] {"CNPJ", "IE", "EMPRESA", "ENDEREÇO"}
												));
										DefaultTableModel model = (DefaultTableModel) context.getMenu().getjTable1().getModel();
										fornecedores.forEach(fornecedor -> {
											model.addRow(new Object[] {fornecedor.getCnpj(), fornecedor.getIe(), fornecedor.getEndereco().toString()});
										});
									}
								}
							}
						}
					}
				}
			}
			catch (ServiceException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
				if (ex.getMessage().contains("Não há")) {
					context.getMenu().getjTable1().setModel(new DefaultTableModel(
							new Object [][] {
				            	null, null, null, null, null, null, null, null, null, null, null,
				            	null, null, null, null, null, null, null, null, null, null, null
				            },
				            new String [] {
				            		"Default1", "Default2", "Default3", "Default4"
				            }
							));
				}
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		};
		return action;
	}
	
	@SuppressWarnings("unchecked")
	public static ActionListener updateFornecedor() {
		ActionListener action = (e) -> {
			try {
				String cnpj = context.getUpdateFornecedor().getjTextField1().getText();
				String ie = context.getUpdateFornecedor().getjTextField2().getText();
				String nome = context.getUpdateFornecedor().getjTextField3().getText();
				String cep = context.getUpdateFornecedor().getjTextField4().getText();
				String logradouro = context.getUpdateFornecedor().getjTextField5().getText();
				String bairro = context.getUpdateFornecedor().getjTextField6().getText();
				String cidade = context.getUpdateFornecedor().getjTextField7().getText();
				String complemento = context.getUpdateFornecedor().getjTextField8().getText();
				String pais = context.getUpdateFornecedor().getjTextField9().getText();
				var numero = context.getUpdateFornecedor().getjTextField10().getText();
				String uf = context.getUpdateFornecedor().getjTextField11().getText();
				String oldCnpj = context.getFornecedor().getCnpj() != null ? context.getFornecedor().getCnpj() : context.getUpdateFornecedor().getjTextField1().getText();

				if (cnpj.equals("") || ie.equals("") || nome.equals("") || cep.equals("") || logradouro.equals("") || bairro.equals("") || cidade.equals("") || pais.equals("") || uf.equals("") || numero.equals("")) {
					throw new NullPointerException("Os campos não podem estar vazios!");
				}
				else {
					cnpj = Validar.validarCNPJ(cnpj);
					if (cnpj != null) {
						ie = Validar.validarIE(context.getUpdateFornecedor().getjTextField2().getText());
						if (ie != null) {
							if (context.getFornecedor().getEndereco().getCep().equals(cep)) {
								context.setFornecedor(new Fornecedor(cnpj, ie, nome, new Endereco(cep, logradouro, bairro, complemento, cidade, uf, pais, Integer.parseInt(numero))));
								service = new FornecedorService();
								int confirm = JOptionPane.showConfirmDialog(null, "Confirma a atualização dos dados do fornecedor:\n\n" + context.getFornecedor());
								if (confirm == 0) {
									if (service.atualizar(context.getFornecedor(), oldCnpj)) {
										JOptionPane.showMessageDialog(null, "Fornecedor atualizado com sucesso!");
										context.getUpdateFornecedor().setVisible(false);
										List<Fornecedor> fornecedores = (List<Fornecedor>) service.listar(Controll.EXCEPTION);
										if (fornecedores != null) {
											context.getMenu().getjTable1().setModel(new DefaultTableModel(
													new Object[][] {},
													new String[] {"CNPJ", "IE", "EMPRESA", "ENDEREÇO"}
													));
											DefaultTableModel model = (DefaultTableModel) context.getMenu().getjTable1().getModel();
											fornecedores.forEach(fornecedor -> {
												model.addRow(new Object[] {fornecedor.getCnpj(), fornecedor.getIe(), fornecedor.getNome(), fornecedor.getEndereco().toString()});
											});
										}
									}
								}
							}
							else {
								JOptionPane.showMessageDialog(null, "Atualize os parâmetros do CEP!");
							}
						}
					}
				}
			}
			catch (ServiceException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
				if (ex.getMessage().contains("Não há")) {
					context.getMenu().getjTable1().setModel(new DefaultTableModel(
							new Object [][] {
				            	null, null, null, null, null, null, null, null, null, null, null,
				            	null, null, null, null, null, null, null, null, null, null, null
				            },
				            new String [] {
				            		"Default1", "Default2", "Default3", "Default4"
				            }
							));
				}
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		};
		return action;
	}
	
	public static ActionListener buscarCadCEP() {
		ActionListener action = (e) -> {
			try {
				String cep = context.getCadFornecedor().getjTextField4().getText();
				Map<String, String> endereco = Validar.validarCEP(cep);
				if (endereco != null) {
					context.getCadFornecedor().getjTextField5().setText(endereco.get("logradouro"));
					context.getCadFornecedor().getjTextField6().setText(endereco.get("bairro"));
					context.getCadFornecedor().getjTextField7().setText(endereco.get("localidade"));
					context.getCadFornecedor().getjTextField9().setText(endereco.get("pais"));
					context.getCadFornecedor().getjTextField11().setText(endereco.get("uf"));
					context.getCadFornecedor().getjTextField4().setText(endereco.get("cep"));
				}
			} 
			catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		};
		return action;
	}
	
	public static ActionListener buscarUpdateCEP() {
		ActionListener action = (e) -> {
			try {
				String cep = context.getUpdateFornecedor().getjTextField4().getText();
				Map<String, String> endereco = Validar.validarCEP(cep);
				if (endereco != null) {
					context.getUpdateFornecedor().getjTextField5().setText(endereco.get("logradouro"));
					context.getUpdateFornecedor().getjTextField6().setText(endereco.get("bairro"));
					context.getUpdateFornecedor().getjTextField7().setText(endereco.get("localidade"));
					context.getUpdateFornecedor().getjTextField8().setText("");
					context.getUpdateFornecedor().getjTextField9().setText(endereco.get("pais"));
					context.getUpdateFornecedor().getjTextField10().setText("");
					context.getUpdateFornecedor().getjTextField11().setText(endereco.get("uf"));
					context.getUpdateFornecedor().getjTextField4().setText(endereco.get("cep"));
					context.getFornecedor().getEndereco().setCep(endereco.get("cep"));
				}
			} 
			catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		};
		return action;
	}
}
