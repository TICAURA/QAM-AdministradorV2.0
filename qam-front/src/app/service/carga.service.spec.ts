import { TestBed } from '@angular/core/testing';

import { GenericAssignService } from './generic-assign.service';

describe('GenericCargaService', () => {
  let service: GenericAssignService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GenericAssignService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});