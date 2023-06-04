package model.entity;

import java.util.Objects;

public class Funcionario {

	private String cpf, rg, nome;
	
	public Funcionario() {
	}

	public Funcionario(
			String cpf, 
			String rg, 
			String nome) 
	{
		this.cpf = cpf;
		this.rg = rg;
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(cpf, rg);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionario other = (Funcionario) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(rg, other.rg);
	}

	@Override
	public String toString() {
		return """
				Nome: %s
				CPF: %s
				RG: %s
				\n""".formatted(nome, cpf, rg);
	}
}
