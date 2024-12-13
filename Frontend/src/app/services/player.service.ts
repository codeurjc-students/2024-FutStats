import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Player } from '../models/player.model';
import { League } from '../models/league.model';
import { Team } from '../models/team.model';
import { PlayerMatch } from '../models/player-match.model';

const BASE_URL = '/api/v1/players/';

@Injectable({ providedIn: 'root' })
export class PlayersService {

	constructor(private httpClient: HttpClient) { }

	getPlayers(): Observable<Player[]> {
		return this.httpClient.get<Player[]>(BASE_URL).pipe(
			catchError(error => this.handleError(error))
		);
	}

	getPlayerByName(name:string): Observable<Player>{
		return this.httpClient.get<Player>(BASE_URL + 'name/' + name).pipe(
			catchError(error => this.handleError(error))
		);
	}

	getPlayer(id: number ): Observable<Player> {
		return this.httpClient.get<Player>(BASE_URL+id).pipe(
			catchError(error => this.handleError(error))
		);
	}

	getLeague(id: number): Observable<League>{
		return this.httpClient.get<League>(BASE_URL+id+'/league').pipe(
			catchError(error => this.handleError(error))
		);
	}

	getTeam(id: number): Observable<Team>{
		return this.httpClient.get<Team>(BASE_URL+id+'/team').pipe(
			catchError(error => this.handleError(error))
		);
	}

	getPlayerMatches(playerId: number): Observable<PlayerMatch[]>{
		return this.httpClient.get<PlayerMatch[]>(BASE_URL+ playerId +'/playerMatches').pipe(
			catchError(error => this.handleError(error))
		);
	}

	addPlayer(player: Player): Observable<Player> {
		if (!player.id) {
		  return this.httpClient.post<Player>(BASE_URL, player).pipe(
			catchError(error => this.handleError(error))
		  );
		} else {
		  return this.httpClient.put<Player>(BASE_URL + player.id, player).pipe(
			catchError(error => this.handleError(error))
		  );
		}
	  }
	
	deletePlayer(player: Player) {
		return this.httpClient.delete<Player>(BASE_URL + player.id).pipe(
			catchError(error => this.handleError(error))
		);
	}
	
	updatePlayer(player: Player): Observable<Player> {
		return this.httpClient.put<Player>(BASE_URL + player.id, player).pipe(
		catchError(error => this.handleError(error))
		);
	}

	private handleError(error: any) {
		console.log("ERROR:");
		console.error(error);
		return throwError("Server error (" + error.status + "): " + error.text())
	}
}