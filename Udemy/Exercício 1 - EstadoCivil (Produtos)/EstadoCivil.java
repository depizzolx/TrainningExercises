package br.com.wises.uniodonto.estadoCivil; //nome do pacote ao qual essa classe pertence

import br.com.wises.uniodonto.beans.Acesso;

public class EstadoCivil extends Acesso // EstadoCivil é o nome da classe pública, que herda métodos da classe Acesso
{
	private static final long serialVersionUID = 1L; //identificador (?)
	private int codigo; //variável que armazena um código inteiro, mas que só pode ser acessado dentro dessa classe
	private Boolean indDesativado = false; //indicador que ativado ou desativado, setado por padrão como desativado

	private String descricao; //armazena a descricao

	public void setCodigo(int codigo)
	{
		this.codigo = codigo;
	}

	public int getCodigo()
	{
		return codigo;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}

	public String getDescricao()
	{
		return descricao;
	}

	public Boolean getIndDesativado() {
		return indDesativado;
	}

	public void setIndDesativado(Boolean indDesativado) {
		this.indDesativado = indDesativado;
	}
	
}
