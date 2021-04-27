export interface User {
  licenseId?: number;
  name: string;
  surname: string;
  gender: string;
  phone?: number;
  email: string;
  birthdate: string;
  dni: string;
  imageFile: string;
  nickname: string;
  password: string;
  securityQuestion: string;
  securityAnswer: string;
  belt: string;
  gym: string;
  weight: string;
  refereeRange: string;
  roles: string[];
}

