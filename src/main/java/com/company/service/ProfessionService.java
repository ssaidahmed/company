package com.company.service;

import com.company.model.Profession;
import com.company.repository.ProfessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProfessionService {

    private ProfessionRepository professionRepository;

    public ProfessionService(ProfessionRepository professionRepository) {
        this.professionRepository = professionRepository;
    }


    public void delete(int id){
        professionRepository.delete(id);
    }

    @Transactional
    public Profession save(Profession prof){
        if(!prof.isNew() && professionRepository.existsById(prof.getId())){
            throw new EntityExistsException("Уже существует объект с таким идентификатором");
        }
        return professionRepository.save(prof);
    }
    @Transactional
    public Profession update(Profession prof){
        if(!prof.isNew() && !professionRepository.existsById(prof.getId())){
            throw new EntityNotFoundException("В базе данных нет объекта с таким идентификатором");
        }
        return professionRepository.save(prof);
    }

    public List<Profession> getAll(){
        return professionRepository.findAll();
    }

    public Profession get(int id){
        return professionRepository.findById(id).orElseThrow(NullPointerException::new);
    }



}
