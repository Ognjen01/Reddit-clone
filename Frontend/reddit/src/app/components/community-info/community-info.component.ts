import { Component, OnInit, Input } from '@angular/core';
import { Community } from 'src/app/model/community';
import { Rule } from 'src/app/model/rule';
import { CommunityService } from 'src/app/services/community_service/community.service';
import { Token } from 'src/app/helper/model/token';
import jwt_decode from "jwt-decode";
import { UserService } from 'src/app/services/user_service/user.service';
import { User } from 'src/app/model/user';
import { Router } from '@angular/router';
import { FlairService } from 'src/app/services/flair_service/flair.service';
import { Flair } from 'src/app/model/flair';
import { BlockUserComponent } from '../block-user/block-user.component';
import { MatDialogRef, MatDialog } from '@angular/material/dialog';
import { SuspendComponent } from '../suspend/suspend.component';



@Component({
  selector: 'app-community-info',
  templateUrl: './community-info.component.html',
  styleUrls: ['./community-info.component.scss']
})
export class CommunityInfoComponent implements OnInit {
  @Input() communityData: Community;
  token: any;
  noviToken: Token;
  role: string;
  currentLoggedUser: User;
  communityUserIds: number[];
  infoLoaded: boolean;
  partOfCommunity: boolean;
  allLoaded: boolean;
  communityFlairs: Flair[];

  constructor(
    private communityService: CommunityService,
    private userService: UserService,
    private router: Router,
    private flairService: FlairService,
    private matDialog: MatDialog,
  ) {
    this.infoLoaded = false
    this.partOfCommunity = false
    this.allLoaded = false

  }

  ngOnInit(): void {

    this.token = localStorage.getItem('user');
    this.noviToken = JSON.parse(this.token) as Token;

    const tokenInfo = this.getDecodedAccessToken(this.noviToken.accessToken);
    const expireDate = tokenInfo.exp;
    this.role = tokenInfo.role.authority;

    this.userService.getByUsername(tokenInfo.sub).subscribe(res => {
      this.currentLoggedUser = res.body as User;

      this.communityService.getComunityUsersIds(this.communityData.communityId).subscribe(res => {
        this.communityUserIds = res.body as number[];

        console.log("OVO SU ČLANOVI: " + this.communityData.communityId)
        console.log(res.body)
        console.log(this.communityUserIds.includes(this.currentLoggedUser.userId))

        if (this.communityUserIds.includes(this.currentLoggedUser.userId)) {
          this.partOfCommunity = true
        }

        this.flairService.getCommunityFlairs(this.communityData.communityId).subscribe(res => {
          this.communityFlairs = res.body as Flair[];
          this.infoLoaded = true
          this.allLoaded = true
        });

      })
    });

  }

  suspendCommunity(event: any) {

    this.matDialog.open(SuspendComponent, {
      "data": this.communityData,
    });

    // this.communityData.isSuspended = true;
    // this.communityService.saveCommunity(this.communityData).subscribe(res => {
    //   console.log(res);
    // })
  }

  editCommunity(event: any) {
    this.router.navigate(['community-edit/' + this.communityData.communityId]);
  }

  leaveCommunity(event: any) {
    this.communityService.removeUserFromCommunity(this.communityData.communityId, this.currentLoggedUser.userId).subscribe(res => {
      console.log("Succesfully left community")
      location.reload()
    })
  }

  joinCommunity(event: any) {
    this.communityService.addUserToCommunity(this.communityData.communityId, this.currentLoggedUser.userId).subscribe(res => {
      console.log("Succesfully joined")
      location.reload()
    })
  }

  blockUser(event: any) {
    this.matDialog.open(BlockUserComponent, {
      "data": this.communityData,
    });
  }

  deleteCommunity(event: any) {
    this.communityService.deleteCommunity(this.communityData.communityId).subscribe(
      res => {
        console.log(res)
        this.router.navigate(['home']);
        // Izbrisati sve postove?
      }
    )
  }

  getDecodedAccessToken(token: string): any {
    try {
      return jwt_decode(token);
    } catch (Error) {
      return null;
    }
  }
}
