import { TestBed } from '@angular/core/testing';

import { FlairService } from './flair.service';

describe('FlairService', () => {
  let service: FlairService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FlairService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
