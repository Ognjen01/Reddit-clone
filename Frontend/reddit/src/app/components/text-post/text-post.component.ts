import { Component, OnInit, Input } from '@angular/core';
import { Post } from 'src/app/model/post';
import { Report } from 'src/app/model/report';
import { ReactionService } from 'src/app/services/reaction_service/reaction.service';
import { ReportService } from 'src/app/services/report_service/report.service';
import { Router } from '@angular/router';
import { NewReportComponent } from '../new-report/new-report.component';
import { MatDialogRef, MatDialog } from '@angular/material/dialog';
import { User } from 'src/app/model/user';
import { Community } from 'src/app/model/community';
import { UserService } from 'src/app/services/user_service/user.service';
import { CommunityService } from 'src/app/services/community_service/community.service';
import { Karma } from 'src/app/helper/model/karma';
import { CommentComponent } from '../comment/comment.component';
import { CreateCommentComponent } from '../create-comment/create-comment.component';
import { FlairService } from 'src/app/services/flair_service/flair.service';
import { Flair } from 'src/app/model/flair';
import { Token } from 'src/app/helper/model/token';
import jwt_decode from "jwt-decode";
import { Reaction } from 'src/app/model/reaction';
import { PostService } from 'src/app/services/post_service/post.service';


@Component({
  selector: 'app-text-post',
  templateUrl: './text-post.component.html',
  styleUrls: ['./text-post.component.scss']
})
export class TextPostComponent implements OnInit {
  @Input("post") post: Post;
  newReport: Report;
  reacted: boolean;
  reported: boolean;
  user!: User;
  currentLoggedUser: User | null;
  community: Community;
  karma: Karma;
  selectedUpvote: boolean;
  selectedDownvote: boolean;
  flair: Flair;
  token: any;
  noviToken: Token;
  reastions: Reaction[];
  blockedUsersIds: number[];
  currentUserIsBlocked: boolean;

  constructor(
    private reactionService: ReactionService,
    private reportService: ReportService,
    private router: Router,
    private matDialog: MatDialog,
    private communityService: CommunityService,
    private userService: UserService,
    private flairService: FlairService,
    private postService: PostService
  ) {
    this.selectedUpvote = false;
    this.selectedDownvote = false;
    this.currentUserIsBlocked = false;

    this.user = {
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

    this.currentLoggedUser = null

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

    this.karma = {
      number: 0,
      entityId: 0
    }

    this.flair = {
      name: "",
      flairId: 0
    }

    try {
      this.token = localStorage.getItem('user');
      this.noviToken = JSON.parse(this.token) as Token;

      const tokenInfo = this.getDecodedAccessToken(this.noviToken.accessToken);
      const expireDate = tokenInfo.exp;

      this.userService.getByUsername(tokenInfo.sub).subscribe(res => {
        this.currentLoggedUser = res.body as User;
      })
    } catch {
      // Ovdje nema tokena
    }
  }

  ngOnInit(): void {
    this.reacted = false;

    this.userService.getById(this.post.userId).subscribe(res => {
      console.log(res)
      this.user = res.body as User;
    })

    this.communityService.getById(this.post.communityId).subscribe(res => {
      console.log(res.body)
      this.community = res.body as Community;
    })

    this.reactionService.getPostKarma(this.post.id).subscribe(res => {
      console.log(res.body)
      this.karma = res.body as Karma;
    });

    if(this.post.flairId != null){
      this.flairService.getFlairById(this.post.flairId).subscribe(res => {
        this.flair = res.body as Flair;
      })
    }
  

    this.getThisCommunityBlockedUser()

    // Check does user already reacted to this post
    this.reactionService.getAllPostReactions(this.post.id).subscribe(res => {
      this.reastions = res.body as Reaction[];
      this.reastions.forEach(reaction => {
        if(reaction.userId == this.currentLoggedUser?.userId){
          if(reaction.reactionType == "UPVOTE"){
            this.reacted = true;
            this.selectedUpvote = true;
          } else if (reaction.reactionType == "DOWNVOTE"){
            this.reacted = true;
            this.selectedDownvote = true;
          }
        }
      })
    })
  }

  upvote(event: any) {

    if (!this.reacted && this.currentLoggedUser != null && !this.currentUserIsBlocked) {
      this.reactionService.createReaction("UPVOTE", this.post, this.currentLoggedUser.userId).subscribe(res => {
        console.log(res)
        this.reacted = true;
        this.selectedUpvote = true;
        this.karma.number += 1;
      })
    } else if (this.currentLoggedUser == null) {
      console.log("Molimo prijavite se")
    }
  }

  downvote(event: any) {
    if (!this.reacted && this.currentLoggedUser != null && !this.currentUserIsBlocked) {
      this.reactionService.createReaction("DOWNVOTE", this.post, this.currentLoggedUser.userId).subscribe(res => {
        console.log(res)
        this.reacted = true;
        this.selectedDownvote = true;
        this.karma.number -= 1;
      })
    } else if (this.currentLoggedUser == null) {
      console.log("Molimo prijavite se")
    }
  }

  report(event: any) {
    if (this.currentLoggedUser != null && !this.currentUserIsBlocked) {
      this.openReport();
    } else {
      console.log("user is blocked for this community")
    }
  }

  comment(event: any) {
    if (this.currentLoggedUser != null && !this.currentUserIsBlocked) {
      this.openComment();
    }
  }

  join(event: any) {
    console.log("User joined!")
    // THis one is unused
  }

  onCommunityNameClick(event: any) {
    this.router.navigate(['community/' + this.community.communityId]);
  }

  onUsernameClick(event: any) {
    this.router.navigate(['profile/' + this.user.userId]);
  }

  onPostTextClicked(event: any) {
    this.router.navigate(['post/' + this.post.id]);
  }

  editPost(event: any) {
    this.router.navigate(['post-edit/' + this.post.id]);
  }

  deletePost(event: any) {
    console.log("Brisnje objave")
    this.postService.delete(this.post.id).subscribe(res=> {
      console.log(res)
    });
    // location.reload();
  }

  openReport() {
    this.matDialog.open(NewReportComponent, {
      "data": this.post,
    });
  }

  openComment() {
    this.matDialog.open(CreateCommentComponent, {
      "data": this.post,
    });
  }

  getDecodedAccessToken(token: string): any {
    try {
      return jwt_decode(token);
    } catch (Error) {
      return null;
    }
  }

  getThisCommunityBlockedUser(){
    this.communityService.getComunityBlockedUsers(this.post.communityId).subscribe(res => {
      this.blockedUsersIds = res.body as number[];
      console.log(this.blockedUsersIds)
      console.log(this.currentLoggedUser?.userId)

      if (this.currentLoggedUser != null){
          this.blockedUsersIds.forEach(num => {
            if (num == this.currentLoggedUser?.userId) {
              this.currentUserIsBlocked = true
              console.log("KORISNIK JE BLOKIRAN OD STRANE ZAJEDNICE: " + this.post.id)
            } else {
              console.log("KORISNIK NIJE BLOKIRAN OD STRANE ZAJEDNICE: " + this.post.id)
            }
          })
      }
    })
  }

}
