import {Injectable} from '@angular/core';

export interface Gender {
    name: string;
    longName: string;
}

@Injectable({
    providedIn: 'root'
})
export class GenderService {

    genders: Gender[];

    constructor() {
        this.genders = [
            {name: 'H', longName: 'Hombre'},
            {name: 'M', longName: 'Mujer'}
        ];
    }

    public getGenderByName(name: string): Gender {
        for (const g of this.genders) {
            if (g.name === name) {
                return g;
            }
        }
        return null;
    }
}
