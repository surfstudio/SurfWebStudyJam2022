import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EnrollWelcomeComponent } from './enroll-welcome.component';

describe('EnrollWelcomeComponent', () => {
  let component: EnrollWelcomeComponent;
  let fixture: ComponentFixture<EnrollWelcomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EnrollWelcomeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EnrollWelcomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
