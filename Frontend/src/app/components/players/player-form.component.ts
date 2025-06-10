import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PlayersService } from 'src/app/services/player.service';
import { LeaguesService } from 'src/app/services/league.service';
import { Team } from 'src/app/models/team.model';
import { League } from 'src/app/models/league.model';
import { Player } from 'src/app/models/player.model';
import { TeamsService } from 'src/app/services/team.service';

@Component({
    templateUrl: './player-form.component.html',
    styleUrls: ['./player-form.component.css'],
    standalone: false
})
export class PlayerFormComponent implements OnInit {
  newPlayer: boolean;
  player: Player;
  leagues: League[] = [];
  teams: Team[] = [];
  selectedLeagueId: string;
  team: Team;
  removeImage: boolean;

  @ViewChild('uploadImage', { static: false })
  fileInput: any;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private service: PlayersService,
    private leagueService: LeaguesService,
    private teamService: TeamsService
  ) {
    const id = activatedRoute.snapshot.params['id'];
    if (id) {
      service.getPlayer(id).subscribe(
        player => this.player = player,
        error => console.error(error)
      );
      this.newPlayer = false;
    } else {
      this.player = {
        name: '',
        age: 0,
        position: '',
        nationality: '',
        team: '',
        league: '',
        image: false
      };
      this.newPlayer = true;
    }
  }

  ngOnInit(): void {
    this.loadLeagues();
    this.loadTeams();
    this.playerImage();
  }

  loadLeagues() {
    this.leagueService.getLeagues().subscribe({
      next: (leagues) => (this.leagues = leagues),
      error: (error) => console.error('Error loading leagues:', error),
    });
  }

  loadTeams() {
    this.teamService.getTeams().subscribe({
      next: (teams) => (this.teams = teams),
      error: (error) => console.error('Error loading teams:', error),
    });
  }

  save() {
    if (this.newPlayer) {
      if (this.removeImage) {
        this.player.image = false;
      }
      this.service.addPlayer(this.player).subscribe(
        (player: Player) => this.uploadImage(player),
        error => alert('Error creating new player: ' + error)
      );
    } else {
      if (this.removeImage) {
        this.player.image = false;
      }
      this.service.updatePlayer(this.player).subscribe(
        (player: Player,) => this.uploadImage(player),
        error => alert('Error creating new player: ' + error)
      );
    }
  }

  uploadImage(player: Player): void {
    if (this.fileInput) {
      const image = this.fileInput.nativeElement.files[0];
      if (image) {
        let formData = new FormData();
        formData.append("imageFile", image);
        this.service.addImage(player, formData).subscribe(
          _ => this.afterUploadImage(player),
          error => alert('Error uploading player image: ' + error)
        );
      } else if (this.removeImage) {
        this.service.deleteImage(player).subscribe(
          _ => this.afterUploadImage(player),
          error => alert('Error deleting player image: ' + error)
        );
      }
    }
    this.afterUploadImage(player);
  }

  onFileSelected(event: any): void {
    const fileInput = event.target.files[0];
    if (fileInput) {
      console.log('Archivo seleccionado:', fileInput.name);
    }
  }

  private afterUploadImage(player: Player) {
    this.router.navigate(['/players/', this.player.id]);
  }

  playerImage() {
    return this.player.image ? "api/v1/players/" + this.player.id + "/image" : 'assets/no_image.jpg';
  }

  cancel() {
    window.history.back(); 
  }
}
