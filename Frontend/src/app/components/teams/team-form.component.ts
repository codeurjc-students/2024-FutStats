import { Team } from 'src/app/models/team.model';
import { TeamsService } from 'src/app/services/team.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { League } from 'src/app/models/league.model';
import { LeaguesService } from 'src/app/services/league.service';

@Component({
  templateUrl: './team-form.component.html',
})
export class TeamFormComponent implements OnInit {
  newTeam: boolean;
  team: Team;
  leagues: League[] = []; // Lista de ligas disponibles
  league: League;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private service: TeamsService,
    private leagueService: LeaguesService // Servicio de ligas
  ) {
    this.team = {
      id: 0,
      name: '',
      trophies: 0,
      nationality: '',
      trainer: '',
      secondTrainer: '',
      president: '',
      stadium: '',
      points: 0,
      league: '',
    }; // Valores predeterminados
    this.newTeam = true;
  }

  ngOnInit(): void {
    this.loadLeagues(); // Cargar ligas al iniciar el componente
  }

  // Cargar las ligas desde el servicio
  private loadLeagues() {
    this.leagueService.getLeagues().subscribe({
      next: (leagues) => (this.leagues = leagues),
      error: (error) => console.error('Error loading leagues:', error),
    });
  }

  cancel() {
    window.history.back();
  }

  save() {
    this.service.addTeam(this.team).subscribe(
      (team: Team) => this.afterSave(team),
      (error) => alert('Error creating new team: ' + error)
    );
  }

  private afterSave(team: Team) {
    this.leagueService.getLeagueByName(this.team.league).subscribe({
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
}
