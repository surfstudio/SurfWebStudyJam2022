import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EnrollSuccessComponent } from './enroll-success.component';

describe('EnrollSuccessComponent', () => {
  let component: EnrollSuccessComponent;
  let fixture: ComponentFixture<EnrollSuccessComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EnrollSuccessComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EnrollSuccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
