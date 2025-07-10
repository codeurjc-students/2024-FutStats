import { Component, OnInit, OnDestroy } from '@angular/core';
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
import { Subscription } from 'rxjs';

@Component({
  templateUrl: './player-detail.component.html',
  styleUrls: ['./player-detail.component.css'],
  standalone: false
})
export class PlayerDetailComponent implements OnInit, OnDestroy {

  user: User;
  player: Player;
  errorMessage: string;
  team: Team;
  league: League;
  playerMatches: PlayerMatch[] = [];
  public playerMatchPage!: number;
  private chartInstance: Chart | null = null;
  private routeSubscription: Subscription;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private service: PlayersService,
    private userService: UsersService,
    public loginService: LoginService,
    private playerMatchService: PlayerMatchesService
  ) { }

  ngOnInit(): void {
    // Suscribirse a los cambios de parámetros de ruta
    this.routeSubscription = this.activatedRoute.params.subscribe(params => {
      const id = params['id'];
      if (id) {
        this.cargarDatos(id);
        this.cargarDatosJugador(id);
      }
    });
  }

  ngOnDestroy(): void {
    // Limpiar la suscripción al destruir el componente
    if (this.routeSubscription) {
      this.routeSubscription.unsubscribe();
    }
    // Destruir la gráfica si existe
    if (this.chartInstance) {
      this.chartInstance.destroy();
    }
  }

  cargarDatosJugador(playerId: number) {
    this.service.getPlayer(playerId).subscribe(
      (player: Player) => {
        this.player = player;

        this.service.getLeague(playerId).subscribe(
          (league: League) => {
            this.league = league;
          },
          (error) => {
            this.errorMessage = 'Error fetching teams';
          }
        );

        this.service.getTeam(playerId).subscribe(
          (team: Team) => {
            this.team = team;
          },
          (error) => {
            this.errorMessage = 'Error fetching teams';
          }
        );

        this.service.getPlayerMatches(playerId).subscribe(
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
      // Calcular el acumulado de goles
      const accumulatedGoals = goals.reduce((acc: number[], curr: number, idx: number) => {
        if (idx === 0) acc.push(curr);
        else acc.push(acc[idx - 1] + curr);
        return acc;
      }, []);
      this.crearGrafica(matchNames, accumulatedGoals);
    });
  }

  crearGrafica(matchNames: string[], goals: number[]) {
    setTimeout(() => {
      const chartElement = document.getElementById('golesChart') as HTMLCanvasElement;
      if (!chartElement) {
        console.error('Canvas element not found');
        return;
      }
      if (this.chartInstance) {
        this.chartInstance.destroy();
      }
      this.chartInstance = new Chart(chartElement, {
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
    }, 0);
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