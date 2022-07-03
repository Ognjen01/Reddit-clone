import { Component, OnInit, Inject } from '@angular/core';
import { Flair } from 'src/app/model/flair';
import { FlairService } from 'src/app/services/flair_service/flair.service';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Community } from 'src/app/model/community';


@Component({
  selector: 'app-create-flair',
  templateUrl: './create-flair.component.html',
  styleUrls: ['./create-flair.component.scss']
})
export class CreateFlairComponent implements OnInit {
  flair: Flair
  constructor(
    @Inject(MAT_DIALOG_DATA) public community: Community,
    private flairService: FlairService
  ) {
    this.flair = {
      flairId: 50,
      name: "",
    }
   }

  ngOnInit(): void {
    console.log(this.community)
  }

  saveFlair(event: any){
    this.flairService.createNew(this.flair).subscribe(res => {
      console.log(res);
      this.flair = res as Flair;
      // Create relation in table
      this.flairService.createCommunityFlairRelation(this.flair, this.community).subscribe(res => {
        console.log(res)
        location.reload()
      })
    })
  }

}
