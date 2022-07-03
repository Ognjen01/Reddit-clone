import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BannedPostComponent } from './banned-post.component';

describe('BannedPostComponent', () => {
  let component: BannedPostComponent;
  let fixture: ComponentFixture<BannedPostComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BannedPostComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BannedPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
