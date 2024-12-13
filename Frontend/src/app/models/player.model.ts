export interface Player{
    id?: number;
    name: string;
    age: number;
    position: string;
    nationality: string;
    team: string;
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
    cards?: number;
    yellowCards?: number;
    redCards?: number;

    // creation
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

    //Goalkeeper
    shootsReceived?: number
    goalsConceded?: number
    saves?: number
    savesAvg?: number
    outBoxSaves?: number
    inBoxSaves?: number
}