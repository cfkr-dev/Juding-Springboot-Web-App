export class User {
    private _licenseId: string;

    get licenseId(): string {
        return this._licenseId;
    }

    set licenseId(value: string) {
        this._licenseId = value;
    }

    private _name: string;

    get name(): string {
        return this._name;
    }

    set name(value: string) {
        this._name = value;
    }

    private _surname: string;

    get surname(): string {
        return this._surname;
    }

    set surname(value: string) {
        this._surname = value;
    }

    private _gender: string;

    get gender(): string {
        return this._gender;
    }

    set gender(value: string) {
        this._gender = value;
    }

    private _phone?: number;

    get phone(): number {
        return this._phone;
    }

    set phone(value: number) {
        this._phone = value;
    }

    private _email: string;

    get email(): string {
        return this._email;
    }

    set email(value: string) {
        this._email = value;
    }

    private _birthdate: string;

    get birthdate(): string {
        return this._birthdate;
    }

    set birthdate(value: string) {
        this._birthdate = value;
    }

    private _dni: string;

    get dni(): string {
        return this._dni;
    }

    set dni(value: string) {
        this._dni = value;
    }

    private _imageFile: string;

    get imageFile(): string {
        return this._imageFile;
    }

    set imageFile(value: string) {
        this._imageFile = value;
    }

    private _nickname: string;

    get nickname(): string {
        return this._nickname;
    }

    set nickname(value: string) {
        this._nickname = value;
    }

    private _password: string;

    get password(): string {
        return this._password;
    }

    set password(value: string) {
        this._password = value;
    }

    private _securityQuestion: string;

    get securityQuestion(): string {
        return this._securityQuestion;
    }

    set securityQuestion(value: string) {
        this._securityQuestion = value;
    }

    private _securityAnswer: string;

    get securityAnswer(): string {
        return this._securityAnswer;
    }

    set securityAnswer(value: string) {
        this._securityAnswer = value;
    }

    private _belt: string;

    get belt(): string {
        return this._belt;
    }

    set belt(value: string) {
        this._belt = value;
    }

    private _gym?: string;

    get gym(): string {
        return this._gym;
    }

    set gym(value: string) {
        this._gym = value;
    }

    private _weight?: string;

    get weight(): string {
        return this._weight;
    }

    set weight(value: string) {
        this._weight = value;
    }

    private _refereeRange?: string;

    get refereeRange(): string {
        return this._refereeRange;
    }

    set refereeRange(value: string) {
        this._refereeRange = value;
    }

    private _roles: string[];

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

