package model.service;

import java.util.List;

import exception.ServiceException;
import model.dao.DAO;
import model.dao.ProdutoDAO;
import model.entity.Produto;

public class ProdutoService implements Service {

	DAO db = new ProdutoDAO();

	@Override
	public <T> Produto procurar(T obj) throws ServiceException {
		if (obj == null || !(obj instanceof Produto)) {
			throw new ServiceException("O valor de entrada não é válido!");
		}
		else {
			db.conectar();
			Produto produto = (Produto) db.procurar(obj);
			db.desconectar();
			if (produto == null) {
				throw new ServiceException("Não foi encontrado nenhum produto com os parâmetros fornecidos!");
			}
			return produto;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<Produto> listar(Controll controll) throws ServiceException {
		db.conectar();
		List<Produto> produtos = (List<Produto>) db.listar();
		db.desconectar();
		if (produtos == null && controll.equals(Controll.EXCEPTION)) {
			throw new ServiceException("Não há produtos cadastrados!");
		}
		return produtos;
	}

	@Override
	public <T> boolean inserir(T obj) throws ServiceException {
		if (obj == null || !(obj instanceof Produto)) {
			throw new ServiceException("O valor de entrada não é válido!");
		}
		else {
			db.conectar();
			boolean state = db.inserir(obj);
			db.desconectar();
			if (!state) {
				throw new ServiceException("Não foi possível finalizar o cadastro!");
			}
			return state;
		}
	}

	@Override
	public <T> boolean atualizar(T obj, T id) throws ServiceException {
		if (obj == null || !(obj instanceof Produto) || id == null) {
			throw new ServiceException("O valor de entrada não é válido!");
		}
		else {
			db.conectar();
			boolean state = db.atualizar(obj, id);
			db.desconectar();
			if (!state) {
				throw new ServiceException("Não foi possível realizar a atualização!");
			}
			return state;
		}
	}

	@Override
	public <T> boolean excluir(T obj) throws ServiceException {
		if (obj == null || !(obj instanceof Produto)) {
			throw new ServiceException("O valor de entrada não é válido!");
		}
		else {
			db.conectar();
			boolean state = db.excluir(obj);
			db.desconectar();
			if (!state) {
				throw new ServiceException("Não foi possível realizar a exclusão!");
			}
			return state;
		}
	}

	@Deprecated
	@Override
	public <T> boolean verificar(T obj) throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}

}
