import {Injectable} from '@angular/core';

export interface Belt {
  name: string;
  longName: string;
  isBlack: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class BeltService {

  belts: Belt[];

  constructor() {
    this.belts = [
      {name: 'B', longName: 'Blanco', isBlack: false},
      {name: 'BAm', longName: 'Blanco-Amarillo', isBlack: false},
      {name: 'Am', longName: 'Amarillo', isBlack: false},
      {name: 'AmN', longName: 'Amarillo-Naranja', isBlack: false},
      {name: 'N', longName: 'Naranja', isBlack: false},
      {name: 'NV', longName: 'Naranja-Verde', isBlack: false},
      {name: 'V', longName: 'Verde', isBlack: false},
      {name: 'VAz', longName: 'Verde-Azul', isBlack: false},
      {name: 'Az', longName: 'Azul', isBlack: false},
      {name: 'AzM', longName: 'Azul-Marrón', isBlack: false},
      {name: 'M', longName: 'Marrón', isBlack: false},
      {name: 'N1', longName: 'Negro - Dan 1', isBlack: true},
      {name: 'N2', longName: 'Negro - Dan 2', isBlack: true},
      {name: 'N3', longName: 'Negro - Dan 3', isBlack: true},
      {name: 'N4', longName: 'Negro - Dan 4', isBlack: true},
      {name: 'N5', longName: 'Negro - Dan 5', isBlack: true},
      {name: 'N6', longName: 'Negro - Dan 6', isBlack: true},
      {name: 'N7', longName: 'Negro - Dan 7', isBlack: true},
      {name: 'N8', longName: 'Negro - Dan 8', isBlack: true},
      {name: 'N9', longName: 'Negro - Dan 9', isBlack: true},
      {name: 'N10', longName: 'Negro - Dan 10', isBlack: true}
    ];
  }

  public getBeltByName(name: string): Belt {
    for (const b of this.belts) {
      if (b.name === name) {
        return b;
      }
    }
    return null;
  }
}
