import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormPromocionComponent } from './form-promocion.component';

describe('FormPromocionComponent', () => {
  let component: FormPromocionComponent;
  let fixture: ComponentFixture<FormPromocionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormPromocionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormPromocionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
