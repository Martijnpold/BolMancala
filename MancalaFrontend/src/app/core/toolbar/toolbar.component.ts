import { Component } from '@angular/core';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent {

  navButtons = [
    { label: 'Games', route: '/games' },
    { label: 'Invites', route: '/invites' },
  ]

  user$ = new Observable()

  logout() {

  }
}
