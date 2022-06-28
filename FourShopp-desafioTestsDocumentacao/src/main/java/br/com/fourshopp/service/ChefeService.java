package br.com.fourshopp.service;

import br.com.fourshopp.entities.Chefe;
import br.com.fourshopp.entities.Cliente;
import br.com.fourshopp.entities.Operador;
import br.com.fourshopp.repository.ChefeRepository;
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
public class ChefeService {

    @Autowired
    private ChefeRepository chefeRepository;
    
    @Autowired
    private enderecoRepository enderecoRepository;

    @Transactional
    public Chefe save(Chefe chefe){
    	enderecoRepository.save(chefe.getEndereco());
        return chefeRepository.save(chefe);
    }
   

    public Chefe findById(Long id){
        return chefeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado"));
    }

    public List<Chefe> listAll(){
        return chefeRepository.findAll();
    }

    public void remove(Long id){
    	chefeRepository.deleteById(id);
    }

    public Chefe update(Chefe chefe, Long id){
        Chefe found = findById(id);
        found.setCargo(chefe.getCargo());
        found.setSalario(chefe.getSalario());
        found.setCelular(chefe.getCelular());
        found.setEmail(chefe.getEmail());
        found.setPassword(chefe.getPassword());
        return chefeRepository.save(found);
    }

    public Chefe loadByEmailAndPassword(String cpf, String password) {
        return chefeRepository.findByCpfAndPassword(cpf,password);
    }
}
