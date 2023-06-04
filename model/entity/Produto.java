package model.entity;

public class Produto {

	private String nome, descricao;
	private int id, qtdEstoque;
	private double preco;
	
	public Produto() {
	}

	public Produto(String nome, String descricao, int id, int qtdEstoque, double preco) {
		this.nome = nome;
		this.descricao = descricao;
		this.id = id;
		this.qtdEstoque = qtdEstoque;
		this.preco = preco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(int qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	@Override
	public String toString() {
		return """
				ID: %d
				Nome: %s
				Descrição: %s
				Preço: %.2f
				Quantidade no Estoque: %d
				\n""".formatted(id, nome, descricao, preco, qtdEstoque);
	}
}
