import { Component, OnInit } from '@angular/core';
import { Post } from 'src/app/model/post';
import { PostService } from 'src/app/services/post_service/post.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {
  poatsList: Post[];
  selectedSortType: string = '';
  constructor(
    private postService : PostService,
      ) {
    
   }

  ngOnInit(): void {
    console.log("ON INIT")
    this.postService.getAll().subscribe(
			res => {
				this.poatsList = res.body as Post[];
        this.shuffle(this.poatsList)
        console.log(res.body);
			}
		);
  }

  shuffle(array: any) {
    let currentIndex = array.length,  randomIndex;
  
    while (currentIndex != 0) {
  
      randomIndex = Math.floor(Math.random() * currentIndex);
      currentIndex--;
  
      [array[currentIndex], array[randomIndex]] = [
        array[randomIndex], array[currentIndex]];
    }
  
    return array;
  }

  sortPopular(){
    console.log("sortPopular")
    this.postService.getAllSortedByPopular().subscribe(
			res => {
				this.poatsList = res.body as Post[];
        console.log(res.body);
			}
		);
  }

  sortUnpopular(){
    console.log("sortUnpopular")
    this.postService.getAllSortedByUnpopular().subscribe(
			res => {
				this.poatsList = res.body as Post[];
        console.log(res.body);
			}
		);
    
  }

  sortNewest(){
    console.log("sortNewest")
    this.postService.getAllSortedByNewest().subscribe(
			res => {
				this.poatsList = res.body as Post[];
        console.log(res.body);
			}
		);
    
  }

  sortOldest(){
    console.log("sortOldest")
    this.postService.getAllSortedByOldest().subscribe(
			res => {
				this.poatsList = res.body as Post[];
        console.log(res.body);
			}
		);
  
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
}
