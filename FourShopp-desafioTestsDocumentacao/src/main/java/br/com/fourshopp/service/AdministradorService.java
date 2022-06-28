package br.com.fourshopp.service;

import br.com.fourshopp.entities.Administrador;
import br.com.fourshopp.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    public Administrador save(Administrador administrador){
        return administradorRepository.save(administrador);
    }

    public Administrador findById(Long id){
        return administradorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado"));
    }

    public List<Administrador> listAll(){
        return administradorRepository.findAll();
    }

    public void remove(Long id){
        administradorRepository.deleteById(id);
    }

    public Optional<Administrador>loadByCpfAndPassword(String cpf, String password){
        return Optional.ofNullable(administradorRepository.findByCpfAndPassword(cpf, password));
    }


    public List<Administrador> findAll() {
        return administradorRepository.findAll();
    }
}
