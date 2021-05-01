import {Competition} from '../competition/competition.model';
import {User} from '../user/user.model';


export class Fight {
    private _idFight: number;
    private _competition: Competition;
    private _levelInTree: number;
    private _upFight: Fight;
    private _downFight: Fight;
    private _parentFight: Fight;
    private _finished: boolean;
    private _winner: User;
    private _loser: User;


    constructor(idFight: number, competition: Competition, levelInTree: number, upFight: Fight, downFight: Fight, parentFight: Fight, isFinished: boolean, winner: User, loser: User) {
        this._idFight = idFight;
        this._competition = competition;
        this._levelInTree = levelInTree;
        this._upFight = upFight;
        this._downFight = downFight;
        this._parentFight = parentFight;
        this._finished = isFinished;
        this._winner = winner;
        this._loser = loser;
    }


    get idFight(): number {
        return this._idFight;
    }

    set idFight(value: number) {
        this._idFight = value;
    }

    get competition(): Competition {
        return this._competition;
    }

    set competition(value: Competition) {
        this._competition = value;
    }

    get levelInTree(): number {
        return this._levelInTree;
    }

    set levelInTree(value: number) {
        this._levelInTree = value;
    }

    get upFight(): Fight {
        return this._upFight;
    }

    set upFight(value: Fight) {
        this._upFight = value;
    }

    get downFight(): Fight {
        return this._downFight;
    }

    set downFight(value: Fight) {
        this._downFight = value;
    }

    get parentFight(): Fight {
        return this._parentFight;
    }

    set parentFight(value: Fight) {
        this._parentFight = value;
    }

    get finished(): boolean {
        return this._finished;
    }

    set finished(value: boolean) {
        this._finished = value;
    }

    get winner(): User {
        return this._winner;
    }

    set winner(value: User) {
        this._winner = value;
    }

    get loser(): User {
        return this._loser;
    }

    set loser(value: User) {
        this._loser = value;
    }
}
