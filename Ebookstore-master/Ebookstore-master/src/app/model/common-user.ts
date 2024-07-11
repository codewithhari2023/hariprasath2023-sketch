import { Role } from "./role";

export interface CommonUser {
    id: number;
    username: string;
    password: String;
    name:String;
    role: Role;
}
