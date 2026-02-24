package br.com.senai.dev_store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Papel:
 *  - Classe de entrada da aplicação Spring Boot. Contém o método main que inicializa
 *    o contexto do Spring e sobe o servidor embutido (por exemplo, Tomcat).
 * Camada:
 *  - Camada de inicialização / configuração da aplicação.
 * Conexões:
 *  - Não depende de classes de negócio diretamente, mas ao iniciar o contexto do Spring
 *    todas as beans (Controllers, Repositories, Services, Entities, etc.) serão
 *    detectadas e inicializadas conforme configuração.
 */

/*
 * @SpringBootApplication
 * - O que faz: Combina @Configuration, @EnableAutoConfiguration e @ComponentScan.
 * - Por que: Permite que o Spring Boot configure automaticamente a aplicação
 *   (datasource, MVC, JPA, etc.) e faça scan de componentes no package atual e subpackages.
 * - Se removida: O aplicativo não fará auto-configuração; seria necessário configurar
 *   manualmente muitas coisas (ou anotar com as anotações combinadas).
 */

@SpringBootApplication
public class DevStoreApplication {

	/**
	 * Método main
	 * - Responsabilidade: Ponto de entrada da JVM. Chama SpringApplication.run para criar
	 *   e inicializar o ApplicationContext do Spring.
	 * - Parâmetros: String[] args (argumentos de linha de comando, repassados ao Spring).
	 * - Retorno: void. O processo permanece vivo enquanto o servidor embutido estiver em execução.
	 * - Comunicação: Ao chamar SpringApplication.run, o Spring inicializa todas as beans,
	 *   aplica configurações e expõe endpoints HTTP.
	 *
	 * Conceito importante:
	 * - Inversão de Controle (IoC) / Contêiner Spring: você não instancia controllers
	 *   manualmente; o Spring cria e injeta dependências automaticamente.
	 */
	public static void main(String[] args) {
		SpringApplication.run(DevStoreApplication.class, args);
	}

}
