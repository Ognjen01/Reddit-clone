import { Component, OnInit, Inject } from '@angular/core';
import { RuleService } from 'src/app/services/rule_service/rule.service';
import { Rule } from 'src/app/model/rule';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-edit-rule',
  templateUrl: './edit-rule.component.html',
  styleUrls: ['./edit-rule.component.scss']
})
export class EditRuleComponent implements OnInit {

  constructor(
    private ruleService: RuleService,
    @Inject(MAT_DIALOG_DATA) public rule: Rule
  ) { }

  ngOnInit(): void {
  }

  saveRule(event: any){
    this.ruleService.saveRule(this.rule).subscribe(res=> {
      console.log(res.body)
      location.reload()
    })
  }
}
