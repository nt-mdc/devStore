package br.com.senai.dev_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.senai.dev_store.entity.Product;

/**
 * Papel:
 * - Interface que expõe operações de persistência para a entidade Product.
 * Camada:
 * - Camada de persistência / Repository.
 * Conexões:
 * - Usada por Controllers ou Services para realizar CRUD no banco sem escrever
 * SQL.
 *
 * Conceito:
 * - Repository Pattern: abstrai acesso a dados; JpaRepository já implementa
 * métodos
 * comuns (save, findAll, findById, deleteById, existsById, etc.).
 */

/*
 * Extender JpaRepository<Product, Long> fornece implementações prontas para
 * operações
 * de persistência. Não precisamos criar uma classe concreta: o Spring Data gera
 * a
 * implementação automaticamente em tempo de execução.
 *
 * Se removêssemos essa interface, teríamos que implementar manualmente a lógica
 * de acesso
 * a dados (DAO) ou usar EntityManager diretamente.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByBarcode(String barcode);
}
