package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import Model.Cliente;
import Model.Compra;
import Model.Pagamento;
import Util.Conexao;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

public class PagamentoController {

	@FXML TableView<Compra>tbl;
	@FXML TableColumn<Compra, Number> colCod;
	@FXML TableColumn<Compra, String> colProduto;
	@FXML TableColumn<Compra, Number> colQuantia;
	@FXML TableColumn<Compra, Number> colTotal;
	@FXML TableColumn<Compra, String> colStatus;
	
	@FXML RadioButton rdCartao;
	@FXML RadioButton rdBoleto;
	@FXML RadioButton rdDeposito;
	@FXML RadioButton rdBit;
	
	Cliente clienteLogado;
	ArrayList<Compra> compras = new ArrayList<>();
	
	public Cliente clienteLogado(Cliente c) {
		
		clienteLogado = c;
		
		return clienteLogado;
	}
	
	@FXML
	public void iniciaTbl() {
		buscaCompras();
		inicializaTB();
	}
	
	public void buscaCompras() {
		
		compras.clear();
		
		try {
			Connection conn = Conexao.getConexao();
			
			String sqlLista = "select * from compra where compra.cliente = ?"; 
			PreparedStatement ps1 = conn.prepareStatement(sqlLista);
			ps1.setString(1, clienteLogado.getNome());
			ResultSet rs = ps1.executeQuery();
			
			while(rs.next()) {
				
				Compra c = new Compra();
			
					c.setCodigo(rs.getInt("codigo"));
					c.getC().setNome(rs.getString("cliente"));
					c.getP().setNome(rs.getString("produto"));
					c.setQt(rs.getInt("quantidade"));
					c.setStatus(rs.getString("status"));
					c.setTotal(rs.getDouble("total"));
				
				compras.add(c);
			}
			
				tbl.setItems(FXCollections.observableArrayList(compras));
				
			conn.close();
		
		}catch (Exception e) {
			mostraMensagem("Erro não identificado.\n " + e.toString(), AlertType.ERROR);
		}
	}
	
	public Compra selecionaCompra(){
		
		Compra pedido = new Compra();
		
		try {
				pedido = tbl.getSelectionModel().getSelectedItem();
			
		}catch (Exception e) {
			mostraMensagem("Erro não identificado.\n " + e.toString(), AlertType.ERROR);
		}
		
		return pedido;
	}

	@FXML
	private void pagarCompra() {
		
		try {
			
		Compra compra = selecionaCompra();	
		Pagamento p = new Pagamento();
		
		if(compra.getStatus().equals("PAGO")) {
			mostraMensagem("Você já pagou por este pedido!", AlertType.INFORMATION);
		}else {
			if(rdCartao.isSelected()) {
				p.setTipoPagamento("Cartão de crédito");
			}else {
				if(rdBoleto.isSelected()) {
					p.setTipoPagamento("Boleto bancário");
				}else {
					if(rdBit.isSelected()) {
						p.setTipoPagamento("BitCoin");
					}else {
						if(rdDeposito.isSelected()) {
							p.setTipoPagamento("Depósito bancário");
						}
					}
				}
			}
		
			Connection conn = Conexao.getConexao();
			String sql = "insert into pagamento (produto,quantia,total,tipo_pagamento,cliente) values(?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, compra.getP().getNome());
			ps.setInt(2, compra.getQt());
			ps.setDouble(3,compra.getTotal());
			ps.setString(4, p.getTipoPagamento());
			ps.setString(5, compra.getC().getNome());
			
			ps.executeUpdate();

			conn.close();
		
			mostraMensagem("Sua compra foi paga com sucesso!", AlertType.CONFIRMATION);
		
			// código para editar a coluna STATUS da tabela COMPRA	na qual foi paga ----------------------------------------
			Connection con = Conexao.getConexao();
			String sqlEdit = "update compra set status = ? where codigo = ? ";
			PreparedStatement ps1 = con.prepareStatement(sqlEdit);
			ps1.setString(1, "PAGO");
			ps1.setInt(2, compra.getCodigo());
			
			ps1.executeUpdate();
		
			con.close();
		
			buscaCompras();
			inicializaTB();
		}
		
		}catch (NullPointerException e) {
			mostraMensagem("Selecione um pedido na tabela de COMPRAS/PAGAMENTO.", AlertType.WARNING);
		}
		catch (Exception e) {
			mostraMensagem("Erro não identificado.\n"  + e.toString() , AlertType.ERROR);
		}
	}
	
	 @FXML
	 public void CancelaPedido() {
		  
		 try {
			 
			Compra compra = selecionaCompra();
			
			if(compra.getStatus().equals("PAGO")) {
				mostraMensagem("Este pedido já foi pago, impossível realizar o cancelamento. \nVocê pode apaga-lo no botão <Apagar compras já pagas>", AlertType.WARNING);
			}else {
			
				Connection conn = Conexao.getConexao();
				String sqlLista = "delete from compra where compra.codigo = ?";
				PreparedStatement ps = conn.prepareStatement(sqlLista);
				ps.setInt(1,compra.getCodigo());
			
				ps.executeUpdate();
			 
				conn.close();
			
				mostraMensagem("Pedido CANCELADO com sucesso! ", AlertType.CONFIRMATION);
			
				buscaCompras();
				inicializaTB();
			}
		 }catch (NullPointerException e) {
			 mostraMensagem("Selecione um pedido para realizar está operação.", AlertType.WARNING);	
			
			}catch (Exception e) {
				mostraMensagem("Erro não identificado.\n " + e.toString(), AlertType.WARNING);
			}
	 }
	 
	 @FXML
	 public void inicializaTB() {
			colCod.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
			colProduto.setCellValueFactory(cellData -> cellData.getValue().getP().nomeProperty());
			colQuantia.setCellValueFactory(cellData -> cellData.getValue().qtProperty());
			colTotal.setCellValueFactory(cellData -> cellData.getValue().totalProperty());
			colStatus.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
	 }
	
	 private void mostraMensagem (String msg, AlertType tipo) { // recebe uma String por paremetro
			
			Alert a = new Alert (tipo);
			
			a.setHeaderText(null); // modificar mensagem
			a.setContentText(msg);
			a.show();
		}
}
