import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TermsAndConditoinsComponent } from './terms-and-conditoins.component';

describe('TermsAndConditoinsComponent', () => {
  let component: TermsAndConditoinsComponent;
  let fixture: ComponentFixture<TermsAndConditoinsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TermsAndConditoinsComponent]
    });
    fixture = TestBed.createComponent(TermsAndConditoinsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
