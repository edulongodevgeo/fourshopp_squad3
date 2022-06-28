package br.com.fourshopp.repository;

import br.com.fourshopp.entities.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
//    @Query("Select c From Administrador c where c.cpf = ?1 and c.password = ?2")
//    Administrador findByCpfAndPassword(@Param("cpf") String cpf, @Param("password") String password);

	Administrador findByCpfAndSenha(String cpf, String senha);

}
