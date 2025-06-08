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

  @ViewChild("file")
  file: any;

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
    this.playerImage();
  }

  loadLeagues() {
    this.leagueService.getLeagues().subscribe({
      next: (leagues) => (this.leagues = leagues),
      error: (error) => console.error('Error loading leagues:', error),
    });
  }

  onLeagueChange() {
    console.log('Liga seleccionada:', this.selectedLeagueId);
    if (this.selectedLeagueId) {
      this.leagueService.getTeamsByName(this.selectedLeagueId).subscribe({
        next: (teams) => {
          console.log('Equipos cargados:', teams);
          this.teams = teams;
        },
        error: (error) => console.error('Error al cargar equipos:', error),
      });
    } else {
      console.log('No se seleccionó ninguna liga, limpiando equipos');
      this.teams = [];
    }
  }

  save() {
    if (this.player.image && this.removeImage) {
      this.player.image = false;
    }
    
    if (this.selectedLeagueId) {
      this.player.league = this.selectedLeagueId;
    }

    if (this.newPlayer) {
      this.service.addPlayer(this.player).subscribe({
        next: (player: Player) => this.uploadImage(player),
        error: (error) => {
          console.error('Error creating player:', error);
          alert('Error creating new player: ' + error);
        }
      });
    } else {
      if (!this.player.id) {
        alert('Error: Player ID is missing');
        return;
      }
      
      this.service.updatePlayer(this.player).subscribe({
        next: (player: Player) => this.uploadImage(player),
        error: (error) => {
          console.error('Error updating player:', error);
          alert('Error updating player: ' + error);
        }
      });
    }
  }

  uploadImage(player: Player): void {
    if (this.file) {
      const image = this.file.nativeElement.files[0];
      if (image) {
        let formData = new FormData();
        formData.append("imageFile", image);
        this.service.addImage(player, formData).subscribe(
          _ => this.afterUploadImage(player),
          error => alert('Error uploading user image: ' + error)
        );
      } else if (this.removeImage) {
        this.service.deleteImage(player).subscribe(
          _ => this.afterUploadImage(player),
          error => alert('Error deleting user image: ' + error)
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
