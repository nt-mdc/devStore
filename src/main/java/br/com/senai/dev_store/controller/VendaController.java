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

import br.com.senai.dev_store.entity.Venda;
import br.com.senai.dev_store.exception.Response;
import br.com.senai.dev_store.repository.VendaRepository;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaRepository repository;

    @PostMapping
    public Response createVenda(@RequestBody Venda venda) {
        repository.save(venda);
        return new Response(201, "Venda created successfully");
    }

    @GetMapping
    public List<Venda> getAllVendas() {
        return repository.findAll();
    }

    @PutMapping("/{id}")
    public Response updateVenda(@PathVariable Long id, @RequestBody Venda updated) {
        if (!repository.existsById(id)) {
            return new Response(404, "Venda not found");
        }

        Venda venda = repository.findById(id).get();

        Optional.ofNullable(updated.getClientName())
                .ifPresent(venda::setClientName);

        Optional.ofNullable(updated.getSaleDate())
                .ifPresent(venda::setSaleDate);

        Optional.ofNullable(updated.getTotalValue())
                .ifPresent(venda::setTotalValue);

        Optional.ofNullable(updated.getProducts())
                .ifPresent(venda::setProducts);

        repository.save(venda);

        return new Response(200, "Venda updated successfully");
    }

    @DeleteMapping("/{id}")
    public Response deleteVenda(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return new Response(404, "Venda not found");
        }
        repository.deleteById(id);
        return new Response(204, "Venda deleted successfully");
    }
}
