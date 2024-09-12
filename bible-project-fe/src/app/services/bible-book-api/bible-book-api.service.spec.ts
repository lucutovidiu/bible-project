import { TestBed } from '@angular/core/testing';

import { BibleBookApiService } from './bible-book-api.service';

describe('BibleBookApiService', () => {
  let service: BibleBookApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BibleBookApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
