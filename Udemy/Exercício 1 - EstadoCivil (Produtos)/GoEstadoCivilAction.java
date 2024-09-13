/*
 * Created on Jan 25, 2024
 */

package br.com.wises.uniodonto.estadoCivil; //nome do pacote a qual essa classe pertence

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import br.com.wises.BaseAction;
import br.com.wises.SessionManager;
import br.com.wises.paging.Page;
import br.com.wises.uniodonto.menu.TelaPermitida;
import br.com.wises.uniodonto.util.Key.ROLES;

/**
 * @author Tiago Depizzol
 * @since Jan 25, 20024
 */
public class GoEstadoCivilAction extends BaseAction //'GoEstadoCivilAction' é o nome da classe pública que herda 'BaseAction' como superclasse
{

	public GoEstadoCivilAction() //chama o construtor da super clase com os seguintes privilégios de acesso abaixo 'ADMINISTRACAO' e 'ATENDENTE'
	{
		super(new ROLES[] { ROLES.ADMINISTRACAO, ROLES.ATENDENTE });
	}

	@Override //sobrescreve um método da superclasse 
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, SessionManager session)
	{
		DynaActionForm dynaForm = (DynaActionForm) form;
		
		// Recupera as permissoes para a pagina requisitada e adiciona no dynaForm 
		TelaPermitida telaPermitida = super.getPermissaoPagina(request, session); //a variável 'telaPermitida' do tipo TelaPermitida, vai na superclasse e consulta no método se o usuários tem os privilégios necessários para o acesso
		if (!telaPermitida.verificarPermissao()) { //'!' inverte o resultado de 'telaPermitida', então, se for 'false', retorna 'true'
		    return mapping.findForward("accessDenied"); //caso o usuário não tenha a permissão de acesso, ele é negado
		}else {
			dynaForm.set("telaPermitida", telaPermitida);
		}

		if(request.getParameter("reset") != null)
			dynaForm.set("estadoCivil", new EstadoCivil());
		dynaForm.set("PAGEBEAN", new Page());
		if(request.getParameter("edit") != null)
			;

		return mapping.findForward("success");
	}
}
