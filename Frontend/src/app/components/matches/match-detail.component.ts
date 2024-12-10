import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { MatchesService } from '../../services/match.service';

import { Match } from '../../models/match.model';
import { Team } from '../../models/team.model';
import { League } from 'src/app/models/league.model';
import { PlayerMatch } from 'src/app/models/player-match.model';
import { LoginService } from 'src/app/services/login.service';

@Component({
  templateUrl: './match-detail.component.html'
})
export class MatchDetailComponent implements OnInit {

  match: Match;
  errorMessage: string;
  team1: string;
  team2: string;
  league: League;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private service: MatchesService,
    public loginService: LoginService,
  ) { }

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.params['id'];
    this.service.getMatch(id).subscribe(
      (match: Match) => {
        this.match = match;

        this.service.getLeague(id).subscribe(
          (league: League) => {
            this.league =league;   
          },
          (error) => {
            this.errorMessage = 'Error fetching teams';
          }
        );

        this.service.getTeam1(id).subscribe(
          (team: Team) => {
            this.team1 = team.name;   
          },
          (error) => {
            this.errorMessage = 'Error fetching teams';
          }
        );

        this.service.getTeam2(id).subscribe(
          (team: Team) => {
            this.team2 = team.name;   
          },
          (error) => {
            this.errorMessage = 'Error fetching teams';
          }
        );
      },
      (error: any) => {
        this.errorMessage = 'Error fetching match details';
        console.error(error);
      }
    );
  }

  goBack(): void {
    this.router.navigate(['/leagues', this.league.id]);
  }
}