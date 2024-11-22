import { TestBed } from '@angular/core/testing';

import { ChapterDisplayService } from './chapter-display.service';

describe('FullChapterDisplayService', () => {
  let service: ChapterDisplayService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChapterDisplayService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
