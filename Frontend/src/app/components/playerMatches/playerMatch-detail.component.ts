import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { PlayerMatchesService } from '../../services/playerMatch.service';

import { Match } from '../../models/match.model';
import {Player } from '../../models/player.model';
import { PlayerMatch } from 'src/app/models/player-match.model';
import { MatchesService } from 'src/app/services/match.service';
import { LoginService } from 'src/app/services/login.service';

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
        private matchService: MatchesService,
        public loginService: LoginService
    ) { }

    ngOnInit(): void {
        const id = this.activatedRoute.snapshot.params['id'];
        this.service.getPlayerMatch(id).subscribe(
            (playerMatch: PlayerMatch) => {
                this.playerMatch = playerMatch;
                console.log(playerMatch);
            },
            (error: any) => {
                this.errorMessage = 'Error fetching match details';
                console.error(error);
            }
        );
    }

    removePlayerMatch() {
        const okResponse = window.confirm('Quieres borrar este jugador?');
        if (okResponse) {
            this.matchService.deletePlayerMatch(this.playerMatch.id,this.playerMatch).subscribe(
                _ => this.router.navigate(['/leagues']),
                error => console.error(error)
            );
        }
      }

    goBack(): void {
        window.history.back();
    }
}