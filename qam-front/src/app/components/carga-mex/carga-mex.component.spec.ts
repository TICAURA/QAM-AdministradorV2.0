import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CargaMexComponent } from './carga-mex.component';

describe('CargaMexComponent', () => {
  let component: CargaMexComponent;
  let fixture: ComponentFixture<CargaMexComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CargaMexComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CargaMexComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
