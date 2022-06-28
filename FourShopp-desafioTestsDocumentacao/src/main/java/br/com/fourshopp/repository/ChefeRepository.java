package br.com.fourshopp.repository;

import br.com.fourshopp.entities.Administrador;
import br.com.fourshopp.entities.Chefe;
import br.com.fourshopp.entities.Funcionario;
import br.com.fourshopp.entities.Operador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChefeRepository extends JpaRepository<Chefe, Long> {

    @Query("Select c From Chefe c where c.cpf = ?1 and c.password = ?2")
    Chefe findByCpfAndPassword(@Param("cpf") String cpf, @Param("password") String password);
	
	 //Chefe findByCpfAndSenha(String cpf, String senha);
}
