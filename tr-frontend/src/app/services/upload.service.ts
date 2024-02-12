import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadService {
  private baseUrl = 'http://localhost:8080/api/upload';

  constructor(private http: HttpClient) { }

  // Receive a remittance file, which will be processed into an operation file on the backend - ONE FILE
  upload(file:File): Observable<HttpEvent<any>> {
    const formData = new FormData();
    formData.append('file', file);

    const req = new HttpRequest('POST', `${this.baseUrl}/file`, formData, {
      // response -> Processamento iniciado em background!
      responseType: 'text',
    });

    return this.http.request(req);
  }
 
}
