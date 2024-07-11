import { Component } from '@angular/core';
import { AppResponse } from 'src/app/model/app-response';
import { Email } from 'src/app/model/email';
import { EmailService } from 'src/app/service/email.service';

@Component({
  selector: 'app-mail-trigger',
  templateUrl: './mail-trigger.component.html',
  styleUrls: ['./mail-trigger.component.css']
})
export class MailTriggerComponent {
email='';
message='';
Emails:Email[]=[]
error=''
subject=''
constructor(private eamilservice:EmailService){}
sendEmail(){
  let emails:Email={email:this.email,message:this.message,subject:this.subject}
console.log(emails);

  
  this.eamilservice.mailtrigger(emails).subscribe({
    next: (response: AppResponse) => {
      this.Emails=response.data
      console.log(this.Emails);
      
    },
    error: (err) => {
      let message: string = err?.error?.error?.message;
      this.error = message.includes(',') ? message.split(',')[0] : message;
    },
  });
}
}
