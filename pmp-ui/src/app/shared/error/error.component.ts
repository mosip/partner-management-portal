import { Component, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.scss']
})
export class ErrorComponent{

  @Input() errorMessage: string;
  constructor(public route: ActivatedRoute, public router: Router) {
    this.route.params.subscribe((message => this.errorMessage = message.errorMessage));
  }

}
