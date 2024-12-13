import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { LeagueListComponent } from './components/leagues/league-list.component';
import { LeagueDetailComponent } from './components/leagues/league-detail.component';
import { LeagueFormComponent } from './components/leagues/league-form.component';

import { MatchDetailComponent } from './components/matches/match-detail.component';
import { MatchFormComponent } from './components/matches/match-form.component';

import { TeamDetailComponent } from './components/teams/team-detail.component';
import { TeamFormComponent } from './components/teams/team-form.component';

import { PlayerDetailComponent } from './components/players/player-detail.component';
import { PlayerFormComponent } from './components/players/player-form.component';

import { PlayerMatchDetailComponent } from './components/playerMatches/playerMatch-detail.component';
import { PlayerMatchFormComponent } from './components/playerMatches/playerMatch-form.component';

import { LoginComponent } from './components/login/login.component';

import { routing } from '../../src/app.routing';


@NgModule({
  declarations: [AppComponent, 
    LeagueListComponent, LeagueDetailComponent, LeagueFormComponent,
    MatchDetailComponent, MatchFormComponent,
    TeamDetailComponent, TeamFormComponent,
    PlayerDetailComponent, PlayerFormComponent,
    PlayerMatchDetailComponent, PlayerMatchFormComponent,
    LoginComponent],
  imports: [BrowserModule, FormsModule, HttpClientModule, routing],
  bootstrap: [AppComponent]
})
export class AppModule { }
