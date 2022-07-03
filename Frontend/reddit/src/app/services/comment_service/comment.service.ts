import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Comment } from 'src/app/model/comment';

@Injectable({
	providedIn: 'root'
})
export class CommentService {
	private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
	private readonly baseURL = "/api/api/comment/"

	constructor(
		private http: HttpClient
	) { }

	createNew(comment: Comment): Observable<any> {
		return this.http.post(this.baseURL,
			{
				commentId: comment.commentId,
				text: comment.text,
				timestamp: new Date(),
				userId: comment.userId,
				postId: comment.postId,
				repliesToCommentId: comment.repliesToCommentId,
				deleted: false
			},
			{ headers: this.headers, responseType: 'json' });
	}

	getCommentById(commentId: number): Observable<any> {
		let queryParams = {};

		queryParams = {
			headers: this.headers,
			observe: 'response'
		};

		return this.http.get(this.baseURL + commentId, queryParams);
	}

	getAllPostComments(postId: number): Observable<any> {
		let queryParams = {};

		queryParams = {
			headers: this.headers,
			observe: 'response'
		};

		return this.http.get(this.baseURL + "post/" + postId, queryParams);
	}

	delete(commentId: number): Observable<any> {
		return this.http.delete(this.baseURL + commentId);
	  }

	getAllCommentComments(commentId: number): Observable<any> {
		let queryParams = {};

		queryParams = {
			headers: this.headers,
			observe: 'response'
		};

		return this.http.get(this.baseURL + "comment/" + commentId, queryParams);
	}

	getAllPostCommentsSortedByPopular(postId: number): Observable<any> {
		let queryParams = {};

		queryParams = {
			headers: this.headers,
			observe: 'response'
		};

		return this.http.get(this.baseURL + "post/" + postId + "/sorted-by-popular", queryParams);
	}

	getAllPostCommentsSortedByUnpopular(postId: number): Observable<any> {
		let queryParams = {};

		queryParams = {
			headers: this.headers,
			observe: 'response'
		};

		return this.http.get(this.baseURL + "post/" + postId + "/sorted-by-unpopular", queryParams);
	}

	getAllPostCommentsSortedByDateAsc(postId: number): Observable<any> {
		let queryParams = {};

		queryParams = {
			headers: this.headers,
			observe: 'response'
		};

		return this.http.get(this.baseURL + "post/" + postId + "/sorted-by-date-asc", queryParams);
	}


	getAllPostCommentsSortedByDateDesc(postId: number): Observable<any> {
		let queryParams = {};

		queryParams = {
			headers: this.headers,
			observe: 'response'
		};

		return this.http.get(this.baseURL + "post/" + postId + "/sorted-by-date-desc", queryParams);
	}

	getAllCommentCommentsSortedByPopular(commentId: number): Observable<any> {
		let queryParams = {};

		queryParams = {
			headers: this.headers,
			observe: 'response'
		};

		return this.http.get(this.baseURL + "comment/" + commentId + "/sorted-by-popular", queryParams);
	}

	getAllCommentCommentsSortedByUnpopular(commentId: number): Observable<any> {
		let queryParams = {};

		queryParams = {
			headers: this.headers,
			observe: 'response'
		};

		return this.http.get(this.baseURL + "comment/" + commentId + "/sorted-by-unpopular", queryParams);
	}

	getAllCommentCommentsSortedByDateAsc(commentId: number): Observable<any> {
		let queryParams = {};

		queryParams = {
			headers: this.headers,
			observe: 'response'
		};

		return this.http.get(this.baseURL + "comment/" + commentId + "/sorted-by-date-asc", queryParams);
	}

	getAllCommentCommentsSortedByDateDesc(commentId: number): Observable<any> {
		let queryParams = {};

		queryParams = {
			headers: this.headers,
			observe: 'response'
		};

		return this.http.get(this.baseURL + "comment/" + commentId + "/sorted-by-date-desc", queryParams);
	}

}
