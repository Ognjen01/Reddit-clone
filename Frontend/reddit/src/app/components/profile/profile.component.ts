import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/user';
import { Post } from 'src/app/model/post';
import { PostService } from 'src/app/services/post_service/post.service';
import { UserService } from 'src/app/services/user_service/user.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user: User;
  postsList: Post[];
  userId: number;

  constructor(
    private postService: PostService,
    private userService: UserService,
    private route: ActivatedRoute
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
  }
  ngOnInit(): void {

    this.route.params.subscribe(params => {
      this.userId = params['id'] as number;

      this.userService.getById(this.userId).subscribe(
        res => {
          this.user = res.body as User;
          console.log(res.body);
        }
      );
  
      this.postService.getAllUserPosts(this.userId).subscribe(
        res => {
          this.postsList = res.body as Post[];
          console.log(res.body);
        }
      );
    });
  }
}
