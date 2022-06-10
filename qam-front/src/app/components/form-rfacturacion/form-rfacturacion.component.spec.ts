import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormRfacturacionComponent } from './form-rfacturacion.component';

describe('FormRfacturacionComponent', () => {
  let component: FormRfacturacionComponent;
  let fixture: ComponentFixture<FormRfacturacionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormRfacturacionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormRfacturacionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
