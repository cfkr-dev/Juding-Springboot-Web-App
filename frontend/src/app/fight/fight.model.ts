import {Competition} from '../competition/competition.model';
import {User} from '../user/user.model';

export class Fight {
    constructor(competition: Competition, levelInTree: number, upFight: Fight, downFight: Fight, parentFight: Fight, isFinished: boolean, winner: User, loser: User) {
        this._competition = competition;
        this._levelInTree = levelInTree;
        this._upFight = upFight;
        this._downFight = downFight;
        this._parentFight = parentFight;
        this._finished = isFinished;
        this._winner = winner;
        this._loser = loser;
    }

    private _competition: Competition;

    get competition(): Competition {
        return this._competition;
    }

    set competition(value: Competition) {
        this._competition = value;
    }

    private _levelInTree: number;

    get levelInTree(): number {
        return this._levelInTree;
    }

    set levelInTree(value: number) {
        this._levelInTree = value;
    }

    private _upFight: Fight;

    get upFight(): Fight {
        return this._upFight;
    }

    set upFight(value: Fight) {
        this._upFight = value;
    }

    private _downFight: Fight;

    get downFight(): Fight {
        return this._downFight;
    }

    set downFight(value: Fight) {
        this._downFight = value;
    }

    private _parentFight: Fight;

    get parentFight(): Fight {
        return this._parentFight;
    }

    set parentFight(value: Fight) {
        this._parentFight = value;
    }

    private _finished: boolean;

    get finished(): boolean {
        return this._finished;
    }

    set finished(value: boolean) {
        this._finished = value;
    }

    private _winner: User;

    get winner(): User {
        return this._winner;
    }

    set winner(value: User) {
        this._winner = value;
    }

    private _loser: User;

    get loser(): User {
        return this._loser;
    }

    set loser(value: User) {
        this._loser = value;
    }
}
