package model.entity;

import java.util.Objects;

public class Fornecedor {

	private String cnpj, ie, nome;
	private Endereco endereco = new Endereco();

	public Fornecedor() {
	}

	public Fornecedor(String cnpj, String ie, String nome, Endereco endereco) {
		this.cnpj = cnpj;
		this.ie = ie;
		this.nome = nome;
		this.endereco = endereco;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}
	
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(cnpj);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fornecedor other = (Fornecedor) obj;
		return Objects.equals(cnpj, other.cnpj);
	}

	@Override
	public String toString() {
		return """
				Nome: %s
				CNPJ: %s
				Inscrição Estadual: %s
				Endereço: %s
				\n""".formatted(nome, cnpj, ie, endereco.toString());
	}
}
