package br.com.fourshopp.service;

import br.com.fourshopp.entities.Chefe;
import br.com.fourshopp.entities.Operador;
import br.com.fourshopp.repository.OperadorRespository;
import br.com.fourshopp.repository.PessoaRepository;
import br.com.fourshopp.repository.enderecoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class OperadorService {

    @Autowired
    private OperadorRespository operadorRepository;
    
    @Autowired
    private enderecoRepository enderecoRepository;
    
    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Transactional
    public Operador save(Operador operador){
    	enderecoRepository.save(operador.getEndereco());
    	System.out.println(operador.toString());
        return operadorRepository.save(operador);
    }


    public Operador create(Operador operador){
        return operadorRepository.save(operador);
    }

    public Operador findById(Long id){
        return operadorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado"));
    }

    public List<Operador> listAll(){
        return operadorRepository.findAll();
    }

    public void remove(Long id){
        operadorRepository.deleteById(id);
    }

    public Operador update(Operador operador, Long id){
        Operador found = findById(id);
        found.setCargo(operador.getCargo());
        found.setSalario(operador.getSalario());
        found.setCelular(operador.getCelular());
        found.setEmail(operador.getEmail());
        found.setPassword(operador.getPassword());
        return operadorRepository.save(found);
    }

    public Optional<Operador> loadByEmailAndPassword(String cpf, String password) {
        return operadorRepository.findByCpfAndPassword(cpf,password);
    }
}
