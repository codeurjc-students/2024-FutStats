import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatchesService } from 'src/app/services/match.service';
import { LeaguesService } from 'src/app/services/league.service';
import { Match } from 'src/app/models/match.model';
import { Team } from 'src/app/models/team.model';
import { League } from 'src/app/models/league.model';

@Component({
  templateUrl: './match-form.component.html',
})
export class MatchFormComponent implements OnInit {
  match: Match;
  leagues: League[] = [];
  teams: Team[] = [];
  league: League;
  selectedLeagueId: string; // Liga seleccionada

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private matchService: MatchesService,
    private leagueService: LeaguesService
  ) {
    this.match = {
      id: 0,
      place: '',
      name: '',
      date: new Date,
      team1: '', // ID del equipo 1
      team2: '', // ID del equipo 2
      league: '', // ID de la liga seleccionada
    };
  }

  ngOnInit(): void {
    this.loadLeagues(); // Cargar todas las ligas al iniciar
  }

  private loadLeagues() {
    this.leagueService.getLeagues().subscribe({
      next: (leagues) => (this.leagues = leagues),
      error: (error) => console.error('Error loading leagues:', error),
    });
  }

  onLeagueChange() {
    console.log('Liga seleccionada:', this.selectedLeagueId);
    if (this.selectedLeagueId) {
      this.leagueService.getTeamsByName(this.selectedLeagueId).subscribe({
        next: (teams) => {
          console.log('Equipos cargados:', teams);
          this.teams = teams;
        },
        error: (error) => console.error('Error al cargar equipos:', error),
      });
    } else {
      console.log('No se seleccionó ninguna liga, limpiando equipos');
      this.teams = [];
    }
  }

  save() {
    // Asignar la liga seleccionada al match antes de enviarlo
    this.match.league = this.selectedLeagueId; 
    this.matchService.addMatch(this.match).subscribe({
      next: (match: Match) => this.afterSave(match),
      error: (error) => alert('Error creating new match: ' + error),
    });
  }

  private afterSave(match: Match) {
    this.leagueService.getLeagueByName(this.match.league).subscribe({
      next: (league: League) => {
        this.league = league;
        this.router.navigate(['/leagues', this.league.id]); // Navigate after league is fetched
      },
      error: (error) => {
        console.error('Error fetching league:', error);
        alert('Failed to fetch league details. Please try again.');
      },
    });
  }

  cancel() {
    window.history.back(); // Volver atrás sin guardar
  }
}
