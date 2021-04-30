import {User} from '../user/user.model';

export interface CompetitionInterface {
    idCompetition: number;
    shortName: string;
    additionalInfo: string;
    minWeight: number;
    maxWeight: number;
    startDate: string;
    endDate: string;
    referee: User;
}
