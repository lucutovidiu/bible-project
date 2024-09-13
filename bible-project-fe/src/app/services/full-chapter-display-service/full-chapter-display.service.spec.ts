import { TestBed } from '@angular/core/testing';

import { FullChapterDisplayService } from './full-chapter-display.service';

describe('FullChapterDisplayService', () => {
  let service: FullChapterDisplayService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FullChapterDisplayService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
