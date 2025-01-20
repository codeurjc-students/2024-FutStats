import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatchesService } from 'src/app/services/match.service';
import { LeaguesService } from 'src/app/services/league.service';
import { Match } from 'src/app/models/match.model';
import { Team } from 'src/app/models/team.model';
import { League } from 'src/app/models/league.model';


@Component({
    templateUrl: './match-form.component.html',
    standalone: false
})
export class MatchFormComponent implements OnInit {
  newMatch: boolean;
  match: Match;
  leagues: League[] = [];
  teams: Team[] = [];
  league: League;
  selectedLeagueId: string; // Liga seleccionada

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private service: MatchesService,
    private leagueService: LeaguesService
  ) {
    const id = activatedRoute.snapshot.params['id'];
    if (id) {
      service.getMatch(id).subscribe(
        match => this.match = match,
        error => console.error(error)
      );
      this.newMatch = false;
    } else {
      this.match = {
        place: '',
        name: '',
        date: new Date,
        team1: '', 
        team2: '', 
        league: '', 
      };
      this.newMatch = true;
    }
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
    if(this.newMatch){
      this.match.league = this.selectedLeagueId;
      this.service.addMatch(this.match).subscribe(
        (match: Match) => this.afterSave(match),
        error => alert('Error creating new league: ' + error)
      );
      }else{
        this.match.league = this.selectedLeagueId;
        this.service.updateMatch(this.match).subscribe(
          (match: Match) => this.afterSave(match),
          error => alert('Error creating new league: ' + error)
        );
      }
  }

  afterSave(match: Match) {
    this.leagueService.getLeagueByName(this.match.league).subscribe({
      next: (league: League) => {
        this.league = league;
        this.router.navigate(['/leagues', this.league.id]);
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
