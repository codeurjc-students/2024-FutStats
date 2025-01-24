import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { NgxPaginationModule } from 'ngx-pagination';

import { MatchesService } from '../../services/match.service';

import { Match } from '../../models/match.model';
import { Team } from '../../models/team.model';
import { League } from 'src/app/models/league.model';
import { PlayerMatch } from 'src/app/models/player-match.model';
import { LoginService } from 'src/app/services/login.service';
import { TeamsService } from 'src/app/services/team.service';

@Component({
    templateUrl: './match-detail.component.html',
    standalone: false
})
export class MatchDetailComponent implements OnInit {

  match: Match;
  errorMessage: string;
  team1: Team;
  team2: Team;
  league: League;
  playerMatches: PlayerMatch[] =  [];
  public playerMatchPage!: number;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private service: MatchesService,
    public loginService: LoginService,
    public teamService: TeamsService
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
            this.team1 = team;   
          },
          (error) => {
            this.errorMessage = 'Error fetching teams';
          }
        );

        this.service.getTeam2(id).subscribe(
          (team: Team) => {
            this.team2 = team;   
          },
          (error) => {
            this.errorMessage = 'Error fetching teams';
          }
        );

        this.service.getPlayerMatches(id).subscribe(
          (playerMatches: PlayerMatch[]=[]) => {
            this.playerMatches = playerMatches;   
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

  removeMatch() {
    const okResponse = window.confirm('Quieres borrar este partido?');
    if (okResponse) {
        this.service.deleteMatch(this.match).subscribe(
            _ => this.router.navigate(['/leagues']),
            error => console.error(error)
        );
    }
  }
  
  createPlayerMatch() {
    this.router.navigate(['/playerMatch/new']);
  }

  team1Image(){
    return this.team1.image? this.teamService.getImage(this.team1.id) : 'assets/no_image.jpg';
  }

  team2Image(){
    return this.team2.image? this.teamService.getImage(this.team2.id) : 'assets/no_image.jpg';
  }

  goBack(): void {
    this.router.navigate(['/leagues', this.league.id]);
  }

  editMatch(){
    this.router.navigate(['/matches/edit', this.match.id]);
  }
}