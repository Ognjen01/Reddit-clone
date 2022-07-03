import { Component, OnInit } from '@angular/core';
import { Post } from 'src/app/model/post';
import { PostService } from 'src/app/services/post_service/post.service';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-post-edit',
  templateUrl: './post-edit.component.html',
  styleUrls: ['./post-edit.component.scss']
})
export class PostEditComponent implements OnInit {
  post: Post;
  postId: number;
  constructor(
    private postService: PostService,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router
  ) { 
    this.post = {
      id: 0,
      title: "",
      text: "",
      flairId: 0,
      communityId: 0, 
      userId: 0, 
      imagePath: "",
      creationDate: new Date()
    }
  }

  ngOnInit(): void {
    
    this.route.params.subscribe(params => {
      this.postId = params['id'] as number;
      this.postService.getById(this.postId).subscribe(res => {
        this.post = res.body as Post;
      })
    });
  }

  deletePost(event: any) {
    console.log("Brisnje objave")
    this.postService.delete(this.post.id).subscribe(res=> {
      console.log(res)
      this.toastr.success('Successfull post delete!', '');
      this.router.navigate(['home']);
    });
  }

  savePost(event: any){
    console.log("Saving post")
    this.postService.savePost(this.post).subscribe(res => {
      console.log(res)
      console.log("Succesful info save")
      this.toastr.success('Successfull post save!', '');
      this.router.navigate(['home']);
    }, 
    err => {
      console.log("Error")
      this.toastr.error('Error!', '');

    })
  }

}
