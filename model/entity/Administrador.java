package model.entity;

import java.util.Objects;

public class Administrador {

	private String usuario, senha;
	private int id;
	
	public Administrador() {
	}

	public Administrador(int id, String usuario, String senha) {
		this.id = id;
		this.usuario = usuario;
		this.senha = senha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, senha, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Administrador other = (Administrador) obj;
		return id == other.id && Objects.equals(senha, other.senha) && Objects.equals(usuario, other.usuario);
	}

	@Override
	public String toString() {
		return """
				ID: %d
				Usuário: %s
				""".formatted(id, usuario);
	}
}
