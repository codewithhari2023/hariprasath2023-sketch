import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MailTriggerComponent } from './mail-trigger.component';

describe('MailTriggerComponent', () => {
  let component: MailTriggerComponent;
  let fixture: ComponentFixture<MailTriggerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MailTriggerComponent]
    });
    fixture = TestBed.createComponent(MailTriggerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
