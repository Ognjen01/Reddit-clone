import { Component, OnInit } from '@angular/core';
import { Community } from 'src/app/model/community';
import { CommunityService } from 'src/app/services/community_service/community.service';
import { Token } from 'src/app/helper/model/token';
import jwt_decode from "jwt-decode";
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/services/user_service/user.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-create-community',
  templateUrl: './create-community.component.html',
  styleUrls: ['./create-community.component.scss']
})
export class CreateCommunityComponent implements OnInit {
  community: Community= {
    communityId: 0,
    name: "",
    description: "",
    creationDate: new Date(),
    isSuspended: false,
    suspendedReason: "",
    moderatorId: 0,
    rules: null
  };
  currentLoggedUser: User;
  token: any;
  noviToken: Token;
  infoLoaded: boolean;
  constructor(
    private communityService: CommunityService,
    private userService: UserService,
    private router: Router
  ) { 
    this.infoLoaded = false
    console.log(localStorage.getItem('user'));
    this.token = localStorage.getItem('user');
    this.noviToken = JSON.parse(this.token) as Token;
    console.log(this.noviToken.accessToken)

    const tokenInfo = this.getDecodedAccessToken(this.noviToken.accessToken);
    const expireDate = tokenInfo.exp;
    console.log(tokenInfo.sub);

    this.userService.getByUsername(tokenInfo.sub).subscribe(res => {
      this.currentLoggedUser = res.body as User;
      this.infoLoaded = true
    })
  }

  ngOnInit(): void {
    this.community = {
      communityId: 0,
      name: "",
      description: "",
      creationDate: new Date(),
      isSuspended: false,
      suspendedReason: "",
      moderatorId: 0,
      rules: null
    }
  }

  createNewCommunity(event: any) {
    this.community.moderatorId = this.currentLoggedUser.userId
    this.communityService.createNew(this.community).subscribe(res => {
      console.log(res)
      this.router.navigate(['home']);
    })
  }

  getDecodedAccessToken(token: string): any {
    try {
      return jwt_decode(token);
    } catch (Error) {
      return null;
    }
  }
}
