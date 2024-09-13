package br.com.wises.uniodonto.estadoCivil; //nome do pacote a qual esse arquivo faz parte

import java.sql.SQLException; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Expression;

import br.com.wises.BaseDao;
import br.com.wises.paging.Page;
import br.com.wises.uniodonto.beans.Combo;
import br.com.wises.util.Util;

/**
 * Explica��o: Classe responsavel por fazer a persistencia do objeto Produto
 *
 * @author Tiago Depizzol
 * @since Jul 18, 2024
 */
public class EstadoCivilDao extends BaseDao //definindo a classe como publica (o nome do arquivo é sempre o mesmo que o da classe)
{

	private static String LIST_PRODUTOS    = "SELECT CD_PRODUTO   AS CODIGO," //'private' é um modificador que não me deixa acessar essa classe através de outra classe
																			  //'static' diz que posso acessar essa variável em qualquer lugar da classe, e não somente nessa instância
			                               + "DS_PRODUTO   AS DESCRICAO FROM TDEPIZZOL_PRODUTO"
			                               + "WHERE IND_DESAT = 0"
			                               + "ORDER BY DS_PRODUTO"; //puxa o registro dos produtos ordenados pelo nome

	private static final String GET_NEXT   = "SELECT NVL(MAX(A.CD_PRODUTO),0)+1 AS NEXT FROM TDEPIZZOL_PRODUTO A"; //o modificador 'final' não me deixa alterar o valor da variável

	private static String BUSCA_DS_PRODUTO = "SELECT T.DS_PRODUTO AS DESCRICAO FROM TDEPIZZOL_PRODUTO T WHERE T.CD_PRODUTO=?;"; //o '?' indica um parâmetro descrito na chamada da query

	public EstadoCivilDao() //chama o construtor da superclasse 'BaseDao'
	{
		super(); 
	}

	public EstadoCivil get(int codigo) throws SQLException //diz para EstadoCivil utilizar o parâmetro 'código' na consulta ao Banco de Dados
														   //'throws SQLException' diz que o  método pode lançar uma exceção
	{
		return (EstadoCivil) super.get(EstadoCivil.class, codigo); //puxa dados da Classe 'EstadoCivil', com o parâmentro 'código'
																   //super.get chama o método get definido na superclasse, a classe 'BaseDao', que busca dados no banco
	}

	public int getProxCodigo() throws SQLException //um objeto nomeado de getProxCodigo que será preenchida pelo próprio método
	{
		return (Integer) getSession().createSQLQuery(GET_NEXT).addScalar("NEXT", //'getSession' retorna a classe Session do hibernate, utilizada pra interaagir com o Banco de Dados
				Hibernate.INTEGER).uniqueResult();								 //e recebe a váriavel GET_NEXT definida no método da classe 'EstadoCivilDao'
	}																		     //'NEXT' é o nome da coluna a ser mapeada. Hibernate.INTEGER diz para tratar como um inteiro
																				 //'uniqueResult' executa a query e retorna um único resultado
	/**Criado por Tiago Depizzol
	 * 19/07/2024
	 * @param codigo
	 */
	 
	public String getEstadoCivil(int codigo) throws SQLException //getEstadoCivil busca a descrição do item asociado ao código em questão
	{
		ArrayList res  = executeQuery(BUSCA_DS_PRODUTO, new Object[] {codigo}); //'executeQuery' é um método de consulta ao Banco de Dados
																				//'new Object[]' cria um array de objetos contendo o valor da consulta, tendo 'código' como parâmetro
																				//parâmetro 'código': o retorno corresponde ao '?' lá em cima, na Query
		Iterator  iter = res.iterator(); //chama um iterator para percorrer o resultado da consulta 'res'
		String desc    = null; //configura uma váriavel 'desc' como nula. essa váriavel armazenará o resultado da query
		while(iter.hasNext()) { //'while' - enquanto houver elementos no iterador / 'iter.hasNext' lê se há mais elementos no iterador 
			Map row = (Map) iter.next(); //mapeia as colunas 
			 desc = (Util.getNullText(row.get("DESCRICAO"))); //obtem o valor da coluna DESCRICAO mapeada no 'row'.
															  //'Util.getNullText' trata os valores nulos através da classe Util
		}
		return desc; //retorna os valores encontrados
	}

	@SuppressWarnings("rawtypes") //bloqueia o compilador de exibir avisos
	public List<EstadoCivil> getEstadosCivis() throws SQLException //lista os dados encontrados anteriormente
	{
		Object[] obj = new Object[] {}; //criação de um Array de objetos zerado

		ArrayList rs = executeQuery(LIST_PRODUTOS, obj); //o Array 'rs' será preenchido com o resultado da Query LIST_PRODUTOS

		Iterator rows = rs.iterator(); //coloca um Iterator pra ler os registros
		HashMap col = null; //mapeia e manipula os dados de 'col' através da função 'HashMap'. A variável, aqui, foi criada como nula
		List<EstadoCivil> ecs = new ArrayList<EstadoCivil>(); //cria uma lista vazia que só pode ser preenchida por valores 'EstadoCivil'. 'ecs' é o nome da lista(variável).
														      //essa lista é implementada por ArrayList

		while(rows.hasNext()) //'while' - enquanto houver elementos no iterador / 'row.hasNext' lê se há mais elementos no iterador 
		{
			col = (HashMap) rows.next(); //'col' armazena o resultado do ArrayList 'rs' e, aqui, converte-o para HashMap, pegando sempre o resultado através do Iterator e 'hasNext'
			EstadoCivil ec = new EstadoCivil(); //'ec' é uma nova instância da classe EstadoCivil, mas chamando-a de 'ec' para poder tratá-la
			ec.setCodigo(Integer.parseInt(Util.getNullText(col.get("CODIGO")))); //setCodigo seta o valor de 'Codigo' de 'ec'
																				 //'col' traz o valor de 'CODIGO' como HashMap
																				 //'Util.getNullText' trata os valores nulos através da classe Util
																				 //'Integer.perseInt' converte String (texto) para Int (número inteiro)
																				 //'setCodigo' 
																				 
			ec.setDescricao(Util.getNullText(col.get("DESCRICAO"))); //seta o valor de 'Descricao' de 'ec'
																	 //'col' traz o valor de 'DESCRICAO' como HashMap
																	 //'Util.getNullText' trata os valores nulos através da classe Util
																	 //'Integer.perseInt' converte String (texto) para Int (número inteiro)
			ecs.add(ec); //adiciona na ArrayList 'ecs' os valores de ec
		}
		finalizarObjetos(); //metódo de fechamento de objetos
		return ecs; //retorta os valores de 'ecs'
	}

	@SuppressWarnings("rawtypes")
	public List<Combo> listComboEstadosCivis() throws SQLException //'Combo' é um tipo de objeto definido pela Classe importada no início do código
																   //'listComboEstadosCivis' método público para retornar uma lista de 'Combo'
																   //aqui estamos definindo o tipo do obejeto como 'Combo'
	{
		Object[] obj = new Object[] {}; //criação de um Array de objetos zerado

		ArrayList rs = executeQuery(LIST_PRODUTOS, obj); //o Array 'rs' será preenchido com o resultado (objetos) da Query LIST_PRODUTOS

		Iterator rows = rs.iterator(); //coloca um Iterator pra ler os registros
		HashMap col = null; //mapeia e manipula os dados de 'col' através da função 'HashMap'. A variável, aqui, foi criada como nula
		List<Combo> listaEstadosCivis = new ArrayList<Combo>(); //aqui estamos transformando a 'List' em um ArrayList (lista concreta) que pode armazenar objetos 'Combo'

		while(rows.hasNext()) //'while' - enquanto houver elementos no iterador / 'row.hasNext' lê se há mais elementos no iterador 
		{
			col = (HashMap) rows.next();//'col' armazena o resultado do ArrayList 'rs' e, aqui, converte-o para HashMap, pegando sempre o resultado através do Iterator e 'hasNext'
			Combo ddd = new Combo(); //crido uma váriavel do tipo 'Combo' chamada 'ddd', que é responsável por criar um objeto do tipo 'Combo'
			ddd.setCod(Util.getNullText(col.get("CODIGO"))); //setando o 'Cod' da váriavel 'ddd' com o 'CODIGO' de 'col', que puxa dados do ArrayList 'rs'
			ddd.setDesc(Util.getNullText(col.get("DESCRICAO"))); //setando o 'Desc' da váriavel 'ddd' com a 'DESCRICAO' de 'col', que puxa dados do ArrayList 'rs'
			listaEstadosCivis.add(ddd); //aqui adicionamos o objeto 'ddd' ao Array listaEstadosCivis
		}
		return listaEstadosCivis; //retorna os valores de listaEstadosCivis
	}

	@SuppressWarnings ("unchecked")
	public List<EstadoCivil> listAllPaged(Page page) //criando um método que indica a páginação dos objetos criados acima
													 //'Page page' é o parâmetro que contém as informações sobre a paginação
	{
		page.addSortableColumn("codigo", "codigo"); //page. indica que estamos gerenciando a paginação e a ordenação dos resultados
													//'SortableColumn' adiciona uma coluna que pode ser usada para ordenar os resultados
													//(1) 'codigo' indica a coluna no banco de dados que será usada para ordenar os resultados
													//(2) 'codigo' indica o rótulo que pode ser usado na interface do usuário
		page.addSortableColumn("descricao", "descricao"); //page. indica que estamos gerenciando a paginação e a ordenação dos resultados
														  //'SortableColumn' adiciona uma coluna que pode ser usada para ordenar os resultados
														  //(1) 'descricao' indica a coluna no banco de dados que será usada para ordenar os resultados
														  //(2) 'descricao' indica o rótulo que pode ser usado na interface do usuário

		Criteria crit = getSession().createCriteria(EstadoCivil.class); //essa parte do código é utilizada para criar uma instância de 'Criteria' na API do Hibernate
																		//Criteria' nos permite fazer consultas ao banco de dados através do Hibernate sem escrever SQL
																		//crit é o nome da variável
																		//getSession é um método que obtém a sessão atual do Hibernate (Isso deve ser definido em algum lugar da classe ou herdado da superclasse 'BaseDao'
																		//o 'createCriteria' é um método da sessão do Hibernate que cria uma consulta para a entidade 'EstadoCivil'
																		//'EstadoCivil.class' indica que essa contulta será feita na EntidadeCivil
		return super.listPaged(crit, page); //'super' chama a superclasse 'BaseDao1
											//'listPaged' um método que é responsável pela paginação
											//'crit' o objeto 'Criteria1 que é responsável pela consulta no Hibernate
											//'page' objeto que contém informações sobre a página atual
											//essa linha chama o método da superclasse para executar a paginação
	}

	/**
	 * Explica��o: Busca um Produto pela Descricao
	 *
	 * @author Tiago Depizzol 
	 * @since Jul 19, 2024
	 * @param String Descricao
	 * @return Produto
	 */
	public EstadoCivil getEstadoCivilByDescricao(String descricao){ //está pegando o EstadoCivil pela descricao (ByDescricao) através do get (Hibernate)
																	//'(String descricao)' indica que o parâmentro 'descricao' deve ser uma String

		Criteria crit = getSession().createCriteria(EstadoCivil.class);
		crit.add(Expression.eq("descricao", descricao)); //'crit.add' está adicionando uma restrição na consulta do Hibernate através de 'eq', que é um método de expressão que cria uma condição de igualdade, comparando o valor 'descricao' com 'descricao'

		return (EstadoCivil) crit.uniqueResult(); //retorna a consulta limitando-a a um único resultado correspondente a chamada
	}

}