import { Team } from 'src/app/models/team.model';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LeaguesService } from '../../services/league.service';
import { League } from '../../models/league.model';
import { LoginService } from 'src/app/services/login.service';
import { Match } from 'src/app/models/match.model';
import { Player } from 'src/app/models/player.model';
import { UsersService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user.model';
import { UserFormComponent } from '../users/user-form.component';
import { NgxPaginationModule } from 'ngx-pagination';

@Component({
    templateUrl: './league-detail.component.html',
    standalone: false
})
export class LeagueDetailComponent implements OnInit {

  user: User;
  league: League;
  teams: Team[] = [];  // To store the players
  matches: Match[] = []; // To store the matches
  players: Player[] = []; // To store the players
  errorMessage: string;
  teamPage: number = 1; 
  matchPage: number = 1; 

  onTeamPageChange(page: number) {
    this.teamPage = page;
  }

  onMatchPageChange(page: number) {
    this.matchPage = page; 
  }

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private service: LeaguesService,
    private userService: UsersService,
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

        this.userService.getMe().subscribe(
          (user: User) => {
            this.user = user;
          },
          (error) => {
            this.errorMessage = 'Error finding user';
          }
        )

      },
      (error: any) => {
        this.errorMessage = 'Error fetching league details';
      }
    );
  }

  leagueImage() {
    return this.league.image ? this.service.getImage(this.league.id) : 'assets/no_image.jpg';
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

  addLeague(){
    const okResponse = window.confirm('Quieres añadir esta liga?');
    if (okResponse) {
      this.userService.addLeague(this.user, this.league).subscribe(
        _ => this.router.navigate(['/users', this.user.id]),
        error => console.error(error)
      );
    }
  }

  goBack() {
    this.router.navigate(['/leagues']);
  }
}