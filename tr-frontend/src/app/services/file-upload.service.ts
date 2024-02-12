import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Operation } from '../Operation';

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  upload(file:File): Observable<HttpEvent<any>> {
    const formData = new FormData();
    formData.append('file', file);

    const req = new HttpRequest('POST', `${this.baseUrl}/upload`, formData, {
      // response -> Processamento iniciado em background!
      responseType: 'text',
    });

    return this.http.request(req);
  }

  listOperations(): Observable<any> {
    return this.http.get(`${this.baseUrl}/operacoes`);
  }

  
}
