import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Post } from 'src/app/model/post';
import { Comment } from 'src/app/model/comment';
import { Karma } from 'src/app/helper/model/karma';

@Injectable({
  providedIn: 'root'
})
export class ReactionService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  private readonly baseURL = "/api/api/reaction/"
  constructor(
    private http: HttpClient
  ) { }

  createReaction(reaction: string, post: Post, userId: number): Observable<any> {
    return this.http.post(this.baseURL,
      {
        reactionId: 10,
        reactionType: reaction,
        userId: userId,
        timestamp: new Date(),
        postId: post.id,
        commentId: null,
      },
      { headers: this.headers, responseType: 'json' });
  }

  getAllPostReactions(postId: number) : Observable<any>{
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: 'response'
    };
    return this.http.get(this.baseURL + "post/" + postId, queryParams);
  }

  getAllCommentReacrions(postId: number): Observable<any> {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: 'response'
    };
    return this.http.get(this.baseURL + "comment/" + postId, queryParams);
  }

  createCommentReaction(reaction: string, comment: Comment, userId: number): Observable<any> {
    return this.http.post(this.baseURL,
      {
        reactionId: 10,
        reactionType: reaction,
        userId: userId,
        timestamp: new Date(),
        postId: null,
        commentId: comment.commentId,
      },
      { headers: this.headers, responseType: 'json' });
  }

  getPostKarma(postId: number): Observable<any> {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: 'response'
    };
    return this.http.get(this.baseURL + "post-karma/" + postId, queryParams);

  }

  getUserKarma(userId: number): Observable<any> {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: 'response'
    };
    return this.http.get(this.baseURL + "user-karma/" + userId, queryParams);

  }

  getCommentKarma(commentId: number): Observable<any> {

    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: 'response'
    };
    return this.http.get(this.baseURL + "comment-karma/" + commentId, queryParams);
    //  this.http.get<Karma>(this.baseURL + "comment-karma/" + commentId);
  }

}
