/*
 * Created on Jul 24, 2024
 */

package br.com.wises.uniodonto.estadoCivil; //a qual pacote essa classe pertence

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import br.com.wises.BaseAction; //superclasse
import br.com.wises.SessionManager;
import br.com.wises.uniodonto.util.Key.ROLES; //chaves de acesso

/**
 * @author Tiago Depizzol
 * @since Jul 24, 2024
 */
public class ExcluirEstadoCivilAction extends BaseAction // ExcluirEstadoCivilAction é o nome da classe pública
{

	public ExcluirEstadoCivilAction() //chama o construtor da superclasse
	{
		super(new ROLES[] {}); //indica que qualquer um, independente do acesso, pode realizar esse tipo de alteração (exclusão)
	}


	@Override //diz que o método abaixo sobrescreve um método da superclasse
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, SessionManager session) 
	{
		int codigo = Integer.parseInt(request.getParameter("codigo")); //obtém o parâmetro 'codigo' da solicitação HTTP (HttpServletRequest) e o converte para um número inteiro

		try
		{
			EstadoCivil estadoCivil = new EstadoCivil(); //cria uma nova instância de 'EstadoCivil'
			estadoCivil.setCodigo(codigo); //define o código de 'EstadoCivil' com base no código recebido na solicitação acima (HTTP)
			EstadoCivilDao estadoCivilDao = new EstadoCivilDao(); //cria uma nova instância de 'EstadoCivilDao' para interadgir com o Banco de Dados
			estadoCivil = (EstadoCivil) estadoCivilDao.get(EstadoCivil.class, codigo); //obtém o objeto 'EstadoCivil do Banco de Dados com o código especificado pelo usuário
			estadoCivilDao.delete(estadoCivil); //exclui o objeto 'EstadoCivil' em questão do Banco de Dados
		}
		catch(Exception e) //em caso de exceção (caso o haja algum erro com o registro)
		{
			log.error("[ExcluirEstadoCivilAction] - Erro ao Excluir Produto: ", e); //gera um log de erro na exceção da exclusão do Produto
			addMessage(request,new ActionMessage("fail.delete")); //adiciona uma mensagem de falha à solitação
			return mapping.findForward("failure"); //redireciona para página de falha
		}

		return mapping.findForward("success"); //redireciona para a página de sucesso
	}

}
