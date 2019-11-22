package Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Produto {

	private IntegerProperty codigo = new SimpleIntegerProperty(0);
	private StringProperty tipo = new SimpleStringProperty("");
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty marca = new SimpleStringProperty("");
	private StringProperty cor = new SimpleStringProperty("");
	private StringProperty material = new SimpleStringProperty("");
	private StringProperty sexo = new SimpleStringProperty();
	private IntegerProperty estoque = new SimpleIntegerProperty(0);
	private DoubleProperty valor = new SimpleDoubleProperty(0);
	private StringProperty tamanho = new SimpleStringProperty("");
	private StringProperty imagem = new SimpleStringProperty("");
	private IntegerProperty loja = new SimpleIntegerProperty(0);
	
	public final StringProperty nomeProperty() {
		return this.nome;
	}
	
	public final String getNome() {
		return this.nomeProperty().get();
	}
	
	public final void setNome(final String nome) {
		this.nomeProperty().set(nome);
	}
	
	public final StringProperty marcaProperty() {
		return this.marca;
	}
	
	public final String getMarca() {
		return this.marcaProperty().get();
	}
	
	public final void setMarca(final String marca) {
		this.marcaProperty().set(marca);
	}
	
	public final StringProperty corProperty() {
		return this.cor;
	}
	
	public final String getCor() {
		return this.corProperty().get();
	}
	
	public final void setCor(final String cor) {
		this.corProperty().set(cor);
	}
	
	public final StringProperty materialProperty() {
		return this.material;
	}
	
	public final String getMaterial() {
		return this.materialProperty().get();
	}
	
	public final void setMaterial(final String material) {
		this.materialProperty().set(material);
	}
	
	

	public final DoubleProperty valorProperty() {
		return this.valor;
	}
	

	public final double getValor() {
		return this.valorProperty().get();
	}
	

	public final void setValor(final double valor) {
		this.valorProperty().set(valor);
	}

	public final IntegerProperty estoqueProperty() {
		return this.estoque;
	}
	

	public final int getEstoque() {
		return this.estoqueProperty().get();
	}
	

	public final void setEstoque(final int estoque) {
		this.estoqueProperty().set(estoque);
	}

	public final StringProperty tamanhoProperty() {
		return this.tamanho;
	}
	

	public final String getTamanho() {
		return this.tamanhoProperty().get();
	}
	

	public final void setTamanho(final String tamanho) {
		this.tamanhoProperty().set(tamanho);
	}

	public final StringProperty imagemProperty() {
		return this.imagem;
	}
	

	public final String getImagem() {
		return this.imagemProperty().get();
	}
	

	public final void setImagem(final String imagem) {
		this.imagemProperty().set(imagem);
	}

	public final StringProperty sexoProperty() {
		return this.sexo;
	}
	

	public final String getSexo() {
		return this.sexoProperty().get();
	}
	

	public final void setSexo(final String sexo) {
		this.sexoProperty().set(sexo);
	}

	public final StringProperty tipoProperty() {
		return this.tipo;
	}
	

	public final String getTipo() {
		return this.tipoProperty().get();
	}
	

	public final void setTipo(final String tipo) {
		this.tipoProperty().set(tipo);
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

	public final IntegerProperty lojaProperty() {
		return this.loja;
	}
	

	public final int getLoja() {
		return this.lojaProperty().get();
	}
	

	public final void setLoja(final int loja) {
		this.lojaProperty().set(loja);
	}
	
}
