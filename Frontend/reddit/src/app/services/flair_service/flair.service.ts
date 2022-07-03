import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Flair } from 'src/app/model/flair';
import { Community } from 'src/app/model/community';

@Injectable({
  providedIn: 'root'
})
export class FlairService {

  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  private readonly baseURL = "/api/api/flair/"
  constructor(
    private http: HttpClient
  ) { }

  getFlairById(flairId: number): Observable<any> {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: 'response'
    };
    return this.http.get(this.baseURL + flairId, queryParams);
  }

  createNew(flair: Flair): Observable<any> {
    return this.http.post(this.baseURL,
      {
        flairId: flair.flairId,
        name: flair.name,
      },
      { headers: this.headers, responseType: 'json' });
  }

  createCommunityFlairRelation(flair: Flair, community: Community): Observable<any> {
    return this.http.post(this.baseURL + "flair-community",
      {
        communityId: community.communityId,
        flairId: flair.flairId
      },
      { headers: this.headers, responseType: 'json' });
  }

  getCommunityFlairs(communityId: number): Observable<any> {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: 'response'
    };
    return this.http.get(this.baseURL + "community/" + communityId, queryParams);
  }

  deleteFlair(flairId: number): Observable<any> {
    return this.http.delete(this.baseURL + flairId);
  }

  saveFlair(flair: Flair): Observable<any> {
    return this.http.put(
      this.baseURL,
      {
        flairId: flair.flairId,
        name: flair.name,
      },
      { headers: this.headers, responseType: 'json' });
  }
}
