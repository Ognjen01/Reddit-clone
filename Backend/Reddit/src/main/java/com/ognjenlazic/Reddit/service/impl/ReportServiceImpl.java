package com.ognjenlazic.Reddit.service.impl;

import com.ognjenlazic.Reddit.model.entity.Report;
import com.ognjenlazic.Reddit.repository.ReportRepository;
import com.ognjenlazic.Reddit.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportRepository reportRepository;
    @Override
    public Report findOne(Long id){
        return reportRepository.findById(id).orElseGet(null);
    }
    @Override
    public List<Report> findAll(){
        return reportRepository.findAll();
    }

    @Override
    public List<Report> findAllNotAccepted() {
        return reportRepository.findAllNotAccepted();
    }

    @Override
    public Report save(Report report){
        return reportRepository.save(report);
    }
    @Override
    public void delete(Long id){
        reportRepository.deleteById(id);
    }
}
