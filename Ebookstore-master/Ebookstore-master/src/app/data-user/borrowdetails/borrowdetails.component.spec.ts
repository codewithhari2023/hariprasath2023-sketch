import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BorrowdetailsComponent } from './borrowdetails.component';

describe('BorrowdetailsComponent', () => {
  let component: BorrowdetailsComponent;
  let fixture: ComponentFixture<BorrowdetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BorrowdetailsComponent]
    });
    fixture = TestBed.createComponent(BorrowdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
