import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from './model/user';
import { Token } from 'src/app/helper/model/token';
import { UserService } from './services/user_service/user.service';
import jwt_decode from "jwt-decode";
import { AuthenticationService } from './services/authentication_service/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class GuardsGuard implements CanActivate {
  user: User;
  token: any;
  noviToken: Token;
  userService: UserService

  constructor(
    private authService: AuthenticationService
  ){

  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot,
    ): Observable<boolean> | Promise<boolean> | boolean {
    return this.authService.isLoggedIn(); // This need some more checks
  }

  getDecodedAccessToken(token: string): any {
    try {
      return jwt_decode(token);
    } catch (Error) {
      return null;
    }
  }
}
