import { TestBed } from '@angular/core/testing';

import { BibleLookupService } from './bible-lookup.service';

describe('BibleLookupService', () => {
  let service: BibleLookupService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BibleLookupService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
