import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutCNABComponent } from './about-cnab.component';

describe('AboutCNABComponent', () => {
  let component: AboutCNABComponent;
  let fixture: ComponentFixture<AboutCNABComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AboutCNABComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AboutCNABComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
