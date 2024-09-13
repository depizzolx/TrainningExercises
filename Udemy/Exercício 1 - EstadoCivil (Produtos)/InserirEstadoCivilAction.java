/*
 * Created on Jan 12, 2006
 * Página de Inserção de registros de Estado Civil, sem alterações na relação com a EstadoCivilDao.
 */

package br.com.wises.uniodonto.estadoCivil; //diz em qual pacote essa classe está localizada

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.DynaActionForm;

import br.com.wises.BaseAction;
import br.com.wises.SessionManager;
import br.com.wises.uniodonto.util.Key.ROLES;

/**
 * @author Tiago Depizzol
 * @since Jul 23, 2024
 */
public class InserirEstadoCivilAction extends BaseAction //InserirEstadoCivilAction é o nome da classe e BaseAction é a superclasse
{

	public InserirEstadoCivilAction() //herda o construtor da superclasse, com o array Roles, definindo que essa ação em questão requer permissõees de ADMINISTRACAO ou ATENDENTE
	{
		super(new ROLES[] { ROLES.ADMINISTRACAO, ROLES.ATENDENTE });
	}

	@Override //indica que o método abaixo sobrescreve um método da superclasse BaseAction
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response, SessionManager session)
	{
		DynaActionForm dynaForm = (DynaActionForm) form; //converte o ActionForm para DynaActionForm

		EstadoCivil estadoCivil = (EstadoCivil) dynaForm.get("estadoCivil"); //obtém o objeto EstadoCivil do formulário dinâmico
		EstadoCivilDao estadoCivilDao = new EstadoCivilDao(); //cria uma instância de EstadoCivilDao para interagir com o banco de dados

		try //tente
		{
			if(estadoCivil.getCodigo() == 0) //se estadoCivil receber um valor igual a zero
			{
				estadoCivil.setCodigo(estadoCivilDao.getProxCodigo()); //se for zero, pega o próximo código disponível e define no objeto estadoCivil
			}
			estadoCivilDao.saveOrUpdate(estadoCivil); //salva ou atualiza o objeto estadoCivil no banco de dados
		}
		catch(Exception e) //em caso de erro no retorno do Banco de Dados
		{
			log.error("[InserirEstadoCivilAction] - erro ao inserir Produto: ", e); //registra o erro
			return mapping.findForward("failure"); //redireciona para a página de falha
		}
		addMessage(request,new ActionMessage("success.insert")); //adiciona uma mensagem de sucesso a solicitação 

		dynaForm.set("estadoCivil", new EstadoCivil()); //reseta o formulário com um novo objeto EstadoCivil
		return mapping.findForward("success"); //redireciona para a página de sucesso
	}
}
