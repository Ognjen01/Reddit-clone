import { Component, OnInit, Inject } from '@angular/core';
import { FlairService } from 'src/app/services/flair_service/flair.service';
import { Flair } from 'src/app/model/flair';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';


@Component({
  selector: 'app-edit-flair',
  templateUrl: './edit-flair.component.html',
  styleUrls: ['./edit-flair.component.scss']
})
export class EditFlairComponent implements OnInit {

  constructor(
    private flairService: FlairService,
    @Inject(MAT_DIALOG_DATA) public flair: Flair
  ) { }

  ngOnInit(): void {
  }

  saveFlair(event: any){
    this.flairService.saveFlair(this.flair).subscribe(res=> {
      console.log(res.body)
      location.reload()
    })
  }

}
