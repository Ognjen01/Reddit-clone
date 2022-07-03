import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication_service/authentication.service';
import { Router } from '@angular/router';
import { Token } from 'src/app/helper/model/token';
import jwt_decode from "jwt-decode";
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/services/user_service/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  currentLoggedUser: User | null;
  token: any;
  noviToken: Token;
  constructor(
    private authService: AuthenticationService,
    private router: Router,
    private userService: UserService
  ) {
    this.currentLoggedUser = null;

    try {
      this.token = localStorage.getItem('user');
      this.noviToken = JSON.parse(this.token) as Token;

      const tokenInfo = this.getDecodedAccessToken(this.noviToken.accessToken);
      const expireDate = tokenInfo.exp;

      this.userService.getByUsername(tokenInfo.sub).subscribe(res => {
        this.currentLoggedUser = res.body as User;
      })
    } catch {
      console.log("Ne moze se pristupiti useru u local storageu");
    }
  }

  ngOnInit(): void {
  }

  logoutClick(event: any) {
    localStorage.removeItem("user");
    // this.authService.logout();
    this.router.navigate(['login']).then(() => {
      window.location.reload();
    });
  }

  homeClick(event: any) {
    this.router.navigate(['home']);
  }

  profileClick(event: any) {
    this.router.navigate(['profile/' + this.currentLoggedUser?.userId]);
  }

  addPostClick(event: any) {
    this.router.navigate(['create-post']);

  }

  createCommunityClick(event: any) {
    this.router.navigate(['create-community']);
  }

  login(event: any) {
    this.router.navigate(['login']);
  }

  allReports(event: any) {
    this.router.navigate(['all-banns']);
  }

  getDecodedAccessToken(token: string): any {
    try {
      return jwt_decode(token);
    } catch (Error) {
      return null;
    }
  }
}
