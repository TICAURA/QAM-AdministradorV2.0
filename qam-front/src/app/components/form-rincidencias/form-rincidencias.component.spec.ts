import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormRincidenciasComponent } from './form-rincidencias.component';

describe('FormRincidenciasComponent', () => {
  let component: FormRincidenciasComponent;
  let fixture: ComponentFixture<FormRincidenciasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormRincidenciasComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormRincidenciasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
