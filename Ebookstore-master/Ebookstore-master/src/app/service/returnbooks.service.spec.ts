import { TestBed } from '@angular/core/testing';

import { ReturnbooksService } from './returnbooks.service';

describe('ReturnbooksService', () => {
  let service: ReturnbooksService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReturnbooksService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
