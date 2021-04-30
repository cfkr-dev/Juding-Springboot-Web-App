import {User} from '../user/user.model';

export interface PostInterface{
    idPost: number;
    author: User;
    title: string;
    body: string;
    timestamp: string;
    imageFile: string;
}
