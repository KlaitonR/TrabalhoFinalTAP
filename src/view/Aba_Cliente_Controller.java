package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import Model.Cliente;
import Model.Compra;
import Model.Loja;
import Model.Produto;
import Util.Conexao;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Aba_Cliente_Controller {

	@FXML TextField txtPesquisa;
	@FXML TextField txtPesquisaProduto;
	@FXML TextField txtQtdCompra;
	
	@FXML TextArea txtDados;
	
	@FXML TableView<Loja> tabLojas;
	@FXML TableColumn<Loja, String> colLoja;
	@FXML TableColumn<Loja,String> colCidade;
	@FXML TableColumn<Loja,String> colTelefone;
	@FXML TableColumn<Loja, Number> colCodigo;
	
	@FXML TableView<Produto> tabProdutos;
	@FXML TableColumn<Produto, String> colTipo;
	@FXML TableColumn<Produto, String> colNome;
	@FXML TableColumn<Produto, String> colMarca;
	@FXML TableColumn<Produto, String> colMaterial;
	@FXML TableColumn<Produto, String> colSexo;
	@FXML TableColumn<Produto, String> colTamanho;
	@FXML TableColumn<Produto, Number> colValor;
	@FXML TableColumn<Produto, Number> colEstoque;
	@FXML TableColumn<Produto, String> colCor;
	@FXML TableColumn<Produto, Number> colCodLoja;
	
	@FXML ImageView imagem;
	
	private ArrayList<Loja> listaLojas = new ArrayList<>();
	private ArrayList<Produto> produtosDasLojas = new ArrayList<>();
	
	Cliente clienteLogado; 

	@FXML
	public void initialize() {
		
		tableViewLojas();
		inicializaTblLojas();
		
		listarTodosOsProdutos();
		inicializaTblProdutos();
	}
	
	public Cliente clienteLogado(Cliente c) {
		
		clienteLogado = c;
		
		return clienteLogado;
	}
	
	@FXML
	private void tableViewLojas() { 
		listaLojas.clear();
		
		try {
			Connection conn = Conexao.getConexao();
			String sqlLista = "select loja.nome,loja.cidade,loja.telefone,loja.codigo from loja order by loja.nome";
			PreparedStatement ps1 = conn.prepareStatement(sqlLista);
			ResultSet rs = ps1.executeQuery();
			
			while(rs.next()) {
				Loja l = new Loja();
				l.setNome(rs.getString("nome"));
				l.setCidade(rs.getString("cidade"));
				l.setTelefone(rs.getString("telefone"));
				l.setCodigo(rs.getInt("codigo"));
				
				listaLojas.add(l);
			}
			
			tabLojas.setItems(FXCollections.observableArrayList(listaLojas));
			conn.close();
		
		}catch (Exception e) {
			mostraMensagem("Erro não identificado", AlertType.ERROR);
		}
		
	}
	
	@FXML
	private void filtrarLojas() {
		if(txtPesquisa.getText().equals("")) {
			tabLojas.setItems(FXCollections.observableArrayList(listaLojas));
			
		}else {
			ArrayList<Loja> aux = new ArrayList<Loja>();
			for(Loja l : listaLojas) {
				if(l.getNome().startsWith(txtPesquisa.getText())) {
				aux.add(l);
			}
		}
		
		tabLojas.setItems(FXCollections.observableArrayList(aux));
		
		}
	}
	
	@FXML
	private void filtrarProdutos() {
		if(txtPesquisaProduto.getText().equals("")) {
			tabProdutos.setItems(FXCollections.observableArrayList(produtosDasLojas));
			
		}else {
			ArrayList<Produto> aux = new ArrayList<Produto>();
			for(Produto p : produtosDasLojas) {
				if(p.getNome().startsWith(txtPesquisaProduto.getText())) {
				aux.add(p);
			}
		}
		
		tabProdutos.setItems(FXCollections.observableArrayList(aux));
		
		}
	}
	
	
	public Loja selecionaLoja(){
		
		Loja l =  new Loja();
		
		try {
			
			l.setCodigo(tabLojas.getSelectionModel().getSelectedItem().getCodigo());
			
		}catch (NullPointerException e) {
			mostraMensagem("Selecione uma LOJA na LISTA DE LOJAS para realizar está operação.", AlertType.INFORMATION);
		
		}catch (Exception e) {
			mostraMensagem("Erro não identificado.\n " + e.toString(), AlertType.ERROR);
		}
		
		return l;
	}
	
	public Produto selecionaProduto(){
		
		Produto produto = new Produto();
		
		try {
			
		produto = tabProdutos.getSelectionModel().getSelectedItem();
		
		if(produto!=null) {
		
			Connection conn = Conexao.getConexao();
		
			String sqlLista = "select * from produto where produto.nome = ? "; //select * from produto where produto.loja=codigoDaLoja order by produto.nome
			PreparedStatement ps1 = conn.prepareStatement(sqlLista);
			ps1.setString(1, produto.getNome());
			ResultSet rs = ps1.executeQuery();
		
			while(rs.next()) {
			
			Produto p = new Produto();
			p.setCodigo(rs.getInt("codigo"));
			p.setTipo(rs.getString("tipo"));
			p.setNome(rs.getString("nome"));
			p.setMarca(rs.getString("marca"));
			p.setCor(rs.getString("cor"));
			p.setMaterial(rs.getString("material"));
			p.setSexo(rs.getString("sexo"));
			p.setEstoque(rs.getInt("estoque"));
			p.setValor(rs.getDouble("valor"));
			p.setTamanho(rs.getString("tamanho"));
			p.setImagem(rs.getString("imagem"));
			p.setLoja(rs.getInt("loja"));
			produto = p;
			}
		
			conn.close();
		}
		
		}catch (Exception e) {
			mostraMensagem("Erro não identificado.\n " + e.toString(), AlertType.ERROR);
		}
		return produto;
	}

	@FXML
	public void listarTodosOsProdutos() {
		
			 produtosDasLojas.clear();
			 
				try {
					Connection conn = Conexao.getConexao();
					
					String sqlLista = "select * from produto order by produto.nome"; //select * from produto where produto.loja=codigoDaLoja order by produto.nome
					PreparedStatement ps1 = conn.prepareStatement(sqlLista);
					ResultSet rs = ps1.executeQuery();
					
					while(rs.next()) {
						
						Produto p = new Produto();
						p.setTipo(rs.getString("tipo"));
						p.setNome(rs.getString("nome"));
						p.setMarca(rs.getString("marca"));
						p.setCor(rs.getString("cor"));
						p.setMaterial(rs.getString("material"));
						p.setSexo(rs.getString("sexo"));
						p.setEstoque(rs.getInt("estoque"));
						p.setValor(rs.getDouble("valor"));
						p.setTamanho(rs.getString("tamanho"));
						p.setImagem(rs.getString("imagem"));
						p.setLoja(rs.getInt("loja"));
						produtosDasLojas.add(p);
					}
					
					tabProdutos.setItems(FXCollections.observableArrayList(produtosDasLojas));
						
					conn.close();
				
				}catch (Exception e) {
					mostraMensagem("Erro não identificado.\n " + e.toString(), AlertType.ERROR);
				}
	}
	
	public void listarProdutosLojaSelecionada() {
		 produtosDasLojas.clear();
		 
		try {
			
			int codLoja = selecionaLoja().getCodigo() ;
		
			try {
				Connection conn = Conexao.getConexao();
				
				String sqlLista = "select * from produto where produto.loja = ? order by nome"; //select * from produto where produto.loja=codigoDaLoja order by produto.nome
				PreparedStatement ps1 = conn.prepareStatement(sqlLista);
				ps1.setInt(1, codLoja);
				ResultSet rs = ps1.executeQuery();
				
				while(rs.next()) {
					
					Produto p = new Produto();
					p.setTipo(rs.getString("tipo"));
					p.setNome(rs.getString("nome"));
					p.setMarca(rs.getString("marca"));
					p.setCor(rs.getString("cor"));
					p.setMaterial(rs.getString("material"));
					p.setSexo(rs.getString("sexo"));
					p.setEstoque(rs.getInt("estoque"));
					p.setValor(rs.getDouble("valor"));
					p.setTamanho(rs.getString("tamanho"));
					p.setImagem(rs.getString("imagem"));
					p.setLoja(rs.getInt("loja"));
					produtosDasLojas.add(p);
				}
				
					tabProdutos.setItems(FXCollections.observableArrayList(produtosDasLojas));
					
				conn.close();
			
			}catch (Exception e) {
				mostraMensagem("Erro não identificado.\n " + e.toString(), AlertType.ERROR);
			}
			
		}catch (NullPointerException e) {
			mostraMensagem("Selecione um PRODUTO para realizar esta operação.", AlertType.WARNING);
		}
	}
	
	@FXML
	private void comprar() {
		//||                                                                                                                     ||
		//VV------------------------------------------------ INICIA AS VARIAVEIS ------------------------------------------------VV
		try {
		Produto produto = selecionaProduto(); // Pega o produto selecionado
		
		Loja lojaDoProduto = new Loja(); // declara uma loja
		//||                                                                                                                     ||
		//VV------------------------------------------------ VERIFICA LOJA DO PRODUTO -------------------------------------------VV
		
			Connection conn = Conexao.getConexao();
			String sqlLista = "select * from loja where loja.codigo = ?"; // procura pela loja 
			PreparedStatement ps = conn.prepareStatement(sqlLista);
			ps.setInt(1, produto.getLoja()); //produto.getLoja() possui o codigo da loja na qual o produto foi cadastrado
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
					
				Loja l =  new Loja();
				
				l.setCodigo(rs.getInt("codigo"));
				l.setNome(rs.getString("nome"));
				
				lojaDoProduto = l; // Pega a Loja na qual o produto está cadastrado
			}
			
		conn.close();
		//||                                                                                                          ||
		//VV------------------------------------------------ INICIA COMPRA -------------------------------------------VV
		Compra compra = new Compra();
		
		int qt=0;
		
			qt = Integer.parseInt(txtQtdCompra.getText());
			
			if(txtQtdCompra.getText() == null) {
				mostraMensagem("Insira um valor numérico no campo QUANTIDADE para realizar esta operação.",AlertType.WARNING);
			}else {
				if(qt<0) {
					mostraMensagem("Impossível inserir números negativos.",AlertType.WARNING);
				}else {
					if(qt==0) {
						mostraMensagem("Escolha uma quantidade maior que 0 (zero).",AlertType.WARNING);
					}else{
						if(produto.getEstoque() == 0 || qt>produto.getEstoque()) {
							mostraMensagem("Não há estoque o suficiente para realizar a operação.", AlertType.WARNING);
						}else {
		
							compra.setC(clienteLogado);
							compra.setP(produto);
							compra.setL(lojaDoProduto);
							compra.setQt(qt);
							compra.setTotal(compra.realizaComprar(qt));
		
							txtDados.setText("Produto: "+produto.getNome()+"\nQuantia: "+qt+"\nTotal da compra: R$"+compra.getTotal());
		
		//||                                                                                                                 ||
		//VV------------------------------------------------ SALVA OS DADOS NO BD A COMPRA -------------------------------------------VV
		
							Connection con = Conexao.getConexao();
							String sql = "insert into compra (produto,cliente,loja,quantidade,total,status) values(?,?,?,?,?,?)";
							PreparedStatement ps1 = con.prepareStatement(sql);
							ps1.setString(1,produto.getNome());
							ps1.setString(2,clienteLogado.getNome());
							ps1.setInt(3,lojaDoProduto.getCodigo());
							ps1.setInt(4,compra.getQt());
							ps1.setDouble(5,compra.getTotal());
							ps1.setString(6, compra.getStatus());
			
							ps1.executeUpdate();
	
							con.close();
			
							mostraMensagem("Produto COMPRADO com sucesso!", AlertType.CONFIRMATION);
						
							listarTodosOsProdutos();
							inicializaTblProdutos();
						
						}
					}
				}
			}
			
		}catch (NullPointerException e) {
			mostraMensagem("Selecione um produto na LISTA DE PRODUTOS para realizar esta operação.", AlertType.WARNING);
		
		}catch (NumberFormatException e) {
			mostraMensagem("Insira apenas números.", AlertType.WARNING);
			txtQtdCompra.selectAll();
			txtQtdCompra.requestFocus();
		
		}catch (Exception e) {
			mostraMensagem("Erro não identificado.\n " + e.toString(), AlertType.ERROR);
		}
		
	}
	
	@FXML
	public void limparTela() {
		txtQtdCompra.setText("");
		txtDados.setText("");
		txtQtdCompra.setText("");
	}
	
	 @FXML 
	 public void vizualizarProduto() {
	
		 try {
		 Produto produto = selecionaProduto();
		 imagem.setImage(new Image(new FileInputStream(produto.getImagem())));
		 
		 }catch (NullPointerException e) {
			mostraMensagem("Selecione um PRODUTO na LISTA DE PRODUTOS para realizar esta operação.", AlertType.WARNING);
			
		 }catch(FileNotFoundException e) {
			mostraMensagem("Arquivo não encontrado.", AlertType.ERROR);
		 }
	 } 
	 
	 @FXML
	 public void abaPagamento() { 
			
			try {
				Stage s1 = new Stage();
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("Pagamento.fxml"));
		        AnchorPane root = (AnchorPane)loader.load();
		        Scene scene = new Scene(root);
		        PagamentoController c = (PagamentoController)loader.getController();
		        c.clienteLogado(clienteLogado);
		        s1.setScene(scene);
		        s1.setResizable(false);
		        s1.setTitle("Aba de pagamentos");
		        
		        s1.show(); 
		        
			} catch(Exception e) {
				mostraMensagem("Erro não identificado.\n " + e.toString(), AlertType.ERROR);
			}
		}
	
	 @FXML
	 public void inicializaTblLojas() {
			colLoja.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
			colCidade.setCellValueFactory(cellData -> cellData.getValue().cidadeProperty());
			colTelefone.setCellValueFactory(cellData -> cellData.getValue().telefoneProperty());
			colCodigo.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
	 }
	 
	 @FXML
	 public void inicializaTblProdutos() {
		colTipo.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
		colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
		colMarca.setCellValueFactory(cellData -> cellData.getValue().marcaProperty());
		colMaterial.setCellValueFactory(cellData -> cellData.getValue().materialProperty());
		colTamanho.setCellValueFactory(cellData -> cellData.getValue().tamanhoProperty());
		colCor.setCellValueFactory(cellData -> cellData.getValue().corProperty());
		colSexo.setCellValueFactory(cellData -> cellData.getValue().sexoProperty());
		colEstoque.setCellValueFactory(cellData -> cellData.getValue().estoqueProperty());
		colValor.setCellValueFactory(cellData -> cellData.getValue().valorProperty());
		colCodLoja.setCellValueFactory(cellData -> cellData.getValue().lojaProperty());
	 }
	
	 private void mostraMensagem (String msg, AlertType tipo) { // recebe uma String por paremetro
			
			Alert a = new Alert (tipo);
			
			a.setHeaderText(null); // modificar mensagem
			a.setContentText(msg);
			a.show();
		}
	 
	 
		
}
