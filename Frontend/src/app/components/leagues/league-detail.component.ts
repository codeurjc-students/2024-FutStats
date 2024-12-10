import { Team } from 'src/app/models/team.model';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LeaguesService } from '../../services/league.service';
import { League } from '../../models/league.model';
import { LoginService } from 'src/app/services/login.service';
import { Match } from 'src/app/models/match.model';
import { Player } from 'src/app/models/player.model';

@Component({
  templateUrl: './league-detail.component.html'
})
export class LeagueDetailComponent implements OnInit {

  league: League;
  teams: Team[] = [];  // To store the players
  matches: Match[] = []; // To store the matches
  players: Player[] = []; // To store the players
  errorMessage: string;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private service: LeaguesService,
    public loginService: LoginService
  ) { }

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.params['id'];
    this.service.getLeagueById(id).subscribe(
      (league: League) => {
        this.league = league;
      
        // Once we have the league we get the teams
        this.service.getTeams(id).subscribe(
          (teams: Team[]) => {
            this.teams = teams;   
          },
          (error) => {
            this.errorMessage = 'Error fetching teams';
          }
        );

        // Once we have the league we get the matches
        this.service.getMatches(id).subscribe(
          (matches: Match[]) => {
            this.matches = matches; 
          },
          (error) => {
            this.errorMessage = 'Error fetching matches';
          }
        );
      },
      (error: any) => {
        this.errorMessage = 'Error fetching league details';
      }
    );
  }
  
  removeLeague() {
    const okResponse = window.confirm('Quieres borrar esta liga?');
    if (okResponse) {
        this.service.deleteLeague(this.league).subscribe(
            _ => this.router.navigate(['/leagues']),
            error => console.error(error)
        );
    }
  }

  editLeague() {
    this.router.navigate(['/leagues/edit', this.league.id]);
  }

  createTeam() {
    this.router.navigate(['/teams/new']);
  }

  createMatch() {
    this.router.navigate(['/matches/new']);
  }

  gotoLeagues(): void {
    this.router.navigate(['/leagues']);
  }

  gotoMatches(): void {
    this.router.navigate(['/matches']);
  }

  gotoTeams(): void {
    this.router.navigate(['/teams']);
  }
}