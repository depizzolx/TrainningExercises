/*
 * Created on Jul 25, 2024
 */

package br.com.wises.uniodonto.estadoCivil; //nome do pacote ao qual a classe pertence

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.wises.SessionManager;
import br.com.wises.paging.PagedListAction; //importa a superclasse para esse arquivo
import br.com.wises.uniodonto.util.Key.ROLES;

/**
 * @author Tiago Depizzol
 * @since Jul 25, 2024
 */
public class ListarEstadoCivilAction extends PagedListAction //o nome da classe pública é 'ListarEstadoCivilAction', e herda métodos da superclasse 'PagedListAction'
{

	public ListarEstadoCivilAction() //chama o construtor da superclasse
	{
		super(new ROLES[] {}); //gerencia as regras de acesso, passando os parâmetros de permissões. aqui não é passado nenhum parâmetro
	}

	@Override //o método abaixo sobrescreve um método da superclasse
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, SessionManager session) //padrão do struts para páginas web da dental
	{
		try
		{
			EstadoCivilDao estadoCivilDao = new EstadoCivilDao(); //cria uma nova instância (objeto da classe em questão) de 'EstadoCivilDao' para interagir com o Banco de Dados
																  //através de 'EstadoCivilDao()'. é chamado o construtor de 'EstadoCivilDao'
																  //o nome dessa instância é 'estadoCivilDao'
			List<EstadoCivil> lista = estadoCivilDao.listAllPaged(this.getPage(request)); //cria uma váriavel de listar chamada 'lista', que armazena objetos do tipo 'EstadoCivil'
																						  //lista a página através do método 'getPage'
			this.getPage(request).setActionBack("/GoEstadoCivil.do"); //isso configura o objeto Page para que, quando necessário, ele saiba que a URL de retorno é "/GoEstadoCivil.do"
			super.setPagedResults(request,lista); //aqui estamos chamando o método 'setPagedResults' da superclasse 'PagedListAction', passando dois argumentos, que é o 'request' e a 'lista'.

			return mapping.findForward("success"); //redireciona para a página de sucesso
		}
		catch(Exception e) //em caso de excessão 
		{
			log.error("[ListarEstadoCivil]-  erro ao listar Produto: ", e); //gera uma log de erro com a mensagem em questão
			return mapping.findForward("failure"); //redireciona para a página de errro
		}
	}
}
