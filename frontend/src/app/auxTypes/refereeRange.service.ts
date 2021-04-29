import {Injectable} from '@angular/core';

export interface RefereeRange {
  name: string;
  longName: string;
}

@Injectable({
  providedIn: 'root'
})
export class RefereeRangeService {

  ranges: RefereeRange[];

  constructor() {
    this.ranges = [
      {name: 'S', longName: 'Solicitante'},
      {name: 'E', longName: 'Árbitro estándar'},
      {name: 'C', longName: 'Árbitro de competición'},
      {name: 'A', longName: 'Árbitro auxiliar'}
    ];
  }

  public getRangeByName(name: string): RefereeRange {
    for (const r of this.ranges) {
      if (r.name === name) {
        return r;
      }
    }
    return null;
  }
}
