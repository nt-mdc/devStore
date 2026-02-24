package br.com.senai.dev_store.config;


import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Papel:
 *  - Configuração de metadados para documentação OpenAPI/Swagger da API.
 * Camada:
 *  - Camada de configuração / infra de documentação.
 * Conexões:
 *  - Fornece informações que a biblioteca springdoc usa para expor documentação interativa.
 */

/*
 * @Configuration
 * - O que faz: Marca a classe como fonte de definições de beans/configurações para o Spring.
 * - Por que é necessária: Permite que o Spring reconheça a configuração ao escanear o pacote.
 * - Se removida: A definição OpenAPI pode não ser aplicada por padrão (dependendo da lib).
 *
 * @OpenAPIDefinition(...)
 * - O que faz: Define metadata (título, versão, descrição) que aparecerá na UI do Swagger.
 * - Por que: Ajuda a documentar a API automaticamente.
 * - Se removida: A UI ainda funcionaria, mas sem esses metadados personalizados.
 */
@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "DevStore API",
        version = "1.0",
        description = "API para o sistema DevStore"
    )
)
public class Swagger {

}
