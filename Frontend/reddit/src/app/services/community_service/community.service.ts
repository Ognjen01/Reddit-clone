import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Community } from 'src/app/model/community';

@Injectable({
  providedIn: 'root'
})
export class CommunityService {
  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  private readonly baseURL = "/api/api/community/"

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

  getById(id: number): Observable<any> {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: 'response'
    };
    return this.http.get(this.baseURL + id, queryParams);
  }

  getComunityUsersIds(communityId: number): Observable<any> {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: 'response'
    };
    return this.http.get(this.baseURL + "community-users/" + communityId, queryParams);
  }

  getComunityBlockedUsers(communityId: number): Observable<any> {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: 'response'
    };
    return this.http.get(this.baseURL + "community-blocked-users/" + communityId, queryParams);
  }

  addUserToCommunity(communityId: number, userId: number): Observable<any> {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: 'response'
    };
    return this.http.post(this.baseURL + "community/"+ communityId + "/user/" + userId, queryParams);
  }


  blockUser(communityId: number, userId: number): Observable<any> {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: 'response'
    };
    return this.http.post(this.baseURL + "block/community/"+ communityId + "/user/" + userId, queryParams);
  }

  removeUserFromCommunity(communityId: number, userId: number): Observable<any> {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: 'response'
    };
    return this.http.delete(this.baseURL + "community/"+ communityId + "/user/" + userId, queryParams);
  }

  createNew(community: Community): Observable<any> {
    return this.http.post(this.baseURL,
      {
        communityId: 10, // Hardcoded value
        name: community.name,
        description: community.description,
        suspendedReason: null,
        creationDate: new Date(),
        suspended: false,
        moderatorId: community.moderatorId,
        rules: [],
      },
      { headers: this.headers, responseType: 'json' });
  }

  saveCommunity(community: Community): Observable<any>{
    return this.http.put(
          this.baseURL,
          {
            communityId: community.communityId, 
            name: community.name,
            description: community.description,
            suspendedReason: community.suspendedReason,
            creationDate: community.creationDate,
            suspended: community.isSuspended,
            moderatorId: community.moderatorId, 
            rules: community.rules,
          },
         { headers: this.headers, responseType: 'json' });
  }

  deleteCommunity(communityId: number): Observable<any> {
		return this.http.delete(this.baseURL + communityId);
	}
}
