import { TestBed } from '@angular/core/testing';

import { SpotifyGuard } from './spotify.guard';

describe('SpotifyGuard', () => {
  let guard: SpotifyGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(SpotifyGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
