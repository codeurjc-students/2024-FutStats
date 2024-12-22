import { PlayersService } from './../../services/player.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PlayerMatchesService } from 'src/app/services/playerMatch.service';
import { Team } from 'src/app/models/team.model';
import { Match } from 'src/app/models/match.model';
import { Player } from 'src/app/models/player.model';
import { PlayerMatch } from 'src/app/models/player-match.model';
import { MatchesService } from 'src/app/services/match.service';

@Component({
  templateUrl: './playerMatch-form.component.html',
})
export class PlayerMatchFormComponent implements OnInit {
  newPlayerMatch: boolean;
  playerMatch: PlayerMatch;
  players: Player[] = [];
  matches: Match[] = [];
  match: Match;
  selectedPlayerId: string;
  selectedMatchId: number;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private matchService: MatchesService,
    private playerService: PlayersService,
    private service: PlayerMatchesService
  ) {
    const id = activatedRoute.snapshot.params['id'];
    if (id) {
      service.getPlayerMatch(id).subscribe(
        playerMatch => this.playerMatch = playerMatch,
        error => console.error(error)
      );
      this.newPlayerMatch = false;
    } else {
      this.playerMatch = {
        name: '',
        match: 0,
        matchName: '',
        shoots: 0,
        goals: 0,
        penaltys: 0,
        faultsReceived: 0,
        offsides: 0,
        commitedFaults: 0,
        recovers: 0,
        duels: 0,
        wonDuels: 0,
        yellowCards: 0,
        redCards: 0,
        passes: 0,
        goodPasses: 0,
        shortPasses: 0,
        longPasses: 0,
        assists: 0,
        dribles: 0,
        centers: 0,
        ballLosses: 0,
        shootsReceived: 0,
        goalsConceded: 0,
        saves: 0,
        savesAvg: 0,
        outBoxSaves: 0,
        inBoxSaves: 0
      };
      this.newPlayerMatch = true;
    }
  }

  ngOnInit(): void {

    this.matchService.getMatches().subscribe({
      next: (matches: Match[]) => {
        this.matches = matches;
      },
      error: (error) => console.error('Error fetching matches:', error),
    });

    this.playerService.getPlayers().subscribe({
      next: (players: Player[]) => {
        this.players = players;
      },
      error: (error) => console.error('Error fetching players:', error),
    });
  }

  save() {
    if (this.newPlayerMatch) {
      this.matchService.getMatchByName(this.playerMatch.matchName).subscribe(
        (match: Match) => {
          this.match = match;

          this.playerMatch.match = this.match.id;
          console.log(this.match.id);
          console.log(this.playerMatch.match);
          console.log(this.playerMatch);
          this.matchService.addPlayerMatch(this.playerMatch).subscribe({
            next: (playerMatch: PlayerMatch) => this.afterSave(playerMatch),
            error: (error) => alert('Error creating new player match: ' + error),
          });
        });
    } else {
      this.service.updatePlayerMatch(this.playerMatch.match, this.playerMatch).subscribe(
        (playerMatch: PlayerMatch) => this.afterSave(playerMatch),
        error => alert('Error creating new league: ' + error)
      );
    }
  }

  private afterSave(playerMatch: PlayerMatch) {
    this.router.navigate(['/leagues']);
  }

  cancel() {
    window.history.back();
  }
}