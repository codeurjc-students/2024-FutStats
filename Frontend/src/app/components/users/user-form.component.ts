import { User } from 'src/app/models/user.model';
import { UsersService } from 'src/app/services/user.service';
import { Component, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Team } from 'src/app/models/team.model';

@Component({
    templateUrl: './user-form.component.html',
    styleUrls: ['./user-form.component.css'],
    standalone: false
})
export class UserFormComponent {
    newUser: boolean;
    user: User;
    removeImage: boolean;

    @ViewChild('uploadImage', { static: false })
    fileInput: any;

    constructor(
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private service: UsersService,
    ) {
        const id = activatedRoute.snapshot.params['id'];
        if (id) {
            service.getUser(id).subscribe(
                user => this.user = user,
                error => console.error(error)
            );
            this.newUser = false;
        } else {
            this.user = {
                name: '',
                password: '',
                email: '',
                roles: [],
                image: false

            };
            this.newUser = true;
        }
    }

    ngOnInit(): void {

    }

    cancel() {
        window.history.back();
    }

    save() {
        if (this.newUser) {
          if (this.removeImage) {
            this.user.image = false;
          }
          this.service.addUser(this.user).subscribe(
            (user: User) => this.uploadImage(user),
            error => alert('Error creating new user: ' + error)
          );
        } else {
          if (this.removeImage) {
            this.user.image = false;
          }
          this.service.updateUser(this.user).subscribe(
            (user: User,) => this.uploadImage(user),
            error => alert('Error creating new user: ' + error)
          );
        }
      }
    
      uploadImage(user: User): void {
        if (this.fileInput) {
          const image = this.fileInput.nativeElement.files[0];
          if (image) {
            let formData = new FormData();
            formData.append("imageFile", image);
            this.service.addImage(user, formData).subscribe(
              _ => this.afterUploadImage(user),
              error => alert('Error uploading user image: ' + error)
            );
          } else if (this.removeImage) {
            this.service.deleteImage(user).subscribe(
              _ => this.afterUploadImage(user),
                error => alert('Error deleting user image: ' + error)
            );
          }
        }
        this.afterUploadImage(user);
      }
    
      onFileSelected(event: any): void {
        const fileInput = event.target.files[0];
        if (fileInput) {
          console.log('Archivo seleccionado:', fileInput.name);
        }
      }
    
      private afterUploadImage(user: User) {
        this.router.navigate(['/leagues']);
      }
      

    userImage() {
        return this.user.image ? "api/v1/users/" + this.user.id + "/image" : 'assets/no_image.jpg';
    }

    
}
