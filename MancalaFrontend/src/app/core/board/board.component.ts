import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Board } from 'src/app/model/board';

export interface Tile {
  cols: number;
  rows: number;
  index: number;
  side: string;
  color: string;
}

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss']
})
export class BoardComponent {
  @Input() showButtons: boolean;
  @Input() board: Board;
  @Input() side: string;
  @Output() onMove = new EventEmitter<number>();

  tiles: Tile[] = [
    { index: 6, cols: 1, rows: 2, side: 'TOP', color: '#7d0c0c' },

    { index: 5, cols: 1, rows: 1, side: 'TOP', color: '#a33c3c' },
    { index: 4, cols: 1, rows: 1, side: 'TOP', color: '#a33c3c' },
    { index: 3, cols: 1, rows: 1, side: 'TOP', color: '#a33c3c' },
    { index: 2, cols: 1, rows: 1, side: 'TOP', color: '#a33c3c' },
    { index: 1, cols: 1, rows: 1, side: 'TOP', color: '#a33c3c' },
    { index: 0, cols: 1, rows: 1, side: 'TOP', color: '#a33c3c' },

    { index: 13, cols: 1, rows: 2, side: 'BOTTOM', color: '#120c7d' },

    { index: 7, cols: 1, rows: 1, side: 'BOTTOM', color: '#332f80' },
    { index: 8, cols: 1, rows: 1, side: 'BOTTOM', color: '#332f80' },
    { index: 9, cols: 1, rows: 1, side: 'BOTTOM', color: '#332f80' },
    { index: 10, cols: 1, rows: 1, side: 'BOTTOM', color: '#332f80' },
    { index: 11, cols: 1, rows: 1, side: 'BOTTOM', color: '#332f80' },
    { index: 12, cols: 1, rows: 1, side: 'BOTTOM', color: '#332f80' },
  ];

  click(index: number) {
    this.onMove.emit(index)
  }
}
