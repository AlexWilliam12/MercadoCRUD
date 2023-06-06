package controller;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import context.Context;
import exception.ServiceException;
import exception.ValidarException;
import model.entity.Administrador;
import model.entity.Fornecedor;
import model.entity.Funcionario;
import model.entity.Produto;
import model.service.AdministradorService;
import model.service.Controll;
import model.service.FornecedorService;
import model.service.FuncionarioService;
import model.service.ProdutoService;
import model.service.Service;
import util.Validar;

public class Controller {
	
	protected static Service service;
	protected static Context context;

	public static void run() {
		context = new Context();
		context.runContext(context.getLogin());
	}

	public static ActionListener login() {
		ActionListener action = (e) -> {
			try {
				String user = context.getLogin().getjTextField1().getText();
				char[] password = context.getLogin().getjPasswordField1().getPassword();
				
				StringBuffer bf = new StringBuffer();
				for (char c : password) {
					bf.append(c);
				}
				
				if (user.equals("") || password.length == 0) {
					throw new IllegalArgumentException("Os parâmetros não podem ser vazios!");
				}
				
				service = new AdministradorService();
				Administrador administrador = new Administrador();
				administrador.setUsuario(user);
				administrador.setSenha(bf.toString());
				administrador = (Administrador) service.procurar(administrador);
				
				if (administrador != null) {
					JOptionPane.showMessageDialog(null, "Login efetuado com sucesso!");
					context.setAdmin(administrador);
					context.getLogin().setVisible(false);
					context.getMenu().setVisible(true);
				}
			} 
			catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		};
		return action;
	}
	
	public static ActionListener cadAdmin1() {
		ActionListener action = (e) -> {
			try {
				context.getLogin().setVisible(false);
				context.getCadAdmin().setVisible(true);
			} 
			catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		};
		return action;
	}
	
	public static ActionListener cadAdmin2() {
		ActionListener action = (e) -> {
			try {
				
				String user = context.getCadAdmin().getjTextField1().getText();
				char[] password = context.getCadAdmin().getjPasswordField1().getPassword();
				char[] confirmPassword = context.getCadAdmin().getjPasswordField2().getPassword();
				
				if (user.equals("") || password.length == 0 || confirmPassword.length == 0) {
					throw new IllegalArgumentException("Os parâmetros não podem ser vazios!");
				}
				
				service = new AdministradorService();
				Administrador admin = new Administrador();
				admin.setUsuario(user);
				
				if (!service.verificar(admin)) {
					
					StringBuffer finalPassword = new StringBuffer();
					for (char c : password) {
						finalPassword.append(c);
					}
					
					StringBuffer confirm = new StringBuffer();
					for (char c : confirmPassword) {
						confirm.append(c);
					}
					
					if (!Validar.confirmarSenha(finalPassword.toString(), confirm.toString())) {
						throw new ValidarException("As senhas não conferem!");
					}
					else {
						admin.setSenha(finalPassword.toString());
						if (service.inserir(admin)) {
							JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
							context.getCadAdmin().setVisible(false);
							context.getLogin().setVisible(true);
						}
					}
				}
			} 
			catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		};
		return action;
	}
	
	@SuppressWarnings("unchecked")
	public static ActionListener listar() {
		ActionListener action = (e) -> {
			try {
				Object[] options = {"Funcionários", "Fornecedores", "Produtos"};
				int response = JOptionPane.showOptionDialog(
						null, 
						"Selecione a opção que deseja listar na tabela", 
						"Atualizar tabela", 
						JOptionPane.DEFAULT_OPTION, 
						JOptionPane.QUESTION_MESSAGE, 
						null, 
						options, 
						options[0]);
				
				switch (response) {
					case 0 -> {
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
					case 1 -> {
						service = new FornecedorService();
						List<Fornecedor> fornecedores = (List<Fornecedor>) service.listar(Controll.EXCEPTION);
						if (fornecedores != null) {
							context.getMenu().getjTable1().setModel(new DefaultTableModel(
									new Object[][] {},
									new String[] {"CNPJ", "IE", "EMPRESA", "ENDERECO"}
									));
							DefaultTableModel model = (DefaultTableModel) context.getMenu().getjTable1().getModel();
							fornecedores.forEach(fornecedor -> {
								model.addRow(new Object[] {fornecedor.getCnpj(), fornecedor.getIe(), fornecedor.getNome(), fornecedor.getEndereco()});
							});
						}
					}
					case 2 -> {
						service = new ProdutoService();
						List<Produto> produtos = (List<Produto>) service.listar(Controll.EXCEPTION);
						if (produtos != null) {
							context.getMenu().getjTable1().setModel(new DefaultTableModel(
									new Object[][] {},
									new String[] {"ID", "PRODUTO", "DESCRICAO", "PRECO", "QUANTIDADE"}
									));
							DefaultTableModel model = (DefaultTableModel) context.getMenu().getjTable1().getModel();
							produtos.forEach(produto -> {
								model.addRow(new Object[] {produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getQtdEstoque()});
							});
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
	
	public static ActionListener perfil() {
		ActionListener action = (e) -> {
			try {
				context.getPerfil().getjTextField1().setText(context.getAdmin().getUsuario());
				context.getPerfil().getjPasswordField1().setText(context.getAdmin().getSenha());
				context.getPerfil().getjPasswordField2().setText(context.getAdmin().getSenha());
				context.getPerfil().setVisible(true);
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		};
		return action;
	}
	
	public static ActionListener updatePerfil() {
		ActionListener action = (e) -> {
			try {
				String user = context.getPerfil().getjTextField1().getText();
				char[] password = context.getPerfil().getjPasswordField1().getPassword();
				char[] confirmPassword = context.getPerfil().getjPasswordField2().getPassword();
				
				if (user.equals("") || password.length == 0 || confirmPassword.length == 0) {
					throw new IllegalArgumentException("Os parâmetros não podem ser vazios!");
				}
				
				StringBuffer finalPassword = new StringBuffer();
				for (char c : password) {
					finalPassword.append(c);
				}
				
				StringBuffer confirm = new StringBuffer();
				for (char c : confirmPassword) {
					confirm.append(c);
				}
				
				if (!Validar.confirmarSenha(finalPassword.toString(), confirm.toString())) {
					throw new ValidarException("As senhas não conferem!");
				}
				else {
					service = new AdministradorService();
					int resp = JOptionPane.showConfirmDialog(null, "Deseja realmente atualizar suas informações ?");
					if (resp == 0) {
						Administrador admin = new Administrador(context.getAdmin().getId(), user, finalPassword.toString());
						if (service.atualizar(admin, context.getAdmin().getId())) {
							JOptionPane.showMessageDialog(null, "Configurações de foram atualizadas com sucesso!");
							context.setAdmin(admin);
							context.getPerfil().setVisible(false);
						}
					}
				}
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		};
		return action;
	}
}
