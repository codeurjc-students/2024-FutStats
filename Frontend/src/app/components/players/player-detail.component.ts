import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { PlayersService } from '../../services/player.service';
import { LoginService } from 'src/app/services/login.service';

import { Player } from '../../models/player.model';
import { Team } from '../../models/team.model';
import { League } from 'src/app/models/league.model';
import { PlayerMatch } from 'src/app/models/player-match.model';

@Component({
  templateUrl: './player-detail.component.html'
})
export class PlayerDetailComponent implements OnInit {

  player: Player;
  errorMessage: string;
  team: Team;
  league: League;
  playerMatches: PlayerMatch[] = [];

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private service: PlayersService,
    public loginService: LoginService
  ) { }

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.params['id'];
    this.service.getPlayer(id).subscribe(
      (player: Player) => {
        this.player = player;

        this.service.getLeague(id).subscribe(
          (league: League) => {
            this.league =league;   
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
          (playerMatches: PlayerMatch[]=[]) => {
            this.playerMatches = playerMatches;
          },
          (error) => {
            this.errorMessage = 'Error fetching teams';
          }
        );
      },
      (error: any) => {
        this.errorMessage = 'Error fetching player details';
        console.error(error);
      }
    );
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
    this.router.navigate(['/players/edit', this.player.id]);
  }

  goBack(): void {
    this.router.navigate(['/teams', this.team.id]);
  }
}