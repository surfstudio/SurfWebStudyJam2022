import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestingEndedComponent } from './testing-ended.component';

describe('TestingEndedComponent', () => {
  let component: TestingEndedComponent;
  let fixture: ComponentFixture<TestingEndedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TestingEndedComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TestingEndedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
