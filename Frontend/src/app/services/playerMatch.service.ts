import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Player } from '../models/player.model';
import { PlayerMatch } from '../models/player-match.model';

const BASE_URL = '/api/v1/playerMatches/';

@Injectable({ providedIn: 'root' })
export class PlayerMatchesService {

	constructor(private httpClient: HttpClient) { }

	getPlayerMatch(id: number): Observable<PlayerMatch> {
		return this.httpClient.get<PlayerMatch>(BASE_URL + id).pipe(
			catchError(error => this.handleError(error))
		);
	}

	getMatch(id: number): Observable<PlayerMatch> {
		return this.httpClient.get<PlayerMatch>(BASE_URL + id + '/match').pipe(
			catchError(error => this.handleError(error))
		);
	}

	getPlayer(id: number): Observable<Player> {
		return this.httpClient.get<Player>(BASE_URL + id + '/player').pipe(
			catchError(error => this.handleError(error))
		);
	}

	getGoalsPerMatch(playerId: number): Observable<any> {
		return this.httpClient.get<any>(BASE_URL + `/goals/${playerId}`).pipe(
			catchError(error => this.handleError(error))
		);
	}

	deletePlayerMatch(playerMatch: PlayerMatch) {
		return this.httpClient.delete<PlayerMatch>(BASE_URL + playerMatch.id).pipe(
			catchError(error => this.handleError(error))
		);
	}

	updatePlayerMatch(id: number | undefined, playerMatch: PlayerMatch): Observable<PlayerMatch> {
		return this.httpClient.put<PlayerMatch>(BASE_URL + playerMatch.id, playerMatch).pipe(
			catchError(error => this.handleError(error))
		);
	}

	private handleError(error: any) {
		console.log("ERROR:");
		console.error(error);
		return throwError("Server error (" + error.status + "): " + error.text())
	}
}