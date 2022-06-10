import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormResetcontraComponent } from './form-resetcontra.component';

describe('FormResetcontraComponent', () => {
  let component: FormResetcontraComponent;
  let fixture: ComponentFixture<FormResetcontraComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormResetcontraComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormResetcontraComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
