package com.ognjenlazic.Reddit.repository;

import com.ognjenlazic.Reddit.model.entity.Comment;
import com.ognjenlazic.Reddit.model.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM reddit.report WHERE accepted = false")
    public List<Report> findAllNotAccepted();
}