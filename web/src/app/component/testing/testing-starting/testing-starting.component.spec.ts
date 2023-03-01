import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestingStartingComponent } from './testing-starting.component';

describe('TestingStartingComponent', () => {
  let component: TestingStartingComponent;
  let fixture: ComponentFixture<TestingStartingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TestingStartingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TestingStartingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
