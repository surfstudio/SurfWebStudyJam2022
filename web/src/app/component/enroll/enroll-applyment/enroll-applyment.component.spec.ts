import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EnrollApplymentComponent } from './enroll-applyment.component';

describe('EnrollApplymentComponent', () => {
  let component: EnrollApplymentComponent;
  let fixture: ComponentFixture<EnrollApplymentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EnrollApplymentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EnrollApplymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
