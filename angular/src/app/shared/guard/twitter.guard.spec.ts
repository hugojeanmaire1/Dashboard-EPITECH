import { TestBed } from '@angular/core/testing';

import { TwitterGuard } from './twitter.guard';

describe('TwitterGuard', () => {
  let guard: TwitterGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(TwitterGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
