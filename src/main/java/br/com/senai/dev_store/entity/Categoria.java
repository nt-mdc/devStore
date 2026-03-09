package br.com.senai.dev_store.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

/**
 * Papel:
 * - Representa a entidade "Categoria" mapeada para uma tabela no banco de dados.
 * Camada:
 * - Camada de persistência / Entity (modelo de domínio que o JPA persiste).
 * Conexões:
 * - Possui uma relação ManyToMany com Product: uma Categoria pode conter
 *   vários Produtos e um Produto pode pertencer a várias Categorias.
 * - É usada pelo JPA (via Repository) para ler/escrever dados no banco.
 *
 * Conceitos:
 * - @ManyToMany: Define que o relacionamento entre Categoria e Product é de
 *   "muitos para muitos". O JPA cria uma tabela intermediária para gerenciar
 *   essa relação.
 * - @JoinTable: Configura a tabela intermediária (categoria_product) com as
 *   colunas de chave estrangeira (categoria_id e product_id).
 */
@Entity
public class Categoria {

    /*
     * @Id
     * - Marca o campo como chave primária da entidade.
     *
     * @GeneratedValue
     * - Indica que o valor do id será gerado automaticamente pelo provedor JPA/DB.
     * - GenerationType.AUTO delega a escolha da estratégia ao provedor.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;

    /*
     * @ManyToMany
     * - O que faz: Indica que o relacionamento entre Categoria e Product é de
     *   "muitos para muitos". Ou seja, uma Categoria pode conter múltiplos Produtos,
     *   e um Produto pode pertencer a várias Categorias.
     *
     * @JoinTable
     * - O que faz: Como os bancos relacionais não suportam N:M diretamente, o JPA
     *   cria uma terceira tabela para nós.
     * - name: Define o nome real dessa tabela no banco (aqui, categoria_product).
     * - joinColumns: define a coluna que faz referência para a ENTIDADE ATUAL
     *   (categoria_id).
     * - inverseJoinColumns: define a coluna que faz referência para a OUTRA
     *   ENTIDADE da lista (product_id).
     */
    @ManyToMany
    @JoinTable(
        name = "categoria_product",
        joinColumns = @JoinColumn(name = "categoria_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonIgnoreProperties("categorias")
    private List<Product> products;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
