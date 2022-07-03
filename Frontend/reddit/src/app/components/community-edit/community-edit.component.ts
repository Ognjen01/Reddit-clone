import { Component, OnInit } from '@angular/core';
import { Community } from 'src/app/model/community';
import { Flair } from 'src/app/model/flair';
import { CommunityService } from 'src/app/services/community_service/community.service';
import { FlairService } from 'src/app/services/flair_service/flair.service';
import { MatDialogRef, MatDialog } from '@angular/material/dialog';
import { CreateRuleComponent } from '../create-rule/create-rule.component';
import { CreateFlairComponent } from '../create-flair/create-flair.component';
import { ActivatedRoute } from '@angular/router';
import { RuleService } from 'src/app/services/rule_service/rule.service';
import { EditRuleComponent } from '../edit-rule/edit-rule.component';
import { Rule } from 'src/app/model/rule';
import { EditFlairComponent } from '../edit-flair/edit-flair.component';

@Component({
  selector: 'app-community-edit',
  templateUrl: './community-edit.component.html',
  styleUrls: ['./community-edit.component.scss']
})
export class CommunityEditComponent implements OnInit {
  community: Community
  communityFlairs: Flair[];
  communityId: number;
  isCommunityLoaded: boolean;
  constructor(
    private communityService: CommunityService,
    private flairService: FlairService,
    private matDialog: MatDialog,
    private route: ActivatedRoute,
    private ruleService: RuleService
  ) {
    this.community = {
      communityId: 0,
      name: "",
      description: "",
      creationDate: new Date(),
      isSuspended: false,
      suspendedReason: "",
      moderatorId: 0,
      rules: null
    }
    this.isCommunityLoaded = false
  }

  ngOnInit(): void {

    this.route.params.subscribe(params => {
      this.communityId = params['id'] as number;

      this.communityService.getById(this.communityId).subscribe(res => { // Hardcoded value
        this.community = res.body as Community;
        console.log(this.community.rules)
        this.isCommunityLoaded = true;
      })

      this.flairService.getCommunityFlairs(this.communityId).subscribe(res => {
        this.communityFlairs = res.body as Flair[];
      })
    });
  }

  saveCommunity(event: any) {
    console.log("SAVE")
    this.communityService.saveCommunity(this.community).subscribe(res => {
      console.log(res.body)
    })
  }

  addFlair(event: any) {
    this.matDialog.open(CreateFlairComponent, {
      "data": this.community,
    });
  }

  addRule(event: any) {
    this.matDialog.open(CreateRuleComponent, {
      "data": this.community,
    });
  }

  editFLair(event: any, flair: Flair) {
    this.matDialog.open(EditFlairComponent, {
      "data": flair,
    });
  }

  deleteFlair(event: any, flairId: number) {
    this.flairService.deleteFlair(flairId).subscribe(res => {
      console.log(res)
      location.reload()
    });
  }

  editRule(event: any, rule: Rule) {
    this.matDialog.open(EditRuleComponent, {
      "data": rule,
    });
  }

  deleteRule(event: any, ruleId: number) {
    console.log("RULE: " + ruleId)
    this.ruleService.deleteRule(ruleId).subscribe(res => {
      console.log(res)
      location.reload()
    })
  }
}
