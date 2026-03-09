package br.com.senai.dev_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.senai.dev_store.entity.Categoria;

/**
 * Papel:
 * - Interface que expõe operações de persistência para a entidade Categoria.
 * Camada:
 * - Camada de persistência / Repository.
 * Conexões:
 * - Usada pelo CategoriaController para realizar CRUD no banco sem escrever SQL.
 *
 * Conceito:
 * - Repository Pattern: abstrai acesso a dados; JpaRepository já implementa
 *   métodos comuns (save, findAll, findById, deleteById, existsById, etc.).
 * - O Spring Data gera a implementação automaticamente em tempo de execução.
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
