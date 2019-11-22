package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import Model.Cliente;
import Model.Loja;
import Util.Conexao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;

public class PrincipalController {
	
@FXML TabPane tabPane;
@FXML TextField txtULoja;
@FXML TextField txtSLoja;
@FXML TextField txtUCliente;
@FXML TextField txtSCliente;

ArrayList<Cliente> clientes = new ArrayList<>();
ArrayList<Loja> lojas = new ArrayList<>();

  Cliente clienteAcessando =  new Cliente();
  Loja lojaAcessando = new Loja();
  
	@FXML
	public void abreTelaCadastroLoja() {
		abreTab("Cadastro da loja","Cadastro_Loja.fxml");
	}

	@FXML
	public void abreTelaCadatroCliente() {
		abreTab("Cadastro do cliente","Cadastro_Cliente.fxml");
	}
	
	@FXML
	public void abreTelaUsuarioLoja() {
		abreTab("Sua Loja","Aba_Loja.fxml");
	}
	
	@FXML
	public void abreTelaUsuarioCliente() {
		abreTab("Sua Conta","Aba_Cliente.fxml");
	}
	
	public void loginLoja() {
		
		lojas.clear();
		boolean existe = false;
		
		try {
			Connection conn = Conexao.getConexao();
			String sqlLista = "select * from loja"; // where codigo = codigoDaLoja;
			PreparedStatement ps1 = conn.prepareStatement(sqlLista);
			ResultSet rs = ps1.executeQuery();
			
			while(rs.next()) {
				Loja l = new Loja();
				
				l.setCodigo(rs.getInt("codigo"));
				l.setNome(rs.getString("nome"));
				l.setCnpj(rs.getString("cnpj"));
				l.setCidade(rs.getString("cidade"));
				l.setBairro(rs.getString("bairro"));
				l.setTelefone(rs.getString("telefone"));
				l.getC().setUsuario(rs.getString("usuario"));
				l.getC().setSenha(rs.getString("senha"));
				lojas.add(l);
			}
				
			conn.close();
			
			for(Loja l:lojas) {
				if(l.getC().getUsuario().equalsIgnoreCase(txtULoja.getText()) && l.getC().getSenha().equalsIgnoreCase(txtSLoja.getText())) { //Conferir se a conta da Loja existe
					lojaAcessando = l;
					existe = true; // existe uma Loja com esses dado
					abreTelaUsuarioLoja(); // abre a aba
			}
		}
			
		}catch (Exception e) {
		mostraMensagem("Erro não encontrado.\n "+ e.toString(), AlertType.ERROR);
	}
		
	if(existe == false) // Caso não exista nenhuma Loja com esses dados
		mostraMensagem("Nome de usuário ou senha incorretos.",AlertType.ERROR);
	
	}
	
	public void loginCliente() {
		
		clientes.clear();
		boolean existe = false;
		
		try {
			Connection conn = Conexao.getConexao();
			String sqlLista = "select * from cliente"; // where codigo = codigoDaLoja;
			PreparedStatement ps1 = conn.prepareStatement(sqlLista);
			ResultSet rs = ps1.executeQuery();
			
			while(rs.next()) {
				Cliente c = new Cliente();
				c.setCodigo(rs.getInt("codigo"));
				c.setNome(rs.getString("nome"));
				c.setCpf(rs.getString("cpf"));
				c.setCidade(rs.getString("cidade"));
				c.setBairro(rs.getString("bairro"));
				c.setTelefone(rs.getString("telefone"));
				c.getC().setNomeUsuario(rs.getString("usuario"));
				c.getC().setSenha(rs.getString("senha"));
				clientes.add(c);
			}
			
			conn.close();
			
			for(Cliente c:clientes) {
				if(c.getC().getNomeUsuario().equalsIgnoreCase(txtUCliente.getText()) && c.getC().getSenha().equalsIgnoreCase(txtSCliente.getText())) {//Conferir se a conta do Cliente existe
					clienteAcessando = c;
					abreTelaUsuarioCliente(); // abre a tela depois de salvar os dados do cliente no arquivo de acesso
					existe = true; // existe um cliente com esses dados
				}
			}
			
			}catch (Exception e) {
				mostraMensagem("Erro não identificado", AlertType.ERROR);
			}
		
			if(existe == false)  // não existe um cliente com esses dados cadastrados
				mostraMensagem("Nome de usuário ou senha incorretos.",AlertType.ERROR);
			
	}
	
	private void abreTab(String titulo, String path) { // verificar se há uma aba abeta, caso não, irá abrir uma nova
		try {
			Tab tab = tabAberta(titulo);
			if (tab==null) {
				tab = new Tab(titulo);
				tab.setClosable(true);
				tabPane.getTabs().add(tab);
				FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
				AnchorPane root = (AnchorPane)loader.load();
				
				if(path.equals("Aba_Cliente.fxml")) {
					Aba_Cliente_Controller c = (Aba_Cliente_Controller)loader.getController();
					tab.setContent((Node)root);
					c.clienteLogado(clienteAcessando);
					
				}else {
					if(path.equals("Aba_Loja.fxml")) {
						Aba_Loja_Controller c = (Aba_Loja_Controller)loader.getController();
						tab.setContent((Node)root);
						c.lojaLogada(lojaAcessando);
					}else {
							if(path.equals("Cadastro_Cliente.fxml")) {
								tab.setContent((Node)root);
								
							}else {
								if(path.equals("Cadastro_Loja.fxml")) {
									tab.setContent((Node)root);
								}
							}
						}
					}
				}
			
			selecionaTab(tab);
			
		}catch(Exception e) {
			mostraMensagem("Erro não identificado", AlertType.ERROR);
		}
	}
	
	private Tab tabAberta(String titulo) { // Verificar se existem abas abertas
		
		for(Tab tb : tabPane.getTabs()) {
			if(!(tb.getText()==null) && tb.getText().equals(titulo))
			return tb;
			}
			return null;
	}
	
	private  void selecionaTab(Tab tab) { // selecionar tela
		tabPane.getSelectionModel().select(tab);
	}

	private void mostraMensagem (String msg, AlertType tipo) { // recebe uma String por paremetro
	
		Alert a = new Alert (tipo);
	
		a.setHeaderText(null); // modificar mensagem
		a.setContentText(msg);
		a.show();
	}

	public Cliente getClienteAcessando() {
		return clienteAcessando;
	}

	public void setClienteAcessando(Cliente clienteAcessando) {
		this.clienteAcessando = clienteAcessando;
	}

	public Loja getLojaAcessando() {
		return lojaAcessando;
	}

	public void setLojaAcessando(Loja lojaAcessando) {
		this.lojaAcessando = lojaAcessando;
	}

}
