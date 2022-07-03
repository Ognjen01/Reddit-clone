import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllBannsComponent } from './all-banns.component';

describe('AllBannsComponent', () => {
  let component: AllBannsComponent;
  let fixture: ComponentFixture<AllBannsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllBannsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllBannsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
