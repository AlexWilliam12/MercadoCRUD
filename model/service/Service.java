package model.service;

import exception.ServiceException;

public interface Service {

	public <T> Object procurar(T obj) throws ServiceException;
	
	public <T> boolean verificar(T obj) throws ServiceException;

	public <T> Object listar(Controll controll) throws ServiceException;

	public <T> boolean inserir(T obj) throws ServiceException;

	public <T> boolean atualizar(T obj, T id) throws ServiceException;

	public <T> boolean excluir(T obj) throws ServiceException;
	
}
