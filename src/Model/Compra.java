package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import Util.Conexao;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Compra{
	
	private IntegerProperty codigo = new SimpleIntegerProperty(0);
	private IntegerProperty qt = new SimpleIntegerProperty(0);
	private DoubleProperty total = new SimpleDoubleProperty(0);
	private StringProperty status = new SimpleStringProperty("Aguardando Pagamento");
	private Cliente c = new Cliente();
	private Produto p =  new Produto();
	private Loja l = new Loja();
	
	public double realizaComprar(int qt) {
		
		double total = 0;
		
		try {
			
			total = qt*p.getValor();
			p.setEstoque(p.getEstoque() - qt);
			
			//--------------VV ATUALIZA O ESTOQUE DO PRODUTO COMPRADO NA TABELA VV----------------
			
			 Connection conn = Conexao.getConexao();
			 String sqlLista = "update produto set estoque=? where produto.codigo=?";
			 PreparedStatement ps1 = conn.prepareStatement(sqlLista);
			 ps1.setInt(1,p.getEstoque());
			 ps1.setInt(2,p.getCodigo());
			 
			 ps1.executeUpdate();
			 
			 conn.close();
			
		}catch (Exception e) {
			mostraMensagem("Erro não identificado.\n" + e.toString(), AlertType.ERROR);
		}
		
		return total; // total da compra
	}
	
	private void mostraMensagem (String msg, AlertType tipo) { // recebe uma String por paremetro
		
		Alert a = new Alert (tipo);
		
		a.setHeaderText(null); // modificar mensagem
		a.setContentText(msg);
		a.show();
	}

	public final IntegerProperty codigoProperty() {
		return this.codigo;
	}
	

	public final int getCodigo() {
		return this.codigoProperty().get();
	}
	

	public final void setCodigo(final int codigo) {
		this.codigoProperty().set(codigo);
	}

	public Cliente getC() {
		return c;
	}

	public void setC(Cliente c) {
		this.c = c;
	}

	public Produto getP() {
		return p;
	}

	public void setP(Produto p) {
		this.p = p;
	}

	public final IntegerProperty qtProperty() {
		return this.qt;
	}
	

	public final int getQt() {
		return this.qtProperty().get();
	}
	

	public final void setQt(final int qt) {
		this.qtProperty().set(qt);
	}

	public Loja getL() {
		return l;
	}

	public void setL(Loja l) {
		this.l = l;
	}

	public final DoubleProperty totalProperty() {
		return this.total;
	}
	

	public final double getTotal() {
		return this.totalProperty().get();
	}
	

	public final void setTotal(final double total) {
		this.totalProperty().set(total);
	}

	public final StringProperty statusProperty() {
		return this.status;
	}
	

	public final String getStatus() {
		return this.statusProperty().get();
	}
	

	public final void setStatus(final String status) {
		this.statusProperty().set(status);
	}

}
