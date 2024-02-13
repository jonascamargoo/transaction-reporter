import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { UploadService } from '../../services/upload.service';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { OperationService } from '../../services/operation.service';

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

  constructor(private uploadService: UploadService, private operationService: OperationService) {}

  ngOnInit(): void {
    this.fileInfos = this.operationService.listOperations();
  }

    // para executar tanto a selecao quanto o upload ao submeter o arquivo, ja que estou utilizando apenas um botão. Caso adicionar mais um botao (dropbox ou google cloud), remover esse metodo e utilizar apenas, juntamente com o
        // <input type='file' class="hidden" (change)="selectFile($event)"/>
        // <button (click)="upload()"></button>
      
    onFileChange(event: any): void {
      this.selectFile(event);
      this.upload();
    }

  // chamado quando um arquivo eh selecionado pelo usuario
  selectFile(event: any): void {
    const file = event.target.files[0];
    if(!this.isTxtFile(file)) {
      this.message = "O arquivo precisa ser do tipo .txt"
      return;
    }
    this.currentFile = file;
    
  }

  isTxtFile(file: File): boolean {
    const allowedExtensions = ['txt'];
    const extension = file.name.split('.').pop();
    return allowedExtensions.includes(extension!.toLowerCase());
  }

  
  // chamado quando o usuario clica no botao de upload
  // Usamos currentFile para acessar o arquivo atual como o primeiro item. Em seguida, chamamos o método uploadService.upload() no currentFile
  // Se a transmissão for concluida, o evento sera um objeto HttpResponse. Neste momento, chamamos uploadService.listOperations() para obter as informações dos arquivos e atribuimos o resultado a variavel fileInfos
  upload(): void {
    if(this.currentFile) {
      this.uploadService.upload(this.currentFile).subscribe({
        next: (event: any) => {
          if(event instanceof HttpResponse) {
            this.message = event.body.message;
            this.fileInfos = this.operationService.listOperations();
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
