import { Component, OnInit, Inject } from '@angular/core';
import { Comment } from 'src/app/model/comment';
import { CommentService } from 'src/app/services/comment_service/comment.service';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserService } from 'src/app/services/user_service/user.service';
import { Token } from 'src/app/helper/model/token';
import jwt_decode from "jwt-decode";
import { User } from 'src/app/model/user';
import { Post } from 'src/app/model/post';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-create-comment',
  templateUrl: './create-comment.component.html',
  styleUrls: ['./create-comment.component.scss']
})
export class CreateCommentComponent implements OnInit {
  newComment: Comment;
  user: User;
  comment: Comment
  post: Post
  commentId: number | null;
  postId: number | null;
  token: any;
  noviToken: Token;

  constructor(
    private commentService: CommentService,
    @Inject(MAT_DIALOG_DATA) public entity: any,
    private userService: UserService,
    private toastr: ToastrService,
  ) {
    this.commentId = null;
    this.postId = null;
  }

  ngOnInit(): void {
    // Get current user!!!
    console.log(localStorage.getItem('user'));
    this.token = localStorage.getItem('user');
    this.noviToken = JSON.parse(this.token) as Token;
    console.log(this.noviToken.accessToken)

    const tokenInfo = this.getDecodedAccessToken(this.noviToken.accessToken);
    const expireDate = tokenInfo.exp;
    console.log(tokenInfo.sub);

    this.userService.getByUsername(tokenInfo.sub).subscribe(res => {
      this.user = res.body as User;

      if (this.entity.commentId > 0) {
        this.comment = this.entity as Comment;
        console.log("PRETVOREN KAO COmment")
        this.commentId = this.comment.commentId
        this.postId = null
      } else {
        this.post = this.entity as Post;
        console.log("PRETVOREN KAO POST")
        this.postId = this.post.id
        this.commentId = null
      }

      this.newComment = {
        text: "",
        timestamp: new Date(),
        isDeleted: false,
        userId: this.user.userId,
        postId: this.postId,
        commentId: 0,
        repliesToCommentId: this.commentId
      }
    })


  }

  createComment(event: any) {
    console.log(this.newComment)
    this.commentService.createNew(this.newComment).subscribe(res => {
      console.log(res)
      this.toastr.success('Successfull comment save!', '');
    }, err => {
      this.toastr.error('Error!', '');
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
