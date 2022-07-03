import { Component, OnInit } from '@angular/core';
import { Report } from 'src/app/model/report';
import { ReportService } from 'src/app/services/report_service/report.service';

@Component({
  selector: 'app-all-banns',
  templateUrl: './all-banns.component.html',
  styleUrls: ['./all-banns.component.scss']
})
export class AllBannsComponent implements OnInit {

  reports: Report[];
  constructor(
    private reportService: ReportService
  ) { }

  ngOnInit(): void {
    this.reportService.getAll().subscribe(res => {
      console.log(res)
      this.reports = res.body as Report[];
      console.log(res.body)
    },
    err => {

    })
  }

}
