package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import Model.Loja;
import Util.Conexao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class Cadastro_Loja_Controller {

	@FXML TextField txtNome;
	@FXML TextField txtCnpj;	
	@FXML TextField txtCidade;
	@FXML TextField txtBairro;
	@FXML TextField txtTelefone;
	@FXML TextField txtUsuario;
	@FXML TextField txtSenha;
	
	ArrayList<Loja> confereLoja = new ArrayList<>();
	
	@FXML
	private void inserir() { //gravar os dados
		
		boolean existe = false;
		boolean conNome = false;
		boolean conCPF = false;
		boolean conUsuario = false;
		boolean campoNulo = false;
		
		try {
			
			if(txtNome.getText().equals("")||txtCnpj.getText().equals("")||txtBairro.getText().equals("")||txtCidade.getText().equals("")||txtTelefone.getText().equals("")||txtUsuario.getText().equals("")||txtSenha.getText().equals(""))
				campoNulo = true;
			
			Connection con = Conexao.getConexao();
			String sqlLista = "select * from loja"; // where codigo = codigoDaLoja;
			PreparedStatement ps1 = con.prepareStatement(sqlLista);
			ResultSet rs = ps1.executeQuery();
				
			while(rs.next()) {
					
				Loja confere = new Loja();
				confere.setNome(rs.getString("nome"));
				confere.setCnpj(rs.getString("cnpj"));
				confere.getC().setUsuario(rs.getString("usuario"));
				confereLoja.add(confere);
			}
			con.close();
				
			String mensagem = "Já existe um usuário com este: | ";
			
			for(Loja loja: confereLoja) {
				
				if(conNome == false) {
					if(loja.getNome().equalsIgnoreCase(txtNome.getText())) {
						mensagem+="NOME |";
						existe = true;
						conNome = true;
					}
				}
				
				if(conCPF == false) {
					if(loja.getCnpj().equalsIgnoreCase(txtCnpj.getText())) {
						mensagem+="CNPJ |";
						existe = true;
						conCPF = true;
					}
				}
				
				if(conUsuario == false) {
					if(loja.getC().getUsuario().equalsIgnoreCase(txtUsuario.getText())) {
						mensagem+="NOME DE USUÁRIO |";
						existe = true;
						conUsuario = true;
					}	
				}
			}
			
			if(existe == false) {
				if(campoNulo == false) {
					Loja l = new Loja();
					l.setNome(txtNome.getText());
					l.setCnpj(txtCnpj.getText());
					l.setBairro(txtBairro.getText());
					l.setCidade(txtCidade.getText());
					l.setTelefone(txtTelefone.getText());
					l.getC().setUsuario(txtUsuario.getText());
					l.getC().setSenha(txtSenha.getText());
			
					Connection conn = Conexao.getConexao();
					String sql = "insert into loja (nome,cnpj,cidade,bairro,telefone,usuario,senha) values(?,?,?,?,?,?,?)";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1,l.getNome());
					ps.setString(2,l.getCnpj());
					ps.setString(3,l.getCidade());
					ps.setString(4,l.getBairro());
					ps.setString(5,l.getTelefone());
					ps.setString(6,l.getC().getUsuario());
					ps.setString(7,l.getC().getSenha());
						
					ps.executeUpdate();
			
					conn.close();
							
					mostraMensagem("Cadastro concluido com sucesso",AlertType.CONFIRMATION);
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
				mostraMensagem("Erro não identificado. \n" + e.toString(), AlertType.WARNING);
			} 
	}
	
	@FXML
	private void limpaTela() {
		txtNome.setText("");
		txtCnpj.setText("");
		txtBairro.setText("");
		txtCidade.setText("");
		txtTelefone.setText("");
		txtUsuario.setText("");
		txtSenha.setText("");
	}
	
	//métoda para exibir mensagens
	private void mostraMensagem (String msg, AlertType tipo) { // recebe uma String por paremetro
		
		Alert a = new Alert (tipo);
		
		a.setHeaderText(null); // modificar mensagem
		a.setContentText(msg);
		a.show();
	}
}
