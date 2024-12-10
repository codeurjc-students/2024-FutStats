import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { League } from '../models/league.model';
import { Team } from '../models/team.model';
import { Match } from '../models/match.model';

const BASE_URL = '/api/v1/leagues/';

@Injectable({ providedIn: 'root' })
export class LeaguesService {

  constructor(private httpClient: HttpClient) { }

  getLeagues(): Observable<League[]> {
    return this.httpClient.get<League[]>(BASE_URL).pipe(
      catchError(error => this.handleError(error))
    );
  }

  getLeagueById(id: number): Observable<League> {
    return this.httpClient.get<League>(BASE_URL + id).pipe(
      catchError(error => this.handleError(error))
    );
  }

  getLeagueByName(name:string): Observable<League>{
    return this.httpClient.get<League>(BASE_URL+ 'name/' + name).pipe(
      catchError(error => this.handleError(error))
    );
  }

  getTeams(id: number): Observable<Team[]>{
    return this.httpClient.get<Team[]>(BASE_URL + id + '/teams').pipe(
      catchError(error => this.handleError(error))
    );
  }

  getTeamsByName(name: string): Observable<Team[]>{
    return this.httpClient.get<Team[]>(BASE_URL + 'name/'+ name + '/teams').pipe(
      catchError(error => this.handleError(error))
    );
  }

  getMatches(id: number): Observable<Match[]>{
    return this.httpClient.get<Match[]>(BASE_URL + id + '/matches').pipe(
      catchError(error => this.handleError(error))
    );
  }

  addLeague(league: League): Observable<League> {
    if (!league.id) {
      return this.httpClient.post<League>(BASE_URL, league).pipe(
        catchError(error => this.handleError(error))
      );
    } else {
      return this.httpClient.put<League>(BASE_URL + league.id, league).pipe(
        catchError(error => this.handleError(error))
      );
    }
  }

  deleteLeague(league: League) {
		return this.httpClient.delete<League>(BASE_URL + league.id).pipe(
			catchError(error => this.handleError(error))
		);
	}

	updateLeague(league: League): Observable<League> {
		return this.httpClient.put<League>(BASE_URL + league.id, league).pipe(
        catchError(error => this.handleError(error))
		);
	}

  private handleError(error: any) {
    console.log("ERROR:");
    console.error(error);
    return throwError("Server error (" + error.status + "): " + error.text());
  }
}
