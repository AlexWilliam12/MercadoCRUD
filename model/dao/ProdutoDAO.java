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
import model.entity.Produto;

public class ProdutoDAO implements DAO {

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
	public <T> Produto procurar(T obj) {
		try {
			if (obj instanceof Produto) {
				
				Produto produto = (Produto) obj;
				
				String query = """
						SELECT P.*, E.quantidade
						FROM Produto P
						INNER JOIN Estoque E
						ON P.id = E.id
						AND P.id = ?;
						""";
				
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setInt(1, produto.getId());
				
				result = pstm.executeQuery();
				
				int count = 0;
				while (result.next()) {
					produto.setId(result.getInt("id"));
					produto.setNome(result.getString("nome"));
					produto.setDescricao(result.getString("descricao"));
					produto.setPreco(result.getDouble("preco"));
					produto.setQtdEstoque(result.getInt("quantidade"));
					count++;
				}
				
				return count != 0 ? produto : null;
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
	public List<Produto> listar() {
		try {
			
			String query = """
					SELECT P.*, E.quantidade
					FROM Produto P
					INNER JOIN Estoque E
					ON P.id = E.id;
					""";
			
			result = stm.executeQuery(query);
			
			List<Produto> produtos = new ArrayList<>();
			
			int count = 0;
			while (result.next()) {
				produtos.add(new Produto(
						result.getString("nome"),
						result.getString("descricao"),
						result.getInt("id"),
						result.getInt("quantidade"),
						result.getDouble("preco")
						));
				count++;
			}
			
			return count != 0 ? produtos : null;
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return null;
	}

	@Override
	public <T> boolean inserir(T obj) {
		try {
			if (obj instanceof Produto) {
				
				Produto produto = (Produto) obj;
				
				String query = "INSERT INTO Produto VALUES (?, ?, ?, ?);";
				
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setInt(1, produto.getId());
				pstm.setString(2, produto.getNome());
				pstm.setString(3, produto.getDescricao());
				pstm.setDouble(4, produto.getPreco());
				
				pstm.execute();
				
				query = "INSERT INTO Estoque VALUES (?, ?);";
				
				pstm = con.prepareStatement(query);
				pstm.setInt(1, produto.getId());
				pstm.setInt(2, produto.getQtdEstoque());
				
				pstm.execute();
				
				return true;
			}
			else {
				throw new EntityException("Os parâmetros de entrada não são validos!");
			}
		}
		catch (SQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(null, "Produto já está registrado no sistema!");
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return false;
	}

	@Override
	public <T> boolean atualizar(T obj, T id) {
		try {
			if (obj instanceof Produto) {
				
				Produto produto = (Produto) obj;
				
				String query = "UPDATE Produto SET id = ?, nome = ?, descricao = ?, preco = ? WHERE id = ?;";
				
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setInt(1, produto.getId());
				pstm.setString(2, produto.getNome());
				pstm.setString(3, produto.getDescricao());
				pstm.setDouble(4, produto.getPreco());
				pstm.setObject(5, id);
				
				pstm.execute();
				
				query = "UPDATE Estoque SET id = ?, quantidade = ? WHERE id = ?;";
				
				pstm = con.prepareStatement(query);
				pstm.setInt(1, produto.getId());
				pstm.setInt(2, produto.getQtdEstoque());
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
			if (obj instanceof Produto) {
				
				Produto produto = (Produto) obj;
				
				String query = "DELETE FROM Estoque WHERE id = ?;";
				
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setInt(1, produto.getId());
				
				pstm.execute();
				
				query = "DELETE FROM Produto WHERE id = ?;";
				
				pstm = con.prepareStatement(query);
				pstm.setInt(1, produto.getId());
				
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
