import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormInterfazComponent } from './form-interfaz.component';

describe('FormInterfazComponent', () => {
  let component: FormInterfazComponent;
  let fixture: ComponentFixture<FormInterfazComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormInterfazComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormInterfazComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
