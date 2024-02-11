import { CommonModule } from '@angular/common';
import { Component, Input, OnInit, Output } from '@angular/core';
import { FileUploadService } from '../../services/file-upload.service';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-btn-select',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './btn-select.component.html',
})
export class BtnSelectComponent implements OnInit {
  // armazena o arquivo atualmente selecionado pra upload
  currentFile?: File;

  // mensagem a ser exibida apos o upload
  message = '';

  // observable que armazena as informações dos arquivos disponiveis no servidor
  fileInfos?: Observable<any>;

  constructor(private uploadService: FileUploadService) {}

  ngOnInit(): void {
    this.fileInfos = this.uploadService.getFiles();
  }

  // chamado quando um arquivo eh selecionado pelo usuario
  selectFile(event: any): void {
    this.currentFile = event.target.files[0];
  }



  // chamado quando o usuario clica no botao de upload
  // Usamos currentFile para acessar o arquivo atual como o primeiro item. Em seguida, chamamos o método uploadService.upload() no currentFile
  // Se a transmissão for concluida, o evento sera um objeto HttpResponse. Neste momento, chamamos uploadService.getFiles() para obter as informações dos arquivos e atribuimos o resultado a variavel fileInfos
  upload(): void {
    if(this.currentFile) {
      this.uploadService.upload(this.currentFile).subscribe({
        next: (event: any) => {
          if(event instanceof HttpResponse) {
            this.message = event.body.message;
            this.fileInfos = this.uploadService.getFiles();
          }
        },
        error: (err: any) => {
          console.log(err);

          if(err.error && err.error.message) {
            this.message = err.error.message;
          } else {
            this.message = 'Could not upload the file!';
          }
        },
        complete: () => {
          this.currentFile = undefined;
        }
      });
    }
  }

}
