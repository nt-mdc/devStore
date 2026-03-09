package br.com.senai.dev_store.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.senai.dev_store.entity.Categoria;
import br.com.senai.dev_store.exception.Response;
import br.com.senai.dev_store.repository.CategoriaRepository;

/**
 * Papel:
 * - Controller que expõe endpoints REST para operações CRUD em Categoria.
 * Camada:
 * - Camada Controller / API.
 * Conexões:
 * - Recebe requisições HTTP, chama o CategoriaRepository para persistência e
 *   retorna DTOs/respostas.
 *
 * Endpoints disponíveis:
 * - POST   /categorias        → Criar nova categoria (com ou sem produtos associados)
 * - GET    /categorias        → Listar todas as categorias (com seus produtos)
 * - PUT    /categorias/{id}   → Atualizar categoria (nome, descrição e/ou produtos)
 * - DELETE /categorias/{id}   → Deletar categoria
 *
 * Nota sobre @ManyToMany:
 * - Ao criar ou atualizar uma Categoria, você pode enviar uma lista de produtos
 *   no JSON. Cada produto deve conter pelo menos o "id" para que o JPA consiga
 *   fazer a associação na tabela intermediária (categoria_product).
 *
 * Exemplo de JSON para POST/PUT:
 * {
 *   "name": "Eletrônicos",
 *   "description": "Produtos eletrônicos em geral",
 *   "products": [
 *     { "id": 1 },
 *     { "id": 3 }
 *   ]
 * }
 */
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository repository;

    /**
     * Criar categoria
     * - Recebe um JSON com dados da categoria (e opcionalmente uma lista de products).
     * - Persiste no banco e retorna um feedback.
     *
     * Como funciona a associação ManyToMany no POST:
     * - Se o JSON contiver "products": [{"id": 1}, {"id": 3}], o JPA vai
     *   inserir registros na tabela intermediária categoria_product vinculando
     *   esta nova categoria aos produtos com id 1 e 3.
     */
    @PostMapping
    public Response createCategoria(@RequestBody Categoria categoria) {
        repository.save(categoria);
        return new Response(201, "Categoria created successfully");
    }

    /**
     * Listar todas as categorias
     * - Retorna todas as categorias com seus produtos associados.
     */
    @GetMapping
    public List<Categoria> getAllCategorias() {
        return repository.findAll();
    }

    /**
     * Atualizar categoria parcialmente
     * - Atualiza campos informados de uma categoria existente.
     * - Utiliza Optional.ofNullable para atualização parcial (PATCH-style):
     *   apenas os campos enviados no JSON serão atualizados.
     */
    @PutMapping("/{id}")
    public Response updateCategoria(@PathVariable Long id, @RequestBody Categoria updated) {
        if (!repository.existsById(id)) {
            return new Response(404, "Categoria not found");
        }

        Categoria categoria = repository.findById(id).get();

        Optional.ofNullable(updated.getName())
                .ifPresent(categoria::setName);

        Optional.ofNullable(updated.getDescription())
                .ifPresent(categoria::setDescription);

        Optional.ofNullable(updated.getProducts())
                .ifPresent(categoria::setProducts);

        repository.save(categoria);

        return new Response(200, "Categoria updated successfully");
    }

    /**
     * Deletar categoria
     * - Remove uma categoria pelo id.
     * - A tabela intermediária (categoria_product) também terá os registros
     *   correspondentes removidos automaticamente pelo JPA.
     */
    @DeleteMapping("/{id}")
    public Response deleteCategoria(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return new Response(404, "Categoria not found");
        }
        repository.deleteById(id);
        return new Response(204, "Categoria deleted successfully");
    }
}
