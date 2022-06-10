import { TestBed } from '@angular/core/testing';

import { Nom035Service } from './nom035.service';

describe('Nom035Service', () => {
  let service: Nom035Service;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Nom035Service);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
