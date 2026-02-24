package br.com.senai.dev_store.controller;

/*
 * Pacote: br.com.senai.dev_store.controller
 * Aqui ficam as classes responsáveis por receber requisições HTTP
 * e orquestrar chamadas para a camada de persistência (Repository)
 */

import java.util.List;

/* Imports do Spring e utilitários usados pelo controller */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* Import da entidade e do repositório que interagem com o banco */
import br.com.senai.dev_store.entity.Product;
import br.com.senai.dev_store.exception.Response;
import br.com.senai.dev_store.repository.ProductRepository;

/**
 * Papel:
 *  - Controller que expõe endpoints REST para operações básicas (CRUD) em Product.
 * Camada:
 *  - Camada Controller / API.
 * Conexões:
 *  - Recebe requisições HTTP, chama o ProductRepository para persistência e retorna DTOs/respostas.
 *
 * Nota didática:
 *  - O Controller é o ponto de entrada para requisições HTTP. Ele deve ser "fino":
 *    delega lógica para Services (quando existirem). Aqui, por simplicidade, usa diretamente o Repository.
 */

/*
 * @RestController
 * - O que faz: Combina @Controller + @ResponseBody. Indica que os retornos dos métodos
 *   devem ser serializados (por exemplo, para JSON) e enviados no corpo da resposta HTTP.
 * - Por que: Para que Spring entenda que esta classe expõe endpoints REST.
 * - Se removida: Métodos não seriam tratados como endpoints REST automaticamente; você precisaria de @Controller e @ResponseBody.
 *
 * @RequestMapping("/products")
 * - O que faz: Define o caminho base para todos os métodos (ex.: GET /products).
 * - Por que: Organiza URLs relacionadas sob um mesmo prefixo.
 * - Se removida: Cada método precisaria conter o caminho completo em suas anotações.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    /*
     * @Autowired
     * - O que faz: Injeta automaticamente a dependência (ProductRepository) gerenciada pelo Spring.
     * - Por que: Para usar o repository sem instanciá-lo manualmente.
     * - Se removida: repository seria nulo a menos que fosse inicializado manualmente (ex.: via construtor).
     *
     * Conceito:
     *  - Injeção de Dependência (DI): o contêiner do Spring fornece as dependências que a classe precisa.
     *  - Benefício: desacoplamento e facilidade de teste (podemos injetar mocks).
     */
    @Autowired
    private ProductRepository repository;

    /**
     * Criar produto
     * - Responsabilidade: Recebe um JSON com dados do produto, persiste no banco e retorna um feedback.
     * - Parâmetros: @RequestBody Product product -> o Spring desserializa o JSON automaticamente para um objeto Product.
     * - Retorno: Response (DTO) com código e mensagem explicativa.
     * - Comunicação: Chama repository.save(product) para persistir.
     *
     * @PostMapping
     * - O que faz: Associa esse método ao verbo HTTP POST no caminho base ("/products").
     * - Se removido: Esse método não seria chamado para requisições POST.
     */
    @PostMapping
    public Response createProduct(@RequestBody Product product) {
        // Salvamos o produto no banco usando o repository (JPA).
        // repository.save persiste tanto para insert quanto update (dependendo do id).
        repository.save(product);
        return new Response(201, "Product created successfully");
    }

    /**
     * Listar todos os produtos
     * - Responsabilidade: Buscar todos os registros de Product no banco.
     * - Parâmetros: nenhum.
     * - Retorno: List<Product> contendo todas as entidades encontradas; será convertido para JSON.
     * - Comunicação: Usa repository.findAll() que executa uma query simples SELECT *.
     *
     * @GetMapping
     * - O que faz: Associa ao verbo HTTP GET no caminho "/products".
     */
    @GetMapping
    public List<Product> getAllProducts() {
        // findAll consulta todos os produtos. Em aplicações reais, cuidado com listas muito grandes
        // (paginacão é recomendada).
        return repository.findAll();
    }

    /**
     * Atualizar produto parcialmente
     * - Responsabilidade: Atualizar campos informados de um produto existente.
     * - Parâmetros:
     *    @PathVariable Long id -> extraído da URL (/products/{id}), identifica o recurso a atualizar.
     *    @RequestBody Product updated -> conteúdo enviado pelo cliente com os novos valores.
     * - Retorno: Response com código e mensagem.
     * - Comunicação: Verifica existência com existsById, busca com findById e salva as mudanças.
     *
     * @PutMapping("/{id}")
     * - O que faz: Associa ao verbo HTTP PUT no caminho "/products/{id}".
     */
    @PutMapping("/{id}")
    public Response updateProduct(@PathVariable Long id, @RequestBody Product updated) {

        // Verifica se o produto existe antes de tentar atualizar.
        if (!repository.existsById(id)) {
            // Se não existe, retornamos 404 indicando que o recurso não foi encontrado.
            return new Response(404, "Product not found");
        }

        // Busca o produto existente. Aqui usamos get() porque já garantimos que existe.
        // Em código de produção é melhor usar Optional#orElseThrow para clareza.
        Product product = repository.findById(id).get();

        // Atualizamos apenas os campos que foram enviados (patch-like).
        // Comentário importante: ao alterar campos da entidade, é necessário salvar novamente
        // para que as mudanças sejam persistidas no banco.
        if (updated.getDescription() != null) {
            product.setDescription(updated.getDescription());
        }

        if (updated.getPrice() != null) {
            product.setPrice(updated.getPrice());
        }

        if (updated.getReleaseDate() != null) {
            product.setReleaseDate(updated.getReleaseDate());
        }

        // Salva as mudanças no banco. Sem essa chamada, as alterações podem não ser persistidas
        // (dependendo do contexto de transação e do estado da entidade).
        repository.save(product);

        return new Response(200, "Product updated successfully");
    }

    /**
     * Deletar produto
     * - Responsabilidade: Remover um produto pelo id.
     * - Parâmetros: @PathVariable Long id -> id do produto a remover.
     * - Retorno: Response com código e mensagem.
     * - Comunicação: Verifica existência e chama repository.deleteById(id).
     *
     * @DeleteMapping("/{id}")
     * - O que faz: Associa ao verbo HTTP DELETE no caminho "/products/{id}".
     */
    @DeleteMapping("/{id}")
    public Response deleteProduct(@PathVariable Long id) {
        // Verifica existência para retornar um erro amigável se não encontrado.
        if (!repository.existsById(id)) {
            return new Response(404, "Product not found");
        }
        // Remove o registro; se ocorrer exceção (ex.: constraint), seria interessante tratar.
        repository.deleteById(id);
        // 204 com mensagem textual aqui é informativo; em APIs REST reais 204 costuma retornar sem corpo.
        return new Response(204, "Product deleted successfully");
    }

}
