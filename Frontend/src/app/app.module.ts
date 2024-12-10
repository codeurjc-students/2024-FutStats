import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { LeagueListComponent } from './components/leagues/league-list.component';
import { LeagueDetailComponent } from './components/leagues/league-detail.component';
import { LeagueFormComponent } from './components/leagues/league-form.component';
import { MatchListComponent } from './components/matches/match-list.component';
import { MatchDetailComponent } from './components/matches/match-detail.component';
import { MatchFormComponent } from './components/matches/match-form.component';
import { TeamListComponent } from './components/teams/team-list.component';
import { TeamDetailComponent } from './components/teams/team-detail.component';
import { TeamFormComponent } from './components/teams/team-form.component';
import { LoginComponent } from './components/login/login.component';

import { routing } from '../../src/app.routing';


@NgModule({
  declarations: [AppComponent, 
    LeagueListComponent, LeagueDetailComponent, LeagueFormComponent,
    MatchListComponent, MatchDetailComponent, MatchFormComponent,
    TeamDetailComponent, TeamListComponent, TeamFormComponent,
    LoginComponent],
  imports: [BrowserModule, FormsModule, HttpClientModule, routing],
  bootstrap: [AppComponent]
})
export class AppModule { }
