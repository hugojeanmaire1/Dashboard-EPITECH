import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoinmarketcapComponent } from './coinmarketcap.component';

describe('CoinmarketcapComponent', () => {
  let component: CoinmarketcapComponent;
  let fixture: ComponentFixture<CoinmarketcapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CoinmarketcapComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CoinmarketcapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
