package view;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import Model.Compra;
import Model.Loja;
import Model.Produto;
import Util.Conexao;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Aba_Loja_Controller {
	
	@FXML TableView<Produto> tabCadastrados;
	@FXML TableColumn<Produto, String> colTipo;
	@FXML TableColumn<Produto, String> colProduto;
	@FXML TableColumn<Produto, String> colMarca;
	@FXML TableColumn<Produto, String> colMaterial;
	@FXML TableColumn<Produto, String> colCor;
	@FXML TableColumn<Produto, String> colSexo;
	@FXML TableColumn<Produto, String> colTamanho;
	@FXML TableColumn<Produto, Number> colQuantia;
	@FXML TableColumn<Produto, Number> colValor;
	
	@FXML TableView<Compra> tabVendas;
	@FXML TableColumn<Compra, String> colProd;
	@FXML TableColumn<Compra, String> colCliente;
	@FXML TableColumn<Compra, Number> colVal;
	@FXML TableColumn<Compra, Number> colCod;
	@FXML TableColumn<Compra, Number> colQt;
	@FXML TableColumn<Compra, String> colStatus;
	
	@FXML TextField txtAtualizaEstoque;
	
	ArrayList<Produto> produtos = new ArrayList<>();
	ArrayList<Compra> compras = new ArrayList<>();

	Loja lojaLogada;
	Produto prodSelecionado;
	
	@FXML 
	public void carregaTabs() {
		
		TableViewProdutos();
		iniciaTable();
		
		TableViewVendas();
		iniciaTableVendas();
	}
	
	public Loja lojaLogada(Loja l) {
		
		lojaLogada = l ;
		
		return  lojaLogada;
	}
	
	public void cadastroProduto() {
		
		try {
			Stage s1 = new Stage();
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("Cadastro_Produto.fxml"));
	        AnchorPane root = (AnchorPane)loader.load();
	        Scene scene = new Scene(root);
	        Cadastro_Produto_Controller c = (Cadastro_Produto_Controller)loader.getController();
	        c.lojaLogando(lojaLogada);
	        s1.setScene(scene);
	        s1.setResizable(false);
	        s1.setTitle("Cadastro de produtos");
	        
	        s1.show(); 
	        
		} catch(Exception e) {
			mostraMensagem("Erro não identificado.\n " + e.toString(), AlertType.ERROR);
		}
	}
	
	 private void TableViewProdutos() {
		
		produtos.clear();

			try {
				
				Connection conn = Conexao.getConexao();
				String sqlLista = "select * from produto where produto.loja = ?";
				PreparedStatement ps1 = conn.prepareStatement(sqlLista);
				ps1.setInt(1, lojaLogada.getCodigo());
				
				ResultSet rs = ps1.executeQuery();
				while(rs.next()) {
					
					Produto p = new Produto();
					
					p.setCodigo(rs.getInt("codigo"));
					p.setTipo(rs.getString("tipo"));
					p.setNome(rs.getString("nome"));
					p.setMarca(rs.getString("marca"));
					p.setCor(rs.getString("cor")); 
					p.setMaterial(rs.getString("material"));
					p.setTamanho(rs.getString("tamanho"));
					p.setSexo(rs.getString("sexo"));
					p.setEstoque(rs.getInt("estoque"));
					p.setValor(rs.getDouble("valor"));
					p.setImagem(rs.getString("imagem"));
					p.setLoja(rs.getInt("loja"));
					
					produtos.add(p);
				}
				
				tabCadastrados.setItems(FXCollections.observableArrayList(produtos));
				
			}catch (Exception e) {
				mostraMensagem("Erro não identificado.\n " + e.toString(), AlertType.ERROR);
			}
	 } 
	 
	 @FXML
	 private void TableViewVendas(){
		 
		compras.clear();
		 
		 try {
			Connection conn = Conexao.getConexao();
			String sqlLista = "select * from compra where compra.loja = ?";
			PreparedStatement ps1 = conn.prepareStatement(sqlLista);
			ps1.setInt(1, lojaLogada.getCodigo());
			ResultSet rs = ps1.executeQuery();
				
			while(rs.next()) {
					
				Compra c = new Compra();
					
				c.setCodigo(rs.getInt("codigo"));
				c.getP().setNome(rs.getString("produto"));
				c.getC().setNome(rs.getString("cliente"));
				c.getL().setCodigo(rs.getInt("loja"));
				c.setQt(rs.getInt("quantidade"));
				c.setTotal(rs.getDouble("total"));
				c.setStatus(rs.getString("status"));
				
				compras.add(c);
			}
				
			tabVendas.setItems(FXCollections.observableArrayList(compras));
				
		}catch (Exception e) {
			mostraMensagem("Erro não identificado.\n " + e.toString(), AlertType.ERROR);
		}
	 }
	 
	 public Produto selecionaProduto(){
			
			Produto produto = new Produto();
			
			try {
				
			produto = tabCadastrados.getSelectionModel().getSelectedItem();

			}catch (Exception e) {
				mostraMensagem("Erro não identificado.\n " + e.toString(), AlertType.ERROR);
			}
				
			return produto;
		}
	 
	 public Compra selecionaCompra(){
			
			Compra compra = new Compra();
			
			try {
				
			compra = tabVendas.getSelectionModel().getSelectedItem();

			}catch (Exception e) {
				mostraMensagem("Erro não identificado.\n " + e.toString(), AlertType.ERROR);
			}
				
			return compra;
		}
	 
	 @FXML
	 public void atualizarEstoque() {
		 
		 int novoEstoque = 0;
		 
		 try {
			 
			 Produto produto = selecionaProduto();
							 
				 if(Integer.parseInt(txtAtualizaEstoque.getText()) < 0) {
					mostraMensagem("Impossível inserir números negativos.",AlertType.WARNING);
				 }else {
					
					 if(Integer.parseInt(txtAtualizaEstoque.getText()) == 0) {
						 mostraMensagem("Insira uma quantidade maior que 0 (zero).",AlertType.WARNING);
					 }else {
				 
						 novoEstoque = Integer.parseInt(txtAtualizaEstoque.getText());
		  
						 Connection conn = Conexao.getConexao();
						 String sqlLista = "update produto set estoque=? where produto.codigo=?";
						 PreparedStatement ps1 = conn.prepareStatement(sqlLista);
						 ps1.setInt(1, novoEstoque + produto.getEstoque());
						 ps1.setInt(2, produto.getCodigo());
			 
						 ps1.executeUpdate();
						 mostraMensagem("Estoque do produto ATUALIZADO com sucesso!", AlertType.CONFIRMATION);
						 conn.close();
			 
						 TableViewProdutos();
						 iniciaTable();
					 }
				 }
				 
		 }catch (NullPointerException e) {
			mostraMensagem("Selecione um PRODUTO na LISTA DE PRODUTOS CADASTRADOS para realizar esta operação.",AlertType.WARNING);
		 }catch (NumberFormatException e) {
			 mostraMensagem("Insira apenas números.", AlertType.WARNING);
			 txtAtualizaEstoque.requestFocus();
			 txtAtualizaEstoque.selectAll();
			 
		 }catch (Exception e) {
			 mostraMensagem("Erro não identificado.\n" + e.toString(), AlertType.WARNING);
		}
			
	 }
	 
	 @FXML
	 public void excluiProduto() {
		  
		 try {
			 
			Produto produto = selecionaProduto();
			
			Connection conn = Conexao.getConexao();
			String sqlLista = "delete from produto where produto.codigo = ?";
			PreparedStatement ps = conn.prepareStatement(sqlLista);
			ps.setInt(1,produto.getCodigo());
			
			ps.executeUpdate();
			
			conn.close();
			
			mostraMensagem("Produto DELETADO com sucesso!", AlertType.CONFIRMATION);
			
			 TableViewProdutos();
			 iniciaTable();
			 
		 }catch (NullPointerException e) {
			 mostraMensagem("Selecione um produto para realizar está operação.", AlertType.ERROR);	
			
		}catch (Exception e) {
			mostraMensagem("Erro não identificado.\n " + e.toString(), AlertType.ERROR);
				
		}
	 }
	 
	 @FXML
	 public void excluiCompra() {
		  
		 try {
			 
			Compra compra = selecionaCompra();
			
			if(compra.getStatus().equals("Aguardando Pagamento")) {
				mostraMensagem("O produto está AGUARDANDO PAGAMENTO, o cliente precisa pagar ou cancelar o pedido para que se possa apagar o histórico desta compra!", AlertType.INFORMATION);
			}else {
			
				Connection conn = Conexao.getConexao();
				String sqlLista = "delete from compra where compra.codigo = ?";
				PreparedStatement ps = conn.prepareStatement(sqlLista);
				ps.setInt(1,compra.getCodigo());
			
				ps.executeUpdate();
			
				conn.close();
			
				mostraMensagem("Venda DELETADO com sucesso!", AlertType.CONFIRMATION);
			 
				TableViewVendas();
				iniciaTableVendas();
			}
			
		 }catch (NullPointerException e) {
			 mostraMensagem("Selecione uma VENDA na LISTA DE PRODUTOS VENDIDOS para realizar está operação.", AlertType.ERROR);	
			
			}catch (Exception e) {
				mostraMensagem("Erro não identificado.\n " + e.toString(), AlertType.ERROR);
				e.printStackTrace();
			}
	 }
	 
	 
	 @FXML
	 private void imprimirRelatorio() {
		 
		 try {
			 
			 compras.clear();
				
				Connection conn = Conexao.getConexao();
				String sqlLista = "select * from compra where compra.loja = ?";
				PreparedStatement ps1 = conn.prepareStatement(sqlLista);
				ps1.setInt(1,lojaLogada.getCodigo());
				ResultSet rs = ps1.executeQuery();
					
				while(rs.next()) {
						
					Compra c = new Compra();
						
					c.setCodigo(rs.getInt("codigo"));
					c.getP().setNome(rs.getString("produto"));
					c.getC().setNome(rs.getString("cliente"));
					c.getL().setNome(rs.getString("loja"));
					c.setQt(rs.getInt("quantidade"));
					c.setTotal(rs.getDouble("total"));
					
					compras.add(c);
				}
				
				for(Compra c : compras) {
					
						FileWriter fw = new FileWriter(lojaLogada.getNome()+" - Relatório de vendas.txt",true);
						BufferedWriter bw = new BufferedWriter(fw);
						bw.append("Produto - "+ c.getP().getNome() +","+"Cliente - "+c.getC().getNome()+","+"Valor unitário - "+
								c.getQt()+","+"Total da Compra - R$"+c.getTotal()+","+"Código da venda - " +c.getCodigo()+"\r\n");
						bw.close();
						fw.close();
					}
				
				mostraMensagem("Relatória TXT extraido com sucesso! Verifique em seus arquivos.", AlertType.CONFIRMATION);
				
			}
			catch (Exception e) {
				mostraMensagem("Erro não identificado.\n " + e.toString(), AlertType.ERROR);
			}
	 }

	 @FXML
		public void iniciaTable() {
		 	colTipo.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
			colProduto.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
			colMarca.setCellValueFactory(cellData -> cellData.getValue().marcaProperty());
			colMaterial.setCellValueFactory(cellData -> cellData.getValue().materialProperty());
			colTamanho.setCellValueFactory(cellData -> cellData.getValue().tamanhoProperty());
			colCor.setCellValueFactory(cellData -> cellData.getValue().corProperty());
			colSexo.setCellValueFactory(cellData -> cellData.getValue().sexoProperty());
			colQuantia.setCellValueFactory(cellData -> cellData.getValue().estoqueProperty());
			colValor.setCellValueFactory(cellData -> cellData.getValue().valorProperty());
		}
	 
	 @FXML
		public void iniciaTableVendas() {
			colProd.setCellValueFactory(cellData -> cellData.getValue().getP().nomeProperty());
			colCliente.setCellValueFactory(cellData -> cellData.getValue().getC().nomeProperty());
			colVal.setCellValueFactory(cellData -> cellData.getValue().totalProperty());
			colCod.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
			colQt.setCellValueFactory(cellData -> cellData.getValue().qtProperty());
			colStatus.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
		}
	 
	 private void mostraMensagem (String msg, AlertType tipo) { // recebe uma String por paremetro
			
			Alert a = new Alert (tipo);
			
			a.setHeaderText(null); // modificar mensagem
			a.setContentText(msg);
			a.show();
	 }

}
