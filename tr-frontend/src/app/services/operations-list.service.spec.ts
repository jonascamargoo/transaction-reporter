import { TestBed } from '@angular/core/testing';

import { OperationsListService } from './operations-list.service';

describe('OperationsListService', () => {
  let service: OperationsListService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OperationsListService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
