import { Component, OnInit, Output } from '@angular/core';
import { Post } from 'src/app/model/post';
import { Comment } from 'src/app/model/comment';
import { PostService } from 'src/app/services/post_service/post.service';
import { CommentService } from 'src/app/services/comment_service/comment.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-single-post',
  templateUrl: './single-post.component.html',
  styleUrls: ['./single-post.component.scss']
})
export class SinglePostComponent implements OnInit {

  @Output() post: Post;
  comments: Comment[] | null;
  postList: Post[];
  arePostsLoaded: boolean;
  areCommentsLoaded: boolean;
  postId: number;
  selectedSortType: string = '';

  constructor(
    private postService: PostService,
    private commentService: CommentService,
    private route: ActivatedRoute
  ) {

  }

  ngOnInit(): void {

    this.arePostsLoaded = false;
    this.areCommentsLoaded = false;

    this.route.params.subscribe(params => {
      this.postId = params['id'] as number;

      this.postService.getById(this.postId).subscribe( // Hardcoded information
      res => {
        this.post = res.body as Post;
        console.log(res.body);
        this.arePostsLoaded = true;

      });

    this.commentService.getAllPostComments(this.postId).subscribe(res => { // Hardcoded information
      this.comments = res.body as Comment[];
      console.log(res.body);
      this.areCommentsLoaded = true;

    });

    });

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
    this.commentService.getAllPostCommentsSortedByPopular(this.postId).subscribe(res => {
      this.comments = res.body as Comment[];
    })
  }

  sortUnpopular(){
    this.commentService.getAllPostCommentsSortedByUnpopular(this.postId).subscribe(res => {
      this.comments = res.body as Comment[];
    })
    
  }

  sortNewest(){
    this.commentService.getAllPostCommentsSortedByDateAsc(this.postId).subscribe(res => {
      this.comments = res.body as Comment[];
    })
    
  }

  sortOldest(){
    this.commentService.getAllPostCommentsSortedByDateDesc(this.postId).subscribe(res => {
      this.comments = res.body as Comment[];
    })
  
  }
}
