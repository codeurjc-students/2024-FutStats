import { League } from '../models/league.model';

export interface Team{
    id?: number;
    name: string;
    trophies: number;
    nationality: string;
    trainer: string;
    secondTrainer: string;
    president: string;
    stadium: string;
    points: number;
    league: string;

    //Other classes

    totalMatches?: number;

    // offensive
    totalShoots?: number;
    totalGoals?: number;
    shootsPerMatch?: number;
    goalsPerMatch?: number;
    scoreAvg?: number;
    penaltys?: number;
    faultsReceived?: number;
    offsides?: number;

    // defensive
    commitedFaults?: number;
    recovers?: number;
    duels?: number;
    wonDuels?: number;
    duelAvg?: number;
    yellowCards?: number;
    redCards?: number;
    cards?: number;

    // creation
    possesion?: number;
    passes?: number;
    passesPerMatch?: number;
    goodPasses?: number;
    passesAvg?: number;
    shortPasses?: number;
    longPasses?: number;
    assists?: number;
    dribles?: number;
    centers?: number;
    ballLosses?: number;

    // matches
    wonMatches?: number;
    lostMatches?: number;
    drawMatches?: number;
    wonMatchesAvg?: number;
}