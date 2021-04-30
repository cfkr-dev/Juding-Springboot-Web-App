import {User} from '../user/user.model';

export interface Post {
  idPost?: number;
  author: User;
  title: string;
  body: string;
  imageFile?: string;
  timestamp: Date;
}
