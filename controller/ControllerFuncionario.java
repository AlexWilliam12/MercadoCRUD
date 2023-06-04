package controller;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import exception.ServiceException;
import model.entity.Funcionario;
import model.service.Controll;
import model.service.FuncionarioService;
import util.Validar;

public class ControllerFuncionario extends Controller {

	@SuppressWarnings("unchecked")
	public static ActionListener configFuncionario() {
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
						String cpf = JOptionPane.showInputDialog("Digite o CPF do funcionário");
						cpf = Validar.validarCPF(cpf);
						if (cpf != null) {
							context.getFuncionario().setCpf(cpf);
							service = new FuncionarioService();
							context.setFuncionario((Funcionario)service.procurar(context.getFuncionario()));
							if (context.getFuncionario() != null) {
								JOptionPane.showMessageDialog(null, "Funcionário encontrado:\n\n" + context.getFuncionario());
							}
						}
					}
					case 1 -> {
						context.getCadFuncionario().setVisible(true);
					}
					case 2 -> {
						String cpf = JOptionPane.showInputDialog("Digite o CPF do funcionário");
						cpf = Validar.validarCPF(cpf);
						if (cpf != null) {
							context.getFuncionario().setCpf(cpf);
							service = new FuncionarioService();
							context.setFuncionario((Funcionario)service.procurar(context.getFuncionario()));
							if (context.getFuncionario() != null) {
								context.getUpdateFuncionario().getjTextField1().setText(context.getFuncionario().getNome());;
								context.getUpdateFuncionario().getjTextField2().setText(context.getFuncionario().getCpf());
								context.getUpdateFuncionario().getjTextField3().setText(context.getFuncionario().getRg());
								context.getUpdateFuncionario().setVisible(true);
							}
						}
					}
					case 3 -> {
						String cpf = JOptionPane.showInputDialog("Digite o CPF do funcionário");
						cpf = Validar.validarCPF(cpf);
						if (cpf != null) {
							context.getFuncionario().setCpf(cpf);
							service = new FuncionarioService();
							context.setFuncionario((Funcionario)service.procurar(context.getFuncionario()));
							int confirm = JOptionPane.showConfirmDialog(null, "Deseja realmente efetuar a exclusão deste Funcionário ?\n\n" + context.getFuncionario());
							if (confirm == 0) {
								if (service.excluir(context.getFuncionario())) {
									JOptionPane.showMessageDialog(null, "O funcionário selecionado foi excluido!");
									service = new FuncionarioService();
									context.setFuncionario(new Funcionario());
									List<Funcionario> funcionarios = (List<Funcionario>) service.listar(Controll.EXCEPTION);
									if (funcionarios != null) {
										context.getMenu().getjTable1().setModel(new DefaultTableModel(
												new Object[][] {},
												new String[] {"CPF", "RG", "NOME"}
												));
										DefaultTableModel model = (DefaultTableModel) context.getMenu().getjTable1().getModel();
										funcionarios.forEach(funcionario -> {
											model.addRow(new Object[] {funcionario.getCpf(), funcionario.getRg(), funcionario.getNome()});
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
	public static ActionListener cadFuncionario() {
		ActionListener action = (e) -> {
			try {
				String nome = context.getCadFuncionario().getjTextField1().getText();
				String cpf = context.getCadFuncionario().getjTextField2().getText();
				String rg = context.getCadFuncionario().getjTextField3().getText();
				
				if (nome.equals("") || cpf.equals("") || rg.equals("")) {
					throw new NullPointerException("Os campos não podem estar vazios!");
				}
				else {
					cpf = Validar.validarCPF(cpf);
					if (cpf != null) {
						rg = Validar.validarRG(rg);
						if (rg != null) {
							service = new FuncionarioService();
							context.setFuncionario(new Funcionario(cpf, rg, nome));
							int confirm = JOptionPane.showConfirmDialog(null, "Confirme os dados do Funcionário:\n\n" + context.getFuncionario());
							if (confirm == 0) {
								if (service.inserir(new Funcionario(cpf, rg, nome))) {
									JOptionPane.showMessageDialog(null, "Funcionário cadastrado com sucesso!");
									context.getCadFuncionario().setVisible(false);
									service = new FuncionarioService();
									List<Funcionario> funcionarios = (List<Funcionario>) service.listar(Controll.EXCEPTION);
									if (funcionarios != null) {
										context.getMenu().getjTable1().setModel(new DefaultTableModel(
												new Object[][] {},
												new String[] {"CPF", "RG", "NOME"}
												));
										DefaultTableModel model = (DefaultTableModel) context.getMenu().getjTable1().getModel();
										funcionarios.forEach(funcionario -> {
											model.addRow(new Object[] {funcionario.getCpf(), funcionario.getRg(), funcionario.getNome()});
										});
									}
								}
							}
							else {
								context.setFuncionario(new Funcionario());
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
	public static ActionListener updateFuncionario() {
		ActionListener action = (e) -> {
			try {
				String nome = context.getUpdateFuncionario().getjTextField1().getText();
				String cpf = context.getUpdateFuncionario().getjTextField2().getText();
				String rg = context.getUpdateFuncionario().getjTextField3().getText();
				String oldCpf = context.getFuncionario().getCpf();

				if (nome.equals("") || cpf.equals("") || rg.equals("")) {
					throw new NullPointerException("Os campos não podem estar vazios!");
				}
				else {
					cpf = Validar.validarCPF(cpf);
					context.getFuncionario().setCpf(context.getUpdateFuncionario().getjTextField2().getText());
					if (cpf != null) {
						rg = Validar.validarRG(context.getUpdateFuncionario().getjTextField3().getText());
						context.getFuncionario().setRg(context.getUpdateFuncionario().getjTextField3().getText());
						if (rg != null) {
							context.getFuncionario().setNome(context.getUpdateFuncionario().getjTextField1().getText());
							service = new FuncionarioService();
							int confirm = JOptionPane.showConfirmDialog(null, "Confirmar atualização dos dados ?\n\n" + context.getFuncionario());
							if (confirm == 0) {
								if (service.atualizar(context.getFuncionario(), oldCpf)) {
									JOptionPane.showMessageDialog(null, "Funcionário atualizado com sucesso!");
									context.getUpdateFuncionario().setVisible(false);
									List<Funcionario> funcionarios = (List<Funcionario>) service.listar(Controll.EXCEPTION);
									if (funcionarios != null) {
										context.getMenu().getjTable1().setModel(new DefaultTableModel(
												new Object[][] {},
												new String[] {"CPF", "RG", "NOME"}
												));
										DefaultTableModel model = (DefaultTableModel) context.getMenu().getjTable1().getModel();
										funcionarios.forEach(funcionario -> {
											model.addRow(new Object[] {funcionario.getCpf(), funcionario.getRg(), funcionario.getNome()});
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
}
