import { User } from 'src/app/models/user.model';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UsersService } from '../../services/user.service';
import { League } from '../../models/league.model';
import { LoginService } from 'src/app/services/login.service';
import { Team } from 'src/app/models/team.model';
import { Player } from 'src/app/models/player.model';


@Component({
    selector: 'user-detail',
    templateUrl: './user-detail.component.html',
    styleUrls: ['./user-detail.component.css'],
    standalone: false
})
export class UserDetailComponent implements OnInit {

    user: User;
    leagues: League[];
    teams: Team[] = [];
    players: Player[] = [];
    errorMessage: string;

    constructor(
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private service: UsersService,
        public loginService: LoginService
    ) { }

    ngOnInit(): void {
        const id = this.activatedRoute.snapshot.params['id'];
        this.service.getUser(id).subscribe(
            (user: User) => {
                this.user = user;

                this.service.getLeagues(id).subscribe(
                    (leagues: League[]) => {
                        this.leagues = leagues;
                    },
                    (error) => {
                        this.errorMessage = 'Error fetching matches';
                    }
                );

                this.service.getTeams(id).subscribe(
                    (teams: Team[]) => {
                        this.teams = teams;
                    },
                    (error) => {
                        this.errorMessage = 'Error fetching teams';
                    }
                );

                this.service.getPlayers(id).subscribe(
                    (players: Player[]) => {
                        this.players = players;
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

    userImage(){
        return this.user.image?  "api/v1/users/" + this.user.id + "/image" : 'assets/no_image.jpg';
    }

    removeUser() {
        const okResponse = window.confirm('Quieres borrar este usuario');
        if (okResponse) {
            this.service.deleteUser(this.user).subscribe(
                _ => this.router.navigate(['/users']),
                error => console.error(error)
            );
        }
    }

    removeLeague(league: League){
        const id = this.activatedRoute.snapshot.params['id'];
        const okResponse = window.confirm('Quieres borrar esta Liga');
        if (okResponse) {
            this.service.deleteLeague(this.user, league).subscribe(
                _ => this.router.navigate(['/users/' + id]),
                error => console.error(error)
            );
        }
    }

    removeTeam(team: Team){
        const id = this.activatedRoute.snapshot.params['id'];
        const okResponse = window.confirm('Quieres borrar este Equipo');
        if (okResponse) {
            this.service.deleteTeam(this.user, team).subscribe(
                _ => this.router.navigate(['/users/' + id]),
                error => console.error(error)
            );
        }
    }

    removePlayer(player: Player){
        const id = this.activatedRoute.snapshot.params['id'];
        const okResponse = window.confirm('Quieres borrar este Jugador');
        if (okResponse) {
            this.service.deletePlayer(this.user, player).subscribe(
                _ => this.router.navigate(['/users/' + id]),
                error => console.error(error)
            );
        }
    }

    editUser() {
        this.router.navigate(['/users/edit', this.user.id]);
    }

    goBack() {
        this.router.navigate(['/users']);
    }
}