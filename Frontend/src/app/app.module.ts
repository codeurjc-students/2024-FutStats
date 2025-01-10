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

import { UserListComponent } from './components/users/user-list.component';
import { UserDetailComponent } from './components/users/user-detail.component';
import { UserFormComponent } from './components/users/user-form.component';

import { NotFoundDetailComponent } from './errorPages/not-found.component';
import { ServerErrrorDetailComponent } from './errorPages/server-error.component';
import { UnauthorizedDetailComponent } from './errorPages/unauthorized.component';

import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { ErrorInterceptor } from './core/errors/interceptors/server-error.interceptor';


import { myProfileComponent } from './components/users/myProfile.component';

import { LoginComponent } from './components/login/login.component';

import { NgxPaginationModule } from 'ngx-pagination';

import { routing } from '../../src/app.routing';
import { ForbiddenDetailComponent } from './errorPages/forbidden.component';

@NgModule({
  declarations: [AppComponent, 
    LeagueListComponent, LeagueDetailComponent, LeagueFormComponent,
    MatchDetailComponent, MatchFormComponent,
    TeamDetailComponent, TeamFormComponent,
    PlayerDetailComponent, PlayerFormComponent,
    PlayerMatchDetailComponent, PlayerMatchFormComponent,
    UserListComponent, UserDetailComponent, UserFormComponent, myProfileComponent,
    NotFoundDetailComponent, ServerErrrorDetailComponent, UnauthorizedDetailComponent,
    ForbiddenDetailComponent,
    LoginComponent],
    providers: [
      { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    ],
  imports: [BrowserModule, FormsModule, HttpClientModule, routing, NgxPaginationModule],
  bootstrap: [AppComponent]
})
export class AppModule { }
