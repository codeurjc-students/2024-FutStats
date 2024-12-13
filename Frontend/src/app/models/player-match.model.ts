export interface PlayerMatch{
    id?: number;
    name: string;
    player: string;
    match: number;
    matchName: string;

    shoots?: number;
    goals?: number;
    penaltys?: number;
    faultsReceived?: number;
    offsides?: number;

    // defensive
    commitedFaults?: number;
    recovers?: number;
    duels?: number;
    wonDuels?: number;
    yellowCards?: number;
    redCards?: number;

    // creation
    passes?: number;
    goodPasses?: number;
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