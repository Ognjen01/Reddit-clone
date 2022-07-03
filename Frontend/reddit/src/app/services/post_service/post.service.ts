import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Post } from 'src/app/model/post';

@Injectable({
	providedIn: 'root'
})
export class PostService {
	private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
	private readonly baseURL = "/api/api/post/"

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

	delete(postId: number): Observable<any> {
		console.log("ID ZA BRISANJE: " + postId)
		return this.http.delete(this.baseURL + postId);
	  }

	getAllSortedByUnpopular(): Observable<any> {
		let queryParams = {};

		queryParams = {
			headers: this.headers,
			observe: 'response'
		};

		return this.http.get(this.baseURL + "sorted-by-popular", queryParams);
	}

	getAllSortedByPopular(): Observable<any> {
		let queryParams = {};

		queryParams = {
			headers: this.headers,
			observe: 'response'
		};

		return this.http.get(this.baseURL + "sorted-by-unpopular", queryParams);
	}

	getAllSortedByNewest(): Observable<any> {
		let queryParams = {};

		queryParams = {
			headers: this.headers,
			observe: 'response'
		};

		return this.http.get(this.baseURL + "sorted-by-date-asc", queryParams);
	}

	getAllSortedByOldest(): Observable<any> {
		let queryParams = {};

		queryParams = {
			headers: this.headers,
			observe: 'response'
		};

		return this.http.get(this.baseURL + "sorted-by-date-desc", queryParams);
	}

	getAllUserPosts(userId: number): Observable<any> {
		let queryParams = {};

		queryParams = {
			headers: this.headers,
			observe: 'response'
		};

		return this.http.get(this.baseURL + "user/" + userId, queryParams);
	}

	getAllCommunityPosts(communityId: number): Observable<any> {
		let queryParams = {};

		queryParams = {
			headers: this.headers,
			observe: 'response'
		};

		return this.http.get(this.baseURL + "community/" + communityId, queryParams);
	}

	createNew(post: Post): Observable<any> {
		return this.http.post(this.baseURL,
			{
				id: post.id,
				title: post.title,
				text: post.text,
				creationDate: post.creationDate,
				imagePath: post.imagePath,
				communityId: post.communityId,
				userId: post.userId,
				flairId: post.flairId
			},
			{ headers: this.headers, responseType: 'json' });
	}

	getById(id: number | null): Observable<any> {
		let queryParams = {};
		queryParams = {
			headers: this.headers,
			observe: 'response'
		};
		return this.http.get(this.baseURL + id, queryParams);
	}

	savePost(post: Post): Observable<any> {
		return this.http.put(
			this.baseURL,
			{
				id: post.id,
				title: post.title,
				text: post.text,
				creationDate: post.creationDate,
				imagePath: post.imagePath,
				communityId: post.communityId,
				userId: post.userId,
				flairId: post.flairId
			},
			{ headers: this.headers, responseType: 'json' });
	}
}
