import { TestBed } from '@angular/core/testing';
import {AuthService} from "./auth.service";

describe('GithubService', () => {
  let service: AuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('User log in', () => {
    expect(service.GoogleAuth).toHaveBeenCalled();
  })
});
