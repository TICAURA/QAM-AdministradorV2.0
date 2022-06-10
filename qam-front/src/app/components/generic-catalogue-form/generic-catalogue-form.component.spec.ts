import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenericCatalogueFormComponent } from './generic-catalogue-form.component';

describe('GenericCatalogueFormComponent', () => {
  let component: GenericCatalogueFormComponent;
  let fixture: ComponentFixture<GenericCatalogueFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GenericCatalogueFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GenericCatalogueFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
