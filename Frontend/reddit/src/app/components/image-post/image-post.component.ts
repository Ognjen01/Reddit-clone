import { Component, OnInit, Input } from '@angular/core';
import { Post } from 'src/app/model/post';

@Component({
  selector: 'app-image-post',
  templateUrl: './image-post.component.html',
  styleUrls: ['./image-post.component.scss']
})
export class ImagePostComponent implements OnInit {
  @Input() post: Post;
  constructor() { }

  ngOnInit(): void {
    console.log("OVO JE ISPIS POSTA " + this.post.title)
  }

}
