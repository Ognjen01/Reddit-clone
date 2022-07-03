import { Component, OnInit, Inject } from '@angular/core';
import { Community } from 'src/app/model/community';
import { CommunityService } from 'src/app/services/community_service/community.service';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-suspend',
  templateUrl: './suspend.component.html',
  styleUrls: ['./suspend.component.scss']
})
export class SuspendComponent implements OnInit {
  
  constructor(
    private communityService: CommunityService,
    @Inject(MAT_DIALOG_DATA) public community: Community,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  suspend(event: any){
    this.community.isSuspended = true;
    this.communityService.saveCommunity(this.community).subscribe(res => {
      console.log(res);
      this.router.navigate(['home']);
    })
  }
}
