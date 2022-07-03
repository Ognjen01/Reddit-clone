import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Rule } from 'src/app/model/rule';

@Injectable({
	providedIn: 'root'
})
export class RuleService {
	private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
	private readonly baseURL = "/api/api/rule/"

	constructor(
		private http: HttpClient
	) { }

	createNew(rule: Rule): Observable<any> {
		return this.http.post(this.baseURL,
			{
				//ruleId: rule.id,
				description: rule.description,
				communityId: rule.communityId
			},
			{ headers: this.headers, responseType: 'json' });
	}

	getAll(): Observable<any> {
		let queryParams = {};

		queryParams = {
			headers: this.headers,
			observe: 'response'
		};

		return this.http.get(this.baseURL, queryParams);
	}

	deleteRule(ruleId: number): Observable<any> {
		return this.http.delete(this.baseURL + ruleId);
	}

	saveRule(rule: Rule): Observable<any> {
		return this.http.put(
			this.baseURL,
			{
				ruleId: rule.ruleId,
				description: rule.description,
				communityId: rule.communityId,
			},
			{ headers: this.headers, responseType: 'json' });
	}
}
