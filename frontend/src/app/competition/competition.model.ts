import {Fight} from '../fight/fight.model';
import {User} from '../user/user.model';

export class Competition {
  private _shortName: string;
  private _additionalInfo: string;
  private _minWeight: number;
  private _maxWeight: number;
  private _startDate: Date;
  private _endDate: Date;
  private _referee: User;
  private _idCompetition?: number;
  private _fights: Fight[];

  constructor(shortName:string, additionalInfo:string, minWeight:number, maxWeight:number, startDate:Date, endDate:Date, referee:User){
  this._shortName = shortName;
  this._additionalInfo = additionalInfo;
  this._minWeight = minWeight;
  this._maxWeight = maxWeight;
  this._startDate = startDate;
  this._endDate = endDate;
  this._referee = referee;
  }


    get shortName(): string {
        return this._shortName;
    }

    set shortName(value: string) {
        this._shortName = value;
    }

    get additionalInfo(): string {
        return this._additionalInfo;
    }

    set additionalInfo(value: string) {
        this._additionalInfo = value;
    }

    get minWeight(): number {
        return this._minWeight;
    }

    set minWeight(value: number) {
        this._minWeight = value;
    }

    get maxWeight(): number {
        return this._maxWeight;
    }

    set maxWeight(value: number) {
        this._maxWeight = value;
    }

    get startDate(): Date {
        return this._startDate;
    }

    set startDate(value: Date) {
        this._startDate = value;
    }

    get endDate(): Date {
        return this._endDate;
    }

    set endDate(value: Date) {
        this._endDate = value;
    }

    get referee(): User {
        return this._referee;
    }

    set referee(value: User) {
        this._referee = value;
    }

    get idCompetition(): number {
        return this._idCompetition;
    }

    set idCompetition(value: number) {
        this._idCompetition = value;
    }

    get fights(): Fight[] {
        return this._fights;
    }

    set fights(value: Fight[]) {
        this._fights = value;
    }
}
