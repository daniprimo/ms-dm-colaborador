package br.com.drogariamenk.msdmcolaborador.aplicacao.controller;

import br.com.drogariamenk.msdmcolaborador.aplicacao.service.ColaboradorService;
import br.com.drogariamenk.msdmcolaborador.dto.request.AtualizarColaboradorRequest;
import br.com.drogariamenk.msdmcolaborador.dto.request.ColaboradorSalvarRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/colaborador")
public class ColaboradorController {

    @Autowired
    private ColaboradorService colaboradorService;

    @PostMapping
    public ResponseEntity salvarUmNovoColaborador(@RequestBody ColaboradorSalvarRequest request) {
        return ResponseEntity.ok(colaboradorService.salvarColaborador(request));
    }

    @GetMapping
    public ResponseEntity listarTodosOsColaboradores() {
        return ResponseEntity.ok(colaboradorService.listarColaboradores());
    }

    @GetMapping("/buscarPeloCpf/{cpf}")
    public ResponseEntity buscarClientePeloCpf(@PathVariable String cpf) {
        Object coalboradorEncontrado = colaboradorService.buscarColaboradorPeloCpf(cpf);
        return ResponseEntity.ok(coalboradorEncontrado);
    }

    @GetMapping("/buscarPeloTelefone/{telefone}")
    public ResponseEntity buscarClientePeloTelefone(@PathVariable String telefone) {
        Object coalboradorEncontrado = colaboradorService.buscarColaboradorPeloTelefone(telefone);
        return ResponseEntity.ok(coalboradorEncontrado);
    }

    @PutMapping("atualizarColaboradorPeloCpf/{cpf}")
    public ResponseEntity atualizarColaboradorPeloCpf(
            @PathVariable String cpf,
            @RequestBody AtualizarColaboradorRequest request
    ){
        return ResponseEntity.ok(colaboradorService.atualizarColaboradorPeloCpf(cpf, request));
    }

    @PutMapping("atualizarColaboradorPeloTelefone/{telefone}")
    public ResponseEntity atualizarClientePeloTelefone(
            @PathVariable String telefone,
            @RequestBody AtualizarColaboradorRequest request
    ){
        return ResponseEntity.ok(colaboradorService
                .atualizarColaboradorPeloTelefone(telefone, request));
    }

    @DeleteMapping("deletarColaboradorPeloCpf/{cpf}")
    public ResponseEntity removerColaboradorPeloCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(colaboradorService.deletarColaboradorPeloCpf(cpf));
    }

    @DeleteMapping("deletarColaboradorPeloTelefone/{telefone}")
    public ResponseEntity removerColaboradorPeloTelefone(@PathVariable String telefone) {
        return ResponseEntity.ok(colaboradorService
                .deletarColaboradorPeloTelefone(telefone));
    }

}
