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

@Component({
  templateUrl: './player-detail.component.html'
})
export class PlayerDetailComponent implements OnInit {

  user: User;
  player: Player;
  errorMessage: string;
  team: Team;
  league: League;
  playerMatches: PlayerMatch[] = [];

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private service: PlayersService,
    private userService: UsersService,
    public loginService: LoginService
  ) { }

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.params['id'];
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

  playerImage() {
    return this.player.image ? this.service.getImage(this.player.id) : 'assets/no_image.jpg';
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

  addPlayer() {
    const okResponse = window.confirm('Quieres añadir esta jugador?');
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