import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditFlairComponent } from './edit-flair.component';

describe('EditFlairComponent', () => {
  let component: EditFlairComponent;
  let fixture: ComponentFixture<EditFlairComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditFlairComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditFlairComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
