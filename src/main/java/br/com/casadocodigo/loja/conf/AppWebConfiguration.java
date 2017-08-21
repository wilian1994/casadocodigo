package br.com.casadocodigo.loja.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.dao.ProdutoDAO;

@ComponentScan(basePackageClasses={HomeController.class, ProdutoDAO.class}) //Para o spring sabe quais classes ele terá que controlar, ou seja "varrer"
@EnableWebMvc 
public class AppWebConfiguration extends WebMvcConfigurerAdapter{

	@Bean //Annotacion que informa que será gerenciado pelo spring.
	public InternalResourceViewResolver internalResourceViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/"); //Configura o diretório no qual encontra-se nossas pages
		resolver.setSuffix(".jsp");  //Configura a extensão do arquivo, evita a necessidade de digitar toda vez o .jsp

		return resolver;
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	

}
