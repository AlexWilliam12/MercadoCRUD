package model.service;

import java.util.List;

import exception.ServiceException;
import model.dao.DAO;
import model.dao.FuncionarioDAO;
import model.entity.Funcionario;

public class FuncionarioService implements Service {
	
	DAO db = new FuncionarioDAO();

	@Override
	public <T> Funcionario procurar(T obj) throws ServiceException {
		if (obj == null || !(obj instanceof Funcionario)) {
			throw new ServiceException("O valor de entrada não é válido!");
		}
		else {
			db.conectar();
			Funcionario funcionario = (Funcionario) db.procurar(obj);
			db.desconectar();
			if (funcionario == null) {
				throw new ServiceException("Não foi encontrado nenhum funcionário com os parâmetros fornecidos!");
			}
			return funcionario;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<Funcionario> listar(Controll controll) throws ServiceException {
		db.conectar();
		List<Funcionario> funcionarios = (List<Funcionario>) db.listar();
		db.desconectar();
		if (funcionarios == null && controll.equals(Controll.EXCEPTION)) {
			throw new ServiceException("Não há funcionarios cadastrados!");
		}
		return funcionarios;
	}

	@Override
	public <T> boolean inserir(T obj) throws ServiceException {
		if (obj == null || !(obj instanceof Funcionario)) {
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
		if (obj == null || !(obj instanceof Funcionario) || id == null) {
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
		if (obj == null || !(obj instanceof Funcionario)) {
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
