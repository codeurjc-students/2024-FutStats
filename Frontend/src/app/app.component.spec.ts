import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing'; // Usar RouterTestingModule para pruebas
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'; // Importa módulos para formularios
import { AppComponent } from './app.component';

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
import { LoginComponent } from './components/login/login.component';

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule, // Mejor para pruebas que RouterModule
        HttpClientModule, // Para servicios que usan HttpClient
        FormsModule, // Si usas formularios de template-driven
        ReactiveFormsModule, // Si usas formularios reactivos
      ],
      declarations: [
        AppComponent,
        LeagueListComponent, LeagueDetailComponent, LeagueFormComponent,
        MatchDetailComponent, MatchFormComponent,
        TeamDetailComponent, TeamFormComponent,
        PlayerDetailComponent, PlayerFormComponent,
        PlayerMatchDetailComponent, PlayerMatchFormComponent,
        UserListComponent, UserDetailComponent, UserFormComponent,
        LoginComponent,
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });
});
