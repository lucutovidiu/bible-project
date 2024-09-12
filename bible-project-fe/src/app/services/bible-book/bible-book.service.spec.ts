import { TestBed } from '@angular/core/testing';

import { BibleBookService } from './bible-book.service';

describe('BibleBookService', () => {
  let service: BibleBookService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BibleBookService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
