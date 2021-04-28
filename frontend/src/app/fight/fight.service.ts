import {Injectable} from '@angular/core';
import {Fight} from './fight.model';

@Injectable()
export class FightService {
    static countParticipants(listOfFights: Fight[]): number{
        let count = 16;
        for (let i = 7 ; i < listOfFights.length ; i++){
            if (listOfFights[i].loser === null){
                if (listOfFights[i].winner === null){
                    count--;
                }
                if (listOfFights[i].loser === null){
                    count--;
                }
            }
        }
        return count;
    }
}
