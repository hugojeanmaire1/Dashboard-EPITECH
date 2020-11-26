import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchAlbumsComponent } from './search-albums.component';

describe('SearchAlbumsComponent', () => {
  let component: SearchAlbumsComponent;
  let fixture: ComponentFixture<SearchAlbumsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchAlbumsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchAlbumsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
