import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Cat } from 'src/app/model/cat';
import { CatService } from 'src/app/service/cat.service';

@Component({
  selector: 'app-placeholder-card',
  templateUrl: './placeholder-card.component.html',
  styleUrls: ['./placeholder-card.component.scss']
})
export class PlaceholderCardComponent implements OnInit {
  baseUrl = 'https://cataas.com'
  requestString = '/cat?json=true&height=500&type=sq'

  image$: Observable<Cat>;

  constructor(private catService: CatService) {
  }

  ngOnInit(): void {
    this.image$ = this.catService.getImage(`${this.baseUrl}${this.requestString}`);
  }
}