import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssingTableComponent } from './assing-table.component';

describe('AssingTableComponent', () => {
  let component: AssingTableComponent;
  let fixture: ComponentFixture<AssingTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssingTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssingTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
