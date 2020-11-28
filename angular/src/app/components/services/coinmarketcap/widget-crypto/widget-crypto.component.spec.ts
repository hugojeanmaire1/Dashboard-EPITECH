import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WidgetCryptoComponent } from './widget-crypto.component';

describe('WidgetCryptoComponent', () => {
  let component: WidgetCryptoComponent;
  let fixture: ComponentFixture<WidgetCryptoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WidgetCryptoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WidgetCryptoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
