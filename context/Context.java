package context;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.entity.Administrador;
import model.entity.Fornecedor;
import model.entity.Funcionario;
import model.entity.Produto;
import view.CadFornecedor;
import view.CadFuncionario;
import view.CadProduto;
import view.Login;
import view.Menu;
import view.MenuAdmin;
import view.Perfil;
import view.UpdateFornecedor;
import view.UpdateFuncionario;
import view.UpdateProduto;

public class Context {
	
	private MenuAdmin cadAdmin = new MenuAdmin();
	private Perfil perfil = new Perfil();
	private Login login = new Login();
	private Menu menu = new Menu();
	private CadFornecedor cadFornecedor = new CadFornecedor();
	private CadProduto cadProduto = new CadProduto();
	private CadFuncionario cadFuncionario = new CadFuncionario();
	private UpdateFuncionario updateFuncionario = new UpdateFuncionario();
	private UpdateFornecedor updateFornecedor = new UpdateFornecedor();
	private UpdateProduto updateProduto = new UpdateProduto();
	
	private Administrador admin = new Administrador();
	private Funcionario funcionario = new Funcionario();
	private Fornecedor fornecedor = new Fornecedor();
	private Produto produto = new Produto();
	
	public Context() {
	}
	
	public void runContext(JFrame frame) {
		try {
			EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                frame.setVisible(true);
	            }
	        });
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public Login getLogin() {
		return login;
	}

	public Menu getMenu() {
		return menu;
	}
	
	public Perfil getPerfil() {
		return perfil;
	}

	public CadFornecedor getCadFornecedor() {
		return cadFornecedor;
	}

	public CadFuncionario getCadFuncionario() {
		return cadFuncionario;
	}
	
	public MenuAdmin getCadAdmin() {
		return cadAdmin;
	}
	
	public CadProduto getCadProduto() {
		return cadProduto;
	}
	
	public UpdateFornecedor getUpdateFornecedor() {
		return updateFornecedor;
	}
	
	public UpdateFuncionario getUpdateFuncionario() {
		return updateFuncionario;
	}
	
	public UpdateProduto getUpdateProduto() {
		return updateProduto;
	}
	
	public Administrador getAdmin() {
		return admin;
	}
	
	public void setAdmin(Administrador admin) {
		this.admin = admin;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
}
