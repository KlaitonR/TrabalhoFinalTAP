package view;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Model.Loja;
import Model.Produto;
import Util.Conexao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ColorPicker;
import javafx.stage.FileChooser;

public class Cadastro_Produto_Controller {
	
	@FXML TextField txtNome;
	@FXML TextField txtQuantia;
	@FXML TextField txtMaterial;
	@FXML TextField txtValor;
	@FXML TextField txtImagem;
	
	@FXML ComboBox<String> cbTamanho;
	@FXML ComboBox<String> txtMarca;
	@FXML ComboBox<String> txtTipo;
	
	@FXML RadioButton rdMas;
	@FXML RadioButton rdFem;
	
	@FXML  ColorPicker cpCor;
	Loja lojaLogada;
	
	ArrayList<Produto> confereProduto = new ArrayList<>();
	
	@FXML
	public void initialize() {
		inicializaComboMarca();
		inicializaComboTamanho();
		inicializaComboTipo();
	}
	
	public Loja lojaLogando(Loja l) {
		
		lojaLogada = l ;
		
		return  lojaLogada; 
	}

	@FXML
	private void cadastrarProduto() {
		
		boolean campoNulo = false;
		boolean existe = false;
		boolean confereTamanho = false;
		
		try {
			
			if(txtNome.getText().equals("")||txtMaterial.getText().equals("")||txtQuantia.getText().equals("")||txtValor.getText().equals("")||txtImagem.getText().equals(""))
				campoNulo = true;
			
			if(txtTipo.getSelectionModel().getSelectedItem().equals("Tênis")) {
				String tamanho = cbTamanho.getSelectionModel().getSelectedItem();
				if(tamanho.equals("PP")||tamanho.equals("P")||tamanho.equals("M")||tamanho.equals("G")||tamanho.equals("GG")){
					confereTamanho = true;
				}
			}
			
			Connection con = Conexao.getConexao();
			String sqlLista = "select * from produto"; // where codigo = codigoDaLoja;
			PreparedStatement ps1 = con.prepareStatement(sqlLista);
			ResultSet rs = ps1.executeQuery();
				
			while(rs.next()) {
					
				Produto confere = new Produto();
				confere.setTipo(rs.getString("tipo"));
				confere.setNome(rs.getString("nome"));
				confere.setMarca(rs.getString("marca"));
				confere.setCor(rs.getString("cor"));
				confere.setMaterial(rs.getString("material"));
				confere.setTamanho(rs.getString("tamanho"));
				confere.setSexo(rs.getString("sexo"));
				confereProduto.add(confere);
			}
			con.close();
			
			for(Produto produto : confereProduto) {
				if(produto.getTipo().equalsIgnoreCase(txtTipo.getSelectionModel().getSelectedItem())) {
					if(produto.getNome().equalsIgnoreCase(txtNome.getText())) {
						if(produto.getMarca().equalsIgnoreCase(txtMarca.getSelectionModel().getSelectedItem())) {
							if(produto.getCor().equalsIgnoreCase(Integer.toHexString(cpCor.getValue().hashCode()))) {
								if(produto.getMaterial().equalsIgnoreCase(txtMaterial.getText())) {
									if(produto.getTamanho().equalsIgnoreCase(cbTamanho.getSelectionModel().getSelectedItem())) {
										if(produto.getSexo().equalsIgnoreCase(rdMas.isSelected()?"M" : "F")) {
											existe = true;
										}
									}
								}
							}
						}
					}
				}
			}
		
			if(existe == false) {
				if(confereTamanho == false) {
					if(campoNulo == false) {
					
						Produto p = new Produto();
		
						p.setTipo(txtTipo.getSelectionModel().getSelectedItem());
						p.setNome(txtNome.getText());
						p.setMarca(txtMarca.getSelectionModel().getSelectedItem());
						p.setCor(Integer.toHexString(cpCor.getValue().hashCode())); //Integer.toHexString(cpCor.getValue().hashCode())
						p.setMaterial(txtMaterial.getText());
						p.setTamanho(cbTamanho.getSelectionModel().getSelectedItem());
						p.setSexo(rdMas.isSelected()?"M" : "F");
						p.setEstoque(Integer.parseInt(txtQuantia.getText()));
						p.setValor(Double.parseDouble(txtValor.getText()));
						p.setImagem(txtImagem.getText());
						p.setCodigo(lojaLogada.getCodigo()); // pega o código da loja que está acessando
			
						Connection conn = Conexao.getConexao();
						String sql = "insert into produto (tipo,nome,marca,cor,material,sexo,estoque,valor,tamanho,imagem,loja) values(?,?,?,?,?,?,?,?,?,?,?)";
						PreparedStatement ps = conn.prepareStatement(sql);
						ps.setString(1,p.getTipo());
						ps.setString(2,p.getNome());
						ps.setString(3,p.getMarca());
						ps.setString(4,p.getCor());
						ps.setString(5,p.getMaterial());
						ps.setString(6,p.getSexo());
						ps.setInt(7,p.getEstoque());
						ps.setDouble(8,p.getValor());
						ps.setString(9,p.getTamanho());
						ps.setString(10,p.getImagem());
						ps.setInt(11, p.getCodigo());
					
						ps.executeUpdate();
		
						conn.close();
	
						mostraMensagem("Cadastro concluido com sucesso.",AlertType.CONFIRMATION);
					}else {
						mostraMensagem("Você deixou campos em branco, por favor, verifique todos os campos, é necessário preenchar todos.", AlertType.WARNING);
					}
				}else {
					mostraMensagem("O TAMANHO escolhido não condiz com o TIPO DE PRODUTO (TÊNIS), por favor, selecione outro tamanho.", AlertType.INFORMATION);
				}
			}else {
				mostraMensagem("Este produto já está cadastrado.", AlertType.INFORMATION);
			}
			
		} catch (NumberFormatException e) {
			mostraMensagem("Erro de converção, dado invalido.", AlertType.ERROR);
		}catch (Exception e) {
			mostraMensagem("Erro não identificado \n" + e.toString(), AlertType.WARNING);
		}
	}

	//Escolher imagem do produto
	@FXML 
	private void escolheImagem() {
		FileChooser fc = new FileChooser();
		File selecao = fc.showOpenDialog(null);
		if(selecao != null) {
			txtImagem.setText(selecao.getAbsolutePath());
		}
	}
	
	@FXML
	private void limpaTela() {
		txtNome.setText("");
		txtMaterial.setText("");
		txtQuantia.setText("");
		txtValor.setText("");
		txtImagem.setText("");
	}
			
	private void inicializaComboMarca() {

		txtMarca.getItems().add("Adidas");
		txtMarca.getItems().add("Reserva");
		txtMarca.getItems().add("Ralph Lauren");
		txtMarca.getItems().add("Hugo Boss");
		txtMarca.getItems().add("Lacoste");
		txtMarca.getItems().add("Calvin Klein");
		txtMarca.getItems().add("Tommy Hilfiger");
		txtMarca.getItems().add("Gucci");
		txtMarca.getItems().add("Mormai");
		txtMarca.getItems().add("Polo");
		txtMarca.getItems().add("Ecko United.");
		txtMarca.getItems().add("Dolce&Gabbana");
		txtMarca.getItems().add("BillaBong");
		txtMarca.getItems().add("Colcci");
		txtMarca.getItems().add("Diesel");
		txtMarca.getItems().add("Hollister");
		txtMarca.getItems().add("Oakley");
		txtMarca.getItems().add("Nike");
		txtMarca.getItems().add("Puma");
		txtMarca.getItems().add("Hurley");
		txtMarca.getItems().add("Element");
		txtMarca.getItems().add("RipCurl");
		txtMarca.getItems().add("Levi's");
		
		txtMarca.getSelectionModel().select(0);	
	}
	
	private void inicializaComboTipo() {

		txtTipo.getItems().add("Camisa");
		txtTipo.getItems().add("Calça");
		txtTipo.getItems().add("Moletom");
		txtTipo.getItems().add("Tênis");
		txtTipo.getItems().add("Shortes");
		
		txtTipo.getSelectionModel().select(0);	
	}
	
	private void inicializaComboTamanho() {

		cbTamanho.getItems().add("PP");
		cbTamanho.getItems().add("P");
		cbTamanho.getItems().add("M");
		cbTamanho.getItems().add("G");
		cbTamanho.getItems().add("GG");
		cbTamanho.getItems().add("35");
		cbTamanho.getItems().add("36");
		cbTamanho.getItems().add("37");
		cbTamanho.getItems().add("38");
		cbTamanho.getItems().add("39");
		cbTamanho.getItems().add("40");
		cbTamanho.getItems().add("41");
		cbTamanho.getItems().add("42");
		cbTamanho.getItems().add("43");
		cbTamanho.getItems().add("44");
		cbTamanho.getItems().add("45");
		
		cbTamanho.getSelectionModel().select(0);	
	}
	
	private void mostraMensagem (String msg, AlertType tipo) { // recebe uma String por paremetro
		
		Alert a = new Alert (tipo);
		
		a.setHeaderText(null); // modificar mensagem
		a.setContentText(msg);
		a.show();
	}
}