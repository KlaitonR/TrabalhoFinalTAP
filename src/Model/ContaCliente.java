package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ContaCliente {
	
	private IntegerProperty codigo = new SimpleIntegerProperty(0);
	private StringProperty nomeUsuario = new SimpleStringProperty("");
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
	
	public final StringProperty nomeUsuarioProperty() {
		return this.nomeUsuario;
	}
	
	public final String getNomeUsuario() {
		return this.nomeUsuarioProperty().get();
	}
	
	public final void setNomeUsuario(final String nomeUsuario) {
		this.nomeUsuarioProperty().set(nomeUsuario);
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
