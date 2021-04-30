export class User {
  private _licenseId: string;
  private _name: string;
  private _surname: string;
  private _gender: string;
  private _phone?: number;
  private _email: string;
  private _birthdate: string;
  private _dni: string;
  private _imageFile: string;
  private _nickname: string;
  private _password: string;
  private _securityQuestion: string;
  private _securityAnswer: string;
  private _belt: string;
  private _gym?: string;
  private _weight?: string;
  private _refereeRange?: string;
  private _roles: string[];


    get licenseId(): string {
        return this._licenseId;
    }

    set licenseId(value: string) {
        this._licenseId = value;
    }

    get name(): string {
        return this._name;
    }

    set name(value: string) {
        this._name = value;
    }

    get surname(): string {
        return this._surname;
    }

    set surname(value: string) {
        this._surname = value;
    }

    get gender(): string {
        return this._gender;
    }

    set gender(value: string) {
        this._gender = value;
    }

    get phone(): number {
        return this._phone;
    }

    set phone(value: number) {
        this._phone = value;
    }

    get email(): string {
        return this._email;
    }

    set email(value: string) {
        this._email = value;
    }

    get birthdate(): string {
        return this._birthdate;
    }

    set birthdate(value: string) {
        this._birthdate = value;
    }

    get dni(): string {
        return this._dni;
    }

    set dni(value: string) {
        this._dni = value;
    }

    get imageFile(): string {
        return this._imageFile;
    }

    set imageFile(value: string) {
        this._imageFile = value;
    }

    get nickname(): string {
        return this._nickname;
    }

    set nickname(value: string) {
        this._nickname = value;
    }

    get password(): string {
        return this._password;
    }

    set password(value: string) {
        this._password = value;
    }

    get securityQuestion(): string {
        return this._securityQuestion;
    }

    set securityQuestion(value: string) {
        this._securityQuestion = value;
    }

    get securityAnswer(): string {
        return this._securityAnswer;
    }

    set securityAnswer(value: string) {
        this._securityAnswer = value;
    }

    get belt(): string {
        return this._belt;
    }

    set belt(value: string) {
        this._belt = value;
    }

    get gym(): string {
        return this._gym;
    }

    set gym(value: string) {
        this._gym = value;
    }

    get weight(): string {
        return this._weight;
    }

    set weight(value: string) {
        this._weight = value;
    }

    get refereeRange(): string {
        return this._refereeRange;
    }

    set refereeRange(value: string) {
        this._refereeRange = value;
    }

    get roles(): string[] {
        return this._roles;
    }

    set roles(value: string[]) {
        this._roles = value;
    }

    isCompetitor(): boolean {
        return this.roles.includes('C');
    }

    isReferee(): boolean {
        return this.roles.includes('R');
    }

    isAdmin(): boolean {
        return this.roles.includes('A');
    }
}

