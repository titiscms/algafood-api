package com.algaworks.algafood.infrastructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafood.domain.service.EnvioEmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService implements EnvioEmailService {
	
	@Autowired
	private ProcessorEmailTemplate processorEmailTemplate;
	
	@Override
	public void enviar(Mensagem mensagem) {
		String corpo = processorEmailTemplate.processarTemplate(mensagem);
		
		log.info("\n[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
	}
}