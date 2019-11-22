package Model;

import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Loja {

	private IntegerProperty codigo = new SimpleIntegerProperty(0);
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty cnpj = new SimpleStringProperty("");
	private StringProperty cidade = new SimpleStringProperty("");
	private StringProperty bairro = new SimpleStringProperty("");
	private StringProperty telefone = new SimpleStringProperty("");
	private ContaLoja c = new ContaLoja();
	
	ArrayList<Produto> produtos = new ArrayList<>();
	
	public ContaLoja getC() {
		return c;
	}
	public void setC(ContaLoja c) {
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
	
	public ArrayList<Produto> getProdutos() {
		return produtos;
	}
	public void setProdutos(ArrayList<Produto> produtos) {
		this.produtos = produtos;
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
	public final StringProperty cnpjProperty() {
		return this.cnpj;
	}
	
	public final String getCnpj() {
		return this.cnpjProperty().get();
	}
	
	public final void setCnpj(final String cnpj) {
		this.cnpjProperty().set(cnpj);
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
