import { UserInterface } from '../user/user.interface';

export interface CompetitionInterface {
  idCompetition: number;
  shortName: string;
  additionalInfo: string;
  minWeight: number;
  maxWeight: number;
  startDate: string;
  endDate: string;
  referee: UserInterface;
}
