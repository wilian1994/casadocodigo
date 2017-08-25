package br.com.casadocodigo.loja.conf;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.CarrinhoCompras;

@ComponentScan(basePackageClasses={HomeController.class, ProdutoDAO.class, FileSaver.class, CarrinhoCompras.class}) //Para o spring sabe quais classes ele terá que controlar, ou seja "varrer"
@EnableWebMvc 
public class AppWebConfiguration extends WebMvcConfigurerAdapter{

	@Bean //Annotacion que informa que será gerenciado pelo spring.
	public InternalResourceViewResolver internalResourceViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/"); //Configura o diretório no qual encontra-se nossas pages
		resolver.setSuffix(".jsp");  //Configura a extensão do arquivo, evita a necessidade de digitar toda vez o .jsp
		 resolver.setExposedContextBeanNames("carrinhoCompras");
		return resolver;
	}


	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource =
				new ReloadableResourceBundleMessageSource();

		messageSource.setBasename("/WEB-INF/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1);

		return messageSource;
	}
	
	@Bean
	public RestTemplate restTemplate() {
	        return new RestTemplate();
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean  //formatando e deixando um padrão para as datas;
	public FormattingConversionService mvcConversionService(){
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		DateFormatterRegistrar formatterRegistrar = new DateFormatterRegistrar();
		formatterRegistrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
		formatterRegistrar.registerFormatters(conversionService);

		return conversionService;
	}

	@Bean
	public MultipartResolver multipartResolver(){
		return new StandardServletMultipartResolver();
	}


}
