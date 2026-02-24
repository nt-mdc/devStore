package br.com.senai.dev_store.exception;

/**
 * Papel:
 *  - DTO simples para padronizar respostas de operações (sucesso/erro) com código e mensagem.
 * Camada:
 *  - Camada de transporte / DTO (Data Transfer Object).
 * Conexões:
 *  - Usado pelos controllers para retornar feedback simples ao cliente em formato JSON.
 *
 * Observação didática:
 *  - DTOs são classes simples que representam a estrutura de dados que enviaremos/receberemos
 *    pela rede. Isso facilita padronização e evolução da API sem expor entidades internas.
 */

/*
 * Esta classe não tem anotações Spring porque é apenas um POJO que será automaticamente
 * convertido para JSON pela infraestrutura do Spring MVC (Jackson), quando retornada
 * por um controller (quando o controller executar serialização).
 */
public class Response {
    private int code;
    private String message;

    /**
     * Construtor
     * - Recebe código numérico (ex.: 200, 201, 404) e uma mensagem legível.
     * - Usamos números semelhantes a códigos HTTP para facilitar entendimento.
     */
    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /* Getters e setters: necessários para o mecanismo de serialização (Jackson) acessar os valores.
       Conceito: Serialização JSON -> o Spring usa bibliotecas (Jackson) para transformar objetos Java
       em JSON automaticamente antes de enviar a resposta HTTP. */
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
