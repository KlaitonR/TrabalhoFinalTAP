package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import Model.Cliente;
import Util.Conexao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class Cadastro_Cliente_Controller {

	@FXML TextField txtNome;
	@FXML TextField txtCpf;
	@FXML TextField	txtCidade;					
	@FXML TextField txtBairro;
	@FXML TextField txtTelefone;
	@FXML TextField	txtNomeUsuario;
	@FXML TextField txtSenha;

	ArrayList<Cliente> confereCliente = new ArrayList<>();
	
	@FXML
	private void inserir() {
		
		boolean existe = false;
		boolean conNome = false;
		boolean conCPF = false;
		boolean conUsuario = false;
		boolean campoNulo = false;
		
		try {
			
			if((txtNome.getText().equals(""))||(txtCpf.getText().equals(""))||(txtBairro.getText().equals(""))||(txtCidade.getText().equals(""))||(txtTelefone.getText().equals(""))||(txtNomeUsuario.getText().equals(""))||(txtSenha.getText().equals("")))
				campoNulo = true;
			
			Connection con = Conexao.getConexao();
			String sqlLista = "select * from cliente";
			PreparedStatement ps1 = con.prepareStatement(sqlLista);
			ResultSet rs = ps1.executeQuery();
				
			while(rs.next()) {
					
				Cliente confere = new Cliente();
				confere.setNome(rs.getString("nome"));
				confere.setCpf(rs.getString("cpf"));
				confere.getC().setNomeUsuario(rs.getString("usuario"));
				confereCliente.add(confere);
			}
			con.close();
				
			String mensagem = "Já existe um usuário com este: | ";
			
			for(Cliente cliente: confereCliente) {
				
				if(conNome == false) {
					if(cliente.getNome().equalsIgnoreCase(txtNome.getText())) {
						mensagem+="NOME |";
						existe = true;
						conNome = true;
					}
				}
				
				if(conCPF == false) {
					if(cliente.getCpf().equalsIgnoreCase(txtCpf.getText())) {
						mensagem+="CPF |";
						existe = true;
						conCPF = true;
					}
				}
				
				if(conUsuario == false) {
					if(cliente.getC().getNomeUsuario().equalsIgnoreCase(txtNomeUsuario.getText())) {
						mensagem+="NOME DE USUÁRIO |";
						existe = true;
						conUsuario = true;
					}	
				}
			}
			
			if(existe == false) {
				if(campoNulo == false) {
					Cliente c = new Cliente();
					c.setNome(txtNome.getText());
					c.setCpf(txtCpf.getText());
					c.setBairro(txtBairro.getText());
					c.setCidade(txtCidade.getText());
					c.setTelefone(txtTelefone.getText());
					c.getC().setNomeUsuario(txtNomeUsuario.getText());
					c.getC().setSenha(txtSenha.getText());
				
					Connection conn = Conexao.getConexao();
					String sql = "insert into cliente (nome,cpf,cidade,bairro,telefone,usuario,senha) values(?,?,?,?,?,?,?)";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1,c.getNome());
					ps.setString(2,c.getCpf());
					ps.setString(3,c.getCidade());
					ps.setString(4,c.getBairro());
					ps.setString(5,c.getTelefone());
					ps.setString(6,c.getC().getNomeUsuario());
					ps.setString(7,c.getC().getSenha());
		
					ps.executeUpdate();
			
					conn.close();
		        
					mostraMensagem("Cadastro concluido com sucesso!",AlertType.CONFIRMATION);
					}else {
						mostraMensagem("Você deixou campos em branco, por favor, verifique todos os campos, é necessário preenchar todos.", AlertType.WARNING);
					}
				}else {
					mostraMensagem(mensagem, AlertType.INFORMATION);
			}
				
		}catch (NumberFormatException e) {
			mostraMensagem("Erro de converção, dado invalido.", AlertType.ERROR);
		}
		
		catch (Exception e) {
			mostraMensagem("Erro não identificado \n" + e.toString(), AlertType.ERROR);
		}
	}
	
	@FXML
	private void limpaTela() {
		txtNome.setText("");
		txtCpf.setText("");
		txtBairro.setText("");
		txtCidade.setText("");
		txtTelefone.setText("");
		txtNomeUsuario.setText("");
		txtSenha.setText("");
	}
	
	private void mostraMensagem (String msg, AlertType tipo) { // recebe uma String por paremetro
		
		Alert a = new Alert (tipo);
		
		a.setHeaderText(null); // modificar mensagem
		a.setContentText(msg);
		a.show();
	}
}
