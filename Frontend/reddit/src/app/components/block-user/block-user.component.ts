import { Component, OnInit, Inject } from '@angular/core';
import { CommunityService } from 'src/app/services/community_service/community.service';
import { Community } from 'src/app/model/community';
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/services/user_service/user.service';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-block-user',
  templateUrl: './block-user.component.html',
  styleUrls: ['./block-user.component.scss']
})
export class BlockUserComponent implements OnInit {
  selectedUserId: number;
  allUsers: User[];
  infoLoaded: boolean;
  constructor(
    @Inject(MAT_DIALOG_DATA) public community: Community,
    private userService: UserService,
    private communityService: CommunityService,
    private toastr: ToastrService,

  ) {
    this.infoLoaded = false
  }

  ngOnInit(): void {
    this.userService.getAll().subscribe(res => {
      this.allUsers = res.body as User[];
      this.allUsers.forEach((user, index) => {
      })
    });
  }

  block(event: any) {
    this.communityService.blockUser(this.community.communityId, this.selectedUserId).subscribe(res => {
      console.log("Succesfully banned user")
      this.toastr.success('Successfull banned user!', '');
    }, err => {
      this.toastr.error('Error!', '');
    })
  }

  selectChangeHandler(event: any) {
    this.selectedUserId = event.target.value;
    console.log(this.selectedUserId)
  }
}
