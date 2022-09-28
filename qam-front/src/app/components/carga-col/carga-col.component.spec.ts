import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CargaColComponent } from './carga-col.component';

describe('CargaColComponent', () => {
  let component: CargaColComponent;
  let fixture: ComponentFixture<CargaColComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CargaColComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CargaColComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
