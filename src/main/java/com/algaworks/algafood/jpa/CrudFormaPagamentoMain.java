package com.algaworks.algafood.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

public class CrudFormaPagamentoMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);

		// Listar todos os formaPagamento do banco de dados inicial
		List<FormaPagamento> formaPagamentosAntes = formaPagamentoRepository.findAll();
		formaPagamentosAntes.forEach(formaPagamento -> {
			System.out.println("ID: " + formaPagamento.getId() + " - Nome: " + formaPagamento.getDescricao());
		});
		
		// Salvando 1 formaPagamento
		FormaPagamento formaPagamento1 = new FormaPagamento();
		formaPagamento1.setDescricao("Cheque à vista");
		
		FormaPagamento formaPagamento2 = new FormaPagamento();
		formaPagamento2.setDescricao("Cheque Pré-datado");
		
		formaPagamentoRepository.save(formaPagamento1);
		formaPagamentoRepository.save(formaPagamento2);
		
		// Listar todos os formaPagamento do banco de dados depois da inclusão
		List<FormaPagamento> formaPagamentosDepois = formaPagamentoRepository.findAll();
		formaPagamentosDepois.forEach(formaPagamento -> {
			System.out.println("ID: " + formaPagamento.getId() + " - Nome: " + formaPagamento.getDescricao());
		});
		
		// Buscar restaurate com ID = 4
		Optional<FormaPagamento> formaPagamentoBuscado= formaPagamentoRepository.findById(4L);
		System.out.println("ID: " + formaPagamentoBuscado.get().getId() + " - Nome: " + formaPagamentoBuscado.get().getDescricao());
		
		FormaPagamento formaPagamento3 = new FormaPagamento();
		formaPagamento3.setId(formaPagamentoBuscado.get().getId());
		formaPagamento3.setDescricao("Promissória");
		
		// atualizar formaPagamento com ID = 4
		FormaPagamento formaPagamentoAlterado = formaPagamentoRepository.save(formaPagamento3);
		System.out.println("ID: " + formaPagamentoAlterado.getId() + " - Nome: " + formaPagamentoAlterado.getDescricao());
		
		
		// Excluir formaPagamento com ID = 5
		FormaPagamento formaPagamentoDeletado = new FormaPagamento();
		formaPagamentoDeletado.setId(5L);
		
		formaPagamentoRepository.deleteById(formaPagamentoDeletado.getId());
		
		
		// Listar todos os formaPagamento do banco de dados
		List<FormaPagamento> formaPagamentos = formaPagamentoRepository.findAll();
		formaPagamentos.forEach(formaPagamento -> {
			System.out.println("ID: " + formaPagamento.getId() + " - Nome: " + formaPagamento.getDescricao());
		});
	}
}
