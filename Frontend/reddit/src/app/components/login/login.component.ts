import { Component, Input, OnInit, Output } from '@angular/core';
import { User } from 'src/app/model/user';
import { Login } from 'src/app/model/login';
import { AuthenticationService } from 'src/app/services/authentication_service/authentication.service';
import { Router } from '@angular/router';
import jwt_decode from "jwt-decode";
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnInit {

  constructor(
		private authenticationService: AuthenticationService,
		private router: Router,
    private toastr: ToastrService,

      ) {
    this.login= {
      username : "",
      password : ""
    };
  }

  login:Login ;


  ngOnInit(): void {
    if(this.login === undefined) {return}
  }

  getDecodedAccessToken(token: string): any {
    try {
      return jwt_decode(token);
    } catch(Error) {
      return null;
    }
  }

  tryLogin(event: any) {
    if(this.login === undefined) {return}

    const auth: any = {};
		auth.username = this.login.username;
		auth.password = this.login.password;

    event.preventDefault();
    console.log(this.login.username);
    console.log(this.login.password);

    this.authenticationService.login(auth).subscribe(
			result => {
        console.log('Successful login!');
        console.log(JSON.stringify(result)); // Test - remove
				localStorage.setItem('user', JSON.stringify(result));
        //location.reload(); // Check this?
				this.router.navigate(['home']).then(() => {
          window.location.reload();
        });

        // Token decoding
        const tokenInfo = this.getDecodedAccessToken(result.accessToken);
        const expireDate = tokenInfo.exp;
        console.log(tokenInfo.role.authority);
			},
			error => {
      this.toastr.error('Error!', '');
        console.log(error.error);
			}
		);
  }

  goToRegistration(event: any){
    this.router.navigate(['register']);
  }
  
}
