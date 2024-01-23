package br.com.drogariamenk.msdmcolaborador.infra.repository;

import br.com.drogariamenk.msdmcolaborador.dominio.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {
}
