import {UserInterface} from '../user/user.interface';

export interface PostInterface{
    idPost: number;
    author: UserInterface;
    title: string;
    body: string;
    timestamp: string;
    imageFile: string;
}
