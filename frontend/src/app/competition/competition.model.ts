import {Fight} from '../fight/fight.model';
import {User} from '../user/user.model';

export class Competition {
    constructor(shortName: string, additionalInfo: string, minWeight: number, maxWeight: number, startDate: Date, endDate: Date, referee: User) {
        this._shortName = shortName;
        this._additionalInfo = additionalInfo;
        this._minWeight = minWeight;
        this._maxWeight = maxWeight;
        this._startDate = startDate;
        this._endDate = endDate;
        this._referee = referee;
    }

    private _shortName: string;

    get shortName(): string {
        return this._shortName;
    }

    set shortName(value: string) {
        this._shortName = value;
    }

    private _additionalInfo: string;

    get additionalInfo(): string {
        return this._additionalInfo;
    }

    set additionalInfo(value: string) {
        this._additionalInfo = value;
    }

    private _minWeight: number;

    get minWeight(): number {
        return this._minWeight;
    }

    set minWeight(value: number) {
        this._minWeight = value;
    }

    private _maxWeight: number;

    get maxWeight(): number {
        return this._maxWeight;
    }

    set maxWeight(value: number) {
        this._maxWeight = value;
    }

    private _startDate: Date;

    get startDate(): Date {
        return this._startDate;
    }

    set startDate(value: Date) {
        this._startDate = value;
    }

    private _endDate: Date;

    get endDate(): Date {
        return this._endDate;
    }

    set endDate(value: Date) {
        this._endDate = value;
    }

    private _referee: User;

    get referee(): User {
        return this._referee;
    }

    set referee(value: User) {
        this._referee = value;
    }

    private _idCompetition?: number;

    get idCompetition(): number {
        return this._idCompetition;
    }

    set idCompetition(value: number) {
        this._idCompetition = value;
    }

    private _fights: Fight[];

    get fights(): Fight[] {
        return this._fights;
    }

    set fights(value: Fight[]) {
        this._fights = value;
    }
}
