import { Component } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { AppComponent } from 'src/app/app.component';
import { LoaderService } from 'src/app/service/loader.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
})
export class AdminHomeComponent extends AppComponent {

  isLoading: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
}
