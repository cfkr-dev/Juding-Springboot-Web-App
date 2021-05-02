import {Injectable} from '@angular/core';
import {NgbDateParserFormatter, NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';

@Injectable()
export class DatepickerService extends NgbDateParserFormatter {

    readonly DELIMITER = '/';

    parse(value: string, withHour: boolean = false): NgbDateStruct | null {
        if (value) {
            let date = value.split(this.DELIMITER);
            if (withHour) {
                let yearWithoutHour = date[2].split(' ');
                return {
                    day: parseInt(date[0], 10),
                    month: parseInt(date[1], 10),
                    year: parseInt(yearWithoutHour[0], 10)
                };
            } else {
                return {
                    day: parseInt(date[0], 10),
                    month: parseInt(date[1], 10),
                    year: parseInt(date[2], 10)
                };
            }
        }
        return null;
    }

    format(date: NgbDateStruct | null): string {
        return date ? (('0' + date.day).substr(-2)) + this.DELIMITER + (('0' + date.month).substr(-2)) + this.DELIMITER + date.year : '';
    }

    formatAlt(date: NgbDateStruct | null): string {
        return date ? (('0' + date.day).substr(-2)) + this.DELIMITER + (('0' + date.month).substr(-2)) + this.DELIMITER + date.year + ' 00:00' : '';
    }
}
