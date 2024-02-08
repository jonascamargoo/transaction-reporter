import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BtnSelectComponent } from './btn-select.component';

describe('BtnSelectComponent', () => {
  let component: BtnSelectComponent;
  let fixture: ComponentFixture<BtnSelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BtnSelectComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BtnSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
