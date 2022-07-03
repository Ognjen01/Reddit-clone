import { Component, OnInit, Inject } from '@angular/core';
import { Rule } from 'src/app/model/rule';
import { RuleService } from 'src/app/services/rule_service/rule.service';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Community } from 'src/app/model/community';

@Component({
  selector: 'app-create-rule',
  templateUrl: './create-rule.component.html',
  styleUrls: ['./create-rule.component.scss']
})
export class CreateRuleComponent implements OnInit {
  rule: Rule;
  constructor(
    private ruleService: RuleService,
    @Inject(MAT_DIALOG_DATA) public community: Community,
  ) {
    this.rule = {
      ruleId: 0,
      description: "",
      communityId : community.communityId,
    }
   }

  ngOnInit(): void {
    console.log(this.community)
  }

  saveRule(event: any){
    this.ruleService.createNew(this.rule).subscribe(res => {
      console.log(res.body)
      location.reload()
    })
  }

}
