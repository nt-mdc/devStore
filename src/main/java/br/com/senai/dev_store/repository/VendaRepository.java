package br.com.senai.dev_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.senai.dev_store.entity.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}
