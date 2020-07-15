package com.algaworks.algafood.core.email;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Valid
@Setter
@Getter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

	@NotNull
	private String remetente;
	private TipoEmail impl = TipoEmail.FAKE;
	private Sandbox sandbox = new Sandbox();;
	
	public enum TipoEmail {
		
		SMTP, FAKE, SANDBOX
	}
	
	@Setter
	@Getter
	public class Sandbox {
		
		@NonNull
		private String destinatario;
	}
}
