import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Pit } from 'src/app/model/pit';

@Component({
  selector: 'app-pit',
  templateUrl: './pit.component.html',
  styleUrls: ['./pit.component.scss']
})
export class PitComponent {
  @Input() hasButton: boolean;
  @Input() showButton: boolean;
  @Input() disabled: boolean;
  @Input() pit: Pit;
  @Output() onMove = new EventEmitter<number>();

  click() {
    this.onMove.emit(this.pit.index)
  }
}
