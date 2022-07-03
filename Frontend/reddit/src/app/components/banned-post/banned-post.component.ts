import { Component, Input, OnInit } from '@angular/core';
import { Report } from 'src/app/model/report';
import { PostService } from 'src/app/services/post_service/post.service';
import { UserService } from 'src/app/services/user_service/user.service';
import { CommunityService } from 'src/app/services/community_service/community.service';
import { Post } from 'src/app/model/post';
import { Community } from 'src/app/model/community';
import { User } from 'src/app/model/user';
import { ReportService } from 'src/app/services/report_service/report.service';
import { CommentService } from 'src/app/services/comment_service/comment.service';
import { Comment } from 'src/app/model/comment';

@Component({
  selector: 'app-banned-post',
  templateUrl: './banned-post.component.html',
  styleUrls: ['./banned-post.component.scss']
})
export class BannedPostComponent implements OnInit {
  @Input() report: Report;

  commentData: Comment;
  post: Post;
  community: Community;
  user: User;

  isPostLoaded: boolean;
  isCommentLoaded: boolean;


  constructor(
    private postService: PostService,
    private userService: UserService,
    private communityService: CommunityService,
    private reportService: ReportService,
    private commentService: CommentService
  ) {

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

    this.post = {
      id: 0,
      title: "",
      text: "",
      creationDate: new Date(),
      imagePath: "",
      userId: 0,
      flairId: 0,
      communityId: 0
    }

  }

  ngOnInit(): void {

    this.isPostLoaded = false
    this.isCommentLoaded = false

    if (this.report.postId != null) {
      this.postService.getById(this.report.postId).subscribe(res => {
        this.post = res.body as Post;
        console.log("KOMPONENTA");
        console.log(res.body)
        this.userService.getById(this.report.userId).subscribe(res => {
          this.user = res.body as User;
          console.log(res.body);
          this.isPostLoaded = true
        })
      })
    }


    if (this.report.commentId != null) {
      this.commentService.getCommentById(this.report.commentId).subscribe(res => {
        this.commentData = res.body as Comment;
        console.log("KOMPONENTA");
        console.log(res.body)
        this.userService.getById(this.report.userId).subscribe(res => {
          this.user = res.body as User;
          console.log(res.body);
          this.isCommentLoaded = true
        })
      })
    }

  }

  acceptReport(event: any) {
    this.report.accepted = true;
    this.reportService.updateReport(this.report).subscribe(res => {
      console.log(res)
      if (this.report.postId != null) {
        this.postService.delete(this.post.id).subscribe(res => {
          console.log(res)
        })
        console.log("Post successguly deleted");
        location.reload();
      } else if (this.report.commentId != null) {
        this.commentService.delete(this.report.commentId).subscribe(res => {
          console.log(res)
        })
        console.log("Comment successguly deleted");
        location.reload();
      }
    })
    // Nastaviti da li se banuje korisnik ili -> briše se post ili komentar iz baze
  }

  declineReport(event: any) {
    this.report.accepted = false;
    console.log(this.report);
    this.reportService.deleteReport(this.report).subscribe(res => {
      console.log(res)
    });
    console.log("Report successguly deleted");
    location.reload();
  }
}
