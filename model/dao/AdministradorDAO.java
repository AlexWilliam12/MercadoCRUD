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
import model.entity.Administrador;

public class AdministradorDAO implements DAO {

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
	public <T> Administrador procurar(T obj) {
		try {
			if (obj instanceof Administrador) {
				
				Administrador administrador = (Administrador) obj;
				
				String query = "SELECT * FROM Administrador WHERE usuario = ? AND senha = ?;";

				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setString(1, administrador.getUsuario());
				pstm.setString(2, administrador.getSenha());
				
				result = pstm.executeQuery();
				
				int count = 0;
				while (result.next()) {
					administrador.setId(result.getInt("id"));
					administrador.setUsuario(result.getString("usuario"));
					administrador.setSenha(result.getString("senha"));
					count++;
				}
				
				return count != 0 ? administrador : null;
			}
			else {
				throw new EntityException("Os parâmetros de entrada não são válidos!");
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return null;
	}
	
	public <T> boolean verificar(T obj) {
		try {
			if (obj instanceof Administrador) {
				
				Administrador administrador = (Administrador) obj;
				
				String query = "SELECT * FROM Administrador WHERE usuario = ?;";

				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setString(1, administrador.getUsuario());
				
				result = pstm.executeQuery();
				
				int count = 0;
				while (result.next()) {
					count++;
				}
				
				return count != 0;
			}
			else {
				throw new EntityException("Os parâmetros de entrada não são válidos!");
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return false;
	}

	@Override
	public List<Administrador> listar() {
		try {
			
			String query = "SELECT * FROM Administrador;";
			
			result = stm.executeQuery(query);
			
			List<Administrador> administradores = new ArrayList<>();
			
			int count = 0;
			while (result.next()) {
				administradores.add(new Administrador(
						result.getInt("id"),
						result.getString("usuario"),
						result.getString("senha")
						));
				count++;
			}
			
			return count != 0 ? administradores : null;
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return null;
	}

	@Override
	public <T> boolean inserir(T obj) {
		try {
			if (obj instanceof Administrador) {
				
				Administrador administrador = (Administrador) obj;
				
				String query = "INSERT INTO Administrador(usuario, senha) VALUES (?, ?);";
				
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setString(1, administrador.getUsuario());
				pstm.setString(2, administrador.getSenha());
				
				pstm.execute();
				
				return true;
			}
			else {
				throw new EntityException("Os parâmetros de entrada não são válidos!");
			}
		}
		catch (SQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(null, "Usuário já está registrado no sistema!");
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return false;
	}

	@Override
	public <T> boolean atualizar(T obj, T id) {
		try {
			if (obj instanceof Administrador) {
				
				Administrador administrador = (Administrador) obj;
				
				String query = "UPDATE Administrador SET usuario = ?, senha = ? WHERE id = ?;";
				
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setString(1, administrador.getUsuario());
				pstm.setString(2, administrador.getSenha());
				pstm.setObject(3, id);
				
				pstm.execute();
				
				return true;
			}
			else {
				throw new EntityException("Os parâmetros de entrada não são válidos!");
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
			if (obj instanceof Administrador) {
				
				Administrador administrador = (Administrador) obj;

				String query = "DELETE FROM Administrador WHERE id = ?;";
				
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setInt(1, administrador.getId());
				
				pstm.execute();
				
				return true;
			}
			else {
				throw new EntityException("Os parâmetros de entrada não são válidos!");
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return false;
	}

}
