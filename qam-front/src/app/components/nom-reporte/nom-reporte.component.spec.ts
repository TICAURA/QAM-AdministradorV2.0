import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NomReporteComponent } from './nom-reporte.component';

describe('NomReporteComponent', () => {
  let component: NomReporteComponent;
  let fixture: ComponentFixture<NomReporteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NomReporteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NomReporteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
