import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PlayerMatchesService } from 'src/app/services/playerMatch.service';
import { Team } from 'src/app/models/team.model';
import { Match } from 'src/app/models/match.model';
import { Player } from 'src/app/models/player.model';
import { PlayerMatch } from 'src/app/models/player-match.model';
import { MatchesService } from 'src/app/services/match.service';
import { PlayersService } from 'src/app/services/player.service';

@Component({
  templateUrl: './playerMatch-form.component.html',
})
export class PlayerMatchFormComponent implements OnInit {
  playerMatch: PlayerMatch;
  players: Player[] = [];
  matches: Match[] = [];
  match: Match;
  selectedPlayerId: string; // ID del jugador seleccionado
  selectedMatchId: number; // ID del partido seleccionado

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private matchService: MatchesService,
    private playerService: PlayersService
  ) {
    this.playerMatch = {
      id: 0,
      name: '',
      player: '',
      match: 0,
      matchName: '',
    };
  }

  ngOnInit(): void {
    // Cargar lista de partidos
    this.matchService.getMatches().subscribe({
      next: (matches: Match[]) => {
        this.matches = matches;
      },
      error: (error) => console.error('Error fetching matches:', error),
    });

    // Cargar lista de jugadores
    this.playerService.getPlayers().subscribe({
      next: (players: Player[]) => {
        this.players = players;
      },
      error: (error) => console.error('Error fetching players:', error),
    });
  }

  save() {
    this.matchService.getMatch(this.selectedMatchId).subscribe(
      (match: Match) => {
        console.log(match);
        this.match = match;
        console.log(this.match);
        this.playerMatch.matchName = this.match.name;
        console.log(this.playerMatch.matchName);
      });

    // Asignar los IDs seleccionados al objeto playerMatch
    this.playerMatch.player = this.selectedPlayerId;
    this.playerMatch.match = this.selectedMatchId;
    this.playerMatch.name = this.selectedPlayerId;

    // Llamada al servicio para guardar la relación
    this.matchService.addPlayerMatch(this.playerMatch.match, this.playerMatch).subscribe({
      next: (playerMatch: PlayerMatch) => this.afterSave(playerMatch),
      error: (error) => alert('Error creating new player match: ' + error),
    });
  }

  private afterSave(playerMatch: PlayerMatch) {
    this.router.navigate(['/matches', this.playerMatch.match]); // Navegar al partido después de guardar
  }

  cancel() {
    window.history.back(); // Volver atrás sin guardar
  }
}