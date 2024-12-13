import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PlayersService } from 'src/app/services/player.service';
import { LeaguesService } from 'src/app/services/league.service';
import { Team } from 'src/app/models/team.model';
import { League } from 'src/app/models/league.model';
import { Player } from 'src/app/models/player.model';
import { TeamsService } from 'src/app/services/team.service';

@Component({
  templateUrl: './player-form.component.html',
})
export class PlayerFormComponent implements OnInit {
  player: Player;
  leagues: League[] = [];
  teams: Team[] = [];
  selectedLeagueId: string; // Liga seleccionada
  team: Team;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private playerService: PlayersService,
    private leagueService: LeaguesService,
    private teamService: TeamsService
  ) {
    this.player = {
      id: 0,
      name: '',
      age: 0,
      position: '',
      nationality: '',
      team: '', 
      league: ''
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
    // Asignar la liga seleccionada al player antes de enviarlo
    this.player.league = this.selectedLeagueId; 
    console.log(this.player);
    this.playerService.addPlayer(this.player).subscribe({
      next: (player: Player) => this.afterSave(player),
      error: (error) => alert('Error creating new player: ' + error),
    });
  }

  private afterSave(player: Player) {
    this.teamService.getTeamByName(this.player.team).subscribe({
      next: (team: Team) => {
        this.team = team;
        this.router.navigate(['/teams', this.team.id]); // Navigate after league is fetched
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
