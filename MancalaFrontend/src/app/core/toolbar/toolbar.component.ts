import { Component, HostListener, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {
  isMobile = false
  maxMobileWidth = 500

  navButtons = [
    { label: 'About Me', route: '/about' },
    { label: 'Projects', route: '/projects' },
  ]

  user$ = new Observable()

  logout() {

  }

  ngOnInit(): void {
    this.resize()
  }

  @HostListener('window:resize')
  public resize() {
    const width = window.screen.width
    this.isMobile = width <= this.maxMobileWidth
  }
}
