/*
 * Created on Jan 25, 2024
 */

package br.com.wises.uniodonto.estadoCivil; //nome do pacote ao qual essa classe pertence

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import br.com.wises.BaseAction;
import br.com.wises.SessionManager;
import br.com.wises.uniodonto.util.Key.ROLES;

/**
 * @author Tiago Depizzol
 * @since Jan 25, 2024
 */
public class FindEstadoCivilAction extends BaseAction //o nome da classe pública é 'FindEstadoCivilAction', herdando a superclasse 'BaseAction'
{

	public FindEstadoCivilAction() //chama o construtor da superclasse
	{
		super(new ROLES[] {}); //método de acesso, e como não tem nenhum parâmetro, diz que podemos interagir sem qualquer privilégio de acesso
	}

	@Override //estamos sobrescrevendo um método da superclasse com o método abaixo
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, SessionManager session)
	{
		String fw = ""; ;//uma variável String com o nome 'fw' definida como vazia
		DynaActionForm dynaForm = (DynaActionForm) form; //uma variável DynaActionForm com o nome 'dynaForm' 
													     //Struts que trabalha com formulários dinâmicos
		EstadoCivil estadoCivil = new EstadoCivil(); //uma variável EstadoCivil chamada ''estadoCivil', e está criando uma nova instância (objeto) da classe 'EstadoCivil'

		String cod = request.getParameter("codigo"); //uma variável Sting com o nome 'cod' 
													 //o objeto 'request' é uma instância de HttpServletRequest, que puxa o valor do parâmetro 'codigo' dessa solicitação HTTP

		int codigo = Integer.parseInt("".equals(cod) ? "0" : cod); //uma váriavel int com o nome 'codigo'
																   //Integer.parseInt() converte uma String em um Int
																   //'? :' é uma forma de escrever o 'if-else', ou seja, se o valor for nulo, retorna '0', se não, retorna o valor de 'cod'
																   //após esse retorno, faz a conversão de Sting para Int, e atribui o valor convertido a variável 'codigo'
		estadoCivil.setDescricao("nome"); //'estadoCivil' é um objeto da classe EstadoCivil, e está chamando o método 'setDescricao', passando 'nome' como argumento. Esse método defne o valor do atributo 'descricao' para o valor de 'nome'
		estadoCivil.setCodigo(codigo); //'estadoCivil' é um objeto da classe EstadoCivil, e está chamado o método 'setCodigo', passando 'codigo' como argumento. Esse método define o valor do atributo 'codigo' para o valor de 'codigo'

		try
		{
			EstadoCivilDao estadoCivilDao = new EstadoCivilDao(); //variável 'estadoCivilDao' que é do tipo EstadoCivilDao
																  //está criando uma nova instância (objeto), chamando o constutor da classe, para interagir com o Banco de Dados

			if(codigo == 0) //se o 'codigo' for igual a 0
			{

				fw = "lista"; //define a variável 'fw' como 'lista'
			}
			else //se o 'cod' não for 0
			{
				estadoCivil = estadoCivilDao.get(estadoCivil.getCodigo()); //utiliza o método 'get' de EstadoCivilDao para buscar o objeto 'EstadoCivil' no banco de dados com o 'codigo' fornecido. O resultado é atribuido a váriavel 'estadoCivil'

				dynaForm.set("estadoCivil", estadoCivil); //atualiza o formulário dinâmico (dynaForm) com o objeto estadoCivil recuperado, permitindo que ele seja usado na interface do usuário.
				fw = "unique"; //define a variável 'fw' como 'unique', para indicar um item específico
			}
		}
		catch(Exception e) //trata exceções que possam ocorrer
		{
			log.error("[FindEstadoCivilAction] - Erro ao procurar Produto: ", e); //log de erro
			fw = "failure"; //seta a variável como 'failure'
		}

		return mapping.findForward(fw); //deve nos levar ao valor da variável
	}
}
