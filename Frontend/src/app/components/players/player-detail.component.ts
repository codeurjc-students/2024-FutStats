import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { PlayersService } from '../../services/player.service';
import { LoginService } from 'src/app/services/login.service';

import { Player } from '../../models/player.model';
import { Team } from '../../models/team.model';
import { League } from 'src/app/models/league.model';
import { PlayerMatch } from 'src/app/models/player-match.model';
import { UsersService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user.model';
import { PlayerMatchesService } from 'src/app/services/playerMatch.service';
import { Chart } from 'chart.js/auto';

@Component({
  templateUrl: './player-detail.component.html',
  styleUrls: ['./player-detail.component.css'],
  standalone: false
})
export class PlayerDetailComponent implements OnInit {

  user: User;
  player: Player;
  errorMessage: string;
  team: Team;
  league: League;
  playerMatches: PlayerMatch[] = [];
  public playerMatchPage!: number;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private service: PlayersService,
    private userService: UsersService,
    public loginService: LoginService,
    private playerMatchService: PlayerMatchesService
  ) { }

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.params['id'];
    this.cargarDatos(id);
    this.service.getPlayer(id).subscribe(
      (player: Player) => {
        this.player = player;

        this.service.getLeague(id).subscribe(
          (league: League) => {
            this.league = league;
          },
          (error) => {
            this.errorMessage = 'Error fetching teams';
          }
        );

        this.service.getTeam(id).subscribe(
          (team: Team) => {
            this.team = team;
          },
          (error) => {
            this.errorMessage = 'Error fetching teams';
          }
        );

        this.service.getPlayerMatches(id).subscribe(
          (playerMatches: PlayerMatch[] = []) => {
            this.playerMatches = playerMatches;
          },
          (error) => {
            this.errorMessage = 'Error fetching teams';
          }
        );

        this.userService.getMe().subscribe(
          (user: User) => {
            this.user = user;
          },
          (error) => {
            this.errorMessage = 'Error finding user';
          }
        )
      },
      (error: any) => {
        this.errorMessage = 'Error fetching player details';
        console.error(error);
      }
    );
  }

  cargarDatos(playerId: number) {
    this.playerMatchService.getGoalsPerMatch(playerId).subscribe((data) => {
      const matchNames = data.map((d: any) => d.matchName);
      const goals = data.map((d: any) => d.goals);

      this.crearGrafica(matchNames, goals);
    });
  }

  crearGrafica(matchNames: string[], goals: number[]) {
    const chartElement = document.getElementById('golesChart') as HTMLCanvasElement;

    if (!chartElement) {
      return;
    }
    new Chart('golesChart', {
      type: 'line',
      data: {
        labels: matchNames,
        datasets: [
          {
            label: 'Goles por Partido',
            data: goals,
            fill: false,
            borderColor: 'rgba(75, 192, 192, 1)',
            tension: 0.2,
            pointBackgroundColor: 'rgba(75, 192, 192, 1)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgba(75, 192, 192, 1)',
          },
        ],
      },
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true,
          },
        },
        plugins: {
          legend: {
            display: true,
            position: 'top',
          },
        },
      },
    });
  }

  playerImage() {
    return this.player.image ? "api/v1/players/" + this.player.id + "/image" : 'assets/no_image.jpg';
  }

  removePlayer() {
    const okResponse = window.confirm('Quieres borrar este jugador?');
    if (okResponse) {
      this.service.deletePlayer(this.player).subscribe(
        _ => window.history.back(),
        error => console.error(error)
      );
    }
  }

  editPlayer(): void {
    this.router.navigate(['/players/edit/', this.player.id]);
  }

  addPlayer() {
    const okResponse = window.confirm('Quieres añadir este jugador?');
    if (okResponse) {
      this.userService.addPlayer(this.user, this.player).subscribe(
        _ => this.router.navigate(['/users', this.user.id]),
        error => console.error(error)
      );
    }
  }

  goBack(): void {
    this.router.navigate(['/teams', this.team.id]);
  }
}