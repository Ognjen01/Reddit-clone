import { Component, Input, OnInit } from '@angular/core';
import { Comment } from 'src/app/model/comment';
import { Flair } from 'src/app/model/flair';
import { User } from 'src/app/model/user';
import { CommentService } from 'src/app/services/comment_service/comment.service';
import { UserService } from 'src/app/services/user_service/user.service';
import { ReactionService } from 'src/app/services/reaction_service/reaction.service';
import { ReportService } from 'src/app/services/report_service/report.service';
import { Karma } from 'src/app/helper/model/karma';
import { NewReportComponent } from '../new-report/new-report.component';
import { MatDialogRef, MatDialog } from '@angular/material/dialog';
import { CreateCommentComponent } from '../create-comment/create-comment.component';
import { Token } from 'src/app/helper/model/token';
import jwt_decode from "jwt-decode";
import { Reaction } from 'src/app/model/reaction';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.scss']
})
export class CommentComponent implements OnInit {
  @Input() comment: Comment;
  comments: Comment[] | null;
  user: User;
  flair: Flair;
  karma: Karma;
  reacted: boolean;
  selectedUpvote: boolean;
  selectedDownvote: boolean;
  currentLoggedUser: User;
  token: any;
  noviToken: Token;
  selectedSortType: string = '';
  reastions: Reaction[];

  constructor(
    private userService: UserService,
    private commentService: CommentService,
    private matDialog: MatDialog,
    private reactionService: ReactionService
  ) {
    this.selectedUpvote = false;
    this.selectedDownvote = false;

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

    this.karma = {
      number: 0,
      entityId: 0
    }

    console.log(localStorage.getItem('user'));
    this.token = localStorage.getItem('user');
    this.noviToken = JSON.parse(this.token) as Token;
    console.log(this.noviToken.accessToken)

    const tokenInfo = this.getDecodedAccessToken(this.noviToken.accessToken);
    const expireDate = tokenInfo.exp;
    console.log(tokenInfo.sub);

    this.userService.getByUsername(tokenInfo.sub).subscribe(res => {
      this.currentLoggedUser = res.body as User;
    })

  }

  ngOnInit(): void {
    this.reacted = false;

    this.userService.getById(this.comment.userId).subscribe(res => {
      this.user = res as User;
    });

    this.userService.getById(this.comment.userId).subscribe(res => {
      console.log(res)
      this.user = res.body as User;
    })

    this.reactionService.getCommentKarma(this.comment.commentId || 0).subscribe(res => {
      console.log(res.body)
      this.karma = res.body as Karma;
    });

    if (this.comment.commentId != null) {
      this.commentService.getAllCommentComments(this.comment.commentId).subscribe(res => {
        this.comments = res.body as Comment[];
        console.log(res.body);
      });
    }

     // Check does user already reacted to this comment
    if(this.comment.commentId != null)
     {this.reactionService.getAllPostReactions(this.comment.commentId).subscribe(res => {
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
    })}

  }

  upvote(event: any) {
    console.log("upvote");
    if (!this.reacted) {
      this.reactionService.createCommentReaction("UPVOTE", this.comment, this.currentLoggedUser.userId).subscribe(res => {
        console.log(res)
        this.reacted = true;
        this.selectedUpvote = true;
        this.karma.number += 1;
      })
    }
  }
  downvote(event: any) {
    console.log("downtvote");
    if (!this.reacted) {
      this.reactionService.createCommentReaction("DOWNVOTE", this.comment, this.currentLoggedUser.userId).subscribe(res => {
        console.log(res)
        this.reacted = true;
        this.selectedDownvote = true;
        this.karma.number -= 1;
      })
    }
  }

  newComment(event: any) {
    this.matDialog.open(CreateCommentComponent, {
      "data": this.comment,
    });
  }

  report(event: any) {
    this.matDialog.open(NewReportComponent, {
      "data": this.comment,
    });
  }

  getDecodedAccessToken(token: string): any {
    try {
      return jwt_decode(token);
    } catch (Error) {
      return null;
    }
  }

  selectChangeHandler(event: any) {
    this.selectedSortType = event.target.value;
    console.log(this.selectedSortType)

    switch(this.selectedSortType) {
      case "sortPopular" :
        this.sortPopular()
        break
      case "sortUnpopular" :
        this.sortUnpopular()
        break
      case "sortNewest" :
        this.sortNewest()
        break
      case "sortOldest" :
        this.sortOldest()
        break
    }
  }

  sortPopular(){
    if(this.comment.commentId != null)
    {this.commentService.getAllCommentCommentsSortedByPopular(this.comment.commentId).subscribe(res => {
      this.comments = res.body as Comment[];
    })}
  }

  sortUnpopular(){
    if(this.comment.commentId != null)
    {this.commentService.getAllCommentCommentsSortedByUnpopular(this.comment.commentId).subscribe(res => {
      this.comments = res.body as Comment[];
    })}
    
  }

  sortNewest(){
    if(this.comment.commentId != null)
    {this.commentService.getAllCommentCommentsSortedByDateAsc(this.comment.commentId).subscribe(res => {
      this.comments = res.body as Comment[];
    })}
    
  }

  sortOldest(){
    if(this.comment.commentId != null)
    {this.commentService.getAllCommentCommentsSortedByDateDesc(this.comment.commentId).subscribe(res => {
      this.comments = res.body as Comment[];
    })}
  
  }
}
