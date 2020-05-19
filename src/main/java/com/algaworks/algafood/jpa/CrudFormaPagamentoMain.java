package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.repository.FormaPagamentoRepository;

public class CrudFormaPagamentoMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);

		// Listar todos os formaPagamento do banco de dados inicial
		List<FormaPagamento> formaPagamentosAntes = formaPagamentoRepository.listar();
		formaPagamentosAntes.forEach(formaPagamento -> {
			System.out.println("ID: " + formaPagamento.getId() + " - Nome: " + formaPagamento.getDescricao());
		});
		
		// Salvando 1 formaPagamento
		FormaPagamento formaPagamento1 = new FormaPagamento();
		formaPagamento1.setDescricao("Cheque à vista");
		
		FormaPagamento formaPagamento2 = new FormaPagamento();
		formaPagamento2.setDescricao("Cheque Pré-datado");
		
		formaPagamentoRepository.salvar(formaPagamento1);
		formaPagamentoRepository.salvar(formaPagamento2);
		
		// Listar todos os formaPagamento do banco de dados depois da inclusão
		List<FormaPagamento> formaPagamentosDepois = formaPagamentoRepository.listar();
		formaPagamentosDepois.forEach(formaPagamento -> {
			System.out.println("ID: " + formaPagamento.getId() + " - Nome: " + formaPagamento.getDescricao());
		});
		
		// Buscar restaurate com ID = 4
		FormaPagamento formaPagamentoBuscado= formaPagamentoRepository.buscar(4L);
		System.out.println("ID: " + formaPagamentoBuscado.getId() + " - Nome: " + formaPagamentoBuscado.getDescricao());
		
		FormaPagamento formaPagamento3 = new FormaPagamento();
		formaPagamento3.setId(formaPagamentoBuscado.getId());
		formaPagamento3.setDescricao("Promissória");
		
		// atualizar formaPagamento com ID = 4
		FormaPagamento formaPagamentoAlterado = formaPagamentoRepository.salvar(formaPagamento3);
		System.out.println("ID: " + formaPagamentoAlterado.getId() + " - Nome: " + formaPagamentoAlterado.getDescricao());
		
		
		// Excluir formaPagamento com ID = 5
		FormaPagamento formaPagamentoDeletado = new FormaPagamento();
		formaPagamentoDeletado.setId(5L);
		
		formaPagamentoRepository.remover(formaPagamentoDeletado);
		
		
		// Listar todos os formaPagamento do banco de dados
		List<FormaPagamento> formaPagamentos = formaPagamentoRepository.listar();
		formaPagamentos.forEach(formaPagamento -> {
			System.out.println("ID: " + formaPagamento.getId() + " - Nome: " + formaPagamento.getDescricao());
		});
	}
}
