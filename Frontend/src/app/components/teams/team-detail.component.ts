import { UsersService } from 'src/app/services/user.service';
import { League } from './../../models/league.model';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { TeamsService } from '../../services/team.service';
import { Team } from '../../models/team.model';
import { LoginService } from 'src/app/services/login.service';
import { TeamMatchesService } from 'src/app/services/teamMatch.service';
import { Player } from 'src/app/models/player.model';
import { User } from 'src/app/models/user.model';
import { Chart } from 'chart.js/auto';

@Component({
  templateUrl: './team-detail.component.html',
  standalone: false
})
export class TeamDetailComponent implements OnInit {

  user: User;
  team: Team;
  league: League;
  players: Player[] = [];
  errorMessage: string;
  public playerPage!: number;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private service: TeamsService,
    private userService: UsersService,
    public loginService: LoginService,
    private teamMatchService: TeamMatchesService
  ) { }

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.params['id'];
    this.cargarDatos(id);
    this.service.getTeam(id).subscribe(
      (team: Team) => {
        this.team = team;

        // Once we have the league we get the teams
        this.service.getPlayersByTeam(id).subscribe(
          (players: Player[]) => {
            this.players = players;
            console.log(players);
          },
          (error) => {
            this.errorMessage = 'Error fetching teams';
          }
        );

        this.service.getLeagueByTeam(id).subscribe(
          (league: League) => {
            this.league = league;
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
        );
      },
      (error: any) => {
        this.errorMessage = 'Error fetching team details';
        console.error(error);
      }
    );
  }

  cargarDatos(teamId: number) {
    this.teamMatchService.getPointsPerMatch(teamId).subscribe((data) => {
      const matchNames = data.map((d: any) => d.matchName);
      const points = data.map((d: any) => d.points);

      this.crearGrafica(matchNames, points);
    });
  }

  crearGrafica(matchNames: string[], points: number[]) {
    new Chart('pointsChart', {
      type: 'line',
      data: {
        labels: matchNames,
        datasets: [
          {
            label: 'Puntos por Partido',
            data: points,
            fill: false,
            borderColor: 'rgba(75, 192, 192, 1)',
            tension: 0.2, // Suaviza las líneas
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

  teamImage() {
    return this.team.image ? this.service.getImage(this.team.id) : 'assets/no_image.jpg';
  }

  createPlayer(): void {
    this.router.navigate(['/players/new']);
  }

  removeTeam() {
    const okResponse = window.confirm('Quieres borrar este equipo?');
    if (okResponse) {
      this.service.deleteTeam(this.team).subscribe(
        _ => this.router.navigate(['/leagues']),
        error => console.error(error)
      );
    }
  }

  editTeam() {
    this.router.navigate(['/teams/edit', this.team.id]);
  }

  addTeam() {
    const okResponse = window.confirm('Quieres añadir este equipo?');
    if (okResponse) {
      this.userService.addTeam(this.user, this.team).subscribe(
        _ => this.router.navigate(['/users', this.user.id]),
        error => console.error(error)
      );
    }
  }

  goBack(): void {
    this.router.navigate(['leagues', this.league.id]);
  }

}