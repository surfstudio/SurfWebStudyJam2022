import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestingProcessComponent } from './testing-process.component';

describe('TestingProcessComponent', () => {
  let component: TestingProcessComponent;
  let fixture: ComponentFixture<TestingProcessComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TestingProcessComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TestingProcessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
