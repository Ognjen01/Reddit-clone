import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class AuthenticationService {
	private headers = new HttpHeaders({'Content-Type': 'application/json'});
  	private readonly loginPath = "/api/api/user/login"
  	private readonly logoutPath = "/api/api/user/logout"


	constructor(
		private http: HttpClient
	) { }

	login(auth: any): Observable<any> {
		return this.http.post(this.loginPath, 
    {username: auth.username, password: auth.password}, 
    {headers: this.headers, responseType: 'json'});
	}

	// Invalidirati token na backendu
	logout(): boolean {
		if (!localStorage.getItem('user')) {
			return false;
	}
	return true;
	}

	isLoggedIn(): boolean {
		if (!localStorage.getItem('user')) {
			console.log("There is no user")
			return false;
		}else {
			// localStorage.removeItem('user');
			// console.log("Logout")
			// return true;
			return true;
		}
	}
}