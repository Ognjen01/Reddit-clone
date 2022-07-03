package com.ognjenlazic.Reddit.service;

import com.ognjenlazic.Reddit.model.entity.Banned;
import com.ognjenlazic.Reddit.repository.BannedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BannedService {

    Banned findOne(Long id);

    List<Banned> findAll();

    Banned save(Banned banned);

    void remove(Long id);
}
