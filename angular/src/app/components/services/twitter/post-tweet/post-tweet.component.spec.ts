import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostTweetComponent } from './post-tweet.component';

describe('PostTweetComponent', () => {
  let component: PostTweetComponent;
  let fixture: ComponentFixture<PostTweetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PostTweetComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PostTweetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
