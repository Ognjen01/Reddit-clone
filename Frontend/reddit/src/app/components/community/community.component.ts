import { Component, OnInit, Output } from '@angular/core';
import { Community } from 'src/app/model/community';
import { Post } from 'src/app/model/post';
import { PostService } from 'src/app/services/post_service/post.service';
import { CommunityService } from 'src/app/services/community_service/community.service';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-community',
  templateUrl: './community.component.html',
  styleUrls: ['./community.component.scss']
})
export class CommunityComponent implements OnInit {

  @Output() communityInformation: Community;
  postsList: Post[];
  communityId: number;

  constructor(
    private postService: PostService,
    private communtiyService: CommunityService,
    private route: ActivatedRoute
  ) {
    this.communityInformation = {
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
  
  ngOnInit(): void {

    this.route.params.subscribe(params => {
      this.communityId = params['id'] as number;

      this.communtiyService.getById(this.communityId).subscribe(res => {
        this.communityInformation = res.body as Community;
      })
  
      this.postService.getAllCommunityPosts(this.communityId).subscribe(
        res => {
          this.postsList = res.body as Post[];
        }
      );
    });

  }

}
