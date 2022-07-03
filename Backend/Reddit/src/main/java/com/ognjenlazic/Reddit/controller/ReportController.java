package com.ognjenlazic.Reddit.controller;

import com.ognjenlazic.Reddit.model.dto.FlairDto;
import com.ognjenlazic.Reddit.model.dto.PostDto;
import com.ognjenlazic.Reddit.model.dto.ReportDto;
import com.ognjenlazic.Reddit.model.entity.Flair;
import com.ognjenlazic.Reddit.model.entity.Post;
import com.ognjenlazic.Reddit.model.entity.Report;
import com.ognjenlazic.Reddit.service.CommentService;
import com.ognjenlazic.Reddit.service.PostService;
import com.ognjenlazic.Reddit.service.ReportService;
import com.ognjenlazic.Reddit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(CommunityController.class);

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ReportDto>> getReports() {
        List<Report> reports = reportService.findAllNotAccepted();
        List<ReportDto> reportDtos = new ArrayList<>();

        for (Report report : reports) {
            if (!report.isAccepted()) {
                reportDtos.add(new ReportDto(report));
            }
        }
        return new ResponseEntity<>(reportDtos, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ReportDto> saveReport(@RequestBody ReportDto reportDto) {

        System.out.println(reportDto.toString());
        Report report = new Report();
        report.setReportId(reportDto.getReportId());
        report.setByUser(userService.findOne(reportDto.getUserId()));
        report.setReason(reportDto.getReason());
        report.setAccepted(false);
        report.setTimestamp(reportDto.getTimestamp());

        if (reportDto.getCommentId() == null) {
            report.setComment(null);
        } else {
            report.setComment(commentService.findOne(reportDto.getCommentId()));
        }

        if (reportDto.getPostId() == null) {
            report.setPost(null);
        } else {
            report.setPost(postService.findOne(reportDto.getPostId()));
        }

        report = reportService.save(report);
        logger.info("New report created");
        return new ResponseEntity<>(new ReportDto(report), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<ReportDto> updateReport(@RequestBody ReportDto reportDto) {
        System.out.println(reportDto);
        Report report = reportService.findOne(reportDto.getReportId());

        if (report == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        report.setReason(reportDto.getReason());
        report.setAccepted(reportDto.isAccepted());
        report = reportService.save(report);
        logger.info("Report updated");

        return new ResponseEntity<>(new ReportDto(report), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        Report report = reportService.findOne(id);
        if (report != null) {
            reportService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
