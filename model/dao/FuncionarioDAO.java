package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import env_variables.BDConnection;
import exception.EntityException;
import model.entity.Funcionario;

public class FuncionarioDAO implements DAO {

	private Connection con;
	private Statement stm;
	private ResultSet result;

	@Override
	public boolean conectar() {
		try {
			Class.forName(BDConnection.getDriver());
			con = DriverManager.getConnection(BDConnection.getServer(), BDConnection.getUser(), BDConnection.getPassword());
			stm = con.createStatement();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return conectado();
	}

	@Override
	public boolean desconectar() {
		try {
			con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return conectado();
	}

	@Override
	public boolean conectado() {
		return con != null;
	}

	@Override
	public <T> Funcionario procurar(T obj) {
		try {
			if (obj instanceof Funcionario) {
				
				Funcionario funcionario = (Funcionario) obj;
				
				String query = "SELECT * FROM Funcionario WHERE cpf = ?;";
				
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setString(1, funcionario.getCpf());
				
				result = pstm.executeQuery();
				
				int count = 0;
				while (result.next()) {
					funcionario.setCpf(result.getString("cpf"));
					funcionario.setRg(result.getString("rg"));
					funcionario.setNome(result.getString("nome"));
					count++;
				}
				
				return count != 0 ? funcionario : null;
			}
			else {
				throw new EntityException("Os parâmetros de entrada não são validos!");
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return null;
	}

	@Override
	public List<Funcionario> listar() {
		try {
			
			String query = "SELECT * FROM Funcionario;";
			
			result = stm.executeQuery(query);
			
			List<Funcionario> funcionarios = new ArrayList<>();
			
			int count = 0;
			while (result.next()) {
				funcionarios.add(new Funcionario(
						result.getString("cpf"),
						result.getString("rg"),
						result.getString("nome")));
				count++;
			}
			
			return count != 0 ? funcionarios : null;
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return null;
	}

	@Override
	public <T> boolean inserir(T obj) {
		try {
			if (obj instanceof Funcionario) {
				
				Funcionario funcionario = (Funcionario) obj;
				
				String query = "INSERT INTO Funcionario VALUES (?, ?, ?);";
				
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setString(1, funcionario.getCpf());
				pstm.setString(2, funcionario.getRg());
				pstm.setString(3, funcionario.getNome());
				
				pstm.execute();
				
				return true;
			}
			else {
				throw new EntityException("Os parâmetros de entrada não são validos!");
			}
		}
		catch (SQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(null, "Funcionário já está registrado no sistema!");
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return false;
	}

	@Override
	public <T> boolean atualizar(T obj, T id) {
		try {
			if (obj instanceof Funcionario) {
				
				Funcionario funcionario = (Funcionario) obj;
				
				String query = "UPDATE Funcionario SET cpf = ?, rg = ?, nome = ? WHERE cpf = ?;";
				
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setString(1, funcionario.getCpf());
				pstm.setString(2, funcionario.getRg());
				pstm.setString(3, funcionario.getNome());
				pstm.setObject(4, id);
				
				pstm.execute();
				
				return true;
			}
			else {
				throw new EntityException("Os parâmetros de entrada não são validos!");
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return false;
	}

	@Override
	public <T> boolean excluir(T obj) {
		try {
			if (obj instanceof Funcionario) {
				
				Funcionario funcionario = (Funcionario) obj;
				
				String query = "DELETE FROM Funcionario WHERE cpf = ?;";
				
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setString(1, funcionario.getCpf());
				
				pstm.execute();
				
				return true;
			}
			else {
				throw new EntityException("Os parâmetros de entrada não são validos!");
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return false;
	}

	@Deprecated
	@Override
	public <T> boolean verificar(T obj) {
		// TODO Auto-generated method stub
		return false;
	}
}
