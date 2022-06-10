import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NomListaComponent } from './nom-lista.component';

describe('NomListaComponent', () => {
  let component: NomListaComponent;
  let fixture: ComponentFixture<NomListaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NomListaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NomListaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
