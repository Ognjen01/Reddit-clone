package com.ognjenlazic.Reddit.service;

import com.ognjenlazic.Reddit.model.entity.Report;
import com.ognjenlazic.Reddit.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ReportService {

    Report findOne(Long id);

    List<Report> findAll();

    List<Report> findAllNotAccepted();

    Report save(Report report);

    void delete(Long id);
}
