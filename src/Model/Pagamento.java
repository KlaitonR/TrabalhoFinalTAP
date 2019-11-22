package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Pagamento {

	private IntegerProperty codigo = new SimpleIntegerProperty(0);
	private StringProperty tipoPagamento = new SimpleStringProperty("");
	
	public final IntegerProperty codigoProperty() {
		return this.codigo;
	}
	
	public final int getCodigo() {
		return this.codigoProperty().get();
	}
	
	public final void setCodigo(final int codigo) {
		this.codigoProperty().set(codigo);
	}

	public final StringProperty tipoPagamentoProperty() {
		return this.tipoPagamento;
	}
	

	public final String getTipoPagamento() {
		return this.tipoPagamentoProperty().get();
	}
	

	public final void setTipoPagamento(final String tipoPagamento) {
		this.tipoPagamentoProperty().set(tipoPagamento);
	}
	
}
