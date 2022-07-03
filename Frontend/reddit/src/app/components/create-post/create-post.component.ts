import { Component, OnInit, } from '@angular/core';
import { Post } from 'src/app/model/post';
import { PostService } from 'src/app/services/post_service/post.service';
import { Community } from 'src/app/model/community';
import { CommunityService } from 'src/app/services/community_service/community.service';
import { UserService } from 'src/app/services/user_service/user.service';
import { Token } from 'src/app/helper/model/token';
import jwt_decode from "jwt-decode";
import { User } from 'src/app/model/user';
import { AngularFireStorageModule } from '@angular/fire/compat/storage';
import { AngularFireStorageReference } from '@angular/fire/compat/storage';
import { AngularFireUploadTask } from '@angular/fire/compat/storage';
import { AngularFireStorage } from '@angular/fire/compat/storage';
import { v4 as uuid } from 'uuid';
import { Observable } from 'rxjs';
import { FlairService } from 'src/app/services/flair_service/flair.service';
import { Flair } from 'src/app/model/flair';
import firebase from 'firebase/compat';
import { URL } from '@angular/fire/compat/database';
import { getStorage, ref, getDownloadURL } from "firebase/storage";
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';



@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.scss']
})
export class CreatePostComponent implements OnInit {
  post: Post;
  communities: Community[];
  selectedCommunity = 0;
  selectedFlair = null;
  token: any;
  noviToken: Token;
  role: string;
  user: User;
  filePath: string;
  ref: AngularFireStorageReference;
  task: AngularFireUploadTask;
  downloadURL: Observable<string>;
  imageEvent: any;
  communityFalirs: Flair[];

  constructor(
    private postService: PostService,
    private communityService: CommunityService,
    private userService: UserService,
    private afStorage: AngularFireStorage,
    private flairService: FlairService,
    private toastr: ToastrService,
    private router: Router
  ) {
    this.imageEvent = null;
    this.post = {
      id: 11, // Hardcoded value
      title: "",
      text: "",
      flairId: null,
      communityId: 0,
      userId: 0,
      imagePath: null,
      creationDate: new Date()
    }

    this.communities = [];
  }

  ngOnInit(): void {
    // Load community
    this.communityService.getAll().subscribe(res => {
      this.communities = res.body as Community[];
    })

    console.log(localStorage.getItem('user'));
    this.token = localStorage.getItem('user');
    this.noviToken = JSON.parse(this.token) as Token;
    console.log(this.noviToken.accessToken)

    const tokenInfo = this.getDecodedAccessToken(this.noviToken.accessToken);
    const expireDate = tokenInfo.exp;
    console.log(tokenInfo.sub);
    this.role = tokenInfo.role.authority;


    this.userService.getByUsername(tokenInfo.sub).subscribe(res => {
      this.user = res.body as User;
    })

  }

  createPost(event: any) {

    if (this.imageEvent == null) {
      this.post.communityId = this.selectedCommunity
      this.post.userId = this.user.userId
      this.post.flairId = this.selectedFlair
      console.log("OVO JE FLAIR ID: " + this.selectedFlair)
      this.postService.createNew(this.post).subscribe(res => {
        console.log("Succesfull save")
        this.toastr.success('Succesful save', '');
        this.router.navigate(['home']);

      },
        err => {
          console.log("Error")
        })
    } else {
      this.upload(event);
    }
  }

  selectChangeHandler(event: any) {
    //update the ui
    this.selectedCommunity = event.target.value;
    console.log(this.selectedCommunity)

    // Get community flairs
    this.flairService.getCommunityFlairs(this.selectedCommunity).subscribe(res => {
      this.communityFalirs = res.body as Flair[];
    })
  }

  getDecodedAccessToken(token: string): any {
    try {
      return jwt_decode(token);
    } catch (Error) {
      return null;
    }
  }

  upload(event: any) {
    const id: string = uuid();
    this.ref = this.afStorage.ref(id);
    this.task = this.ref.put(this.imageEvent.target.files[0]);
    // Ovaj url nije doba
    this.task.then(t => {
      const storage = getStorage();
      const storageRef = ref(storage, id);
      getDownloadURL(storageRef).then((url) => {
        console.log(url)
        this.post.communityId = this.selectedCommunity
        this.post.userId = this.user.userId
        this.post.flairId = this.selectedFlair
        this.post.imagePath = url
        console.log("OVO JE FLAIR ID: " + this.selectedFlair)
        this.postService.createNew(this.post).subscribe(res => {
          console.log("Succesfull save")
          this.toastr.success('Successfull post save!', '');
          this.router.navigate(['home']);

        },
          err => {
            console.log("Error")
          })

      })

    })

    // console.log(this.ref.getDownloadURL().subscribe(url => {
    //   console.log(URL)
    // }))


  }

  selectFlairChangeHandler(event: any) {
    this.selectedFlair = event.target.value;
    console.log(this.selectedFlair)

  }
}
