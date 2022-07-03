import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Report } from 'src/app/model/report';

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  private readonly baseURL = "/api/api/report/"

  constructor(
    private http: HttpClient
  ) { }

  getAll(): Observable<any> {
		let queryParams = {};

		queryParams = {
			headers: this.headers,
			observe: 'response'
		};

		return this.http.get(this.baseURL, queryParams);
	}

  createNew(report: Report): Observable<any> {
		return this.http.post(this.baseURL,
			{
			  reportId: 10,
        timestamp: new Date(),
        reason: report.reason,
        userId: report.userId,
        commentId: report.commentId,
        postId: report.postId,
        acceted: false
			},
			{ headers: this.headers, responseType: 'json' });
	}

  updateReport(report: Report): Observable<any> {
    return this.http.put(this.baseURL,
			{
			  reportId: report.reportId,
        timestamp: report.timestamp,
        reason: report.reason,
        userId: report.userId,
        commentId: report.commentId,
        postId: report.postId,
        accepted: report.accepted
			},
			{ headers: this.headers, responseType: 'json' });
  }

  deleteReport(report: Report): Observable<any> {
    return this.http.delete(this.baseURL + report.reportId);
  }
}
