package controller;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import exception.ServiceException;
import model.entity.Produto;
import model.service.Controll;
import model.service.ProdutoService;

public class ControllerProduto extends Controller {

	@SuppressWarnings("unchecked")
	public static ActionListener configProduto() {
		ActionListener action = (e) -> {
			try {
				Object[] options = {"PROCURAR", "CADASTRAR", "ATUALIZAR", "DELETAR"};
				int response = JOptionPane.showOptionDialog(
						null, 
						"Selecione uma operação", 
						"CRUD", 
						JOptionPane.DEFAULT_OPTION, 
						JOptionPane.QUESTION_MESSAGE, 
						null, 
						options, 
						options[0]);
				
				
				switch (response) {
					case 0 -> {
						var id = JOptionPane.showInputDialog("Digite o ID do produto");
						if (id != null) {
							boolean controll = false;
							for (int i = 0; i < id.length(); i++) {
								if (!Character.isDigit(id.charAt(i))) {
									controll = true;
								}
							}
							if (!controll) {
								context.getProduto().setId(Integer.parseInt(id));
								service = new ProdutoService();
								context.setProduto((Produto)service.procurar(context.getProduto()));
								if (context.getProduto() != null) {
									JOptionPane.showMessageDialog(null, "Produto encontrado:\n\n" + context.getProduto());
								}
							}
							else {
								JOptionPane.showMessageDialog(null, "Apenas números são aceitos!");
							}
						}
					}
					case 1 -> {
						service = new ProdutoService();
						List<Produto> produtos = (List<Produto>)service.listar(Controll.NORMAL);
						if (produtos != null) {
							context.getCadProduto().getjList1().setModel(new AbstractListModel<String>() {
								private static final long serialVersionUID = 1L;
								List<String> items = null;
								{
									items = new ArrayList<>();
									for (Produto produto : produtos) {
										 items.add(produto.getId() + ") " + produto.getNome());
									}
								}
					            public int getSize() { return items.size(); }
					            public String getElementAt(int i) { return items.get(i); }
							});
						}
						context.getCadProduto().setVisible(true);
					}
					case 2 -> {
						var id = JOptionPane.showInputDialog("Digite o ID do produto");
						if (id != null) {
							boolean controll = false;
							for (int i = 0; i < id.length(); i++) {
								if (!Character.isDigit(id.charAt(i))) {
									controll = true;
								}
							}
							if (!controll) {
								context.getProduto().setId(Integer.parseInt(id));
								service = new ProdutoService();
								context.setProduto((Produto)service.procurar(context.getProduto()));
								if (context.getProduto() != null) {
									context.getUpdateProduto().getjTextField1().setText(context.getProduto().getNome());
									context.getUpdateProduto().getjTextField2().setText(String.valueOf(context.getProduto().getPreco()));
									context.getUpdateProduto().getjTextArea1().setText(context.getProduto().getDescricao());
									context.getUpdateProduto().getjSpinner1().setValue(context.getProduto().getQtdEstoque());
									context.getUpdateProduto().setVisible(true);
								}
							}
							else {
								JOptionPane.showMessageDialog(null, "Apenas números são aceitos!");
							}
						}
					}
					case 3 -> {
						var id = JOptionPane.showInputDialog("Digite o ID do produto (Apenas números)");
						if (id != null) {
							boolean controll = false;
							for (int i = 0; i < id.length(); i++) {
								if (!Character.isDigit(id.charAt(i))) {
									controll = true;
								}
							}
							if (!controll) {
								context.getProduto().setId(Integer.parseInt(id));
								service = new ProdutoService();
								context.setProduto((Produto)service.procurar(context.getProduto()));
								int confirm = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir este produto:\n\n" + context.getProduto());
								if (confirm == 0) {
									if (service.excluir(context.getProduto())) {
										JOptionPane.showMessageDialog(null, "O produto selecionado foi excluido!");
										service = new ProdutoService();
										context.setProduto(new Produto());
										List<Produto> produtos = (List<Produto>) service.listar(Controll.EXCEPTION);
										if (produtos != null) {
											context.getMenu().getjTable1().setModel(new DefaultTableModel(
													new Object[][] {},
													new String[] {"ID", "PRODUTO", "DESCRIÇÃO", "PREÇO", "QUANTIDADE"}
													));
											DefaultTableModel model = (DefaultTableModel) context.getMenu().getjTable1().getModel();
											produtos.forEach(produto -> {
												model.addRow(new Object[] {produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getQtdEstoque()});
											});
										}
									}
								}
							}
							else {
								JOptionPane.showMessageDialog(null, "Apenas números são aceitos!");
							}
						}
					}
					default -> {
						break;
					}
				}
			}
			catch (ServiceException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
				if (ex.getMessage().contains("Não há")) {
					context.getMenu().getjTable1().setModel(new DefaultTableModel(
							new Object [][] {
				            	null, null, null, null, null, null, null, null, null, null, null,
				            	null, null, null, null, null, null, null, null, null, null, null
				            },
				            new String [] {
				            		"Default1", "Default2", "Default3", "Default4"
				            }
							));
				}
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		};
		return action;
	}
	
	@SuppressWarnings("unchecked")
	public static ActionListener cadProduto() {
		ActionListener action = (e) -> {
			try {
				String nome = context.getCadProduto().getjTextField1().getText();
				String desc = context.getCadProduto().getjTextArea1().getText();
				var preco = context.getCadProduto().getjTextField2().getText();
				var qtd = context.getCadProduto().getjSpinner1().getValue();
				
				if (nome.equals("") || preco.equals("") || preco.equals("0") || qtd.equals(0) || qtd.equals("")) {
					throw new NullPointerException("Os campos não podem estar vazios!");
				}
				if (preco.contains("..")) {
					throw new IllegalArgumentException("O campo preço possui erros de digitação!");
				}
				else {
					service = new ProdutoService();
					Produto produto = new Produto();
					produto.setNome(nome);
					produto.setDescricao(desc);
					produto.setPreco(Double.parseDouble(preco));
					produto.setQtdEstoque((int)qtd);
					context.setProduto(produto);
					int confirm = JOptionPane.showConfirmDialog(null, "Confirme o Produto a ser cadastrado:\n\n" + context.getProduto());
					if (confirm == 0) {
						if (service.inserir(produto)) {
							JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
							context.getCadFuncionario().setVisible(false);
							service = new ProdutoService();
							List<Produto> produtos = (List<Produto>) service.listar(Controll.EXCEPTION);
							if (produtos != null) {
								context.getMenu().getjTable1().setModel(new DefaultTableModel(
										new Object[][] {},
										new String[] {"ID", "PRODUTO", "DESCRIÇÃO", "PREÇO", "QUANTIDADE"}
										));
								DefaultTableModel model = (DefaultTableModel) context.getMenu().getjTable1().getModel();
								produtos.forEach(p -> {
									model.addRow(new Object[] {p.getId(), p.getNome(), p.getDescricao(), p.getPreco(), p.getQtdEstoque()});
								});
								context.getCadProduto().setVisible(false);
							}
						}
					}
					else {
						context.setProduto(new Produto());
					}
				}
			}
			catch (ServiceException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
				if (ex.getMessage().contains("Não há")) {
					context.getMenu().getjTable1().setModel(new DefaultTableModel(
							new Object [][] {
				            	null, null, null, null, null, null, null, null, null, null, null,
				            	null, null, null, null, null, null, null, null, null, null, null
				            },
				            new String [] {
				            		"Default1", "Default2", "Default3", "Default4"
				            }
							));
				}
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		};
		return action;
	}
	
	@SuppressWarnings("unchecked")
	public static ActionListener updateProduto() {
		ActionListener action = (e) -> {
			try {
				String nome = context.getUpdateProduto().getjTextField1().getText();
				String desc = context.getUpdateProduto().getjTextArea1().getText();
				double preco = Double.parseDouble(context.getUpdateProduto().getjTextField2().getText());
				int qtd = (int) context.getUpdateProduto().getjSpinner1().getValue();
				
				if (nome.equals("") || preco == 0.0 || qtd == 0) {
					throw new NullPointerException("Os campos não podem estar vazios!");
				}
				else {
					service = new ProdutoService();
					Produto produto = new Produto();
					produto.setId(context.getProduto().getId());
					produto.setNome(nome);
					produto.setDescricao(desc);
					produto.setPreco(preco);
					produto.setQtdEstoque(qtd);
					context.setProduto(produto);
					int confirm = JOptionPane.showConfirmDialog(null, "Confirme o Produto a ser atualizado:\n\n" + context.getProduto());
					if (confirm == 0) {
						if (service.atualizar(produto, context.getProduto().getId())) {
							JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");
							context.getUpdateProduto().setVisible(false);
							service = new ProdutoService();
							List<Produto> produtos = (List<Produto>) service.listar(Controll.EXCEPTION);
							if (produtos != null) {
								context.getMenu().getjTable1().setModel(new DefaultTableModel(
										new Object[][] {},
										new String[] {"ID", "PRODUTO", "DESCRIÇÃO", "PREÇO", "QUANTIDADE"}
										));
								DefaultTableModel model = (DefaultTableModel) context.getMenu().getjTable1().getModel();
								produtos.forEach(p -> {
									model.addRow(new Object[] {p.getId(), p.getNome(), p.getDescricao(), p.getPreco(), p.getQtdEstoque()});
								});
							}
						}
					}
				}
			}
			catch (ServiceException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
				if (ex.getMessage().contains("Não há")) {
					context.getMenu().getjTable1().setModel(new DefaultTableModel(
							new Object [][] {
				            	null, null, null, null, null, null, null, null, null, null, null,
				            	null, null, null, null, null, null, null, null, null, null, null
				            },
				            new String [] {
				            		"Default1", "Default2", "Default3", "Default4"
				            }
							));
				}
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		};
		return action;
	}
}
