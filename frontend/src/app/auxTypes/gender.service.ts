import {Injectable} from '@angular/core';

export interface Gender {
  name: string;
  longName: string;
}

@Injectable({
  providedIn: 'root'
})
export class GenderService {

  ranges: Gender[];

  constructor() {
    this.ranges = [
      {name: 'H', longName: 'Hombre'},
      {name: 'M', longName: 'Mujer'}
    ];
  }

  public getGenderByName(name: string): Gender {
    for (const g of this.ranges) {
      if (g.name === name) {
        return g;
      }
    }
    return null;
  }
}
