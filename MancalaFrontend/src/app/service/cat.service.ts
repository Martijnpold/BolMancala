import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cat } from '../model/cat';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CatService {

  constructor(private httpClient: HttpClient) { }

  getImage(imageUrl: string): Observable<Cat> {
    return this.httpClient.get<Cat>(imageUrl)
    .pipe(map((doc: any) => {
      const obj = Cat.fromDoc(doc);
      return obj;
    }))
  }
}
