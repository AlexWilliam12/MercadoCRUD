package model.dao;

public interface DAO {
	
	public boolean conectar();
	
	public boolean desconectar();
	
	public boolean conectado();
	
	public <T> Object procurar(T obj);
	
	public <T> boolean verificar(T obj);
	
	public <T> Object listar();
	
	public <T> boolean inserir(T obj);
	
	public <T> boolean atualizar(T obj, T id);
	
	public <T> boolean excluir(T obj);
	
}
