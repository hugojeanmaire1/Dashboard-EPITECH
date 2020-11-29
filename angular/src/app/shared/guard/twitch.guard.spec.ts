import { TestBed } from '@angular/core/testing';

import { TwitchGuard } from './twitch.guard';

describe('TwitchGuard', () => {
  let guard: TwitchGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(TwitchGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
