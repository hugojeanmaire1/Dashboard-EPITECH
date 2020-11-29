import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganisationProjectComponent } from './organisation-project.component';

describe('OrganisationProjectComponent', () => {
  let component: OrganisationProjectComponent;
  let fixture: ComponentFixture<OrganisationProjectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrganisationProjectComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrganisationProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
