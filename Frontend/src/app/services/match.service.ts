import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Match } from '../models/match.model';
import { Team } from '../models/team.model';
import { League } from '../models/league.model';
import { PlayerMatch } from '../models/player-match.model';

const BASE_URL = '/api/v1/matches/';

@Injectable({ providedIn: 'root' })
export class MatchesService {

	constructor(private httpClient: HttpClient) { }

	getMatches(): Observable<Match[]> {
		return this.httpClient.get<Match[]>(BASE_URL).pipe(
			catchError(error => this.handleError(error))
		);
	}

	getMatch(id: number): Observable<Match> {
		return this.httpClient.get<Match>(BASE_URL + id).pipe(
			catchError(error => this.handleError(error))
		);
	}

	getLeague(id: number): Observable<League>{
		return this.httpClient.get<League>(BASE_URL+id+'/league').pipe(
			catchError(error => this.handleError(error))
		);
	}

	getTeam1(id: number): Observable<Team>{
		return this.httpClient.get<Team>(BASE_URL+id+'/team1').pipe(
			catchError(error => this.handleError(error))
		);
	}

	getTeam2(id: number): Observable<Team>{
		return this.httpClient.get<Team>(BASE_URL+id+'/team2').pipe(
			catchError(error => this.handleError(error))
		);
	}

	getPlayerMatches(matchId: number): Observable<PlayerMatch[]>{
		return this.httpClient.get<PlayerMatch[]>(BASE_URL+matchId+'/playersMatches').pipe(
			catchError(error => this.handleError(error))
		);
	}

	addMatch(match: Match): Observable<Match> {
		if (!match.id) {
		  return this.httpClient.post<Match>(BASE_URL, match).pipe(
			catchError(error => this.handleError(error))
		  );
		} else {
		  return this.httpClient.put<Match>(BASE_URL + match.id, match).pipe(
			catchError(error => this.handleError(error))
		  );
		}
	}
	
	deleteMatch(match: Match) {
		return this.httpClient.delete<Match>(BASE_URL + match.id).pipe(
			catchError(error => this.handleError(error))
		);
	}
	
	updateMatch(match: Match): Observable<Match> {
		return this.httpClient.put<Match>(BASE_URL + match.id, match).pipe(
		catchError(error => this.handleError(error))
		);
	}

	addPlayerMatch(id: number, playerMatch: PlayerMatch): Observable<PlayerMatch> {
		if (!playerMatch.id) {
		  return this.httpClient.post<PlayerMatch>(BASE_URL + id + '/playersMatch', playerMatch).pipe(
			catchError(error => this.handleError(error))
		  );
		} else {
		  return this.httpClient.put<PlayerMatch>(BASE_URL + playerMatch.id, playerMatch).pipe(
			catchError(error => this.handleError(error))
		  );
		}
	}
	
	deletePlayerMatch(id: number|undefined, playerMatch: PlayerMatch) {
		return this.httpClient.delete<PlayerMatch>(BASE_URL + id + '/playersMatch/' + playerMatch.id).pipe(
			catchError(error => this.handleError(error))
		);
	}
	
	updatePlayerMatch(id: number, playerMatch: PlayerMatch): Observable<PlayerMatch> {
		return this.httpClient.put<PlayerMatch>(BASE_URL + id + '/playersMatch' + playerMatch.id, playerMatch).pipe(
		catchError(error => this.handleError(error))
		);
	}

	private handleError(error: any) {
		console.log("ERROR:");
		console.error(error);
		return throwError("Server error (" + error.status + "): " + error.text())
	}
}