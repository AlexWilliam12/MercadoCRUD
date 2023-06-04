package model.service;

import java.util.List;

import exception.ServiceException;
import model.dao.DAO;
import model.dao.FornecedorDAO;
import model.entity.Fornecedor;

public class FornecedorService implements Service {

	DAO db = new FornecedorDAO();

	@Override
	public <T> Fornecedor procurar(T obj) throws ServiceException {
		if (obj == null || !(obj instanceof Fornecedor)) {
			throw new ServiceException("O valor de entrada não é válido!");
		}
		else {
			db.conectar();
			Fornecedor fornecedor = (Fornecedor) db.procurar(obj);
			db.desconectar();
			if (fornecedor == null) {
				throw new ServiceException("Não foi encontrado nenhum fornecedor com os parâmetros fornecidos!");
			}
			return fornecedor;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<Fornecedor> listar(Controll controll) throws ServiceException {
		db.conectar();
		List<Fornecedor> fornecedores = (List<Fornecedor>) db.listar();
		db.desconectar();
		if (fornecedores == null && controll.equals(Controll.EXCEPTION)) {
			throw new ServiceException("Não há fornecedores cadastrados!");
		}
		return fornecedores;
	}

	@Override
	public <T> boolean inserir(T obj) throws ServiceException {
		if (obj == null || !(obj instanceof Fornecedor)) {
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
		if (obj == null || !(obj instanceof Fornecedor) || id == null) {
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
		if (obj == null || !(obj instanceof Fornecedor)) {
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
