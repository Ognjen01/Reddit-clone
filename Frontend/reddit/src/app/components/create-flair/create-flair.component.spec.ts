import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateFlairComponent } from './create-flair.component';

describe('CreateFlairComponent', () => {
  let component: CreateFlairComponent;
  let fixture: ComponentFixture<CreateFlairComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateFlairComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateFlairComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
