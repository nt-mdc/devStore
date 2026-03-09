package br.com.senai.dev_store.entity;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.List;

@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String clientName;
    private LocalDate saleDate;
    private Double totalValue;

    /*
     * @ManyToMany
     * - O que faz: Indica que o relacionamento entre Venda e Product é de
     * "muitos para muitos".
     * Ou seja, uma Venda engloba múltiplos Produtos, e um Produto pode aparecer em
     * várias Vendas.
     * 
     * @JoinTable
     * - O que faz: Como os bancos relacionais não suportam N:M diretamente, o JPA
     * cria uma terceira tabela para nós.
     * - name: Define o nome real dessa tabela no banco (aqui, venda_product).
     * - joinColumns: define a coluna que faz referência para a ENTIDADE ATUAL
     * (venda_id).
     * - inverseJoinColumns: define a coluna que faz referência para a OUTRA
     * ENTIDADE da lista (product_id).
     */
    @ManyToMany
    @JoinTable(name = "venda_product", joinColumns = @JoinColumn(name = "venda_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
