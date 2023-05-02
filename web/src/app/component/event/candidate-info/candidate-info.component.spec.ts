import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CandidateInfoComponent } from './candidate-info.component';

describe('CandidateInfoComponent', () => {
  let component: CandidateInfoComponent;
  let fixture: ComponentFixture<CandidateInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CandidateInfoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CandidateInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
