import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { PlayerMatchesService } from '../../services/playerMatch.service';

import { Match } from '../../models/match.model';
import {Player } from '../../models/player.model';
import { PlayerMatch } from 'src/app/models/player-match.model';
import { LoginService } from 'src/app/services/login.service';
import { TeamsService } from 'src/app/services/team.service';
import { PlayersService } from 'src/app/services/player.service';

@Component({
    templateUrl: './playerMatch-detail.component.html'
})
export class PlayerMatchDetailComponent implements OnInit {

    playerMatch: PlayerMatch;
    player: Player;
    match: Match;
    errorMessage: string;

    constructor(
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private service: PlayerMatchesService,
        private teamService: TeamsService,
        private playerService: PlayersService,
        public loginService: LoginService
    ) { }

    ngOnInit(): void {
        const id = this.activatedRoute.snapshot.params['id'];
        this.service.getPlayerMatch(id).subscribe(
            (playerMatch: PlayerMatch) => {
                this.playerMatch = playerMatch;
                console.log(this.playerMatch);
            },
            (error: any) => {
                this.errorMessage = 'Error finding playerMatch';
                console.error(error);
            }
        );
        
        this.service.getPlayer(id).subscribe(
            (player: Player) => {
                this.player = player;
            },
            (error: any) => {
                this.errorMessage = 'Error finding player';
                console.error(error);
            }
        );
    }

    removePlayerMatch() {
        const okResponse = window.confirm('Quieres borrar este jugador?');
        if (okResponse) {
            this.service.deletePlayerMatch(this.playerMatch).subscribe(
                _ => this.router.navigate(['/leagues']),
                error => console.error(error)
            );
        }
    }

    playerImage() {
        return this.player.image ? this.playerService.getImage(this.player.id) : 'assets/no_image.jpg';
      }

    editPlayerMatch(){
        this.router.navigate(['/playerMatch/edit', this.playerMatch.id]);
    }

    goBack(): void {
        this.router.navigate(['/players', this.player.id]);
    }
}