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
import model.entity.Endereco;
import model.entity.Fornecedor;

public class FornecedorDAO implements DAO {
	
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
	public <T> Fornecedor procurar(T obj) {
		try {
			if (obj instanceof Fornecedor) {
				
				Fornecedor fornecedor = (Fornecedor) obj;
				
				String query = """
						SELECT F.*, E.* FROM (Fornecedor F, Cep C)
						INNER JOIN Endereco E
						ON F.cnpj = C.cnpj
						AND E.cep = C.cep
						AND F.cnpj = ?;""";
				
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setString(1, fornecedor.getCnpj());
				
				result = pstm.executeQuery();
				
				int count = 0;
				while (result.next()) {
					fornecedor.setCnpj(result.getString("cnpj"));
					fornecedor.setIe(result.getString("inscricao_estadual"));
					fornecedor.setNome(result.getString("nome"));
					fornecedor.setEndereco(new Endereco(
							result.getString("cep"),
							result.getString("logradouro"),
							result.getString("bairro"),
							result.getString("complemento"),
							result.getString("cidade"),
							result.getString("uf"),
							result.getString("pais"),
							result.getInt("numero")
							));
					count++;
				}
				
				return count != 0 ? fornecedor : null;
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

	@Override
	public List<Fornecedor> listar() {
		try {
			
			String query = """
					SELECT F.*, E.* FROM (Fornecedor F, Cep C)
					INNER JOIN Endereco E
					ON F.cnpj = C.cnpj
					AND E.cep = C.cep;""";
			
			result = stm.executeQuery(query);
			
			List<Fornecedor> fornecedors = new ArrayList<>();
			
			int count = 0;
			while (result.next()) {
				fornecedors.add(new Fornecedor(
						result.getString("cnpj"),
						result.getString("inscricao_estadual"),
						result.getString("nome"),
						new Endereco(
								result.getString("cep"),
								result.getString("logradouro"),
								result.getString("bairro"),
								result.getString("complemento"),
								result.getString("cidade"),
								result.getString("uf"),
								result.getString("pais"),
								result.getInt("numero")
								)));
				count++;
			}
			
			return count != 0 ? fornecedors : null;
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return null;
	}

	@Override
	public <T> boolean inserir(T obj) {
		try {
			if (obj instanceof Fornecedor) {
				
				Fornecedor fornecedor = (Fornecedor) obj;
				
				String query = "INSERT INTO Fornecedor VALUES (?, ?, ?);";
				
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setString(1, fornecedor.getCnpj());
				pstm.setString(2, fornecedor.getIe());
				pstm.setString(3, fornecedor.getNome());
				
				pstm.execute();
				
				query = """
						SELECT count(C.cnpj) as Count 
						FROM Cep C, Fornecedor F, Endereco E 
						WHERE C.cep = E.cep
						AND F.cnpj = ?
						AND C.cnpj = ?;""";
				
				pstm = con.prepareStatement(query);
				pstm.setString(1, fornecedor.getCnpj());
				pstm.setString(2, fornecedor.getCnpj());
				
				result = pstm.executeQuery();
				
				int count = 0;
				while (result.next()) {
					count = result.getInt("Count");
				}

				if (count == 0) {
					Endereco endereco = fornecedor.getEndereco();
					query = "INSERT INTO Endereco VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
					pstm = con.prepareStatement(query);
					pstm.setString(1, endereco.getCep());
					pstm.setString(2, endereco.getLogradouro());
					pstm.setString(3, endereco.getBairro());
					pstm.setString(4, endereco.getComplemento());
					pstm.setString(5, endereco.getCidade());
					pstm.setString(6, endereco.getUf());
					pstm.setString(7, endereco.getPais());
					pstm.setInt(8, endereco.getNumero());
					pstm.execute();
				}
				
				query = "INSERT INTO Cep VALUES (?, ?);";
				
				pstm = con.prepareStatement(query);
				pstm.setString(1, fornecedor.getCnpj());
				pstm.setString(2, fornecedor.getEndereco().getCep());
				
				pstm.execute();
				
				return true;
			}
			else {
				throw new EntityException("Os parâmetros de entrada não são válidos!");
			}
		}
		catch (SQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(null, "Fornecedor já está registrado no sistema!");
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return false;
	}

	@Override
	public <T> boolean atualizar(T obj, T id) {
		try {
			if (obj instanceof Fornecedor) {
				
				Fornecedor fornecedor = (Fornecedor) obj;
				
				String query = "UPDATE Fornecedor SET cnpj = ?, inscricao_estadual = ?, nome = ? WHERE cnpj = ?;";
				
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setString(1, fornecedor.getCnpj());
				pstm.setString(2, fornecedor.getIe());
				pstm.setString(3, fornecedor.getNome());
				pstm.setObject(4, id);
				
				pstm.execute();
				
				query = """
						SELECT count(C.cnpj) as Count 
						FROM Cep C, Fornecedor F, Endereco E 
						WHERE C.cep = E.cep
						AND F.cnpj = ?
						AND C.cnpj = ?;""";
										
				pstm = con.prepareStatement(query);
				pstm.setString(1, fornecedor.getCnpj());
				pstm.setString(2, fornecedor.getCnpj());
				
				result = pstm.executeQuery();
				
				int count = 0;
				while (result.next()) {
					count = result.getInt("Count");
				}
				
				if (count == 1) {
					Endereco endereco = fornecedor.getEndereco();
					query = "INSERT INTO Endereco VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
					pstm = con.prepareStatement(query);
					pstm.setString(1, endereco.getCep());
					pstm.setString(2, endereco.getLogradouro());
					pstm.setString(3, endereco.getBairro());
					pstm.setString(4, endereco.getComplemento());
					pstm.setString(5, endereco.getCidade());
					pstm.setString(6, endereco.getUf());
					pstm.setString(7, endereco.getPais());
					pstm.setInt(8, endereco.getNumero());
					pstm.execute();
				}
				
				query = "UPDATE Cep SET cnpj = ?, cep = ? WHERE cnpj = ?;";
				
				pstm = con.prepareStatement(query);
				pstm.setString(1, fornecedor.getCnpj());
				pstm.setString(2, fornecedor.getEndereco().getCep());
				pstm.setObject(3, id);
				
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
			if (obj instanceof Fornecedor) {
				
				Fornecedor fornecedor = (Fornecedor) obj;
				
				String query = "DELETE FROM Cep WHERE cnpj = ?;";
				
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setString(1, fornecedor.getCnpj());
				
				pstm.execute();
				
				query = "DELETE FROM Fornecedor WHERE cnpj = ?;";
				
				pstm = con.prepareStatement(query);
				pstm.setString(1, fornecedor.getCnpj());
				
				pstm.execute();
				
				query = """
						SELECT count(C.cnpj) as Count 
						FROM Cep C, Fornecedor F, Endereco E 
						WHERE C.cep = E.cep
						AND F.cnpj = ?
						AND C.cnpj = ?;""";
				
				pstm = con.prepareStatement(query);
				pstm.setString(1, fornecedor.getEndereco().getCep());
				pstm.setString(2, fornecedor.getEndereco().getCep());
				
				result = pstm.executeQuery();
				
				int count = 0;
				while (result.next()) {
					count = result.getInt("Count");
				}
				
				if (count == 0) {
					query = "DELETE FROM Endereco WHERE cep = ?;";
					pstm = con.prepareStatement(query);
					pstm.setString(1, fornecedor.getEndereco().getCep());
					pstm.execute();
				}
				
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