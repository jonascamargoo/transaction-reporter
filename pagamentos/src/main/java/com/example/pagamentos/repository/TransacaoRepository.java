package com.example.pagamentos.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.pagamentos.entities.Transacao;

public interface TransacaoRepository extends CrudRepository<Transacao, Long> {
    // listando todas as lojas em ordem alfabetica e mostrando as transacoes mais recentes primeiro. O comando SQL equivalente montado pelo spring data eh: SELECT * FROM transacao ORDER BY nome_loja ASC, id DESC;
    List<Transacao> findAllByOrderByNomeDaLojaAscIdDesc();
}
