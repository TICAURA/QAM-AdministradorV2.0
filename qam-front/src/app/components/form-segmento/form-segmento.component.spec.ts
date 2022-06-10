import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormSegmentoComponent } from './form-segmento.component';

describe('FormSegmentoComponent', () => {
  let component: FormSegmentoComponent;
  let fixture: ComponentFixture<FormSegmentoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormSegmentoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormSegmentoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
