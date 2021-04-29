export interface UserInterface{
    licenseId: string;
    name: string;
    surname: string;
    gender: string;
    phone: number;
    email: string;
    dni: string;
    nickname: string;
    securityQuestion: string;
    securityAnswer: string;
    roles: string[];
    imageFile: string;
    birthdate: string;
    belt: string;
    gym?: string;
    weight?: number;
    refereeRange?: string;
}
