import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchTweetsComponent } from './search-tweets.component';

describe('SearchTweetsComponent', () => {
  let component: SearchTweetsComponent;
  let fixture: ComponentFixture<SearchTweetsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchTweetsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchTweetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
