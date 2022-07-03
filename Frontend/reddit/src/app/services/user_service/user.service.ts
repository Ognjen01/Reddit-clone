import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from 'src/app/model/user';
import { ResetPassword } from 'src/app/helper/model/reset_password';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  private readonly baseURL = "/api/api/user/"

  constructor(
    private http: HttpClient
  ) { }

  register(user: User): Observable<any> {
    return this.http.post(this.baseURL,
      {
        userId: user.userId,
        username: user.username,
        password: user.password,
        email: user.email,
        registrationDate: user.registrationDate,
        avatar: "https://i.redd.it/7z6purnyuvx51.png", // Hardcoded information
        displayName: user.displayName,
        description: user.description,
        userType: "USER"// Hardcoded information
      },
      { headers: this.headers, responseType: 'json' });
  }

  resetPassword(reset_password: ResetPassword): Observable<any> {
    return this.http.put(this.baseURL + "resetPassword",
      {
        userId: reset_password.userId,
        oldPassword: reset_password.oldPassword,
        newPassword: reset_password.newPassword
      },
      { headers: this.headers, responseType: 'json' });
  }

  getById(id: number): Observable<any> {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: 'response'
    };
    return this.http.get(this.baseURL + id, queryParams);
  }

  getAll(): Observable<any> {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: 'response'
    };
    return this.http.get(this.baseURL, queryParams);
  }

  getByUsername(username: string): Observable<any> {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: 'response'
    };
    return this.http.get(this.baseURL + "username/" + username, queryParams);
  }

  saveUser(user: User): Observable<any> {
    return this.http.put(this.baseURL,
      {
        userId: user.userId,
        username: user.username,
        password: user.password,
        email: user.email,
        registrationDate: user.registrationDate,
        avatar: "https://i.redd.it/7z6purnyuvx51.png", // Hardcoded information
        displayName: user.username,
        description: user.description,
        userType: "USER"// Hardcoded information
      },
      { headers: this.headers, responseType: 'json' });
  }

}
