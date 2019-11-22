package Model;

import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cliente {

	private IntegerProperty codigo = new SimpleIntegerProperty(0);
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty cpf = new SimpleStringProperty("");
	private StringProperty cidade = new SimpleStringProperty("");
	private StringProperty bairro = new SimpleStringProperty("");
	private StringProperty telefone = new SimpleStringProperty("");
	private ContaCliente c = new ContaCliente();
	
	ArrayList<Produto> favoritos = new ArrayList<>();
	ArrayList<Compra> compras = new ArrayList<>();
	
	public ContaCliente getC() {
		return c;
	}

	public void setC(ContaCliente c) {
		this.c = c;
	}

	public final StringProperty nomeProperty() {
		return this.nome;
	}
	
	public final String getNome() {
		return this.nomeProperty().get();
	}
	
	public final void setNome(final String nome) {
		this.nomeProperty().set(nome);
	}

	
	public final StringProperty cidadeProperty() {
		return this.cidade;
	}
	
	public final String getCidade() {
		return this.cidadeProperty().get();
	}
	
	public final void setCidade(final String cidade) {
		this.cidadeProperty().set(cidade);
	}
	
	public final StringProperty bairroProperty() {
		return this.bairro;
	}
	
	public final String getBairro() {
		return this.bairroProperty().get();
	}
	
	public final void setBairro(final String bairro) {
		this.bairroProperty().set(bairro);
	}

	public ArrayList<Produto> getFavoritos() {
		return favoritos;
	}

	public void setFavoritos(ArrayList<Produto> favoritos) {
		this.favoritos = favoritos;
	}

	public ArrayList<Compra> getCompras() {
		return compras;
	}

	public void setCompras(ArrayList<Compra> compras) {
		this.compras = compras;
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

	public final StringProperty cpfProperty() {
		return this.cpf;
	}
	

	public final String getCpf() {
		return this.cpfProperty().get();
	}
	

	public final void setCpf(final String cpf) {
		this.cpfProperty().set(cpf);
	}
	

	public final StringProperty telefoneProperty() {
		return this.telefone;
	}
	

	public final String getTelefone() {
		return this.telefoneProperty().get();
	}
	

	public final void setTelefone(final String telefone) {
		this.telefoneProperty().set(telefone);
	}

}
