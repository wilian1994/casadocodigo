package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;


@Controller
@RequestMapping("/produtos")
public class ProdutosController {

	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.addValidators(new ProdutoValidation());
	}
	@Autowired
	private ProdutoDAO dao;
	@Autowired
	private FileSaver saver;

	@RequestMapping("/form")
	public ModelAndView form(Produto prod) {

		ModelAndView modelAndView = new ModelAndView("produtos/form");
		modelAndView.addObject("tipos", TipoPreco.values());

		return modelAndView;

	}

	@RequestMapping(method=RequestMethod.POST)
	@CacheEvict(value="produtosHome", allEntries=true)
	public ModelAndView gravar(MultipartFile sumario,@Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes) {

		if(result.hasErrors()) {
			return form(produto);
		}

		String sumarioPath = saver.write("arquivos-sumario", sumario);

		produto.setSumarioPath(sumarioPath);


		System.out.println(sumario.getOriginalFilename());

		dao.gravar(produto);
		redirectAttributes.addFlashAttribute("message","Produto cadastrado com sucesso");
		return new ModelAndView("redirect:produtos");
	}

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("produtos/lista");
		List<Produto> produtos = dao.listar();
		modelAndView.addObject("produtos", produtos);

		return modelAndView;
	}

	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable("id") Integer id){
		ModelAndView modelAndView = new ModelAndView("/produtos/detalhe");
		Produto produto = dao.find(id);
		modelAndView.addObject("produto", produto);
		return modelAndView;
	}
	
	@RequestMapping("/{id}")
	@ResponseBody
	public Produto detalheJson(@PathVariable("id") Integer id){
		return dao.find(id);
	}


}