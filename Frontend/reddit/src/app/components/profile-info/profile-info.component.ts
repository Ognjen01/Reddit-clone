import { Component, OnInit, Input } from '@angular/core';
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/services/user_service/user.service';
import { Token } from 'src/app/helper/model/token';
import jwt_decode from "jwt-decode";
import { Router } from '@angular/router';
import { ReactionService } from 'src/app/services/reaction_service/reaction.service';
import { Karma } from 'src/app/helper/model/karma';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-profile-info',
  templateUrl: './profile-info.component.html',
  styleUrls: ['./profile-info.component.scss']
})
export class ProfileInfoComponent implements OnInit {

  currentLoggedUser: User;
  token: any;
  noviToken: Token;
  karma: Karma;
  userId: number;
  @Input() user: User;

  constructor(
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute,
    private reactionService: ReactionService
  ) {
    this.token = localStorage.getItem('user');
    this.noviToken = JSON.parse(this.token) as Token;

    const tokenInfo = this.getDecodedAccessToken(this.noviToken.accessToken);
    const expireDate = tokenInfo.exp;

    this.userService.getByUsername(tokenInfo.sub).subscribe(res => {
      this.currentLoggedUser = res.body as User;
    })

    this.currentLoggedUser = {
      userId: 0,
      username: "",
      registrationDate: new Date(),
      description: "",
      displayName: "",
      email: "",
      avatar: "",
      password: "",
      confirmPassword: "",
      userType: ""
    }

    this.karma = {
      number: 0,
      entityId: 0
    }
  }

  ngOnInit(): void {

    this.route.params.subscribe(params => {
      this.userId = params['id'] as number;
      this.reactionService.getUserKarma(this.userId).subscribe(res => {
        this.karma = res.body as Karma;
      });
    });
  }

  suspendUser(event: any) {
    console.log("Suspending user: " + this.user.username)
    // Uraditi suspenziju
  }

  editProfile(event: any) {
    this.router.navigate(['edit-profile-info/' + this.currentLoggedUser?.userId]);
  }

  editPassword(event: any) {
    this.router.navigate(['reset-password/' + this.currentLoggedUser?.userId]);
  }

  getDecodedAccessToken(token: string): any {
    try {
      return jwt_decode(token);
    } catch (Error) {
      return null;
    }
  }

}
