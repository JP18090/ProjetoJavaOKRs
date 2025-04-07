package com.example.okr.controller;

import com.example.okr.model.Objetivo;
import com.example.okr.repository.ObjetivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/objetivos")
public class ObjetivoController {

    @Autowired
    private ObjetivoRepository repository;

    @GetMapping
    public List<Objetivo> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Objetivo> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(objetivo -> ResponseEntity.ok().body(objetivo))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Objetivo criar(@RequestBody Objetivo objetivo) {
        return repository.save(objetivo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Objetivo> atualizar(@PathVariable Long id, @RequestBody Objetivo dadosAtualizados) {
        return repository.findById(id)
                .map(obj -> {
                    obj.setTitulo(dadosAtualizados.getTitulo());
                    obj.setDescricao(dadosAtualizados.getDescricao());
                    obj.setPorcentagemConclusao(dadosAtualizados.getPorcentagemConclusao());
                    return ResponseEntity.ok(repository.save(obj));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return repository.findById(id)
                .map(obj -> {
                    repository.delete(obj);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}