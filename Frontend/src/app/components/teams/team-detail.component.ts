import { League } from './../../models/league.model';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { TeamsService } from '../../services/team.service';
import { Team } from '../../models/team.model';
import { LoginService } from 'src/app/services/login.service';
import { LeaguesService } from 'src/app/services/league.service';
import { Player } from 'src/app/models/player.model';

@Component({
  templateUrl: './team-detail.component.html'
})
export class TeamDetailComponent implements OnInit {

  team: Team;
  league: League;
  errorMessage: string;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private service: TeamsService,
    public loginService: LoginService,
  ) { }

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.params['id'];
    this.service.getTeam(id).subscribe(
      (team: Team) => {
        this.team = team;

        this.service.getLeagueByTeam(id).subscribe(
          (league: League) => {
            this.league = league;   
          },
          (error) => {
            this.errorMessage = 'Error fetching teams';
          }
        );
      },
      (error: any) => {
        this.errorMessage = 'Error fetching team details';
        console.error(error);
      }
    );
  }
  
  editTeam() {
    this.router.navigate(['/teams/edit', this.team.id]);
  }

  goBack(): void {
    this.router.navigate(['leagues', this.league.id]);
  }

}