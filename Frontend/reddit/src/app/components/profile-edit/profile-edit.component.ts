import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user_service/user.service';
import { User } from 'src/app/model/user';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';



@Component({
  selector: 'app-profile-edit',
  templateUrl: './profile-edit.component.html',
  styleUrls: ['./profile-edit.component.scss']
})
export class ProfileEditComponent implements OnInit {
  user: User;
  userId: number;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router
  ) { 
    this.user = {
      userId : 0,
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

    this.route.params.subscribe(params => {
      this.userId = params['id'] as number;
      this.userService.getById(this.userId).subscribe(res => {
        this.user = res.body as User;
        console.log(res);
      });
    });
  }

  saveUserInformation(event: any){
    this.userService.saveUser(this.user).subscribe(res => {
      console.log("Succesful info save")
      this.toastr.success('Successfull profile information save!', '');
      this.router.navigate(['profile/' + this.userId]);
    }, 
    err => {
      this.toastr.error('Error!', '');
      console.log("Error")
    })
  }

}
