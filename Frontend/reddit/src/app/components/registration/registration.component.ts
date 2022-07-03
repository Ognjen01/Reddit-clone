import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/user';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user_service/user.service';


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {

  constructor(
    private userService: UserService,
		private router: Router
  ) { 
    this.user = {
      userId : 10,
      username : "",
      registrationDate: new Date(),
      description: "",
      displayName: "",
      email: "",
      avatar: "",
      password: "",
      confirmPassword : "",
      userType: ""
    }
  }

  ngOnInit(): void {
  }

  user: User;

  register(event: any){
    if(this.user === undefined) {return}

    event.preventDefault();

    this.userService.register(this.user).subscribe(
			result => {
        console.log('Successful login!');
        console.log(JSON.stringify(result));
				//localStorage.setItem('user', JSON.stringify(result));
				this.router.navigate(['home']);
			},
			error => {
        console.log(error.error);
			}
		);

  }

}
