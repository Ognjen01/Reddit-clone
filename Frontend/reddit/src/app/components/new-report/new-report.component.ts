import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Post } from 'src/app/model/post';
import { Report } from 'src/app/model/report';
import { ReportService } from 'src/app/services/report_service/report.service';
import { ReportReason } from 'src/app/model/enumeration/report_reason';
import { UserService } from 'src/app/services/user_service/user.service';
import { Token } from 'src/app/helper/model/token';
import jwt_decode from "jwt-decode";
import { User } from 'src/app/model/user';
import { Comment } from 'src/app/model/comment';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-new-report',
  templateUrl: './new-report.component.html',
  styleUrls: ['./new-report.component.scss']
})
export class NewReportComponent implements OnInit {
  reports = ReportReason;
  newReport: Report;
  enumKeys: string[];
  selectedReason: string = '';
  token: any;
  noviToken: Token;
  role: string;
  user: User;
  comment: Comment
  post: Post
  commentId: number | null;
  postId: number | null;


  constructor(
    @Inject(MAT_DIALOG_DATA) public entity: any,
    private reportService: ReportService,
    private userService: UserService,
    private toastr: ToastrService,

  ) {
    this.enumKeys = []
    console.log(entity)

    if(entity.commentId > 0){
      this.comment = entity as Comment;
      console.log("PRETVOREN KAO COmment")
      this.commentId = this.comment.commentId
      this.postId = null
    } else {
      this.post = entity as Post;
      console.log("PRETVOREN KAO POST")
      this.postId = this.post.id
      this.commentId = null
    }

  }

  ngOnInit(): void {
    console.log(this.entity)
    this.enumKeys = Object.keys(this.reports);

    // Get current user!!!
    console.log(localStorage.getItem('user'));
    this.token = localStorage.getItem('user');
    this.noviToken = JSON.parse(this.token) as Token;
    console.log(this.noviToken.accessToken)

    const tokenInfo = this.getDecodedAccessToken(this.noviToken.accessToken);
    const expireDate = tokenInfo.exp;
    console.log(tokenInfo.sub);
    this.role = tokenInfo.role.authority;


    this.userService.getByUsername(tokenInfo.sub).subscribe(res => {
      this.user = res.body as User;
    })

  }

  report(event: any) {
    console.log(this.selectedReason)
    if (this.selectedReason != '') {

      this.newReport = {
        reportId: 0,
        reason: this.selectedReason,
        timestamp: new Date(),
        accepted: false,
        userId: this.user.userId,
        postId: this.postId,
        commentId: this.commentId
      }

      console.log(this.newReport)

      this.reportService.createNew(this.newReport).subscribe(res => {
        console.log(res)
      this.toastr.success('Successfull repor save!', '');

      })
    } else {
      console.log("Nije moguća prijava nista odabrali razlog")
      this.toastr.error('Error!', '');
    }
  }

  selectChangeHandler(event: any) {
    //update the ui
    this.selectedReason = event.target.value;
    console.log(this.selectedReason)
  }

  getDecodedAccessToken(token: string): any {
    try {
      return jwt_decode(token);
    } catch (Error) {
      return null;
    }
  }
}
