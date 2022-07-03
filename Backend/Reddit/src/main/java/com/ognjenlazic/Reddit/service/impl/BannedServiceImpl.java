package com.ognjenlazic.Reddit.service.impl;

import com.ognjenlazic.Reddit.model.entity.Banned;
import com.ognjenlazic.Reddit.repository.BannedRepository;
import com.ognjenlazic.Reddit.service.BannedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannedServiceImpl implements BannedService {

    @Autowired
    BannedRepository bannedRepository;

    @Override
    public Banned findOne(Long id){
        return bannedRepository.findById(id).orElseGet(null);
    }

    @Override
    public List<Banned> findAll(){
        return bannedRepository.findAll();
    }

    @Override
    public Banned save(Banned banned){
        return bannedRepository.save(banned);
    }

    @Override
    public void remove(Long id){
        bannedRepository.deleteById(id);
    }
}
