/*
 * Created on Jul 25, 2024
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

public class AtivacaoEstadoCivilAction extends BaseAction //AtivacaoEstadoCivilAction é o nome da classe pública, que herdas métodos da superclasse BaseAction
{

	public AtivacaoEstadoCivilAction() //chamma o construtor da superclasse e dá os parâmetros de acesso, que nesse caso não há nenhum
	{

		super(new ROLES[] {});
	}

	@Override //sobrescreve um método da superclasse
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, SessionManager session)
	{
		DynaActionForm dynaForm = (DynaActionForm) form; //cria uma variável chamada 'dynaForm', do ti´p DynaActionForm

		String cod = request.getParameter("codigo"); //cria umma variável String chamada 'cod', que pega o parâmetro 'codigo' da requisição HTTP
		String ativa = request.getParameter("ativar") //cria uma váriavel String chamada 'ativa' que pega o parâmetro 'ativar' da requisição HTTP
		try
		{
			if(cod != null && !"".equals(cod)) //se o 'cod' for diferente de 'null', ou uma String vazia ('&& !""), ela entra no if (vazia ou nula) ('==' não funciona pra comparar String em Java)
			{
				int codigo = Integer.parseInt(cod); //cria uma váriavel int chamada 'codigo', convertendo o 'cod' de String para Int
				boolean ativar = "true".equals(ativa);  //cria uma váriavel booleana chamada 'ativar' que recebe o valor da variável 'ativa', se essa variável ativa tiver o valor 'true'. Se não, retorna 'false
				EstadoCivilDao estadoCivilDao = new EstadoCivilDao(); //cria uma váriavel 'estadoCivilDao' do tipo 'EstadoCivilDao' que chama o construtor '()' para interagir com o banco de dados
				EstadoCivil estadoCivil = (EstadoCivil) estadoCivilDao.get(EstadoCivil.class, codigo); //cria uma váriavel chamada 'estadoCivil', do tipo EstadoCivil.
																									   //'get' é uma chamada ao Banco de Dados definido na superclasse que busca no Banco um registro com a chave-primária) parâmentro fornecido em 'codigo'
																									   //EstadoCivil.class indica ao 'get' que ele deve buscar no banco um objeto da classe EstadoCivil
																									   //'codigo' é o segundo parâmentro fornecido, diz que, dentro dos objetos de EstadoCivil, será buscado o parâmetro fornecido anteriormente
				estadoCivil.setIndDesativado(!ativar); //ativação e desativação dos Estados Civis
													   //se o objeto encontrado for diferente de 'ativar' (!ativar), seta como IndDesativado
				estadoCivilDao.saveOrUpdate(estadoCivil); //salva ou faz o update nas modificações

				dynaForm.set("estadoCivil", new EstadoCivil());  //seta no dynaForm o resultado de 'estadoCivil' como um novo objeto da classe EstadoCivil
			}
			else
				mapping.findForward("failure"); //se o código for nulo ou vazio, retorna um log de falha
		}
		catch(Exception e)
		{
			mapping.findForward("failure"); //redireciona para a página de falha
		}
		return mapping.findForward("success");
	}

}
