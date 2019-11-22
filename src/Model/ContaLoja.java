package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ContaLoja {

	private IntegerProperty codigo = new SimpleIntegerProperty(0);
	private StringProperty usuario = new SimpleStringProperty("");
	private StringProperty senha = new SimpleStringProperty("");
	
	public final IntegerProperty codigoProperty() {
		return this.codigo;
	}
	
	public final int getCodigo() {
		return this.codigoProperty().get();
	}
	
	public final void setCodigo(final int codigo) {
		this.codigoProperty().set(codigo);
	}
	
	public final StringProperty usuarioProperty() {
		return this.usuario;
	}
	
	public final String getUsuario() {
		return this.usuarioProperty().get();
	}
	
	public final void setUsuario(final String usuario) {
		this.usuarioProperty().set(usuario);
	}
	
	public final StringProperty senhaProperty() {
		return this.senha;
	}
	
	public final String getSenha() {
		return this.senhaProperty().get();
	}
	
	public final void setSenha(final String senha) {
		this.senhaProperty().set(senha);
	}
	
}
