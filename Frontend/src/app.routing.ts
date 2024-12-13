import { Routes, RouterModule } from '@angular/router';

import { LeagueDetailComponent } from './app/components/leagues/league-detail.component';
import { LeagueListComponent } from './app/components/leagues/league-list.component';
import { LeagueFormComponent } from './app/components/leagues/league-form.component';

import { MatchDetailComponent } from './app/components/matches/match-detail.component';
import { MatchFormComponent } from './app/components/matches/match-form.component';

import { TeamDetailComponent } from './app/components/teams/team-detail.component';
import { TeamFormComponent } from './app/components/teams/team-form.component';

import { PlayerDetailComponent } from './app/components/players/player-detail.component';
import { PlayerFormComponent } from './app/components/players/player-form.component';

import { PlayerMatchDetailComponent } from './app/components/playerMatches/playerMatch-detail.component';
import { PlayerMatchFormComponent } from './app/components/playerMatches/playerMatch-form.component';

const appRoutes: Routes = [
    { path: 'leagues', component: LeagueListComponent },
    { path: 'leagues/new', component: LeagueFormComponent},
    { path: 'leagues/:id', component: LeagueDetailComponent},
    { path: 'leagues/edit/:id', component: LeagueFormComponent},
    { path: 'matches/new', component: MatchFormComponent},
    { path: 'matches/:id', component: MatchDetailComponent},
    { path: 'teams/new', component: TeamFormComponent},
    { path: 'teams/edit/:id', component: TeamFormComponent},
    { path: 'teams/:id', component: TeamDetailComponent},
    { path: 'players/new', component: PlayerFormComponent},
    { path: 'players/:id', component: PlayerDetailComponent},
    { path: 'players/edit/:id', component: PlayerFormComponent},
    { path: 'playerMatch/new', component: PlayerMatchFormComponent},
    { path: 'playerMatch/:id', component: PlayerMatchDetailComponent},
    { path: 'playerMatch/edit/:id', component: PlayerMatchFormComponent},
    { path: '', redirectTo: 'leagues', pathMatch: 'full' }
]

export const routing = RouterModule.forRoot(appRoutes);