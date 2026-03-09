package br.com.senai.dev_store.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;

/**
 * Papel:
 * - Representa a entidade "Product" mapeada para uma tabela no banco de dados.
 * Camada:
 * - Camada de persistência / Entity (modelo de domínio que o JPA persiste).
 * Conexões:
 * - É usada pelo JPA (via Repository) para ler/escrever dados no banco.
 *
 * Conceitos:
 * - JPA (Java Persistence API): permite mapear classes Java para tabelas
 * relacionais.
 * - Jackson serializa/deserializa este objeto para/desde JSON quando ele
 * circula pela API.
 */

/*
 * @Entity
 * - O que faz: Indica que a classe é uma entidade JPA e que deve ser mapeada
 * para uma tabela.
 * - Por que é necessária: Sem ela, o JPA não consideraria essa classe para
 * persistência.
 * - Se removida: Você não poderia salvar/consultar objetos Product via JPA
 * automaticamente.
 */
@Entity
public class Product {

    /*
     * @Id
     * - Marca o campo como chave primária da entidade.
     * 
     * @GeneratedValue
     * - Indica que o valor do id será gerado automaticamente pelo provedor JPA/DB.
     *
     * Observação:
     * - GenerationType.AUTO delega a escolha da estratégia ao provedor (ex.:
     * sequence, identity).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;
    private LocalDate releaseDate;
    private Double price;

    /*
     * @Column(unique = true)
     * - O que faz: Garante no banco de dados que não haverão dois produtos
     * com o mesmo código de barras. Se tentarmos salvar um duplicado,
     * o banco de dados rejeita a operação.
     * 
     * Relações Básicas:
     * - @ManyToMany (usada em Venda): Vendas têm vários Produtos e Produtos podem
     * estar em múltiplas Vendas. Uma tabela extra é criada para gerenciar os IDs.
     */
    @Column(unique = true)
    private String barcode;

    /*
     * @ManyToMany(mappedBy = "products")
     * - O que faz: Define o lado INVERSO da relação ManyToMany com Categoria.
     * - mappedBy = "products": indica que o campo "products" na entidade Categoria
     *   é o dono da relação (quem possui @JoinTable).
     * - Isso torna a relação bidirecional: a partir de um Product, podemos acessar
     *   suas Categorias.
     *
     * @JsonIgnoreProperties("products")
     * - O que faz: Evita recursão infinita na serialização JSON.
     * - Sem essa anotação, ao serializar um Product, o Jackson tentaria serializar
     *   suas Categorias, que por sua vez tentariam serializar seus Products,
     *   causando um loop infinito (StackOverflowError).
     */
    @ManyToMany(mappedBy = "products")
    @JsonIgnoreProperties("products")
    private List<Categoria> categorias;

    /*
     * Getters e Setters:
     * - São necessários para que o JPA e o mecanismo de serialização (Jackson)
     * possam
     * acessar e modificar os campos.
     * - Concept note: Em projetos reais, você pode preferir imutabilidade / DTOs
     * para
     * entrada/saída e deixar a entidade mais restrita. Aqui usamos o estilo
     * clássico.
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
}
