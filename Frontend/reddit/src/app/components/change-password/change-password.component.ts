import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user_service/user.service';
import { ResetPassword } from 'src/app/helper/model/reset_password';
import { Token } from 'src/app/helper/model/token';
import jwt_decode from "jwt-decode";
import { Router } from '@angular/router';
import { User } from 'src/app/model/user';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {
  resetPassword: ResetPassword = {
    userId : 0, // Cahnge User Id - depends on current user
    oldPassword : "",
    newPassword : "",
    confirmNewPassword : ""
  };

  currentLoggedUser: User;
  token: any;
  noviToken: Token;

  constructor(
    private userService: UserService
  ) {
    this.token = localStorage.getItem('user');
    this.noviToken = JSON.parse(this.token) as Token;

    const tokenInfo = this.getDecodedAccessToken(this.noviToken.accessToken);
    const expireDate = tokenInfo.exp;

    this.userService.getByUsername(tokenInfo.sub).subscribe(res => {
      this.currentLoggedUser = res.body as User;
      this.resetPassword.userId = this.currentLoggedUser.userId
    });
   }

  ngOnInit(): void {
  }

  tryResetPassword(event: any){
    if(this.resetPassword.newPassword == this.resetPassword.confirmNewPassword){
      event.preventDefault();
      this.userService.resetPassword(this.resetPassword).subscribe(
        res => {
          console.log(res)
      });
    } else {
      // Obraditi grešku 
    }

  }

  getDecodedAccessToken(token: string): any {
    try {
      return jwt_decode(token);
    } catch (Error) {
      return null;
    }
  }
}
