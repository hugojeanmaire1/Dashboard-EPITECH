import { TestBed } from '@angular/core/testing';

import { GithubGuard } from './github.guard';

describe('GithubGuard', () => {
  let guard: GithubGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(GithubGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
