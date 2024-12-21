import { Team } from '../models/team.model';

export interface League{
    id?: number;
    name: string;
    president: string;
    nationality: string;
    teams: Team[];
    image: boolean;
}