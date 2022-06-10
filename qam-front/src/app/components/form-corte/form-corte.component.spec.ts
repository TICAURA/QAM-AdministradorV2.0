import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormCorteComponent } from './form-corte.component';

describe('FormCorteComponent', () => {
  let component: FormCorteComponent;
  let fixture: ComponentFixture<FormCorteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormCorteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormCorteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
