package model.service;

import java.util.List;

import exception.ServiceException;
import model.dao.AdministradorDAO;
import model.dao.DAO;
import model.entity.Administrador;

public class AdministradorService implements Service {

	DAO db = new AdministradorDAO();

	@Override
	public <T> Administrador procurar(T obj) throws ServiceException {
		if (obj == null || !(obj instanceof Administrador)) {
			throw new ServiceException("O valor de entrada não é válido!");
		}
		else {
			db.conectar();
			Administrador administrador = (Administrador) db.procurar(obj);
			db.desconectar();
			if (administrador == null) {
				throw new ServiceException("Não foi encontrado nenhum administrador com os parâmetros fornecidos!");
			}
			return administrador;
		}
	}

	@Override
	public <T> boolean verificar(T obj) throws ServiceException {
		if (obj == null || !(obj instanceof Administrador)) {
			throw new ServiceException("O valor de entrada não é válido!");
		}
		else {
			db.conectar();
			boolean state = db.verificar(obj);
			db.desconectar();
			if (state) {
				throw new ServiceException("Nome de usuário já existente, digite outro!");
			}
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<Administrador> listar(Controll controll) throws ServiceException {
		db.conectar();
		List<Administrador> administradores = (List<Administrador>) db.listar();
		db.desconectar();
		if (administradores == null && controll.equals(Controll.EXCEPTION)) {
			throw new ServiceException("Não há administradores cadastrados!");
		}
		return administradores;
	}

	@Override
	public <T> boolean inserir(T obj) throws ServiceException {
		if (obj == null || !(obj instanceof Administrador)) {
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
		if (obj == null || !(obj instanceof Administrador) || id == null) {
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
		if (obj == null || !(obj instanceof Administrador)) {
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

}
