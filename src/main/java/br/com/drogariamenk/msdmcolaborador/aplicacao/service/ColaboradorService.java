package br.com.drogariamenk.msdmcolaborador.aplicacao.service;

import br.com.drogariamenk.msdmcolaborador.dto.request.AtualizarColaboradorRequest;
import br.com.drogariamenk.msdmcolaborador.dto.request.ColaboradorSalvarRequest;
import org.springframework.stereotype.Service;

@Service
public interface ColaboradorService<T> {

    public T salvarColaborador(ColaboradorSalvarRequest request);
    public T listarColaboradores();
    public T buscarColaboradorPeloTelefone(String telefone);
    public T buscarColaboradorPeloCpf(String cpf);

    public T atualizarColaboradorPeloCpf(String cpf, AtualizarColaboradorRequest request);
    public T atualizarColaboradorPeloTelefone(String telefone, AtualizarColaboradorRequest request);

    public T deletarColaboradorPeloTelefone(String telefone);
    public T deletarColaboradorPeloCpf(String cpf);



}
