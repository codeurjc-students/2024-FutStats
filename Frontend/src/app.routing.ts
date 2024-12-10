import { Routes, RouterModule } from '@angular/router';
import { LeagueDetailComponent } from './app/components/leagues/league-detail.component';
import { LeagueListComponent } from './app/components/leagues/league-list.component';
import { LeagueFormComponent } from './app/components/leagues/league-form.component';
import { MatchListComponent } from './app/components/matches/match-list.component';
import { MatchDetailComponent } from './app/components/matches/match-detail.component';
import { MatchFormComponent } from './app/components/matches/match-form.component';
import { TeamListComponent } from './app/components/teams/team-list.component';
import { TeamDetailComponent } from './app/components/teams/team-detail.component';
import { TeamFormComponent } from './app/components/teams/team-form.component';

const appRoutes: Routes = [
    { path: 'login', component: LoginComponent},
    { path: 'leagues', component: LeagueListComponent },
    { path: 'leagues/new', component: LeagueFormComponent},
    { path: 'leagues/:id', component: LeagueDetailComponent},
    { path: 'leagues/edit/:id', component: LeagueFormComponent},
    { path: 'matches', component: MatchListComponent},
    { path: 'matches/new', component: MatchFormComponent},
    { path: 'matches/:id', component: MatchDetailComponent},
    { path: 'teams', component: TeamListComponent},
    { path: 'teams/new', component: TeamFormComponent},
    { path: 'teams/edit/:id', component: TeamFormComponent},
    { path: 'teams/:id', component: TeamDetailComponent},
    { path: '', redirectTo: 'leagues', pathMatch: 'full' }
]

export const routing = RouterModule.forRoot(appRoutes);